/**
 * 
 */
package validation;

/**
 * 
 */
public class ValidarIban {

	/**
     * Valida si un String es un IBAN español válido.
     * @param iban El código IBAN a validar.
     * @return true si es válido, false en caso contrario.
     */
    public static boolean validarIban(String iban) {
        if (iban == null) return false;

        // 1. Limpiar: quitar espacios y pasar a mayúsculas
        String ibanLimpio = iban.replaceAll("\\s+", "").toUpperCase();

        // 2. Validar formato básico: ES + 22 dígitos (total 24 caracteres)
        if (!ibanLimpio.matches("ES\\d{22}")) {
            return false;
        }

        // 3. Reordenar: mover los primeros 4 caracteres (ESXX) al final
        String reordenado = ibanLimpio.substring(4) + ibanLimpio.substring(0, 4);

        // 4. Convertir letras a números (A=10, B=11, ..., Z=35)
        StringBuilder sb = new StringBuilder();
        for (char c : reordenado.toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append(Character.getNumericValue(c));
            } else {
                sb.append(c);
            }
        }

        // 5. Aplicar módulo 97 (debe dar 1)
        try {
            java.math.BigInteger ibanNumerico = new java.math.BigInteger(sb.toString());
            return ibanNumerico.mod(java.math.BigInteger.valueOf(97)).intValue() == 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
}
