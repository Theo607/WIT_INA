package GRID;

/**
 * Klasa zajmujaca sie wilkiem
 * @param hares Tablica przechowujaca informacje o zajacach
 */
public class Wolf extends Animal {
    private Animal[] hares;

    /**
     * Konstruktor wilka
     * @param state Stan wilka
     * @param position Jego pozycja
     */
    public Wolf(AnimalState state, Position position) {
        super(state, position);
    }

    /**
     * Ustawianie zajecy z planszy
     * @param hares Zajace z planszy
     */
    public void setHares(Animal[] hares) {
        this.hares = hares;
    }

    /**
     * Funkcja zwracajaca najblizszego zajaca
     * @return Najblizszy zajac
     */
    public Hare getClosestHare() {
        Hare closestHare = null;
        double minDistance = Double.MAX_VALUE;
        Hare[] closeHares = new Hare[this.hares.length - 1]; // Exclude the wolf itself
        int closeHareCount = 0;

        for (int i = 1; i < this.hares.length; i++) {
            Animal animal = this.hares[i];
            if (animal instanceof Hare && animal.animalIsAlive() && !animal.animalIsClicked()) {
                double distance = getPosition().distanceTo(animal.getPosition());
                if (distance < minDistance) {
                    minDistance = distance;
                    closeHareCount = 0;
                    closeHares = new Hare[this.hares.length - 1]; // Reset closeHares array
                    closeHares[closeHareCount++] = (Hare) animal;
                } else if (distance == minDistance) {
                    closeHares[closeHareCount++] = (Hare) animal;
                }
            }
        }
        if (closeHareCount == 0) {
            return null; // No hares found
        } else {
            int randomIndex = getInt(0, closeHareCount);
            closestHare = closeHares[randomIndex];
        }
        return closestHare;
    }

    /**
     * Funkcja sprawdzajaca czy da sie zjesc zajaca
     * @param hare Zajac do sprawdzenia
     * @return Bool mowiacy o zjadalnosci zajaca
     */
    public boolean isHareInRange(Hare hare) {
        double distance = getPosition().distanceTo(hare.getPosition());
        return distance <= 1.5; 
    }

    /**
     * Funkcja zwracajaca najlepszy ruch dla wilka
     * @param harePosition pozycja najblizszego zajaca
     * @return Nowa pozycja na ktora nalezy sie przesunac
     */
    public Position bestMove(Position harePosition) {
        Position currePosition = getPosition();

        Position[] possibleMoves = {
            new Position(currePosition.getRow() - 1, currePosition.getColumn()), // Up
            new Position(currePosition.getRow() + 1, currePosition.getColumn()), // Down
            new Position(currePosition.getRow(), currePosition.getColumn() - 1), // Left
            new Position(currePosition.getRow(), currePosition.getColumn() + 1),
            new Position(currePosition.getRow() - 1, currePosition.getColumn() - 1), // Up-Left
            new Position(currePosition.getRow() - 1, currePosition.getColumn() + 1), // Up-Right
            new Position(currePosition.getRow() + 1, currePosition.getColumn() - 1), // Down-Left
            new Position(currePosition.getRow() + 1, currePosition.getColumn() + 1)  // Down-Right
        };

        Position bestMove = null;
        double minDistance = Double.MAX_VALUE;
        int bestMovesCount = 0;
        Position[] bestMoves = new Position[possibleMoves.length];

        for (Position move : possibleMoves) {
            if (isInBounds(move) && !isPositionOccupied(move)) {
                double distance = move.distanceTo(harePosition);
                if (distance < minDistance) {
                    minDistance = distance;
                    bestMovesCount = 0;
                    bestMoves[bestMovesCount++] = move; // Reset bestMoves array
                } else if (distance == minDistance) {
                    bestMoves[bestMovesCount++] = move;
                }
            }
        }

        if (bestMovesCount == 0) {
            return null; // No valid moves found
        } else {
            int randomIndex = getInt(0, bestMovesCount);
            bestMove = bestMoves[randomIndex];
        }

        return bestMove;

    }

    /**
     * Funkcja odpowiadajaca za ruchy i akcje ktore podejmuje wilk
     */

    @Override
    public synchronized void nextMove() {
        Hare closestHare = getClosestHare();
        if(closestHare == null) {
            gameEnded();
            return; // No hares left, end the game
        } else {
            if(this.animalIsClicked()) {
                sleep();
            } else {
                if (isHareInRange(closestHare)) {
                    closestHare.deleteAnimal(); // Kill the closest hare
                    for(int i = 0; i < 5; i++) {
                        sleep();
                        return;
                    }
                } else {
                    Position closestHarePosition = closestHare.getPosition();
                    Position bestMove = bestMove(closestHarePosition);
                    if (bestMove != null) {
                        updatePosition(bestMove); // Move towards the closest hare
                    } else {
                        // No valid move found, just sleep
                        sleep();
                    }
                }
                sleep();
            }
        }
    }
    
}
