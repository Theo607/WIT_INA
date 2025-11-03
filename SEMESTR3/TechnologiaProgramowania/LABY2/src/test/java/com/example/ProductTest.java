import static org.junit.jupiter.api.Assertions.*;

import example.Product;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    public void testProductCreation() {
        Product prod = new Product("Test Product", 10.0, 5, "kg");
        assertEquals(prod.name, "Test Product");
        assertEquals(prod.price, 10.0);
        assertEquals(prod.quantity, 5.0);
        assertEquals(prod.unit, "kg");
    }

    @Test
    public void testToString() {
        Product prod = new Product("Test Product", 10.0, 5, "kg");
        String expected = "Test Product   10.0 zl   5.0   kg";
        assertEquals(expected, prod.toString());
    }
}
