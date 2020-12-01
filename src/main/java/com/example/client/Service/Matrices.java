package com.example.client.Service;

import com.example.client.model.Matrix;
import com.example.client.model.MatrixSerializable;

import java.io.*;
import java.util.ArrayList;

public class Matrices {
    private Matrices() {
    }


    public static void serialize(ObjectOutputStream outputStream, MatrixSerializable matrix) {
        try {
            outputStream.writeInt(matrix.getRows());
            outputStream.writeInt(matrix.getColumns());
            Double[][] elements=matrix.getElements();
            for (int i = 0; i < matrix.getRows(); i++) {
                for (int j = 0; j < matrix.getColumns(); j++) {
                    outputStream.writeObject(elements[i][j]);
                }
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(FileWriter fileWriter, MatrixSerializable matrix) {
        try {
            fileWriter.write(matrix.getRows() + "\n");
            fileWriter.write(matrix.getColumns() + "\n");
            fileWriter.write(matrix.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MatrixSerializable deserialize(ObjectInputStream inputStream) {
        MatrixSerializable matrix = null;
        try {
            int rows = inputStream.readInt();
            int columns = inputStream.readInt();
            matrix = new MatrixSerializable(rows, columns);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    matrix.set(inputStream.readObject(), i, j);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    public static MatrixSerializable readFromFile(FileReader fileReader) throws IllegalArgumentException{
        MatrixSerializable matrix = null;
        StreamTokenizer streamTokenizer = new StreamTokenizer(fileReader);
        try {
            streamTokenizer.nextToken();
            int rows = (int)streamTokenizer.nval;
            streamTokenizer.nextToken();
            int columns = (int)streamTokenizer.nval;
            if (rows < 0 || columns < 0) {
                throw new IllegalArgumentException("Одна (или обе) размерности меньше нуля");
            }
            streamTokenizer.nextToken();
            matrix = new MatrixSerializable(rows, columns);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    matrix.set(streamTokenizer.nval, i, j);
                    streamTokenizer.nextToken();
                }
            }
            streamTokenizer.nextToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    public static Matrix matrixToMatrix(MatrixSerializable matrixSerializable)
    {
        return new Matrix(new ArrayList<ArrayList<Double>>() {
            {
                Double[][] elements=matrixSerializable.getElements();
                for (int i = 0; i < matrixSerializable.getRows(); i++) {
                    ArrayList<Double> list = new ArrayList<>();
                    for (int j = 0; j < matrixSerializable.getColumns(); j++) {
                        list.add(elements[i][j]);
                    }
                    this.add(list);
                }
            }
        });
    }
}
