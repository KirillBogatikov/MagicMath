package org.kllbff.magic.math.structs;

import java.util.Arrays;

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
