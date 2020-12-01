package com.example.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatrixSerializable implements Serializable {
    private ArrayList<ArrayList<Double>> matrix;
    private int rows;
    private int columns;
    private Double[][] elements;

    public MatrixSerializable(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.elements = new Double[rows][columns];
    }

    public void set(Object obj, int i, int j) {
        this.elements[i][j] = (Double) obj;
    }
}
