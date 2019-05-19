package org.kllbff.magic.math.structs;

import java.util.Arrays;

public class FractionsMatrix {
    public Fraction[][] mx;
    private int width, height;
    private int x, y;
    
    public FractionsMatrix(int width, int height) {
        if(width < 1) {
            throw new RuntimeException("Matrix's width cann't be less than 1 (given " + width + ")");
        }
        if(height < 1) {
            throw new RuntimeException("Matrix's height cann't be less than 1 (given " + height + ")");
        }
        
        mx = new Fraction[width][height];
        
        this.width = width;
        this.height = height;
        this.x = 0;
        this.y = 0;
    }
    
    public Fraction get(int x, int y) {
        return mx[x][y];
    }
    
    public FractionsMatrix set(int x, int y, Fraction val) {
        mx[x][y] = val;
        return this;
    }
    
    public FractionsMatrix add(Fraction val) {
        this.mx[x++][y] = val;
        if(x == width) {
            x = 0;
            y++;
        }
        return this;
    }
    
    public FractionsMatrix insertRow(int y, Fraction... row) {
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
    
    public FractionsMatrix insertColumn(int x, Fraction... column) {
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
    
    public Fraction[] getRow(int y) {
        Fraction[] row = new Fraction[width];
        for(int i = 0; i < width; i++) {
            row[i] = mx[i][y];
        }
        return row;
    }
    
    public FractionsMatrix addColumns(int count) {
        mx = Arrays.copyOf(mx, width + count);
        for(int x = 0; x < count; x++) {
            mx[x + width] = new Fraction[height];
        }
        width += count;
        return this;
    }
    
    public FractionsMatrix addRows(int count) {
        height += count;
        for(int i = 0; i < width; i++) {
            mx[i] = Arrays.copyOf(mx[i], height);
        }
        return this;
    }
    
    public FractionsMatrix addColumn(Fraction[] values) {
        addColumns(1);
        int count = Math.min(height, values.length);
        mx[width - 1] = Arrays.copyOf(values, count);
        return this;
    }
    
    public FractionsMatrix addRow(Fraction[] values) {
        addRows(1);
        int count = Math.min(width, values.length);
        for(int i = 0; i < count; i++) {
            mx[i][height - 1] = values[i];
        }
        return this;
    }
    
    public Fraction getDeterminant() {
        if(width != height) {
            throw new RuntimeException("Cannot calculate determinant of non-square matrix (" + width + "x" + height + ")");
        }
        
        if(width == 2) {
            return mx[0][0].mul(mx[1][1]).sub(mx[0][1].mul(mx[1][0]));
        }
        
        if(width == 3) {
            return mx[0][0].mul(mx[1][1]).mul(mx[2][2]).sum( 
                   mx[0][2].mul(mx[1][0]).mul(mx[2][1])).sum( 
                   mx[0][1].mul(mx[1][2]).mul(mx[2][0])).sub(
                   mx[0][2].mul(mx[1][1]).mul(mx[2][0])).sub(
                   mx[0][0].mul(mx[2][1]).mul(mx[1][2])).sub(
                   mx[1][0].mul(mx[0][1]).mul(mx[2][2]));
        }
        
        Fraction determ = new Fraction();
        int k;
        for(int i = 0; i < width; i++) {
            if(i % 2 == 0) {
                k = -1;
            } else {
                k = 1;
            }
            determ = determ.sum(mx[i][0].mul(new Fraction(k)).mul(getMinorFor(i, 1))); 
        }
        
        return determ;
    }
    
    public FractionsMatrix copy() {
        FractionsMatrix copy = new FractionsMatrix(width, height);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                copy.mx[x][y] = mx[x][y];
            }
        }
        return copy;
    }
    
    public FractionsMatrix strikeOutColumn(int column) {
        for(int i = column; i < width - 1; i++) {
            mx[i] = mx[i + 1];
        }
        width--;
        return this;
    }
    
    public FractionsMatrix strikeOutRow(int row) {
        for(int x = 0; x < width; x++) {
            for(int y = row; y < height - 1; y++) {
                mx[x][y] = mx[x][y + 1];
            }
        }
        height--;
        return this;
    }
    
    public Fraction getMinorFor(int x, int y) {
        FractionsMatrix mtx = copy();
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
