package org.kllbff.magic.math.structs;

import org.kllbff.magic.math.VarargsMath;

/**
 * <h3>Represents an ordinary fraction by two whole values: numerator and denominator</h3>
 * <p>The numerator and denominator are represented by two 64-bit integer values (<code>long</code>).</p>
 * <p>This class extends {@link java.lang.Number Number} class, therefore it implements some methods for numbers:
 *     <ul>
 *          <li>{@link #intValue()} returns a whole part of fraction - result of integer division numerator on denominator;</li>
 *          <li>{@link #doubleValue()} returns a decimal representation of fraction;</li>
 *          <li>{@link #floatValue()} returns a decimal represetation of fraction by 32-bit <code>float</code> value;</li>
 *          <li>{@link #longValue()} returns a whole part of fraction - result of integer division numerator on denominator - by 64-bit <code>long</code> value;</li>
 *     </ul><br>
 *     but has some own methods:
 *     <ul>
 *          <li>{@link #sum(Fraction)} returns a new instance of Fraction, respresenting sum of two fractions - this and specified;</li>
 *          <li>{@link #sub(Fraction)} returns the result of subtracting the specified fraction from this fraction as new instance of Fraction;</li>
 *          <li>{@link #mul(Fraction)} returns a new instance of Fraction, respresenting the result of the multiplication of two fractions - this and specified;</li>
 *          <li>{@link #div(Fraction)} returns the result of division the this fraction on the specfied fraction as new instance of Fraction;</li>
 *          <li>getters ({@link #getNumerator()}, {@link #getDenominator()}) and setters ({@link #setNumerator(long)}, {@link #setDenominator(long)}) for numerator and denominator;</li>
 *          <li>{@link #reduce()} reduces this fraction;</li>
 *          <li>{@link #reverse()} returns an inverted fraction, created by change of numerator and denominator in places.</li>
 *     </ul>
 * 
 * 
 * @author Kirill Bogatikov
 * @version 1.0
 * @since 1.0
 */
public class Fraction extends Number {
    public static final long NUMBER_DEFAULT_DENOMINATOR = 100_000_000;
    private static final long serialVersionUID = -1702449358862740938L;
    private long n, d;
    
    /**
     * Initializes object by given values for numerator and denominator
     * 
     * @param n numerator value
     * @param d denominator value
     * @throws ArithmeticException if denominator equals zero
     */
    public Fraction(long n, long d) {
        if(d == 0) {
            throw new ArithmeticException("/ by zero");
        }
        
        this.n = n;
        this.d = d;
        reduce();
    }
    
    /**
     * Initializes object by using given value as numerator and 1 as denominator
     * <p>Example:<br>
     * Any whole number can be represented as ordinary fraction by dividing value on 1:
     * <pre><code>
     *     long a = 5;
     *     Fraction f = new Fraction(a);
     *     &#47;*
     *      * f now equals 5/1
     *      *&#47;
     * </code></pre>
     * 
     * 
     * @param i given whole <code>long</code> value
     * @throws ArithmeticException if denominator equals zero
     */
    public Fraction(long i) {
        this(i, 1);
    }
    
    /**
     * Initializes object by using given value, multiplied by {@link #NUMBER_DEFAULT_DENOMINATOR} as numerator and {@link #NUMBER_DEFAULT_DENOMINATOR} as denominator
     * 
     * @param def given value, represented by any child of Number class
     */
    public Fraction(Number def) {
        this((int)(def.doubleValue() * NUMBER_DEFAULT_DENOMINATOR), NUMBER_DEFAULT_DENOMINATOR);
    }
    
    /**
     * Creates 'empty' fraction with value zero: <code>0/1</code>
     */
    public Fraction() {
        this(0);
    }
    
    /**
     * Reduces this fraction by dividing <i>numerator</i> and <i>denominator</i> on equal numbers
     * <p>
     *     This method calculates the <b>GCD</b> - <i>greates common divisor</i> of two values - fraction's numerator and denominator. GCD calculated by {@link VarargsMath#gcd(long, long)}<br>
     *     Then numerator and denominator divides on GCD value and saves into object 
     * </p> 
     * <p>
     *     This method called automatically only at constructor on initializing fraction, because calculating GCD is slow and resource-intensive operation.<br>
     *     But it is recommended to call this method manually after every operations over this fraction and other BIG or unknown size fraction
     * </p>
     */
    public void reduce() {
        if(n != 1 && d != 1) {
            long gcd = VarargsMath.gcd(n, d);
            n /= gcd;
            d /= gcd;
        }        
    }
    
    /**
     * Sets a new value for <i>numerator</i>
     * 
     * @param n new numerator value
     */
    public void setNumerator(long n) {
        this.n = n;
    }
    
