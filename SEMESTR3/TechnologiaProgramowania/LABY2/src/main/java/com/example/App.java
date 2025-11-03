package example;

import java.io.IOException;
import java.util.Vector;

public class App {

    private Invoice current_invoice;

    private void handleAdd() {
        if (current_invoice == null) {
            System.out.println("No invoice selected");
            return;
        }
        System.out.println("Enter product name: ");
        String product_name = System.console().readLine();
        System.out.println("Enter product price (zl): ");
        double product_price = 0.0;
        String input = System.console().readLine();
        if (input == null || input.isEmpty()) {
            System.out.println("Invalid input");
            return;
        }
        try {
            product_price = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            return;
        }
        System.out.println("Enter product quantity: ");
        input = System.console().readLine();
        double product_quantity = 0.0;
        if (input == null || input.isEmpty()) {
            System.out.println("Invalid input");
            return;
        }
        try {
            product_quantity = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            return;
        }
        System.out.println("Enter unit of measurement: ");
        String product_unit = System.console().readLine();
        Product product = new Product(
            product_name,
            product_price,
            product_quantity,
            product_unit
        );
        current_invoice.addProduct(product);
    }

    private void listProducts() {
        if (current_invoice == null) {
            System.out.println("No invoice selected");
            return;
        }
        System.out.println("Products:");
        int i = 0;
        for (Product product : current_invoice.getProducts()) {
            System.out.println(
                (i) +
                    ". " +
                    product.name +
                    " - " +
                    product.price +
                    " - " +
                    product.quantity +
                    " - " +
                    product.unit
            );
            i++;
        }
    }

    private void handleEdit() {
        if (current_invoice == null) {
            System.out.println("No invoice selected");
            return;
        }
        listProducts();
        System.out.println("Enter product id: ");
        String input = System.console().readLine();
        int product_id = 0;
        try {
            product_id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid product id");
            return;
        }
        System.out.println("Enter new product name: ");
        String product_name = System.console().readLine();
        System.out.println("Enter new product price (zl): ");
        input = System.console().readLine();
        double product_price = 0.0;
        if (!input.isEmpty()) try {
            product_price = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid price");
            return;
        }
        else {
            product_price = 0.0;
        }
        System.out.println("Enter new product quantity: ");
        input = System.console().readLine();
        double product_quantity = 0.0;
        if (!input.isEmpty()) try {
            product_quantity = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity");
            return;
        }
        else {
            product_quantity = 0.0;
        }
        System.out.println("Enter new product unit: ");
        String product_unit = System.console().readLine();

        System.out.println("Save (y, n): ");
        String save = System.console().readLine();
        if (!save.contains("y")) {
            return;
        }
        if (product_name.isEmpty()) product_name = null;
        if (product_unit.isEmpty()) product_unit = null;

        current_invoice.editProduct(
            product_id,
            product_name,
            product_price,
            product_quantity,
            product_unit
        );
    }

    private void handleDelete() {
        if (current_invoice == null) {
            System.out.println("No invoice selected");
            return;
        }
        listProducts();
        System.out.println("Enter product id: ");
        int product_id = Integer.parseInt(System.console().readLine());
        current_invoice.removeProduct(product_id);
    }

    private void handleHelp() {
        System.out.println("Available commands:");
        System.out.println("list - list all products in the invoice");
        System.out.println("add - add a new product to the invoice");
        System.out.println("edit - edit an existing product in the invoice");
        System.out.println("delete - delete a product from the invoice");
        System.out.println("help - display this help message");
        System.out.println("save - save the invoice");
        System.out.println("quit - exit the program");
    }

    private void handleCommand(String cmd) {
        switch (cmd) {
            case "list":
                listProducts();
                break;
            case "add":
                handleAdd();
                break;
            case "edit":
                handleEdit();
                break;
            case "delete":
                handleDelete();
                break;
            case "help":
                handleHelp();
                break;
            case "save":
                handleSave();
                break;
            case "quit":
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid command");
        }
    }

    private void handleSave() {
        Writer writer = new Writer(current_invoice);
        writer.writeToPDF();
    }

    private Client clientInit() {
        String cmd = "";
        Client client = null;
        while (!cmd.contains("y")) {
            System.out.println("Firm name: ");
            String firm_name = System.console().readLine();
            System.out.println("Enter address: ");
            String address = System.console().readLine();
            System.out.println("Enter NIP: ");
            String nip = System.console().readLine();

            client = new Client(firm_name, address, nip);

            System.out.println("Client information: " + client.toString());
            System.out.println("Confirm (y, n): ");
            cmd = System.console().readLine();
        }

        return client;
    }

    public void run() {
        Client current_client = clientInit();
        current_invoice = new Invoice(current_client);

        System.out.println("Enter one of the following instructions: ");
        System.out.println("0. list");
        System.out.println("1. add");
        System.out.println("2. edit");
        System.out.println("3. delete");
        System.out.println("4. exit");
        System.out.println("5. help");
        System.out.println("6. save");
        String cmd = "";
        while (cmd != "exit") {
            System.out.println("Enter command: ");
            cmd = System.console().readLine();
            handleCommand(cmd);
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}
