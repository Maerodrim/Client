package com.example.client.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MatrixSerializable implements Serializable
{
    private ArrayList<ArrayList<Double>> matrix;
    private int rows;
    private int columns;
    private Double[][] elements;
    public  Double getElement(int i,int j){
        return matrix.get(i).get(j);
    }
    public  void setElement(int i,int j,Double field){
        matrix.get(i).set(j,field);
    }
    public  ArrayList<ArrayList<Double>> getMatrix(){
        return matrix;
    }
    public  void setMatrix(ArrayList<ArrayList<Double>> matrix){
        this.matrix = matrix;
    }
    public  int getСolumnNumber(){
        return matrix.size();
    }
    public  int getLineNumber(){
        return matrix.get(0).size();
    }
}
