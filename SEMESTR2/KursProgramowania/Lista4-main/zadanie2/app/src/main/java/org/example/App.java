package org.example;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

class PascalTriangle {
    private final int[][] triangle;

    public PascalTriangle(int rows) {
        triangle = new int[rows][rows];
        generate(rows);
    }

    private void generate(int rows) {
        for (int i = 0; i < rows; i++) {
            triangle[i][0] = triangle[i][i] = 1;
            for (int j = 1; j < i; j++) {
                triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
        }
    }

    public int get(int row, int col) {
        return triangle[row][col];
    }

    public int size() {
        return triangle.length;
    }
}

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        var args = getParameters().getRaw();
        if (args.isEmpty()) {
            System.out.println("Usage: gradlew run --args=\"<rows>\"");
            return;
        }

        int rows;
        try {
            rows = Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            System.out.println("Invalid number: " + args.get(0));
            return;
        }

        PascalTriangle triangle = new PascalTriangle(rows);
        int cols = rows * 2;

        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(-20);
        grid.setAlignment(Pos.CENTER);


        for (int i = 0; i < rows; i++) {
            int startCol = rows - i;
            for (int j = 0; j <= i; j++) {
                String text = String.valueOf(triangle.get(i, j));
                Label label = new Label(text);
                label.setFont(new Font(12));
                label.setMinWidth(50);
                grid.add(label, startCol + j * 2, i);
            }
        }

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setTitle("Pascal's Triangle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
