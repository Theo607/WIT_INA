import java.io.*;
import java.net.*;

public class RunClient {

    public static void main(String[] args) throws Exception {
        
        Socket client = new Socket("localhost", 1234);

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while ((line = in.readLine()) != null) {
            System.out.println(line);
            if (line.contains("Enter type")) {
                String userInput = console.readLine();
                out.println(userInput);
            } else if (line.contains("Command:")) {
                String userInput = console.readLine();
                out.println(userInput);
                if ("draw".equalsIgnoreCase(userInput.trim())) {
                    // Read lines until END_DRAW
                    while ((line = in.readLine()) != null && !line.equals("END_DRAW")) {
                        System.out.println(line);
                    }
                }
            } else if (line.contains("Value:")) {
                String userInput = console.readLine();
                out.println(userInput);
                if (!"search".equalsIgnoreCase(userInput.trim())) {
                    // Read lines until END_DRAW
                    while ((line = in.readLine()) != null && !line.equals("END_DRAW")) {
                        System.out.println(line);
                    }
                }
            }
        }

        client.close();
    }
}
