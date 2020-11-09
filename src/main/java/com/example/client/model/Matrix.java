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
public class Matrix {
    private ArrayList<ArrayList<Double>> matrix;
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