    /**
     * Sets a new value for <i>denominator</i>
     * 
     * @param d new denominator value
     */
    public void setDenominator(long d) {
        this.d = d;
    }
    
    /**
     * Returns value of fraction's current numerator
     *  
     * @return value of fraction's current numerator
     */
    public long getNumerator() {
        return n;
    }
    
    /**
     * Returns value of fraction's current denominator
     *  
     * @return value of fraction's current denominator
     */
    public long getDenominator() {
        return d;
    }
    
    /**
     * Calculates a sum of this fraction with specified fraction 
     * 
     * @param other specified fraction
     * @return a new instance of Fraction, respresenting sum of two fractions - this and specified
     */
    public Fraction sum(Fraction other) {
        long fn = this.n * other.d + other.n * this.d;
        long fd = this.d * other.d;
        
        return new Fraction(fn, fd);
    }
    
    /**
     * Calculates a difference between this fraction with specified fraction 
     * 
     * @param other specified fraction
     * @return the result of subtracting the specified fraction from this fraction as new instance of Fraction
     */
    public Fraction sub(Fraction other) {
        long fn = this.n * other.d - other.n * this.d;
        long fd = this.d * other.d;
        
        return new Fraction(fn, fd);
    }
    
    /**
     * Calculates a product of this fraction and specified fraction 
     *  
     * @param other specified fraction
     * @return product of this fraction and specified fraction, represented by new instande of Fraction
     */
    public Fraction mul(Fraction other) {
        return new Fraction(this.n * other.n, this.d * other.d);
    }
    
    /**
     * Calculates a quotient of this fraction and specified fraction by multipling this fraction on inverted other
     * <p>Example:<pre><code>
     *     1   3   1   4   4
     *     - / - = - * - = -
     *     2   4   2   3   6
     * </code></pre>
     * 
     * @param other specified fraction
     * @return a quotient of this fraction and specified fraction by multipling this fraction on inverted other
     */
    public Fraction div(Fraction other) {
        return new Fraction(this.n * other.d, this.d * other.n);
    }
    
    /**
     * Returns inverted version of this fraction as new instance of Fraction
     * <p>
     *     Fraction A is the inverted version of the fraction B if the numerator A is denominator B, and the denominator A is the numerator B
     * </p>
     * @return inverted version of this fraction as new instance of Fraction
     */
    public Fraction reverse() {
        return new Fraction(d, n);
    }
    
    /**
     * @return result of dividing numerator by denominator, casted to <code>int</code>
     */
    @Override
    public int intValue() {
        return (int)(n / d);
    }

    /**
     * @return result of dividing numerator by denominator
     */
    @Override
    public long longValue() {
        return n / d;
    }

    /**
     * @return result of dividing casted to float numerator by denominator
     */
    @Override
    public float floatValue() {
        return ((float)n) / d;
    }

    /**
     * @return result of dividing casted to double numerator by denominator
     */
    @Override
    public double doubleValue() {
        return ((double)n) / d;
    }

    /**
     * Returns a string representation of the object
     * <p>
     *     A string representation builded for maximal human-readable format:
     *     &#60;whole part&#62;(&#60;numerator&#62;/&#60;denominator&#62;).
     * </p>
     * <dl>
     *     <dt>Whole part</dt>
     *     <dd>A whole part calculated by dividing numerator on denominator. If whole equals zero, it does not added to String</dd>
     *     <dt>Numerator</dt>
     *     <dd>In the case of an ordinary fraction without whole part, a simple numerator will be used. If whole part does not equals zero, used 
     *         result of subtraction whole part from numerator</dd>
     *      <dt>Denominator</dt>
     *      <dd>Always fraction's denomintor without changed</dd>
     * </dl>
     * <p>Example:
     * <pre><code>
     *     Fraction a = new Fraction(10, 5);
     *     String a_s = a.toString(); //contains "2"
     *     
     *     Fraction b = new Fraction(4, 3);
     *     String b_s = b.toString(); //contains "1(1/3)"
     *     
     *     Fraction c = new Fraction(1, 3);
     *     String c_s = c.toString(); //contains "1/3"
     * </code></pre>
     */
    @Override
    public String toString() {
        if(n == 0) {
            return "0";
        }
        
        long w = n / d;
        long ln = n;
        
        StringBuilder r = new StringBuilder();
        if(w != 0) {
            r.append(w);
            ln -= w * d;
        } 
        if(n % d != 0) {
            r.append("(").append(ln).append("/").append(d).append(")");
        }
        return r.toString();
    }
    
    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (int) (d ^ (d >>> 32));
        result = 31 * result + (int) (n ^ (n >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Fraction other = (Fraction) obj;
        return d != other.d || n != other.n;
    }
}
