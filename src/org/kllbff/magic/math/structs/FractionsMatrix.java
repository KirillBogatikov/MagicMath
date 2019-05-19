package org.kllbff.magic.math.structs;

/**
 * <h3>Represents double values matrix</h3>
 * <p>Matrix - is a group of numbers, placed into table with fixed width and height. This implentation uses array of arrays of Fraction objects to store given numbers</p>
 * <p>If you need to get maximum speed - use {@link Matrix}. This matrix much accuracy, but is highly slowly. However, it is optimal solution for some algorithms,
 *    such as {@link org.kllbff.magic.math.algothms.GaussAlgorithm Gauss} and {@link org.kllbff.magic.math.algothms.KramerAlgorithm Kramer} algorithms</p>
 * <p>FractionsMatrix has basic methods:
 *     <ul>
 *          <li>getters {@link #get(int, int)} and setters {@link #set(int, int, Fraction)} for each cell</li>
 *          <li>{@link #getMinorFor(int, int)} - for calculating addition minor for specified cell</li>
 *          <li>{@link #getDeterminant()} - for calculating matrix's determinant</li>
 *     </ul>
 *     and some specific methods that facilitate the work with matrix:
 *     <ul>
 *          <li>{@link #strikeOutColumn(int)}</li>
 *          <li>{@link #strikeOutRow(int)}</li>
 *          <li>{@link #addColumns(int)}</li>
 *          <li>{@link #addRows(int)}</li>
 *          <li>{@link #insertColumn(int, Fraction...)}</li>
 *          <li>{@link #insertRow(int, Fraction...)}</li>
 *     </ul>
 * 
 * @author Kirill Bogatikov
 * @since 1.0
 * @version 1.0
 */
public class FractionsMatrix extends Matrix<Fraction> {

    /**
     * Initializes matrix with specified width and height
     * 
     * @param width width of matrix
     * @param height height of matrix
     * @throws RuntimeException if width or height less than 1
     */
    public FractionsMatrix(int width, int height) {
        super(width, height);
    }
    
    @Override
    public FractionsMatrix copy() {
        FractionsMatrix copy = new FractionsMatrix(width, height);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                copy.mx[x][y] = mx[x][y];
            }
        }
        return copy;
    }
    
    /**
     * Returns matrix's determinant
     * <p>This implemention optimized to work with Fractions: more accuracy, but more slowly</p>
     * See {@link Matrix#getDeterminant()} for more details
     */
    @Override
    public Fraction getDeterminant() {
        if(width != height) {
            throw new RuntimeException("Cannot calculate determinant of non-square matrix (" + width + "x" + height + ")");
        }
        
        if(width == 2) {
            return get(0, 0).mul(get(1, 1)).sub(get(0, 1).mul(get(1, 0)));
        }
        
        if(width == 3) {
            return get(0, 0).mul(get(1, 1)).mul(get(2, 2)).sum( 
                   get(0, 2).mul(get(1, 0)).mul(get(2, 1))).sum( 
                   get(0, 1).mul(get(1, 2)).mul(get(2, 0))).sub(
                   get(0, 2).mul(get(1, 1)).mul(get(2, 0))).sub(
                   get(0, 0).mul(get(2, 1)).mul(get(1, 2))).sub(
                   get(1, 0).mul(get(0, 1)).mul(get(2, 2)));
        }
        
        Fraction determ = new Fraction();
        int k;
        for(int i = 0; i < width; i++) {
            if(i % 2 == 0) {
                k = -1;
            } else {
                k = 1;
            }
            determ = determ.sum(get(i, 0).mul(new Fraction(k)).mul((Fraction)getMinorFor(i, 1))); 
        }
        
        return determ;
    }
}
