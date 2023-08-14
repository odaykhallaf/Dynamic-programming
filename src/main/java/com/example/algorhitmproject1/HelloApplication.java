package com.example.algorhitmproject1;

import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class HelloApplication extends Application {
    private static final int INFINITY = Integer.MAX_VALUE;
    private static String selectedFile;
    private Label pathLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Uniform Cost Algorithm");

        VBox root = new VBox();
        root.setPadding(new Insets(10));

        Button openButton = new Button("Open File");
        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                if (selectedFile != null) {
                    HelloApplication.selectedFile = selectedFile.getAbsolutePath();
                    System.out.println("Selected File: " + HelloApplication.selectedFile);
                    processFileData();
                }
            }
        });

        pathLabel = new Label("Optimal Paths: ");
        root.getChildren().addAll(openButton, pathLabel);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void processFileData() {
        if (selectedFile == null) {
            System.out.println("No file selected.");
            return;
        }

        String startCity;
        String endCity;
        Map<String, List<City>> cityInfoMap = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));

            // Read start city and end city
            startCity = reader.readLine();
            endCity = reader.readLine();

            // Read city data
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\t");
                String city = tokens[0];
                double latitude = Double.parseDouble(tokens[1]);
                double longitude = Double.parseDouble(tokens[2]);
                cityInfoMap.put(city, new ArrayList<>());
                cityInfoMap.get(city).add(new City(city, latitude, longitude));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        List<List<String>> optimalPaths = findOptimalPaths(startCity, endCity, cityInfoMap);
        displayOptimalPaths(optimalPaths);
    }

    public static List<List<String>> findOptimalPaths(String startCity, String endCity,
                                                      Map<String, List<City>> cityInfoMap) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        Map<String, Integer> costMap = new HashMap<>();
        Map<String, List<List<String>>> pathMap = new HashMap<>();

        for (String city : cityInfoMap.keySet()) {
            costMap.put(city, INFINITY);
            pathMap.put(city, new ArrayList<>());
        }

        costMap.put(startCity, 0);
        priorityQueue.add(new Node(startCity, costMap.get(startCity), Arrays.asList(startCity)));

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            String currentCity = currentNode.city;
            int currentCost = currentNode.cost;
            List<String> currentPath = currentNode.path;

            if (currentCity.equals(endCity)) {
                pathMap.get(endCity).add(currentPath);
                continue;
            }

            if (currentCost > costMap.get(currentCity)) {
                continue; // Skip outdated paths
            }

            List<City> adjacentCities = cityInfoMap.get(currentCity);
            for (City adjacentCity : adjacentCities) {
                String nextCity = adjacentCity.name;
                int nextCost = currentCost + calculateCost(adjacentCity, cityInfoMap.get(nextCity).get(0));
                List<String> nextPath = new ArrayList<>(currentPath);
                nextPath.add(nextCity);

                if (nextCost < costMap.get(nextCity)) {
                    costMap.put(nextCity, nextCost);
                    priorityQueue.add(new Node(nextCity, nextCost, nextPath));
                    pathMap.get(nextCity).clear();
                    pathMap.get(nextCity).add(nextPath);
                } else if (nextCost == costMap.get(nextCity)) {
                    pathMap.get(nextCity).add(nextPath);
                }
            }
        }

        return pathMap.get(endCity);
    }

    private static int calculateCost(City city1, City city2) {
        double lat1 = city1.latitude;
        double lon1 = city1.longitude;
        double lat2 = city2.latitude;
        double lon2 = city2.longitude;

        // Distance calculation (you can replace it with your own cost calculation logic)
        double distance = Math.sqrt(Math.pow(lat2 - lat1, 2) + Math.pow(lon2 - lon1, 2));
        return (int) (distance * 100); // Convert distance to cost (you can adjust the multiplier as needed)
    }

    private void displayOptimalPaths(List<List<String>> optimalPaths) {
        StringBuilder sb = new StringBuilder("Optimal Paths:\n");

        for (List<String> path : optimalPaths) {
            sb.append(String.join(" -> ", path));
            sb.append("\n");
        }

        pathLabel.setText(sb.toString());
    }

    private static class Node implements Comparable<Node> {
        String city;
        int cost;
        List<String> path;

        public Node(String city, int cost, List<String> path) {
            this.city = city;
            this.cost = cost;
            this.path = path;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    private static class City {
        String name;
        double latitude;
        double longitude;

        public City(String name, double latitude, double longitude) {
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
