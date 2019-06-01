package org.kllbff.magic.math.structs;

/**
 * <h3>Represents an ordinary quadratic equation</h3>
 * <p>
 *     Quadratic equation can have three parameters: A, B and C. A cannot be equals zero, unlike the other two. Solving of this equation is very simple task:
 *     calculated discriminant by formulae <code><i>b<sup>2</sup> - 4ac</i></code>. Then, if discriminant less than 0, equation has no solutions, if it equals zero,
 *     equation has two same roots, and if discriminant bigger than 0, equation has to different solutions.<br> 
 *     Any solution can be calculated as <pre>
 *     <code><i>
 *                   -b &#177; &#8730;D
 *          q1, q2 = <sup>___________</sup>
 *                   2ac
 *     </code></i>
 * </p>
 * 
 * @author Kirill Bogatikov
 * @version 1.0
 * @since 1.0
 */
public class QuadraticEquation {
    private double a, b, c;
    
    /**
     * Conctructs ready equation by given parameters
     * 
     * @param a coefficient, must be &#8800; 0
     * @param b coefficient
     * @param c coefficient
     * @throws RuntimeException if A parameter is zero
     */
    public QuadraticEquation(double a, double b, double c) {
        if(a == 0) {
            throw new RuntimeException("Algebraic exception: A coefficient cannot be zero");
        }
        
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    /**
     * Returns current value of A parameter
     * 
     * @return current value of A parameter
     */
    public double getA() {
        return a;
    }

    /**
     * Changes current value of A parameter to given
     * 
     * @param a new value of A parameter
     * @throws RuntimeException if A parameter is zero
     */
    public void setA(double a) {
        if(a == 0) {
            throw new RuntimeException("Algebraic exception: A coefficient cannot be zero");
        }
        
        this.a = a;
    }
    
    /**
     * Returns current value of B parameter
     * 
     * @return current value of B parameter
     */
    public double getB() {
        return b;
    }

    /**
     * Changes current value of B parameter to given
     * 
     * @param b new value of B parameter
     * @throws RuntimeException if B parameter is zero
     */
    public void setB(double b) {
        this.b = b;
    }

    /**
     * Returns current value of C parameter
     * 
     * @return current value of C parameter
     */
    public double getC() {
        return c;
    }

    /**
     * Changes current value of C parameter to given
     * 
     * @param c new value of A parameter
     * @throws RuntimeException if C parameter is zero
     */
    public void setC(double c) {
        this.c = c;
    }
    
    /**
     * Returns discriminant for this equation, calculated as <i>b<sup>2</sup> - 4ac</i>
     * @return discriminant for this equation
     */
    public double getDiscriminant() {
        return b * b - 4 * a * c;
    }
    
    /**
     * Returns an array of solutions or null
     * 
     * @return array of solutions or null
     */
    public double[] solve() {
        double d = getDiscriminant();
        if(d < 0) {
            return null; 
        }
        
        return new double[] { 
            (-b + Math.sqrt(d)) / (2 * a),
            (-b - Math.sqrt(d)) / (2 * a)      
        };
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(a).append("x2 ");
        
        if(b > 0) {
            builder.append("+");
        }
        builder.append(" ").append(b).append("x");
        
        if(c > 0) {
            builder.append("+");
        }
        builder.append(" ").append(c).append(" = 0");
        return builder.toString();
    }
}
