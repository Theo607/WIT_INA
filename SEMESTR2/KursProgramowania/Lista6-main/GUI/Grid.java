package GUI;

import javax.swing.*;
import java.awt.*;

import GRID.AnimalState;

/**
 * Klasa obslugujaca graficzna reprezentacje planszy
 * @param gridCells Tablicza 2-wymiarowa komorek z planszy
 */
public class Grid extends JPanel {
    
    private Cell[][] gridCells;

    /**
     * Konstruktor Grida
     * @param width Szerokosc
     * @param height Wysokosc
     */
    public Grid(int width, int height) {
        setLayout(new GridLayout(height, width));
        gridCells = new Cell[height][width];

        AnimalState emptyState = new AnimalState(-1, "");
        gridCells = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gridCells[i][j] = new Cell(emptyState);
                add(gridCells[i][j]);
            }
        }
        
        setPreferredSize(new Dimension(width * 50, height * 50));
    }

    /**
     * Odswiezanie obiektu
     * @param states Statusy komorek
     * @param indexes Indexy wzgledem pozycji
     */
    public void refreshGrid(AnimalState[] states, Integer[][] indexes) {
        if (states == null || indexes == null) {
            throw new IllegalArgumentException("States or indexes cannot be null.");
        }

        this.removeAll();
        
        AnimalState emptyState = new AnimalState(-1, "");

        for(int i = 0; i < gridCells.length; i++) {
            for (int j = 0; j < gridCells[i].length; j++) {
                Integer index = indexes[i][j];
                if (index != null && index >= 0 && index < states.length) {
                    gridCells[i][j] = new Cell(states[index]);
                } else {
                    gridCells[i][j] = new Cell(emptyState);
                }
                add(gridCells[i][j]);
            }
        }
        revalidate();
        repaint();
    }

}
