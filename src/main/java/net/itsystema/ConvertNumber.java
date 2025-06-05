package net.itsystema;

/**
 * Utility class for converting numbers to their text representation
 */
public class ConvertNumber {
    
    private static final String[] UNIDADES = {"", "UN ", "DOS ", "TRES ", "CUATRO ", "CINCO ", "SEIS ", "SIETE ", "OCHO ", "NUEVE "};
    private static final String[] DECENAS = {"", "DIEZ ", "VEINTE ", "TREINTA ", "CUARENTA ", "CINCUENTA ", "SESENTA ", "SETENTA ", "OCHENTA ", "NOVENTA "};
    private static final String[] CENTENAS = {"", "CIENTO ", "DOSCIENTOS ", "TRESCIENTOS ", "CUATROCIENTOS ", "QUINIENTOS ", "SEISCIENTOS ", "SETECIENTOS ", "OCHOCIENTOS ", "NOVECIENTOS "};
    private static final String[] ESPECIALES = {"", "ONCE ", "DOCE ", "TRECE ", "CATORCE ", "QUINCE ", "DIECISEIS ", "DIECISIETE ", "DIECIOCHO ", "DIECINUEVE "};
    
    /**
     * Converts a number to its text representation
     * 
     * @param numero The number to convert as a string
     * @param mayusculas Whether to return the result in uppercase
     * @param moneda The currency code (MXN, USD, EUR)
     * @return The text representation of the number
     */
    public String convertir(String numero, boolean mayusculas, String moneda) {
        try {
            String[] parts = numero.replace(',', '.').split("\\.");
            String entero = parts[0];
            String decimal = parts.length > 1 ? parts[1] : "00";
            
            // Ensure decimal part has 2 digits
            if (decimal.length() == 1) {
                decimal += "0";
            } else if (decimal.length() > 2) {
                decimal = decimal.substring(0, 2);
            }
            
            String resultado = "";
            if (!entero.equals("0")) {
                resultado = convertirNumero(entero);
            }
            
            // Add currency name
            String nombreMoneda = "";
            if (moneda.equals("MXN")) {
                nombreMoneda = "PESOS";
            } else if (moneda.equals("USD")) {
                nombreMoneda = "DÓLARES";
            } else if (moneda.equals("EUR")) {
                nombreMoneda = "EUROS";
            }
            
            if (!resultado.isEmpty()) {
                resultado += nombreMoneda + " " + decimal + "/100 ";
                
                if (moneda.equals("MXN")) {
                    resultado += "M.N.";
                } else if (moneda.equals("USD")) {
                    resultado += "USD";
                } else if (moneda.equals("EUR")) {
                    resultado += "EUR";
                }
            }
            
            return mayusculas ? resultado : resultado.toLowerCase();
        } catch (Exception e) {
            return "Error al convertir el número: " + e.getMessage();
        }
    }
    
    private String convertirNumero(String numero) {
        if (numero.equals("0")) {
            return "CERO ";
        }
        
        if (numero.equals("100")) {
            return "CIEN ";
        }
        
        StringBuilder result = new StringBuilder();
        int longitud = numero.length();
        
        // Process thousands
        if (longitud > 6) {
            String millones = numero.substring(0, longitud - 6);
            numero = numero.substring(longitud - 6);
            
            if (!millones.equals("1")) {
                result.append(convertirNumero(millones)).append("MILLONES ");
            } else {
                result.append("UN MILLON ");
            }
        }
        
        // Process thousands
        if (longitud > 3) {
            String miles = numero.length() > 3 ? numero.substring(0, numero.length() - 3) : numero;
            numero = numero.length() > 3 ? numero.substring(numero.length() - 3) : "";
            
            if (!miles.equals("1")) {
                result.append(convertirNumero(miles)).append("MIL ");
            } else {
                result.append("MIL ");
            }
        }
        
        // Process hundreds, tens and units
        if (!numero.isEmpty()) {
            int num = Integer.parseInt(numero);
            
            if (num > 99) {
                result.append(CENTENAS[num / 100]);
                num %= 100;
            }
            
            if (num > 0) {
                if (num < 10) {
                    result.append(UNIDADES[num]);
                } else if (num < 20) {
                    result.append(ESPECIALES[num - 10]);
                } else {
                    result.append(DECENAS[num / 10]);
                    int unidad = num % 10;
                    if (unidad > 0) {
                        result.append("Y ").append(UNIDADES[unidad]);
                    }
                }
            }
        }
        
        return result.toString();
    }
}