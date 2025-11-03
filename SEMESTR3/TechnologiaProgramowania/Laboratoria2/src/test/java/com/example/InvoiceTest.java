import static org.junit.jupiter.api.Assertions.*;

import example.Client;
import example.Invoice;
import example.Product;
import java.util.Vector;
import org.junit.jupiter.api.Test;

public class InvoiceTest {

    @Test
    public void testCreation() {
        Client client = new Client("John Doe", "123 Main St", "1234567890");
        Invoice invoice = new Invoice(client);
        assertEquals(client, invoice.getClient());
    }

    @Test
    public void testAddProduct() {
        Client client = new Client("John Doe", "123 Main St", "1234567890");
        Invoice invoice = new Invoice(client);
        Product test_product = new Product("Product A", 10.0, 1.0, "unit");
        invoice.addProduct(test_product);
        Vector<Product> products = invoice.getProducts();
        assertEquals(1, products.size());
        Product product = products.get(0);
        assertEquals("Product A", product.name);
        assertEquals(10.0, product.price);
        assertEquals(1.0, product.quantity);
        assertEquals("unit", product.unit);
    }

    @Test
    public void testRemoveProduct() {
        Client client = new Client("John Doe", "123 Main St", "1234567890");
        Invoice invoice = new Invoice(client);
        Product test_product = new Product("Product A", 10.0, 1.0, "unit");
        invoice.addProduct(test_product);
        invoice.removeProduct(0);
        Vector<Product> products = invoice.getProducts();
        assertEquals(0, products.size());
    }
}
