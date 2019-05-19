package org.kllbff.magic.math.algothms;

import java.util.List;

import org.kllbff.magic.math.structs.Fraction;
import org.kllbff.magic.math.structs.FractionsMatrix;

public class GaussAlgorithm {
    private int capacity;
    private FractionsMatrix matrix;
    
    public GaussAlgorithm(int capacity) {
        matrix = new FractionsMatrix(capacity, capacity);
        this.capacity = capacity;
    }
    
    public void add(Fraction... values) {
        for(Fraction f : values) {
            matrix.add(f);
        }
    }
    
    public void add(double... numbers) {
        Fraction[] fractions = new Fraction[capacity];
        for(int i = 0; i < capacity; i++) {
            fractions[i] = new Fraction(numbers[i]);
        }
        add(fractions);
    }
    
    private void moveNonZeroRow(int offset) {
        int i;
        for(i = offset; i < capacity; i++) {
            if(matrix.get(offset, i).doubleValue() != 0.0) {
                break;
            }
        }
        List<Fraction> rowA = matrix.getRow(i);
        matrix.strikeOutRow(i).insertRow(offset, rowA.toArray(new Fraction[0]));
    }
    
    public Fraction[] solve(Fraction... free) {
        matrix.insertColumn(capacity, free);
        for(int e = 0; e < capacity - 1; e++) {
            moveNonZeroRow(e);
            
            List<Fraction> row = matrix.getRow(e);
            for(int y = e + 1; y < capacity; y++) {
                Fraction m = matrix.get(e, y);
                m = m.div(row.get(e));
                
                for(int x = e; x < capacity + 1; x++) {
                    Fraction f = matrix.get(x, y).sub(row.get(x).mul(m));
                    matrix.set(x, y, f);
                }
            }
        }
        
        Fraction[] result = new Fraction[capacity];
        result[capacity - 1] = matrix.get(capacity, capacity - 1).div(matrix.get(capacity - 1, capacity - 1));
        for(int i = capacity - 1; i > -1; i--) {
            Fraction f = new Fraction(0);
            for(int j = 2; j > i; j--) {
                f = f.sum(result[j].mul(matrix.get(j, i)));
            }
            result[i] = matrix.get(capacity, i).sub(f).div(matrix.get(i, i));
        }
        
        return result;
    }
    
    public Fraction[] solve(double... free) {
        Fraction[] fractions = new Fraction[capacity];
        for(int i = 0; i < capacity; i++) {
            fractions[i] = new Fraction(free[i]);
        }
        return solve(fractions);
    }
}
