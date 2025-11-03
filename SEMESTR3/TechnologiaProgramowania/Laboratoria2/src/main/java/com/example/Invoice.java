package example;

import java.util.Date;
import java.util.Vector;

public class Invoice {

    public Client client;
    public Vector<Product> products;
    public Date date;

    public Invoice(Client client) {
        this.client = client;
        this.products = new Vector<>();
        this.date = new Date();
    }

    public Client getClient() {
        return client;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(int id) {
        int i = 0;
        for (Product product : products) {
            if (i == id) {
                products.remove(product);
                break;
            }
            i++;
        }
    }

    public void editProduct(
        int id,
        String name,
        double price,
        double quantity,
        String unit
    ) {
        int i = 0;
        for (Product p : products) {
            if (i == id) {
                if (name != null && !name.isEmpty()) p.name = name;
                if (price > 0) p.price = price;
                if (quantity > 0) p.quantity = quantity;
                if (unit != null && !unit.isEmpty()) p.unit = unit;
                break;
            }
            i++;
        }
    }

    public Vector<Product> getProducts() {
        return products;
    }

    public String toString() {
        String client_information =
            "Name: " +
            client.name +
            "\n" +
            "Address: " +
            client.address +
            "\n" +
            "NIP: " +
            client.nip +
            "\n";

        String products_information = "";
        for (Product product : products) {
            products_information += product.toString() + '\n';
        }
        String product = "Name   " + "Price   " + "Quantity   " + "Unit";
        return (
            date.toString() +
            '\n' +
            client_information +
            product +
            "\n" +
            products_information
        );
    }
}
