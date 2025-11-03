package GRID;

/**
 * Klasa zajmujaca sie obsluga parametrow do powstania planszy
 * @param width Szerokosc planszy
 * @param height Wysokosc planszy
 * @param speed Predkosc symulacji
 * @param hareCount Ilosc zajecy
 * @param rng Generator liczb losowych
 */
public class Parameters {
    private int width;
    private int height;
    private int speed;
    private int hareCount; 
    private Rng rng;

    /**
     * Konstruktor do parametrow
     * @param width Szerokosc 
     * @param height Wysokosc
     * @param speed Predkosc
     * @param hareCount Liczba zajecy
     * @param rng Generator liczb
     */
    public Parameters(int width, int height, int speed, int hareCount, Rng rng) {
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.hareCount = hareCount;
        this.rng = rng;
    }

    /**
     * Geter szerokosci
     * @return Szerokosc
     */
    public int getWidth() {
        return width;
    }

    /**
     * Geter wysokosci
     * @return Wysokosc
     */
    public int getHeight() {
        return height;
    }

    /**
     * Geter predkosci
     * @return Predkosc
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Geter cyklu odpoczynku
     * @return Cykl odpoczynku
     */
    public int getCycle() {
        return rng.getInt((int)Math.floor(speed * 0.5), (int)Math.floor(speed * 1.5));
    }


    /**
     * Geter liczby zajecy
     * @return Liczba Zajecy
     */
    public int getHareCount() {
        return hareCount;
    }

    /**
     * Geter losowej pozycji
     * @return Losowa pozycja z planszy
     */
    public Position getRandomPosition() {
        return rng.getRandomPosition(width, height);
    }

    /**
     * Geter losowej liczby pomiedzy min i max
     * @param min Najmniejsza mozliwa do uzyskania
     * @param max Ograniczenie gorne
     * @return Liczba pomiedzy min i max
     */
    public int getInt(int min, int max) {
        return rng.getInt(min, max);
    }




    
}
