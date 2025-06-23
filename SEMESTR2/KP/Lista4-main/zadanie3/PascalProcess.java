import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class PascalProcess {
    public static void main(String[] args) {

        List<String> commandList = new ArrayList<>();

        
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")) {
            commandList.add("pascal.exe");
        } else if(os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            commandList.add("./pascal");
        } else {
            System.out.println("Unsupported operating system.");
            return;
        }
        
        int count = 0;
        for (String arg : args) {
            commandList.add(arg);
            count = count + 1;
        }

        try {
            ProcessBuilder builder = new ProcessBuilder(commandList);
            builder.redirectErrorStream(true);

            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(count, 1));
            while((line = reader.readLine()) != null) {
            JLabel label = new JLabel(line);
            label.setFont(new Font("Serif", Font.PLAIN, 20));
            mainPanel.add(label);
            }
            JFrame frame = new JFrame("Pascal's Triangle");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(mainPanel);
            frame.pack();
            frame.setVisible(true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
        
        
    }
}
