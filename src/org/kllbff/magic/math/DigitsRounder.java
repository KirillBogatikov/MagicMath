package org.kllbff.magic.math;

public class DigitsRounder {
    public static final int MAX_DIGITS_COUNT = 16;
    
    /**
     * Returns a value, rounded to specified number of decimal digits
     * <p>If specified digits count greater than {@value #MAX_DIGITS_COUNT}, returned initial value</p>
     * <p>If specified digits count is negative number, returns {@link Double#NEGATIVE_INFINITY} value</p>
     * 
     * @param value initial value
     * @param digitsNumber specified number of decimal digit
     * @return value, rounded to specified number of decimal digits
     */
    public static final double round(double value, int digitsNumber) {
        if(digitsNumber >= MAX_DIGITS_COUNT) {
            return value;
        }
        if(digitsNumber < 0) {
            return value;
        }
        if(digitsNumber == 0) {
            return Math.round(value);                    
        }
        
        double multiplexor = Math.pow(10, digitsNumber);
        
        return Math.round(value * multiplexor) / multiplexor;
    }
}
