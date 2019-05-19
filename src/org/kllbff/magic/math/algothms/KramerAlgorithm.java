package org.kllbff.magic.math.algothms;

import org.kllbff.magic.math.structs.Fraction;
import org.kllbff.magic.math.structs.FractionsMatrix;

public class KramerAlgorithm {
    private int capacity;
    private FractionsMatrix matrix;
    
    public KramerAlgorithm(int capacity) {
        this.capacity = capacity;
        this.matrix = new FractionsMatrix(capacity, capacity);
    }
    
    public void add(Fraction... values) {
        for(Fraction v : values) {
            matrix.add(v);
        }
    }
    
    public void add(double... numbers) {
        Fraction[] fractions = new Fraction[capacity];
        for(int i = 0; i < capacity; i++) {
            fractions[i] = new Fraction(numbers[i]);
        }
        add(fractions);
    }
    
    public Fraction[] solve(Fraction... free) {
        Fraction[] values = new Fraction[capacity];
        Fraction D = matrix.getDeterminant();
        for(int i = 0; i < capacity; i++) {
            FractionsMatrix c = matrix.copy();
            for(int j = 0; j < capacity; j++) {
                c.set(i, j, free[j]);
            }            
            Fraction d = c.getDeterminant();
            values[i] = d.div(D);
        }
        return values;
    }
    
    public Fraction[] solve(double... free) {
        Fraction[] fractions = new Fraction[capacity];
        for(int i = 0; i < capacity; i++) {
            fractions[i] = new Fraction(free[i]);
        }
        return solve(fractions);
    }
}
