package example;

public class Client {

    public String name;
    public String address;
    public String nip;

    public Client(String name, String address, String nip) {
        this.name = name;
        this.address = address;
        this.nip = nip;
    }

    public String toString() {
        return (
            "\n name: '" +
            name +
            "\n address: " +
            address +
            "\n nip: '" +
            nip +
            '\n'
        );
    }
}
