package org.kllbff.magic.math.structs;

import java.util.Arrays;

/**
 * <h3>Represents double values matrix</h3>
 * <p>Matrix - is a group of numbers, placed into table with fixed width and height. This implentation uses array of arrays of double values to store given numbers</p>
 * <p>If you need to get maximum accuracy - use {@link FractionsMatrix}. This matrix much faster, but is highly inaccurate when working with fractions, 
 *    such as {@link org.kllbff.magic.math.algothms.GaussAlgorithm Gauss} and {@link org.kllbff.magic.math.algothms.KramerAlgorithm Kramer} algorithms</p>
 * <p>Matrix has basic methods:
 *     <ul>
 *          <li>getters {@link #get(int, int)} and setters {@link #set(int, int, double)} for each cell</li>
 *          <li>{@link #getMinorFor(int, int)} - for calculating addition minor for specified cell</li>
 *          <li>{@link #getDeterminant()} - for calculating matrix's determinant</li>
 *     </ul>
 *     and some specific methods that facilitate the work with matrix:
 *     <ul>
 *          <li>{@link #strikeOutColumn(int)}</li>
 *          <li>{@link #strikeOutRow(int)}</li>
 *          <li>{@link #addColumn(double...)}</li>
 *          <li>{@link #addColumns(int)}</li>
 *          <li>{@link #addRow(double...)}</li>
 *          <li>{@link #addRows(int)}</li>
 *          <li>{@link #insertColumn(int, double...)}</li>
 *          <li>{@link #insertRow(int, double...)}</li>
 *     </ul>
 * </p>
 * 
 * @author Kirill Bogatikov
 * @since 1.0
 * @version 1.0
 */
public class Matrix {
    public double[][] mx;
    private int width, height;
    private int x, y;
    
    public Matrix(int width, int height) {
        if(width < 1) {
            throw new RuntimeException("Matrix's width cann't be less than 1 (given " + width + ")");
        }
        if(height < 1) {
            throw new RuntimeException("Matrix's height cann't be less than 1 (given " + height + ")");
        }
        
        mx = new double[width][height];
        
        this.width = width;
        this.height = height;
        this.x = 0;
        this.y = 0;
    }
    
    public double get(int x, int y) {
        return mx[x][y];
    }
    
    public Matrix set(int x, int y, double val) {
        mx[x][y] = val;
        return this;
    }
    
    public Matrix add(double val) {
        this.mx[x++][y] = val;
        if(x == width) {
            x = 0;
            y++;
        }
        return this;
    }
    
    public Matrix insertRow(int y, double... row) {
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
    
    public Matrix insertColumn(int x, double... column) {
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
    
    public Matrix addColumns(int count) {
        width += count;
        mx = Arrays.copyOf(mx, width);
        return this;
    }
    
    public Matrix addRows(int count) {
        height += count;
        for(int i = 0; i < width; i++) {
            mx[i] = Arrays.copyOf(mx[i], height);
        }
        return this;
    }
    
    public Matrix addColumn(double... values) {
        addColumns(1);
        int count = Math.min(height, values.length);
        mx[width - 1] = Arrays.copyOf(values, count);
        return this;
    }
    
    public Matrix addRow(double... values) {
        addRows(1);
        int count = Math.min(width, values.length);
        for(int i = 0; i < count; i++) {
            mx[i][height - 1] = values[i];
        }
        return this;
    }
    
    public double getDeterminant() {
        if(width != height) {
            throw new RuntimeException("Cannot calculate determinant of non-square matrix (" + width + "x" + height + ")");
        }
        
        if(width == 2) {
            return mx[0][0] * mx[1][1] - mx[0][1] * mx[1][0];
        }
        
        if(width == 3) {
            return mx[0][0] * mx[1][1] * mx[2][2] + 
                   mx[0][2] * mx[1][0] * mx[2][1] + 
                   mx[0][1] * mx[1][2] * mx[2][0] -
                   mx[0][2] * mx[1][1] * mx[2][0] -
                   mx[0][0] * mx[2][1] * mx[1][2] -
                   mx[1][0] * mx[0][1] * mx[2][2];
        }
        
        double determ = 0.0, k;
        for(int i = 0; i < width; i++) {
            if(i % 2 == 0) {
                k = -1;
            } else {
                k = 1;
            }
            determ += k * mx[i][0] * getMinorFor(i, 1); 
        }
        
        return determ;
    }
    
    public Matrix copy() {
        Matrix copy = new Matrix(width, height);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                copy.mx[x][y] = mx[x][y];
            }
        }
        return copy;
    }
    
    public Matrix strikeOutColumn(int column) {
        for(int i = column; i < width - 1; i++) {
            mx[i] = mx[i + 1];
        }
        width--;
        return this;
    }
    
    public Matrix strikeOutRow(int row) {
        for(int x = 0; x < width; x++) {
            for(int y = row; y < height - 1; y++) {
                mx[x][y] = mx[x][y + 1];
            }
        }
        height--;
        return this;
    }
    
    public double getMinorFor(int x, int y) {
        Matrix mtx = copy();
        mtx.strikeOutColumn(x).strikeOutRow(y);
        return mtx.getDeterminant();
    }
    
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
