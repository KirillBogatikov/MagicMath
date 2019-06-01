package org.kllbff.magic.math.structs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h3>Represents double values matrix</h3>
 * <p>Matrix - is a group of numbers, placed into table with fixed width and height. This implentation uses array of arrays of double values to store given numbers</p>
 * <p>If you need to get maximum accuracy - use {@link FractionsMatrix}. This matrix much faster, but is highly inaccurate when working with fractions, 
 *    such as {@link org.kllbff.magic.math.algothms.GaussAlgorithm Gauss} and {@link org.kllbff.magic.math.algothms.KramerAlgorithm Kramer} algorithms</p>
 * <p>Matrix has basic methods:
 *     <ul>
 *          <li>getters {@link #get(int, int)} and setters {@link #set(int, int, Number)} for each cell</li>
 *          <li>{@link #getMinorFor(int, int)} - for calculating addition minor for specified cell</li>
 *          <li>{@link #getDeterminant()} - for calculating matrix's determinant</li>
 *          <li>getters for width and height</li>
 *     </ul>
 *     and some specific methods that facilitate the work with matrix:
 *     <ul>
 *          <li>{@link #strikeOutColumn(int)}</li>
 *          <li>{@link #strikeOutRow(int)}</li>
 *          <li>{@link #addColumns(int)}</li>
 *          <li>{@link #addRows(int)}</li>
 *          <li>{@link #insertColumn(int, E...)}</li>
 *          <li>{@link #insertRow(int, E...)}</li>
 *     </ul>
 * 
 * @param <E> a child of Number class, used as one cell value
 * @author Kirill Bogatikov
 * @since 1.0
 * @version 1.0
 */
public class Matrix<E extends Number> {
    protected Number[][] mx;
    protected int width, height;
    protected int x, y;
    
    /**
     * Initializes matrix with specified width and height
     * 
     * @param width width of matrix
     * @param height height of matrix
     * @throws RuntimeException if width or height less than 1
     */
    public Matrix(int width, int height) {
        if(width < 1) {
            throw new RuntimeException("Matrix's width cann't be less than 1 (given " + width + ")");
        }
        if(height < 1) {
            throw new RuntimeException("Matrix's height cann't be less than 1 (given " + height + ")");
        }
        
        mx = new Number[width][height];
        
        this.width = width;
        this.height = height;
        this.x = 0;
        this.y = 0;
    }
    
    /**
     * Returns current matrix's width
     * 
     * @return current matrix's width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns current matrix's height
     * 
     * @return current matrix's height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Returns one row from matrix in List<E>
     * 
     * @param y index of row 
     * @return one row from matrix in List<E>
     */
    @SuppressWarnings("unchecked")
    public List<E> getRow(int y) {
        ArrayList<E> row = new ArrayList<E>();
        for(int x = 0; x < width; x++) {
            row.add((E)mx[x][y]);
        }
        return row;
    }
    
    /**
     * Returns one column from matrix in List<E>
     * 
     * @param x index of column 
     * @return one column from matrix in List<E>
     */
    @SuppressWarnings("unchecked")
    public List<E> getColumn(int x) {
        ArrayList<E> row = new ArrayList<E>();
        for(int y = 0; y < height; y++) {
            row.add((E)mx[x][y]);
        }
        return row;
    }
    
    /**
     * Returns value stored in cell at x column and y row 
     * 
     * @param x column of cell
     * @param y row of cell
     * @return value stored in specified cell
     */
    @SuppressWarnings("unchecked")
    public E get(int x, int y) {
        return (E)mx[x][y];
    }
    
    /**
     * Sets a new value to specified cell
     * 
     * @param x column of cell
     * @param y row of cell
     * @param val new value
     * @return pointer to this Matrix
     */
    public Matrix<E> set(int x, int y, E val) {
        mx[x][y] = val;
        return this;
    }
    
    /**
     * Adds value to next cell. Cell specifies by internal field x and y, incremtable at each call this method
     * <p>
     * After saving value, x-pointer increases by 1. If value of x-pointer bigger than width, y-poiter increases by 1 
     * and x-pointer sets to zero. 
     * </p>
     * 
     * @param val value for cell
     * @return pointer to this Matrix
     */
    public Matrix<E> add(E val) {
        this.mx[x++][y] = val;
        if(x == width) {
            x = 0;
            y++;
        }
        return this;
    }
    
    /**
     * Insert specified value to row at specified position
     * <p>This method does not rewrite values in specified row. It shifts all next rows by 1 and sets free row by given values</p>
     * 
     * @param y target row index
     * @param row values for new row
     * @return pointer to this Matrix
     */
    @SuppressWarnings("unchecked")
    public Matrix<E> insertRow(int y, E... row) {
        if(mx[0].length == height) {
            addRows(1);
        } else {
            height++;
        }
        
        for(int i = height - 1; i > y; i--) {
            for(int j = 0; j < width; j++) {
                mx[j][i] = mx[j][i - 1];
            }
        }
        for(int i = 0; i < width; i++) {
            mx[i][y] = row[i];
        }
        return this;
    }
    
    /**
     * Insert specified value to column at specified position
     * <p>This method does not rewrite values in specified column. It shifts all next columns by 1 and sets free column by given values</p>
     * 
     * @param x target column index
     * @param column values for new column
     * @return pointer to this Matrix
     */
    @SuppressWarnings("unchecked")
    public Matrix<E> insertColumn(int x, E... column) {
        if(mx.length == width) {
            addColumns(1);
        } else {
            width++;
        }
        
        for(int y = 0; y < height; y++) {
            mx[x][y] = column[y];
        }
        return this;
    }
    
    /**
     * Extends the matrix width by the specified number of columns
     * 
     * @param count count of new columns
     * @return pointer to this Matrix
     */
    public Matrix<E> addColumns(int count) {
        mx = Arrays.copyOf(mx, width + count);
        for(int i = 0; i < count; i++) {
            mx[width + i] = new Number[height];
        }
        width += count;
        return this;
    }
    
    /**
     * Extends the matrix height by the specified number of row
     * 
     * @param count count of new rows
     * @return pointer to this Matrix
     */
    public Matrix<E> addRows(int count) {
        height += count;
        for(int i = 0; i < width; i++) {
            mx[i] = Arrays.copyOf(mx[i], height);
        }
        return this;
    }
    
    /**
     * Returns determinant of this matrix or throw Exception if matrix does not square
     * <p>This method can calculate matrix's determinant by three algorithms:
     *     <dl>
     *          <dt>Diagonals</dt>
     *          <dd>In 2x2 matrix determinant calculates very simple: a<sub>0, 0</sub> * a<sub>1, 1</sub> - a<sub>1, 0</sub> * a<sub>0, 1</sub></dd>     
     *          <dt>Triangles</dt>
     *          <dd>In 3x3 matrix determinant calculates by triangles method: <a href="https://en.wikipedia.org/wiki/Rule_of_Sarrus">Triangles Rule</a></dd>
     *          <dt>Minors</dt>
     *          <dd>In <i>n</i>x<i>n</i> matrix determinant calculates by formulae <a href="https://en.wikipedia.org/wiki/Determinant">Determinant</a></dd>
     *     </dl>
     * </p>
     * @return determinant of this matrix
     */
    public Number getDeterminant() {
        if(width != height) {
            throw new RuntimeException("Cannot calculate determinant of non-square matrix (" + width + "x" + height + ")");
        }
        
        if(width == 2) {
            return mx[0][0].doubleValue() * mx[1][1].doubleValue() - mx[0][1].doubleValue() * mx[1][0].doubleValue();
        }
        
        System.err.println(mx[2][0]);
        
        if(width == 3) {
            return mx[0][0].doubleValue() * mx[1][1].doubleValue() * mx[2][2].doubleValue() + 
                   mx[0][2].doubleValue() * mx[1][0].doubleValue() * mx[2][1].doubleValue() + 
                   mx[0][1].doubleValue() * mx[1][2].doubleValue() * mx[2][0].doubleValue() -
                   mx[0][2].doubleValue() * mx[1][1].doubleValue() * mx[2][0].doubleValue() -
                   mx[0][0].doubleValue() * mx[2][1].doubleValue() * mx[1][2].doubleValue() -
                   mx[1][0].doubleValue() * mx[0][1].doubleValue() * mx[2][2].doubleValue();
        }
        
        double determ = 0.0, k;
        for(int i = 0; i < width; i++) {
            if(i % 2 == 0) {
                k = -1;
            } else {
                k = 1;
            }
            determ += k * mx[i][0].doubleValue() * getMinorFor(i, 1).doubleValue(); 
        }
        
        return determ;
    }
    
    /**
     * Returns a new instance of Matrix, containing all values from this matrix
     * 
     * @return a new instance of Matrix, containing all values from this matrix
     */
    public Matrix<E> copy() {
        Matrix<E> copy = new Matrix<E>(width, height);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                copy.mx[x][y] = mx[x][y];
            }
        }
        return copy;
    }
    
    /**
     * Shifts all columns after specified index to left on one column
     * <p>Native matrix does not cut, but width field will be decreased by 1</p>
     * 
     * @param column index of deleting column
     * @return pointer to this Matrix
     */
    public Matrix<E> strikeOutColumn(int column) {
        for(int i = column; i < width - 1; i++) {
            mx[i] = mx[i + 1];
        }
        width--;
        return this;
    }
    
    /**
     * Shifts all rows after specified index to top on one row
     * <p>Native matrix does not cut, but height field will be decreased by 1</p>
     * 
     * @param row index of deleting row
     * @return pointer to this Matrix
     */
    public Matrix<E> strikeOutRow(int row) {
        for(int x = 0; x < width; x++) {
            for(int y = row; y < height - 1; y++) {
                mx[x][y] = mx[x][y + 1];
            }
        }
        height--;
        return this;
    }
    
    /**
     * Returns addition minor for specified cell
     * <p>Minor is a determinant of matrix without column and row of specified cell</p>
     * 
     * @param x column of specified cell
     * @param y row of specified cell
     * @return return determinant of 
     */
    public Number getMinorFor(int x, int y) {
        Matrix<E> mtx = copy();
        mtx.strikeOutColumn(x).strikeOutRow(y);
        return mtx.getDeterminant();
    }
    
    @Override
    public int hashCode() {
        return 128 + Arrays.deepHashCode(mx);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        Matrix<?> other = (Matrix<?>) obj;
        return Arrays.deepEquals(mx, other.mx);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width - 1; x++) {
                builder.append(mx[x][y]).append(" ");
            }
            builder.append(mx[width - 1][y]).append("\n");
        }
        return builder.toString();
    }
}
