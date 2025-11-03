import javax.swing.*;
import java.awt.*;

class PascalTriangle {
    private int[][] triangle;
    private int rows;

    public PascalTriangle(int rows) {
        this.rows = rows;
        triangle = new int[rows][rows];
        generateTriangle();
    }

    private void generateTriangle() {
        for (int i = 0; i < rows; i++) {
            triangle[i][0] = 1;
            triangle[i][i] = 1;
            for (int j = 1; j < i; j++) {
                triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
        }
    }
    public int[][] getTriangle() {
        return triangle;
    }
    public int[] getRow(int row) {
        return triangle[row];
    }
    public int getElement(int row, int col) {
        return triangle[row][col];
    }
    public int getRows() {
        return rows;
    }
}

public class PascalsTriangleGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pascal's Triangle");
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        try {
            int rows = Integer.parseInt(args[0]);
            PascalTriangle pascalTriangle = new PascalTriangle(rows);
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(rows, 1));
            mainPanel.setBackground(new Color(240, 240, 240));
            for(int i = 0; i < rows; i++) {
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout());
                for(int j = 0; j <= i; j++) {
                    JLabel label = new JLabel(pascalTriangle.getElement(i, j) + "", SwingConstants.CENTER);
                    JLabel blank = new JLabel("");
                    panel.add(label);
                    panel.add(blank);
                }
                mainPanel.add(panel);
            }
            frame.add(mainPanel);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid number of rows. Please enter a valid integer.");
            return;
        }
        catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return;
        }

        frame.pack();
        frame.setVisible(true);
    }
}
