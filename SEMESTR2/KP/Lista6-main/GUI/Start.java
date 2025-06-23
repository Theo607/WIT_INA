package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * KLasa obslugujaca panel startowy
 * @param started Wystartowanie aplikacji
 * @param width Szerokosc
 * @param height Wysokosc 
 * @param speed Predkosc
 * @param hare Liczba zajecy
 */
public class Start extends JPanel{

    private boolean started = false;
    private int width;
    private int height;
    private int speed;
    private int hare;

    /**
     * Konstruktor panelu startowego
     */
    public Start() {
        setLayout(new BorderLayout(10, 10));

        JPanel textFieldsPanel = new JPanel();
        textFieldsPanel.setLayout(new GridLayout(2, 4));

        JLabel widthLabel = new JLabel("Width: ");
        JLabel heightLabel = new JLabel("Height: ");
        JLabel speedLabel = new JLabel("Speed: ");
        JLabel hareLabel = new JLabel("Hare: ");

        JTextField widthField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField speedField = new JTextField();
        JTextField hareFIeld = new JTextField();

        textFieldsPanel.add(widthLabel);
        textFieldsPanel.add(widthField);

        textFieldsPanel.add(heightLabel);
        textFieldsPanel.add(heightField);

        textFieldsPanel.add(speedLabel);
        textFieldsPanel.add(speedField);

        textFieldsPanel.add(hareLabel);
        textFieldsPanel.add(hareFIeld);

        JLabel welcomeLabel = new JLabel("Welcome! Please enter the simulation parameters below:");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        add(welcomeLabel, BorderLayout.NORTH);

        textFieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textFieldsPanel.setBackground(Color.LIGHT_GRAY);
        add(textFieldsPanel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Simulation");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.WHITE);
        startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(startButton, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            try {
                width = Integer.parseInt(widthField.getText());
                height = Integer.parseInt(heightField.getText());
                speed = Integer.parseInt(speedField.getText());
                hare = Integer.parseInt(hareFIeld.getText());

                if (width <= 0 || height <= 0 || speed <= 0 || hare < 0) {
                    JOptionPane.showMessageDialog(this, "Please enter valid positive integers for all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Proceed with the simulation using the parameters
                    System.out.println("Starting simulation with parameters: Width=" + width + ", Height=" + height + ", Speed=" + speed + ", Hare=" + hare);
                    // Here you would typically start the simulation
                }

                started = true;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid integers in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Geter szerokosci
     * @return Szerokosc
     */
    public int getWidthValue() {
        return width;
    }
    /**
     * Geter wysokosci
     * @return Wysokosc
     */
    public int getHeightValue() {
        return height;
    }
    /**
     * Geter predkosci
     * @return Predkosc
     */
    public int getSpeedValue() {
        return speed;
    }
    /**
     * Geter liczby zajecy
     * @return Liczba zajecy
     */
    public int getHareValue() {
        return hare;
    }
    /**
     * Geter wystartowania symulacji
     * @return boolean mowiacy o tym czy wczytano wsztstkie dane
     */
    public boolean isStarted() {
        return started;
    }
}
