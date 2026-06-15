-- ============================================================
--  BancoPersonal — Script de creación de base de datos
--  Versión 2.0  |  Fase 2: cuentas y movimientos
-- ============================================================
--  HISTORIAL DE VERSIONES
--  v1.0 → tablas cliente y usuario (login)
--  v2.0 → tablas cuenta y movimiento (operativa bancaria)
-- ============================================================
--  INSTRUCCIONES PARA EL ALUMNO:
--  1. Abre MySQL Workbench
--  2. Ejecuta este script completo con Ctrl+Shift+Enter
--  3. Refresca el panel de esquemas
--  Resultado esperado: BancoPersonal con 4 tablas
-- ============================================================

DROP DATABASE IF EXISTS BancoPersonal;

CREATE DATABASE BancoPersonal
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_spanish_ci;

USE BancoPersonal;


-- ============================================================
--  TABLA: cliente
--
--  Almacena los datos personales e identificativos.
--  Existe con independencia de si tiene acceso digital o no.
--  Un cliente puede tener una o varias cuentas bancarias.
-- ============================================================
CREATE TABLE cliente (
    id              INT             NOT NULL AUTO_INCREMENT,
    nombre          VARCHAR(100)    NOT NULL,
    apellidos       VARCHAR(150)    NOT NULL,
    dni             VARCHAR(9)      NOT NULL,
    email           VARCHAR(100),
    telefono        VARCHAR(15),
    fecha_alta      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_cliente        PRIMARY KEY (id),
    CONSTRAINT uq_cliente_dni    UNIQUE (dni),
    CONSTRAINT uq_cliente_email  UNIQUE (email),

    -- Búsquedas por DNI en fases posteriores
    INDEX idx_cliente_dni (dni)
);


