package com.example.client.controller;

import com.example.client.Service.Matrices;
import com.example.client.dto.MatrixDto;
import com.example.client.model.Matrix;

import javax.naming.NameNotFoundException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class Client {

    private static Socket clientSocket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static void main(String[] args) {

        BufferedOutputStream bos = null;
        try {
            String firstMatrixName = "first";//args[0];
            String secondMatrixName = "second";//args[1];
            String resultMatrixName = "result";//args[2];
            //String action = args[3];

            Matrix secondMatrix = new Matrix(new ArrayList<ArrayList<Double>>() {
                {
                    for (int i = 0; i < 5; i++) {
                        int finalI = i;
                        ArrayList<Double> list = new ArrayList<>();
                        for (int j = 0; j < 5; j++) {
                            list.add((double) 1);
                        }
                        this.add(list);
                    }
                }
            });
            Matrix firstMatrix = new Matrix(new ArrayList<ArrayList<Double>>() {
                {
                    for (int i = 0; i < 5; i++) {
                        int finalI = i;
                        ArrayList<Double> list = new ArrayList<>();
                        for (int j = 0; j < 5; j++) {
                            list.add((double) 2);
                        }
                        this.add(list);
                    }
                }
            });

            try (
                    ObjectOutputStream MatrixWriter1 = new ObjectOutputStream(new FileOutputStream(firstMatrixName))) {
                Matrices.serialize(MatrixWriter1, firstMatrix);
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
            try (
                    ObjectOutputStream MatrixWriter2 = new ObjectOutputStream(new FileOutputStream(secondMatrixName))) {
                Matrices.serialize(MatrixWriter2, secondMatrix);
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
            try (
                    ObjectInputStream firstMatrixReader = new ObjectInputStream(new FileInputStream(firstMatrixName))) {
                firstMatrix = Matrices.deserialize(firstMatrixReader);
            } catch (
                    Exception e) {
                e.printStackTrace();
            }

            try (
                    ObjectInputStream secondMatrixReader = new ObjectInputStream(new FileInputStream(secondMatrixName))) {
                secondMatrix = Matrices.deserialize(secondMatrixReader);
            } catch (
                    Exception e) {
                e.printStackTrace();
            }

            URL url = new URL("http://localhost:8080/MatrixController/addition");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            bos = new

                    BufferedOutputStream(urlConnection.getOutputStream());
            bos.write((new

                    MatrixDto(firstMatrix)).

                    toString().

                    getBytes());
            bos.write((new

                    MatrixDto(secondMatrix)).

                    toString().

                    getBytes());

            String result = urlConnection.getResponseMessage();
            System.out.println("server response: " + result); //проверить, что вернет сервер

            /*try (ObjectOutputStream resultMatrixWriter = new ObjectOutputStream(new FileOutputStream(resultMatrixName))) {
                Matrices.serialize(resultMatrixWriter, resultMatrix);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bos != null;
                bos.flush(); //очищает поток output-a
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 /*       try {
            try {
                clientSocket = new Socket("localhost", 8080);
                System.out.println("Соединение с сервером установлено\n");
                in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                out = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));

                String firstMatrixName = args[0];
                String secondMatrixName = args[1];
                String resultMatrixName = args[2];
                String action = args[3];

                Matrix firstMatrix = null;
                Matrix secondMatrix = null;

                try (ObjectInputStream firstMatrixReader = new ObjectInputStream(new FileInputStream(firstMatrixName))) {
                    firstMatrix = Matrices.deserialize(firstMatrixReader);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (ObjectInputStream secondMatrixReader = new ObjectInputStream(new FileInputStream(secondMatrixName))) {
                    secondMatrix = Matrices.deserialize(secondMatrixReader);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Первая матрица:");
                if (firstMatrix.getCountRows() <= 10 && firstMatrix.getCountColumns() <= 10) {
                    System.out.println(firstMatrix);
                } else {
                    System.out.println("Матрица велика для отображения");
                }
                System.out.println("Вторая матрица:");
                if (secondMatrix.getCountRows() <= 10 && secondMatrix.getCountColumns() <= 10) {
                    System.out.println(firstMatrix);
                } else {
                    System.out.println("Матрица велика для отображения");
                }
                System.out.println("Действие: " + action);

                Matrices.serialize(out, firstMatrix);
                Matrices.serialize(out, secondMatrix);
                out.writeObject(Action.valueOf(action));
                out.flush();
                System.out.println("Идёт вычисление...");
                Matrix resultMatrix = Matrices.deserialize(in);
                System.out.println("Результат:");
                if (resultMatrix.getCountRows() <= 10 && resultMatrix.getCountColumns() <= 10) {
                    System.out.println(resultMatrix);
                } else {
                    System.out.println("Матрица велика для отображения");
                }
                try (ObjectOutputStream resultMatrixWriter = new ObjectOutputStream(new FileOutputStream(resultMatrixName))) {
                    Matrices.serialize(resultMatrixWriter, resultMatrix);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Готово, результат записан в " + resultMatrixName); // получив - выводим на экран
                System.out.println("Соединение разорвано");
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Клиент был закрыт");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }*/

    }
}
