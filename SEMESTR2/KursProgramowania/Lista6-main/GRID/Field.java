package GRID;

/**
 * Kluczowa klasa pakietu, przechowuje informacje o zwierzakach, ich pozycjach, indexach itd
 * @param animals Tablica zwierząt
 * @param parameters Parametry z jakimi zostanie uruchomiona plansz (szerokosc, wysokosc, szybkosc itd.)
 * @param indexes Indexy zwierzat z tablicy
 * @param positions Pozycje zwierzat z tablicy
 * @param gameEnded Status skonczenia symulacji
 */
public class Field {
    private Animal[] animals;
    private Parameters parameters;
    private Integer[][] indexes;
    private Position[] positions;
    private boolean gameEnded;

    /**
     * Konstruktor planszy
     * @param parameters Parametry potrzebne do stworzenia planszy
     */
    public Field(Parameters parameters) {
        this.gameEnded = false;
        this.parameters = parameters;
        this.animals = new Animal[parameters.getHareCount() + 1]; // +1 for the wolf
        this.indexes = new Integer[parameters.getHeight()][parameters.getWidth()];
        for (int i = 0; i < parameters.getHeight(); i++) {
            for (int j = 0; j < parameters.getWidth(); j++) {
                indexes[i][j] = null; // Initialize indexes to null
            }
        }
        this.positions = new Position[parameters.getHareCount() + 1]; // +1 for the wolf
        for (int i = 0; i < parameters.getHareCount() + 1; i++) {
            positions[i] = null; // Initialize positions to null
        }
        initializeAnimals();
    }

    /**
     * Metoda tworząca wszystkie zwierzęta.
     */
    private void initializeAnimals() {
        Position wolfPosition = parameters.getRandomPosition();
        AnimalState wolfState = new AnimalState(0, "Wolf");
        animals[0] = new Wolf(wolfState, wolfPosition);

        indexes[wolfPosition.getRow()][wolfPosition.getColumn()] = 0; // Set wolf index
        positions[0] = wolfPosition; // Set wolf position
        animals[0].setField(this); // Set field for the wolf
        for(int i = 1; i < animals.length; i++) {
            Position harePosition = parameters.getRandomPosition();
            while(indexes[harePosition.getRow()][harePosition.getColumn()] != null) {
                // Ensure hare does not spawn on the same position as another animal
                harePosition = parameters.getRandomPosition();
            }
            animals[i] = new Hare(new AnimalState(i, "Hare"), harePosition);
            indexes[harePosition.getRow()][harePosition.getColumn()] = i; // Set hare index
            positions[i] = harePosition; // Set hare position
            animals[i].setField(this); // Set field for the hare
            ((Hare) animals[i]).setWolf((Wolf) animals[0]); // Set the wolf for each hare
        }
        // Set the hares for the wolf
        ((Wolf) animals[0]).setHares(animals);
    }

    /**
     * Metoda, ktora uruchamia pokolei wszystkie zwierzeta z planszy
     */
    public void fieldStart() {
        for (Animal animal : animals) {
            if (animal != null) {
                animal.start(); // Start each animal thread
            }
        }
    }

    /**
     * Metoda zabijajaca dane zwierze/watek
     * @param index Index zwierzecia do zabicia
     */
    public void killAnimal(int index) {
         // Kill the animal at the specified index
         // Clear the position of the killed animal
        if(positions[index] == null) {
            Position newPosition = animals[index].getPosition();
            positions[index] = newPosition; // Ensure position is not null

        }
        indexes[positions[index].getRow()][positions[index].getColumn()] = null; // Clear the index of the killed animal
        animals[index].killAnimal();
        positions[index] = null;
    }

    /**
     * Metoda zabijajaca wszystkie zwierzecia
     */
    public void killAll() {
        for (Animal animal : animals) {
            if (animal != null && animal.animalIsAlive()) {
                animal.killAnimal(); // Kill all animals
            }
        }
        gameEnded = true; // Set game ended to true
    }

    /**
     * Metoda zwracajaca wszystkie zwierzecia jako tablice
     * @return Tablica zwierzat z planszy
     */
    public Animal[] getAnimals() {
        return animals;
    }

    /**
     * Geter tablicy statusów zwierzat z planszy
     * @return tablica statusów zwierząt z planszy
     */
    public AnimalState[] getAnimalStates() {
        AnimalState[] states = new AnimalState[animals.length];
        for (int i = 0; i < animals.length; i++) {
            states[i] = animals[i].getAnimalState();
        }
        return states;
    }

    /**
     * Geter Indexow wzgledem pozycji
     * @return Tablica dwu-wymiarowa podaje index zwierzecia znajdujacego sie na podanej pozycji
     */
    public Integer[][] getIndexes() {
        return indexes;
    }


    /**
     * Geter dlugosci odpoczynku
     * @return zwraca odpowienia dlugosc odpoczynku zwierzecia
     */
    public int getCycle() {
        return parameters.getCycle();
    }

    /**
     * Geter losowej liczby z [min,max)
     * @param min Najmniejsza możliwa liczba
     * @param max Ograniczenie górne
     * @return Losowa liczba pomiedzy min i max
     */
    public int getInt(int min, int max) {
        return parameters.getInt(min, max);
    }

    /**
     * Funkcja sprawdzajaca czy pozycja jest na planszy
     * @param position Pozycja do sprawdzenia 
     * @return boolean mowiacy o tym czy pozycja jest na planszy
     */
    public boolean inBounds(Position position) {
        return position.getRow() >= 0 && position.getRow() < parameters.getHeight() &&
               position.getColumn() >= 0 && position.getColumn() < parameters.getWidth();
    }

    /**
     * Geter sprawdzajacy czy pozycja jest zajeta na planszy
     * @param position Pozycja do sprawdzenia
     * @return Boolean mowiacy o tym czy pozycja jest zajeta
     */
    public boolean isPositionOccupied(Position position) {
        return indexes[position.getRow()][position.getColumn()] != null;
    }

    /**
     * Funkcja sprawdzajacy czy symulacja sie skonczyla
     * @return Boolean mowiacy o tym czy sie skonczyla
     */
    public boolean gameEnded() {
        boolean allHaresDead = true;
        for (int i = 1; i < animals.length; i++) {
            if (animals[i].animalIsAlive()) {
                allHaresDead = false;
                break;
            }
        }
        if (allHaresDead) {
            gameEnded = true; 
        }
        return gameEnded;
    }

    /**
     * Metoda odswiezajaca informacje o pozycji danego zwierzecia
     * @param index Index zwierzecia
     * @param newPosition Jego nowa pozycja
     */
    public void updatePosition(int index, Position newPosition) {
        Position oldPosition = positions[index];
        
        indexes[oldPosition.getRow()][oldPosition.getColumn()] = null; // Clear old position
        indexes[newPosition.getRow()][newPosition.getColumn()] = index; // Set new index
        positions[index] = newPosition; // Update position
       
    }


    
}
