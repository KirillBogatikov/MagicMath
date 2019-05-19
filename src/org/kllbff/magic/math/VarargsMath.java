package org.kllbff.magic.math;

/**
 * <h3>Provides some methods, similar to {@link Math} class's methods</h3>
 * <p>All methods implemented to work with varargs - an array of unlimited length<p>
 * 
 * @author Kirill Bogatikov
 * @since 1.0
 * @version 1.0
 */
public final class VarargsMath {
    /**
     * Returns a minimal value from given 
     * <p>Uses linear search algotihm</p>
     * 
     * @param numbers a varargs sequence of given numbers 
     * @return minimal value from sequence
     */
    public static Number min(Number... numbers) {
        Number min = Double.MAX_VALUE;
        for(Number number : numbers) {
            if(number.doubleValue() < min.doubleValue()) {
                min = number;
            }
        }
        return min;
    }
    
    /**
     * Returns a maximal value from given 
     * <p>Uses linear search algotihm</p>
     * 
     * @param numbers a varargs sequence of given numbers 
     * @return maximal value from sequence
     */
    public static Number max(Number... numbers) {
        Number max = -Double.MAX_VALUE;
        for(Number number : numbers) {
            if(number.doubleValue() > max.doubleValue()) {
                max = number;
            }
        }
        return max;
    }
    
    /**
     * Returns a avaerage value, calculated from given values 
     * <p>Uses linear algotihm: sum of all divided on count</p>
     * 
     * @param numbers a varargs sequence of given numbers 
     * @return average value from sequence
     */
    public static double average(Number... numbers) {
        double average = 0.0;
        for(Number number : numbers) {
            average += number.doubleValue();
        }
        return average / numbers.length;
    }
    
    /**
     * Returns a Great Common Divisor for some long integer values
     * <p>Uses next algorithm: GCD(a, b, c) = GCD(GCD(a, b), c)</p>
     * <p>For each pair <i>value - last GCD</i> used {@link #gcd(long, long)} method</p>
     * 
     * @param numbers a varargs sequence of given numbers  
     * @return GCD for all values from sequence
     */
    public static long gcd(long... numbers) {
        long gcd = numbers[0];
        for(int i = 1; i < numbers.length; i++) {
            gcd = gcd(gcd, numbers[i]);
        }
        return gcd;
    }
    
    /**
     * Returns a Great Common Divisor for two long integer values
     * <p>Uses residual algorithm without recursion</p>
     * 
     * @param a first long integer value
     * @param b second long integer value
     * @return a Great Common Divisor for two long integer values
     */
    public static long gcd(long a, long b) {
        if(a == 0 || b == 0) {
            return 1;
        }
        int k = 1;
        if(a < 0) {
            a = -a;
        }
        if(b < 0) {
            b = -b;
            if(a < 0) k = -1;
        }
        
        long t;
        while (b != 0) {
            t = b;
            b = a % b;
            a = t;
        }
        return a * k;
    }
}
