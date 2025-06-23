package GRID;
/**
 * Klasa przechowująca status danego zwierzęcia
 * @param alive Parametr mówiący o tym czy zwierze żyje
 * @param index Index danego zwierzęcia
 * @param type Typ/rasa/gatunek danego zwierzęcia
 * @param clicked Status klikniecia danego zwierzęcia
 */
public class AnimalState {
    private boolean alive;
    private int index;
    private String type;
    private boolean clicked;

    /**
     * Konstruktor AnimalState
     * @param index Index zwierzęcia
     * @param type Rasa/Typ zwierzęcia
     */
    public AnimalState(int index, String type) {
        this.alive = true;
        this.index = index;
        this.type = type;
        this.clicked = false;
    }

    /**
     * Uśmiercanie zwierzaka
     */
    public void dead() {
        this.alive = false;
    }

    /**
     * Metoda ustawiania na klikniete zwierze
     * @param clicked Bool ustawiajacy status klikniecia
     */
    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    /**
     * Geter który mówi czy zwierze żyje
     * @return
     */
    public boolean lives() {
        return alive;
    }

    /**
     * Geter indexu
     * @return Index zwierzęcia
     */
    public int getIndex() {
        return index;
    }

    /**
     * Geter tyou
     * @return Typ zwierzęcia
     */
    public String getType() {
        return type;
    }

    /**
     * Geter statusu clicked
     * @return Stan klikniecia zwierzecia
     */
    public boolean isClicked() {
        return clicked;
    }

    /**
     * Geter emoji
     * @return Zwraca emotke według typu
     */
    public String getEmoji() {
        if(type.equals("Hare")){
            return "\uD83D\uDC07"; // Hare emoji
        }
        if(type.equals("Wolf")){
            return "\uD83D\uDC3A"; // Wolf emoji
        }
        return "";
    }
}
