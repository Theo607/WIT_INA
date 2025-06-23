package GRID;

/**
 * Klasa zajmujaca sie zajacami
 * @param wolf Odniesienie do wilka
 */
public class Hare extends Animal {
    private Wolf wolf;

    /**
     * Konstruktor zajaca
     * @param state Stan zajaca
     * @param position Pozycja zajaca
     */
    public Hare(AnimalState state, Position position) {
        super(state, position);
    }

    /**
     * seter wilka
     * @param wolf Wilk
     */
    public void setWolf(Wolf wolf) {
        this.wolf = wolf;
    }

    /**
     * Funkcja opisujaca ruchy podejmowane przez zajaca
     */
    @Override
    public synchronized void nextMove() {
        if(animalIsAlive() && !animalIsClicked()) {
            Position currentPosition = getPosition();
            Position wolfPosition = wolf.getPosition();

            Position[] possibleMoves ={
                new Position(currentPosition.getRow() - 1, currentPosition.getColumn()), // Up
                new Position(currentPosition.getRow() + 1, currentPosition.getColumn()), // Down
                new Position(currentPosition.getRow(), currentPosition.getColumn() - 1), // Left
                new Position(currentPosition.getRow(), currentPosition.getColumn() + 1),
                new Position(currentPosition.getRow() - 1, currentPosition.getColumn() - 1), // Up-Left
                new Position(currentPosition.getRow() - 1, currentPosition.getColumn() + 1), // Up-Right
                new Position(currentPosition.getRow() + 1, currentPosition.getColumn() - 1), // Down-Left
                new Position(currentPosition.getRow() + 1, currentPosition.getColumn() + 1)  // Down-Right
            };

            Position[] bestMoves = new Position[possibleMoves.length];
            int bestMoveCount = 0;
            double maxDistance = 0;
            for (Position move : possibleMoves) {
                if (isInBounds(move) && !isPositionOccupied(move)) {
                    double distance = move.distanceTo(wolfPosition);
                    if (distance > maxDistance) {
                        maxDistance = distance;
                        bestMoveCount = 0;
                        bestMoves[bestMoveCount++] = move;
                    } else if (distance == maxDistance) {
                        bestMoves[bestMoveCount++] = move;
                    }
                }
            }

            Position bestMove = null;
            if (bestMoveCount > 0) {
                int randomIndex = getInt(0, bestMoveCount);
                bestMove = bestMoves[randomIndex];
            } else {
                // If no valid moves, stay in place
                bestMove = currentPosition;
            }

            this.updatePosition(bestMove);

        } else {
            // If the hare is clicked, it does not move
            sleep();
        }
    }
    
}
