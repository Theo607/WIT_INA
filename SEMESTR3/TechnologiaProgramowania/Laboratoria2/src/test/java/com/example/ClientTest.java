import static org.junit.jupiter.api.Assertions.*;

import example.Client;
import org.junit.jupiter.api.Test;

public class ClientTest {

    @Test
    public void testClientCreation() {
        Client client = new Client("John Doe", "Bakers 18", "123456");
        assertEquals("John Doe", client.name);
        assertEquals("Bakers 18", client.address);
        assertEquals("123456", client.nip);
    }

    @Test
    public void testClientCreationNoName() {
        Client client = new Client(null, "Bakers 18", "123456");
        assertNull(client.name);
        assertEquals("Bakers 18", client.address);
        assertEquals("123456", client.nip);
    }

    @Test
    public void testClientCreationNoAddress() {
        Client client = new Client("John Doe", null, "123456");
        assertEquals("John Doe", client.name);
        assertNull(client.address);
        assertEquals("123456", client.nip);
    }

    @Test
    public void testClientCreationNoNip() {
        Client client = new Client("John Doe", "Bakers 18", null);
        assertEquals("John Doe", client.name);
        assertEquals("Bakers 18", client.address);
        assertNull(client.nip);
    }

    @Test
    public void testClientCreationNoNameNoAddress() {
        Client client = new Client(null, null, "123456");
        assertNull(client.name);
        assertNull(client.address);
        assertEquals("123456", client.nip);
    }

    @Test
    public void testClientCreationNoNameNoAddressNoNip() {
        Client client = new Client(null, null, null);
        assertNull(client.name);
        assertNull(client.address);
        assertNull(client.nip);
    }

    @Test
    public void testClientCreationNoNameNoAddressNoNipNoId() {
        Client client = new Client(null, null, null);
        assertNull(client.name);
        assertNull(client.address);
        assertNull(client.nip);
    }
}
