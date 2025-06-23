package GUI;

import javax.swing.JButton;
import GRID.AnimalState;

/**
 * Klasa zajmujaca sie pojedynczymi komorkami grida, przesyla statusy klikniec i obrazuje stan pozycji.
 * @param state Stan danej komorki / pozycji (zwierzecia ktore na nim jest)
 */
public class Cell extends JButton {
    private AnimalState state;

    /**
     * Konstruktur komorki
     * @param state Stan danej komorki
     */
    public Cell(AnimalState state) {
        if(state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }

        this.state = state;
        
        setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 16));
        if(state.getType() == null) {
            setText("");
        } else {
            setText(state.getEmoji());
        }
        
        if(state.isClicked()) {
            setBackground(java.awt.Color.RED);
        } else {
            setBackground(java.awt.Color.WHITE);
        }
        
        this.addActionListener(e -> handleClick());
    }

    /**
     * Metoda obslugujaca klikniecia mysza
     */
    public void handleClick() {
        state.setClicked(!state.isClicked());
        setBackground(state.isClicked() ? java.awt.Color.RED : java.awt.Color.WHITE);
        if(state.isClicked()) {
            setBackground(java.awt.Color.RED);
        } else {
            setBackground(java.awt.Color.WHITE);
        }
    }

    /**
     * Geter statusu komorki
     * @return Status komorki
     */
    public AnimalState getState() {
        return state;
    }
    
}