-- ============================================================
--  TABLA: usuario
--
--  Credenciales de acceso digital al sistema.
--  No todos los clientes tienen usuario: solo aquellos que
--  han activado el acceso online pueden operar sus cuentas
--  desde la aplicación.
--
--  Relación: un cliente tiene cero o un usuario.
--            un usuario pertenece a exactamente un cliente.
-- ============================================================
CREATE TABLE usuario (
    id              INT             NOT NULL AUTO_INCREMENT,
    username        VARCHAR(50)     NOT NULL,
    password        VARCHAR(60)     NOT NULL,
                                    -- VARCHAR(60): BCrypt genera hashes de exactamente
                                    -- 60 caracteres. Ni uno más, ni uno menos.
                                    -- Declarar la longitud exacta es más honesto
                                    -- y eficiente que usar VARCHAR(255).
    rol             ENUM('CLIENTE','ADMIN')
                                    NOT NULL DEFAULT 'CLIENTE',
    id_cliente      INT             NOT NULL,

    CONSTRAINT pk_usuario           PRIMARY KEY (id),
    CONSTRAINT uq_usuario_username  UNIQUE (username),

    -- Si se elimina el cliente se elimina también su usuario.
    -- No tiene sentido mantener credenciales sin titular.
    CONSTRAINT fk_usuario_cliente
        FOREIGN KEY (id_cliente) REFERENCES cliente(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    -- Índice sobre username: se consulta en CADA login.
    -- Sin índice → recorrido completo de la tabla.
    -- Con índice → búsqueda directa en milisegundos.
    INDEX idx_usuario_username (username)
);


-- ============================================================
--  TABLA: cuenta
--
--  Cada fila es una cuenta bancaria perteneciente a un cliente.
--  Un cliente puede tener una o varias cuentas de cualquier tipo.
--
--  TIPOS DE CUENTA:
--    CORRIENTE   → operativa diaria, sin rentabilidad
--    AHORRO      → para acumular fondos
--    PLAZO_FIJO  → dinero inmovilizado durante un período pactado
--    REMUNERADA  → corriente con rentabilidad por saldo medio
--
--  BORRADO LÓGICO:
--    Las cuentas nunca se eliminan físicamente.
--    El campo `activa` permite cerrarlas conservando el historial.
--    Borrar una cuenta con movimientos está impedido por la FK
--    de la tabla movimiento (ON DELETE RESTRICT).
-- ============================================================

SELECT id, iban, tipo, saldo, fecha_apertura, activa, id_cliente FROM cuenta where activa = 1;
SELECT id, iban, tipo, saldo, fecha_apertura, activa, id_cliente FROM cuenta where activa = 0;


UPDATE cuenta SET activa = 1 WHERE id = 1;
CREATE TABLE cuenta (
    id              INT             NOT NULL AUTO_INCREMENT,
    iban            VARCHAR(34)     NOT NULL,
                                    -- Formato estándar ISO 13616
                                    -- España: ES + 2 dígitos control + 20 dígitos = 24 chars
                                    -- VARCHAR(34) da margen para cualquier país de la UE
    tipo            ENUM('CORRIENTE','AHORRO','PLAZO_FIJO','REMUNERADA')
                                    NOT NULL DEFAULT 'CORRIENTE',
    saldo           DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
                                    -- DECIMAL y no DOUBLE: precisión exacta para dinero.
                                    -- DOUBLE acumula errores de coma flotante:
                                    --   0.1 + 0.2 = 0.30000000000000004
                                    -- DECIMAL(15,2) garantiza exactitud hasta 0.01€
                                    -- y soporta saldos de hasta 9.999.999.999.999,99€
    fecha_apertura  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activa          TINYINT(1)      NOT NULL DEFAULT 1,
                                    -- 1 = cuenta activa  |  0 = cuenta cerrada
    id_cliente      INT             NOT NULL,

    CONSTRAINT pk_cuenta            PRIMARY KEY (id),
    CONSTRAINT uq_cuenta_iban       UNIQUE (iban),

    CONSTRAINT fk_cuenta_cliente
        FOREIGN KEY (id_cliente) REFERENCES cliente(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    -- Búsqueda por IBAN: se usa en cada transferencia
    INDEX idx_cuenta_iban     (iban),

    -- Búsqueda de todas las cuentas de un cliente
    INDEX idx_cuenta_cliente  (id_cliente)
);


-- ============================================================
--  TABLA: movimiento
--
--  Registra cada operación realizada sobre una cuenta.
--  Es el libro contable de la aplicación: NUNCA se modifica
--  ni se borra un movimiento ya registrado.
--
--  TIPOS DE MOVIMIENTO:
--    INGRESO                → entrada de dinero externo
--    REINTEGRO              → salida de dinero
--    TRANSFERENCIA_ENVIADA  → salida hacia otra cuenta
--    TRANSFERENCIA_RECIBIDA → entrada desde otra cuenta
--
--  DOBLE APUNTE EN TRANSFERENCIAS:
--    Cuando Carlos transfiere 100€ a Laura se insertan DOS filas:
--      fila 1 → TRANSFERENCIA_ENVIADA,   origen=Carlos,  destino=Laura
--      fila 2 → TRANSFERENCIA_RECIBIDA,  origen=Laura,   destino=Carlos
--    Así cada cliente ve su extracto completo sin JOINs complejos.
--
--  SALDO_RESULTANTE:
--    Guarda el saldo DESPUÉS de cada operación.
--    Permite mostrar el extracto con el saldo en cada línea
--    sin recalcular toda la historia cada vez.
--
--  ON DELETE EN LAS FOREIGN KEYS — dos comportamientos distintos
--  porque las dos FKs tienen semántica diferente:
--
--    id_cuenta_origen  → RESTRICT:
--      No permite borrar una cuenta que tenga movimientos.
--      Refuerza el borrado lógico: si quieres "borrar" una cuenta,
--      primero ciérrala (activa = 0). Si tiene movimientos, el motor
--      te lo impide antes de que llegues a intentarlo.
--
--    id_cuenta_destino → SET NULL:
--      Si la cuenta destino de una transferencia se cierra o elimina,
--      el movimiento histórico se conserva con destino a NULL.
--      Representa "transferencia enviada a una cuenta ya cerrada".
--      Es información válida: la operación ocurrió.
-- ============================================================

 
CREATE TABLE movimiento (
    id                  INT             NOT NULL AUTO_INCREMENT,
    tipo                ENUM('INGRESO','REINTEGRO',
                             'TRANSFERENCIA_ENVIADA','TRANSFERENCIA_RECIBIDA')
                                        NOT NULL,
    importe             DECIMAL(15,2)   NOT NULL,
    saldo_resultante    DECIMAL(15,2)   NOT NULL,
    fecha_operacion     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descripcion         VARCHAR(255),
    id_cuenta_origen    INT             NOT NULL,
    id_cuenta_destino   INT,            -- NULL en ingresos y reintegros
	
    CONSTRAINT pk_movimiento             PRIMARY KEY (id),

    CONSTRAINT fk_mov_cuenta_origen
        FOREIGN KEY (id_cuenta_origen)  REFERENCES cuenta(id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_mov_cuenta_destino
        FOREIGN KEY (id_cuenta_destino) REFERENCES cuenta(id)
        ON DELETE SET NULL,

    -- Búsqueda de movimientos de una cuenta (extracto)
    INDEX idx_mov_cuenta_origen  (id_cuenta_origen),

    -- Búsqueda de transferencias recibidas desde una cuenta
    INDEX idx_mov_cuenta_destino (id_cuenta_destino),

    -- Índice compuesto para la consulta más habitual del extracto:
    --   SELECT * FROM movimiento
    --   WHERE id_cuenta_origen = ?
    --   ORDER BY fecha_operacion DESC
    --
    -- Sin este índice: MySQL filtra rápido por id_cuenta_origen
    -- pero luego ordena TODO el resultado en memoria.
    --
    -- Con este índice: el motor resuelve filtro Y orden en una
    -- sola pasada porque los datos ya están ordenados por fecha
    -- dentro de cada cuenta en el propio índice.
    --
    -- REGLA: en un índice compuesto, primero la columna de filtro
    -- igualdad (=) y luego la columna de ordenación (ORDER BY).
    -- Invertirlo no produciría el mismo efecto.
    INDEX idx_mov_extracto (id_cuenta_origen, fecha_operacion)
);


-- ============================================================
--  RESUMEN VISUAL DEL ESQUEMA
-- ============================================================
--
--  cliente ───────────────────────────────────────────────
--    id, nombre, apellidos, dni, email, telefono, fecha_alta
--         │
--         ├── usuario  (0 ó 1 por cliente)
--         │     id, username, password(BCrypt), rol
--         │     id_cliente ──FK CASCADE──► cliente.id
--         │
--         └── cuenta   (1 ó N por cliente)
--               id, iban, tipo, saldo, activa, fecha_apertura
--               id_cliente ──FK CASCADE──► cliente.id
--                    │
--                    └── movimiento  (0 ó N por cuenta)
--                          id, tipo, importe, saldo_resultante
--                          fecha_operacion, descripcion
--                          id_cuenta_origen  ──FK RESTRICT──► cuenta.id
--                          id_cuenta_destino ──FK SET NULL──► cuenta.id
--
-- ============================================================
--  CADENA DE INTEGRIDAD REFERENCIAL
-- ============================================================
--
--  Acción              Efecto en cascada
--  ──────────────────────────────────────────────────────────
--  DELETE cliente   →  CASCADE  →  DELETE usuario
--                   →  CASCADE  →  DELETE cuenta (solo si no tiene movimientos)
--  DELETE cuenta    →  RESTRICT →  ERROR si tiene movimientos
--                   →  SET NULL →  movimiento.id_cuenta_destino = NULL
--
-- ============================================================
