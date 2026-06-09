-- ============================================================
--  BancoPersonal — Script de datos de prueba
--  Versión 2.1  |  Contraseñas corregidas (cumplen ValidadorPassword)
-- ============================================================
--  INSTRUCCIONES:
--  1. Ejecuta primero 01_crear_banco_personal_v2.sql
--  2. Ejecuta este script a continuación
--  3. Verifica con las consultas del final
-- ============================================================
--  CREDENCIALES DE ACCESO:
--
--    username  │ contraseña │ rol
--    ──────────┼────────────┼─────────
--    carlos    │ banco1     │ CLIENTE
--    laura     │ banco2     │ CLIENTE
--    antonio   │ banco3     │ CLIENTE
--    admin     │ admin1     │ ADMIN
--
--  Todas cumplen el ValidadorPassword:
--    ✅ Entre 6 y 30 caracteres
--    ✅ Al menos una letra
--    ✅ Al menos un número
--    ✅ Sin espacios
--
--  Marta (dni 55555555E) NO tiene usuario → cliente sin acceso digital
-- ============================================================

USE BancoPersonal;

-- ============================================================
--  PASO 1 — Clientes
-- ============================================================
INSERT INTO cliente (nombre, apellidos, dni, email, telefono) VALUES
    ('Carlos',  'Romero Fuentes',  '11111111A', 'carlos.romero@mail.com',  '611000001'),
    ('Laura',   'Sanchez Mora',    '22222222B', 'laura.sanchez@mail.com',  '622000002'),
    ('Antonio', 'Perez Castillo',  '33333333C', 'antonio.perez@mail.com',  '633000003'),
    ('Sofia',   'Jimenez Rubio',   '44444444D', 'sofia.jimenez@mail.com',  '644000004'),
    ('Marta',   'Vidal Torres',    '55555555E', 'marta.vidal@mail.com',    '655000005');



-- ============================================================
--  PASO 2 — Usuarios
--
--  Contraseñas hasheadas con BCrypt strength=10.
--  Cada hash tiene exactamente 60 caracteres.
--
--    carlos  / banco1  → letra(s) + número ✅
--    laura   / banco2  → letra(s) + número ✅
--    antonio / banco3  → letra(s) + número ✅
--    admin   / admin1  → letra(s) + número ✅
-- ============================================================
INSERT INTO usuario (username, password, rol, id_cliente) VALUES
    ('carlos',
     '$2a$10$c2K.AkE6kFHHmr1555npuO6aAxYOABoT93il9q9ChtFOrRZNM/UCO',
     'CLIENTE',
     (SELECT id FROM cliente WHERE dni = '11111111A')),

    ('laura',
     '$2a$10$hWlW3Wr6gbwa8HwhOM1RK.76khlmOYRi8aHx4VuNMKXKxV0r.SU26',
     
     'CLIENTE',
     (SELECT id FROM cliente WHERE dni = '22222222B')),

    ('antonio',
     '$2a$10$nFaZufzvDkZAoslOuqMjrOCsQokD8u9fil6LnWtLgzkpLg4Wodpt2',
     'CLIENTE',
     (SELECT id FROM cliente WHERE dni = '33333333C')),

    ('admin',
     '$2a$10$cj13.aOviiI85aGyA8DyvuyxnLUAVmKHrdrBflV4MrzxlBBgrN2z.',
     'ADMIN',
     (SELECT id FROM cliente WHERE dni = '44444444D'));
     
-- ===========================================================
-- ===========================================================

INSERT INTO usuario (username, password, rol, id_cliente) VALUES
    ('carlos',
     'Banco1',
     'CLIENTE',
     (SELECT id FROM cliente WHERE dni = '11111111A')),

    ('laura',
     'Banco2',
     'CLIENTE',
     (SELECT id FROM cliente WHERE dni = '22222222B')),

    ('antonio',
     'Banco3',
     'CLIENTE',
     (SELECT id FROM cliente WHERE dni = '33333333C')),

    ('admin',
     'Banco4',
     'ADMIN',
     (SELECT id FROM cliente WHERE dni = '44444444D'));
     
     SELECT id, username, password, rol, id_cliente FROM usuario WHERE username = 'carlos';
	 select * from usuario;
     select * from cliente;
     select * from cuenta;
     drop table usuario;

-- ============================================================
--  PASO 3 — Cuentas
-- ============================================================
INSERT INTO cuenta (iban, tipo, saldo, id_cliente) VALUES

    ('ES91 2100 0418 4502 0005 1332', 'CORRIENTE', 0.00,
     (SELECT id FROM cliente WHERE dni = '11111111A')),

    ('ES80 2310 0001 1800 0001 2345', 'AHORRO',    0.00,
     (SELECT id FROM cliente WHERE dni = '11111111A')),

    ('ES76 0081 0166 2700 0100 0219', 'CORRIENTE', 0.00,
     (SELECT id FROM cliente WHERE dni = '22222222B')),

    ('ES79 2100 0813 6101 2345 6789', 'CORRIENTE', 0.00,
     (SELECT id FROM cliente WHERE dni = '33333333C')),

    ('ES58 0049 0001 5410 2057 0421', 'PLAZO_FIJO', 0.00,
     (SELECT id FROM cliente WHERE dni = '33333333C')),

    ('ES21 0049 6778 5021 2345 6789', 'REMUNERADA', 0.00,
     (SELECT id FROM cliente WHERE dni = '44444444D')),

    ('ES44 2100 0418 4502 0005 9999', 'CORRIENTE', 0.00,
     (SELECT id FROM cliente WHERE dni = '55555555E'));


