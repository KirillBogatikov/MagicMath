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
    
    public void add(double... values) {
        for(double f : values) {
            matrix.add(f);
        }
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
    
    public double[] solve(Number... freeNumbers) {
        if(freeNumbers.length < capacity) {
            throw new RuntimeException("Free column has not enough items: " + freeNumbers.length + ", " + capacity + " need");
        }
        
        Fraction[] fractions = new Fraction[capacity];
        for(int i = 0; i < capacity; i++) {
            fractions[i] = Fraction.create(freeNumbers[i]);
        }
        
        matrix.insertColumn(capacity, fractions);
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
        
        Fraction[] unknowns = new Fraction[capacity];
        unknowns[capacity - 1] = matrix.get(capacity, capacity - 1).div(matrix.get(capacity - 1, capacity - 1));
        for(int i = capacity - 1; i > -1; i--) {
            Fraction f = new Fraction(0);
            for(int j = capacity - 1; j > i; j--) {
                f = f.sum(unknowns[j].mul(matrix.get(j, i)));
            }
            unknowns[i] = matrix.get(capacity, i).sub(f).div(matrix.get(i, i));
        }
        
        double[] result = new double[capacity];
        for(int i = 0; i < unknowns.length; i++) {
            result[i] = unknowns[i].doubleValue();
        }
        return result;
    }
}
