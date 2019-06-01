package org.kllbff.magic.math.algothms;

import org.kllbff.magic.math.structs.Matrix;

public class KramerAlgorithm {
    private int capacity;
    private Matrix<Double> matrix;
    
    public KramerAlgorithm(int capacity) {
        if(capacity < 2) {
            throw new RuntimeException("Equations system must have at least two equations");
        }
        
        this.capacity = capacity;
        this.matrix = new Matrix<Double>(capacity, capacity);
    }
    
    public void add(double... values) {
        for(double v : values) {
            matrix.add(v);
        }
    }
    
    public double[] solve(Double... free) {
        double[] values = new double[capacity];
        Number D = matrix.getDeterminant();
        for(int i = 0; i < capacity; i++) {
            Matrix<Double> c = matrix.copy();
            for(int j = 0; j < capacity; j++) {
                c.set(i, j, free[j]);
            }            
            Number d = c.getDeterminant();
            values[i] = d.doubleValue() / D.doubleValue();
        }
        return values;
    }
}
