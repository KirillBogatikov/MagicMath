package org.kllbff.magic.math;

import static org.kllbff.magic.math.DigitsRounder.round;

/**
 * Provides methods to calculating some fixed values with relatively low accuracy:
 * <ul>
 *     <li>Sine of angle: {@link #sin(double)}</ul>
 *     <li>Cosine of angle: {@link #cos(double)}</ul>
 * </ul>
 * 
 * Also class contains some previously calculated values, such as
 * <ul>
 *     <li>PI / 2 - radian measure of 90 degrees angle;</li>
 *     <li>PI / 4 - radian measure of 45 degrees angle;</li>
 *     <li>&#8730;2 - square root from 2;</li>
 *     <li>&#8730;3 - square root from 3;</li>
 * </ul> 
 * 
 * Accuracy of calculations fixed by value of {@link DigitsRounder#MAX_DIGITS_COUNT}, as default it is {@value DigitsRounder#MAX_DIGITS_COUNT}} 
 * 
 * @author Kirill Bogatikov
 * @version 1.0
 * @since 1.0
 */
public class PlanimetryValues {
    public static final double PI2 = Math.PI / 2,
                               PI4 = Math.PI / 4;
    public static final double SQRT_OF_TWO   = Math.sqrt(2),
                               SQRT_OF_THREE = Math.sqrt(3);
    
    /**
     * Returns sinus of specified angle
     * <p>Default {@link Math#sin(double)} method can return vary accuracy value, such as <code>6.123031769111886e-17</code> for sin(&#960; / 2).
     *    This method returns fixed values for basic angles: 0, 90, 180 and 270 degrees. Thus, you no longer need to manually keep track of and rounding the value for the most proper use and display</p>
     * 
     * @param angle specified angle, in radians
     * @return sinus of specified angle
     */
    public static double cos(double angle) {
        int degrees = (int)round(Math.toDegrees(round(angle, 5)), 0);
        degrees = (degrees + 360) % 360;
        
        switch(degrees) {
            case 0: return 1;
            case 180: return -1;
            case 90: case 270: return 0;
            default: return round(Math.cos(angle), 10);
        }
    }
    
    /**
     * Returns cosine of specified angle
     * <p>Default {@link Math#sin(double)} method can return vary accuracy value, such as <code>-6.123031769111886e-17</code> for cos(&#960;).
     *    This method returns fixed values for basic angles: 0, 90, 180 and 270 degrees. Thus, you no longer need to manually keep track of and rounding the value for the most proper use and display</p>
     * 
     * @param angle specified angle, in radians
     * @return cosine of specified angle
     */
    public static double sin(double angle) {
        int degrees = (int)round(Math.toDegrees(round(angle, 5)), 0);
        degrees = (degrees + 360) % 360;
        
        switch(degrees) {
            case 90: return 1;
            case 270: return -1;
            case 0: case 180: return 0;
            default: return round(Math.sin(angle), 10);
        }
    }
}
