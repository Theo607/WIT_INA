package App;

import GRID.*;
import GUI.*;
import javax.swing.*;

/**
 * Klasa App obsługuje wczytywanie danych i uruchamianie programu. Tworzy główne okno i dodaje do niego odpowiednie panele.
 */

public class App {

    /**
     * Główna funkcja App, uruchamia program, konstruuje potrzebne obiekty.
     * @param args Nie są używane.
     */
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Simulation");
        mainFrame.setSize(800, 600);
        Start start = new Start();
        mainFrame.add(start);
        mainFrame.setVisible(true);

        while(start.isStarted() == false) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int width = start.getWidthValue();
        int height = start.getHeightValue();
        int speed = start.getSpeedValue();
        int hare = start.getHareValue();
        int refreshRate = (int)Math.floor(speed * 0.1);

        Rng rng = new Rng();
        Parameters parameters = new Parameters(width, height, speed, hare, rng);

        Field field = new Field(parameters);

        Grid grid = new Grid(width, height);
        mainFrame.remove(start);
        mainFrame.setSize(width * 125 + 50, height * 125 + 50);
        mainFrame.add(grid);
        mainFrame.repaint();
        mainFrame.setVisible(true);

        field.fieldStart();
        final Timer[] timer = new Timer[1];
        timer[0] = new Timer(refreshRate, e -> {
            grid.refreshGrid(field.getAnimalStates(), field.getIndexes());
            grid.repaint();
            field.killAnimal(2);
            if(field.gameEnded()) {
                JOptionPane.showMessageDialog(mainFrame, "Game Over! The wolf has caught all the hares.");
                field.killAll();
                mainFrame.dispose();
                timer[0].stop();
            }
        });
        timer[0].start();
    }
}