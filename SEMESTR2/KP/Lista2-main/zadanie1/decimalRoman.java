import java.util.regex.Pattern;

public class decimalRoman {
    private static String[] numerals = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };

    public static class decimalRomanException extends Exception {
        public decimalRomanException(String message) {
            super(message);
        }
    }

    public static int romanToDecimalConversion(String Roman) throws decimalRomanException {
        if (!isValidRomanNumber(Roman)) {
            throw new decimalRomanException("Invalid Roman number: " + Roman);
        }
        int decimal = 0;
        int lastNumber = 0;
        for (int i = Roman.length() - 1; i >= 0; i--) {
            char ch = Roman.charAt(i);
            int number = RomanToDecimal(ch);
            if (number < lastNumber) {
                decimal -= number;
            } else {
                decimal += number;
            }
            lastNumber = number;
        }
        return decimal;
    }

    public static String decimalToRomanConversion(int decimal) throws decimalRomanException {
        if (decimal < 1 || decimal > 3999) {
            throw new decimalRomanException("Number out of range");
        }
        String roman = "";
        for (int i = numerals.length - 1; i >= 0; i--) {
            while (decimal >= DecimalToRoman(i)) {
                roman += numerals[i];
                decimal -= DecimalToRoman(i);
            }
        }
        return roman;
    }

    private static int RomanToDecimal(char ch) throws decimalRomanException {
        switch (ch) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: throw new decimalRomanException("Invalid character in Roman numeral");
        }
    }

    private static int DecimalToRoman(int i) throws decimalRomanException {
        if (i < 0 || i >= numerals.length) {
            throw new decimalRomanException("Invalid index");
        }
        switch (numerals[i]) {
            case "I": return 1;
            case "IV": return 4;
            case "V": return 5;
            case "IX": return 9;
            case "X": return 10;
            case "XL": return 40;
            case "L": return 50;
            case "XC": return 90;
            case "C": return 100;
            case "CD": return 400;
            case "D": return 500;
            case "CM": return 900;
            case "M": return 1000;
            default: throw new decimalRomanException("Invalid Roman numeral");
        }
    }

    private static final String ROMAN_REGEX = 
        "^(M{0,3})" +          
        "(CM|CD|D{0,1}C{0,3})" + 
        "(XC|XL|L{0,1}X{0,3})" + 
        "(IX|IV|V{0,1}I{0,3})$";  

    public static boolean isValidRomanNumber(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        
        for (char ch : input.toCharArray()) {
            if ("IVXLCDM".indexOf(ch) == -1) {
                return false;
            }
        }
        
        if (input.contains("IIII") || input.contains("VV") || input.contains("XXXX") ||
            input.contains("LL") || input.contains("CCCC") || input.contains("DD") || input.contains("MMMM")) {
            return false;
        }
        
        if (input.contains("IL") || input.contains("IC") || input.contains("ID") || input.contains("IM") ||
            input.contains("VX") || input.contains("VL") || input.contains("VC") || input.contains("VD") || input.contains("VM") ||
            input.contains("XD") || input.contains("XM") || input.contains("LC") || input.contains("LD") || input.contains("LM") ||
            input.contains("DM") || input.contains("IXI") || input.contains("IVI") || input.contains("I")) {
            return false;
        }
        
        return Pattern.matches(ROMAN_REGEX, input);
    }

    public static void main(String[] args) {
        for (String arg : args) {
            try {
                try {
                    int decimal = Integer.parseInt(arg);
                    String roman = decimalToRomanConversion(decimal);
                    System.out.println("Decimal: " + decimal + " -> Roman number: " + roman);
                } catch (NumberFormatException e) {
                    try {
                        int decimal = romanToDecimalConversion(arg);
                        System.out.println("Roman number: " + arg + " -> Decimal: " + decimal);
                    } catch (decimalRomanException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                } catch (decimalRomanException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Unexpected error occurred with input '" + arg + "': " + e.getMessage());
            }
        }
    }
}