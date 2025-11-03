package example;

public class Product {

    public String name;
    public double price;
    public double quantity;
    public String unit;

    public Product(String name, double price, double quantity, String unit) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String toString() {
        return (name + "   " + price + " zl   " + quantity + "   " + unit);
    }
}