-- ============================================================
--  PASO 4 — Movimientos y saldos iniciales
-- ============================================================
SET @cta_carlos_corriente = (SELECT id FROM cuenta WHERE iban = 'ES91 2100 0418 4502 0005 1332');
SET @cta_carlos_ahorro    = (SELECT id FROM cuenta WHERE iban = 'ES80 2310 0001 1800 0001 2345');
SET @cta_laura            = (SELECT id FROM cuenta WHERE iban = 'ES76 0081 0166 2700 0100 0219');
SET @cta_antonio          = (SELECT id FROM cuenta WHERE iban = 'ES79 2100 0813 6101 2345 6789');
SET @cta_antonio_plazo    = (SELECT id FROM cuenta WHERE iban = 'ES58 0049 0001 5410 2057 0421');
SET @cta_sofia            = (SELECT id FROM cuenta WHERE iban = 'ES21 0049 6778 5021 2345 6789');
SET @cta_marta            = (SELECT id FROM cuenta WHERE iban = 'ES44 2100 0418 4502 0005 9999');

-- Ingresos iniciales
INSERT INTO movimiento (tipo, importe, saldo_resultante, id_cuenta_origen, id_cuenta_destino, descripcion) VALUES
    ('INGRESO', 3000.00, 3000.00, @cta_carlos_corriente, NULL, 'Ingreso inicial'),
    ('INGRESO', 5000.00, 5000.00, @cta_carlos_ahorro,    NULL, 'Ingreso inicial'),
    ('INGRESO', 1500.00, 1500.00, @cta_laura,            NULL, 'Ingreso inicial'),
    ('INGRESO', 2200.00, 2200.00, @cta_antonio,          NULL, 'Ingreso inicial'),
    ('INGRESO', 8000.00, 8000.00, @cta_antonio_plazo,    NULL, 'Constitucion plazo fijo'),
    ('INGRESO', 4500.00, 4500.00, @cta_sofia,            NULL, 'Ingreso inicial'),
    ('INGRESO',  900.00,  900.00, @cta_marta,            NULL, 'Ingreso inicial');

UPDATE cuenta SET saldo = 3000.00 WHERE id = @cta_carlos_corriente;
UPDATE cuenta SET saldo = 5000.00 WHERE id = @cta_carlos_ahorro;
UPDATE cuenta SET saldo = 1500.00 WHERE id = @cta_laura;
UPDATE cuenta SET saldo = 2200.00 WHERE id = @cta_antonio;
UPDATE cuenta SET saldo = 8000.00 WHERE id = @cta_antonio_plazo;
UPDATE cuenta SET saldo = 4500.00 WHERE id = @cta_sofia;
UPDATE cuenta SET saldo =  900.00 WHERE id = @cta_marta;

-- Transferencia: Carlos → Laura (200€)
INSERT INTO movimiento (tipo, importe, saldo_resultante, id_cuenta_origen, id_cuenta_destino, descripcion) VALUES
    ('TRANSFERENCIA_ENVIADA',   200.00, 2800.00, @cta_carlos_corriente, @cta_laura, 'Transferencia a Laura Sanchez'),
    ('TRANSFERENCIA_RECIBIDA',  200.00, 1700.00, @cta_laura, @cta_carlos_corriente, 'Transferencia de Carlos Romero');

UPDATE cuenta SET saldo = 2800.00 WHERE id = @cta_carlos_corriente;
UPDATE cuenta SET saldo = 1700.00 WHERE id = @cta_laura;

-- Reintegro: Antonio retira 300€
INSERT INTO movimiento (tipo, importe, saldo_resultante, id_cuenta_origen, id_cuenta_destino, descripcion) VALUES
    ('REINTEGRO', 300.00, 1900.00, @cta_antonio, NULL, 'Reintegro en cajero');

UPDATE cuenta SET saldo = 1900.00 WHERE id = @cta_antonio;


-- ============================================================
--  VERIFICACION FINAL
-- ============================================================

-- Usuarios con sus contraseñas (para referencia del profesor)
SELECT username,
       rol,
       LEFT(password, 7)  AS algoritmo,
       LENGTH(password)   AS longitud_hash
FROM usuario
ORDER BY id;

-- Cuentas con saldo actual
SELECT c.nombre, c.apellidos,
       cu.iban, cu.tipo,
       cu.saldo,
       CASE WHEN u.id IS NOT NULL THEN 'Si' ELSE 'No' END AS acceso_digital
FROM cuenta cu
JOIN  cliente c ON cu.id_cliente = c.id
LEFT JOIN usuario u ON c.id = u.id_cliente
ORDER BY c.apellidos, cu.tipo;


