package com.example.client.Service;

import com.example.client.model.Matrix;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Matrices {
    private Matrices() {
    }

    public static void serialize(ObjectOutputStream outputStream, Matrix matrix) {
        try {
            outputStream.writeObject(matrix);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Matrix deserialize(ObjectInputStream inputStream) {
        Matrix matrix = null;
        try {
            matrix = new Matrix((ArrayList<ArrayList<Double>>) inputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return matrix;
    }
}
