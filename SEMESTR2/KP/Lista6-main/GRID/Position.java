package GRID;
/**
 * Klasa, która tworzy pojecie pozycji
 * @param row Rząd 
 * @param column Kolumna 
 */
public class Position {
    private int row;
    private int column;

    /**
     * Konstruktor pozycji
     * @param x Rzad
     * @param y Kolumna
     */
    public Position(int x, int y) {
        row = x;
        column = y;
    }

    /**
     * Geter rzedu
     * @return Rzad
     */
    public int getRow() {
        return row;
    }

    /**
     * Geter kolumny
     * @return Kolumna
     */
    public int getColumn() {
        return column;
    }

    /**
     * Funkcja mierzaca odlegosc pozycji od innej podanej pozycjis
     * @param other Inna pozycjia do której liczymy odległosć
     * @return Odległosc do innej pozycji
     */
    public double distanceTo(Position other) {
        if (other == null) {
            return 0;
        }
        return Math.sqrt(Math.pow(row - other.getRow(), 2) + Math.pow(column - other.getColumn(), 2));
    }

    /**
     * Funkcja przesuniecia o wektor zwraca nowa pozycjie
     * @param point wektor o ktory przesuwamy
     * @return przesuniete koordy
     */
    public Position move(Position point) {
        return new Position(row + point.getRow(), column + point.getColumn());
    }
}
