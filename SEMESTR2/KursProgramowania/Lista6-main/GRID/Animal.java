package GRID;

/**
 * Klasa Animal emuluje zachowanie zwierzęcia.
 * @param state Przechowuje stan w jakim jest zwierze, jego status kliknięcia, to czy żyje, jego typ.
 * @param position Przechowuje pozycje.
 * @param field Odnośnik do pola z wszystkimi zwierzętami, używany do pozyskiwania informacji.
 */

public class Animal extends Thread {
    private AnimalState state;
    private Position position;
    private Field field;
    
    /**
     * Konstruktor klasy Animal.
     * @param state Stan danego zwierzaka.
     * @param position Jego pozycja.
     */
    public Animal(AnimalState state, Position position) {
        this.state = state;
        this.position = position;
    }

    /**
     * Sprawdza czy zwierze żyje
     * @return Zwraca to czy zwierze żyje
     */
    public boolean animalIsAlive() {
        return state.lives();
    }

    /**
     * Czy zwierze zostało klikniete
     * @return zwraca to stan jego klikniecia
     */
    public boolean animalIsClicked() {
        return state.isClicked();
    }

    /**
     * Zabija zwierze, ustwia jego status zycia na martwy
     */
    public void killAnimal() {
        state.dead();
    }

    /**
     * Zabija zwierze na polu
     */
    public void deleteAnimal() {
        field.killAnimal(state.getIndex());
    }

    /**
     * Konczy symulacje
     */
    public void gameEnded() {
        field.gameEnded();
    }

    /**
     * Geter pozycji
     * @return zwraca pozycje zwierzecia
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Seter pola
     * @param field Ustawia parametr field 
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * Geter AnimalState
     * @return zwraca AnimalState
     */
    public AnimalState getAnimalState() {
        return state;
    }

    /**
     * Wyciaga z field losowa liczbe pomiedz min a max
     * @param min najmniejsza mozliwa liczba do uzyskania
     * @param max ograniczenie gorne losowej liczby
     * @return losowa liczba
     */
    public int getInt(int min, int max) {
        return field.getInt(min, max);
    }

    /**
     * Usypia zwierze na pewien cykl
     */
    public void sleep() {
        try {
            Thread.sleep(field.getCycle());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sprawdza czy podana pozycja jest w polu
     * @param position Pozycja do sprawdzenia
     * @return Bool który mówi czy pozycja jest na planszy
     */
    public boolean isInBounds(Position position) {
        return field.inBounds(position);
    }

    /**
     * Sprawdza czy pozycja jest zajeta na planszy
     * @param position Pozycja do sprawdzenia
     * @return boolean ktory mowi czy pozycja na planszy jest zajeta
     */
    public boolean isPositionOccupied(Position position) {
        return field.isPositionOccupied(position);
    }

    /**
     * Odswiezenie pozycji na planszy i lokalnie dla zwierzecia
     * @param newPosition
     */
    public void updatePosition(Position newPosition) {
        field.updatePosition(state.getIndex(), newPosition);
        this.position = newPosition;
    }

    /**
     * Metoda powodująca nastepny ruch zwierzęcia
     */
    public void nextMove() {}

    /**
     * Metoda, która mówi jak działa wątek. Póki zwierze żyje to najpierw odpoczywa cykl potem się porusza.
     */
    public void run() {
        while (state.lives()) {
            sleep();
            nextMove();
        }
    }
}
