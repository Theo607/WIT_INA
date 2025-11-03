package src;

// AWT and Swing for GUI components and event handling
import javax.swing.*; // Provides classes for building graphical user interfaces
import javax.swing.filechooser.FileNameExtensionFilter; // Allows filtering file types in file choosers
import java.awt.*; // Provides classes for graphics, colors, and shapes
// Data structures
import java.util.function.Consumer; // Represents a function that accepts a single input and returns no result

// File I/O
import java.io.*; // Provides classes for file input and output



/**
 * Represents the menu bar for the Paint application.
 * Provides options for file operations, tools, and settings.
 */
public class PaintMenuBar extends JMenuBar {

    /**
     * Constructs the PaintMenuBar with the given callbacks for tool, color, and stroke selection.
     *
     * @param toolSelector       Callback for selecting a drawing tool.
     * @param colorSelector      Callback for selecting the outline color.
     * @param fillColorSelector  Callback for selecting the fill color.
     * @param strokeSelector     Callback for selecting the stroke width.
     * @param canvas             The PaintCanvas instance to interact with.
     */
    public PaintMenuBar(Consumer<Tool> toolSelector, Consumer<Color> colorSelector, Consumer<Color> fillColorSelector,
            Consumer<Integer> strokeSelector, PaintCanvas canvas) {
        // Create menus
        JMenu Info = new JMenu("Info"); // Menu for application information
        JMenu File = new JMenu("File"); // Menu for file operations
        JMenu Tools = new JMenu("Tools"); // Menu for drawing tools

        // Create menu items for Info menu
        JMenuItem About = new JMenuItem("About"); // Displays application information
        JMenuItem Help = new JMenuItem("Help"); // Displays usage instructions
        JMenuItem Exit = new JMenuItem("Exit"); // Exits the application

        // Create menu items for File menu
        JMenuItem Save = new JMenuItem("Save"); // Saves the current canvas to a file
        JMenuItem Open = new JMenuItem("Open"); // Opens a saved canvas from a file
        JMenuItem New = new JMenuItem("New"); // Creates a new canvas

        // Create menu items for Tools menu
        JMenuItem Circle = new JMenuItem("Circle"); // Selects the circle drawing tool
        JMenuItem Rectangle = new JMenuItem("Rectangle"); // Selects the rectangle drawing tool
        JMenuItem Path = new JMenuItem("Path"); // Selects the path drawing tool

        // Add action listeners for tool selection
        Circle.addActionListener(e -> toolSelector.accept(Tool.CIRCLE));
        Rectangle.addActionListener(e -> toolSelector.accept(Tool.RECTANGLE));
        Path.addActionListener(e -> toolSelector.accept(Tool.PATH));

        // Add menu items to Info menu
        Info.add(About);
        Info.add(Help);
        Info.add(Exit);

        // Add menu items to File menu
        File.add(Save);
        File.add(Open);
        File.add(New);

        // Add menu items to Tools menu
        Tools.add(Circle);
        Tools.add(Rectangle);
        Tools.add(Path);

        // Add functionality for the "New" menu item
        New.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to create a new canvas? Unsaved changes will be lost.", "New Canvas", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                canvas.getFigures().clear(); // Clear all shapes from the canvas
                canvas.repaint(); // Repaint the canvas
            }
        });

        // Add functionality for the "Save" menu item
        Save.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json")); // Filter for JSON files

            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                // Ensure the file has a .json extension
                if (!file.getName().toLowerCase().endsWith(".json")) {
                    file = new File(file.getAbsolutePath() + ".json");
                }
                canvas.getFigures().saveToFile(file); // Save the shapes to the file
            }
        });

        // Add functionality for the "Open" menu item
        Open.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json")); // Filter for JSON files

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                canvas.getFigures().loadFromFile(file); // Load shapes from the file
                canvas.repaint(); // Repaint the canvas
            }
        });

        // Add functionality for the "About" menu item
        About.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "Paint Application\nVersion 1.0\nCreated by Mateusz Smuga\nSimple paint program to draw and manipulate shapes.",
                    "About", JOptionPane.INFORMATION_MESSAGE);
        });

        // Add functionality for the "Help" menu item
        Help.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "Instructions:\n- Use the toolbar to select drawing tools (Circle, Rectangle, Path)." +
                            "\n- Choose colors and stroke width from the settings.\n- Right-click to edit selected shapes.\n - Press E or R to rotate selected shape.",
                    "Help", JOptionPane.INFORMATION_MESSAGE);
        });

        // Add functionality for the "Exit" menu item
        Exit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0); // Exit the application
            }
        });

        // Create the Settings menu
        JMenu Settings = new JMenu("Settings");

        // Add functionality for selecting the outline color
        JMenuItem colorItem = new JMenuItem("Select Outline Color");
        colorItem.addActionListener(e -> {
            Color selected = JColorChooser.showDialog(null, "Choose Outline Color", Color.BLACK);
            if (selected != null) {
                colorSelector.accept(selected); // Set the selected outline color
            }
        });
        Settings.add(colorItem);

        // Add functionality for selecting the fill color
        JMenuItem fillColorItem = new JMenuItem("Select Fill Color");
        fillColorItem.addActionListener(e -> {
            Color selected = JColorChooser.showDialog(null, "Choose Fill Color", Color.WHITE);
            if (selected != null) {
                fillColorSelector.accept(selected); // Set the selected fill color
            }
        });
        Settings.add(fillColorItem);

        // Add functionality for selecting the stroke width
        JComboBox<Integer> strokeBox = new JComboBox<>(new Integer[] { 1, 2, 4, 6, 8, 10 });
        strokeBox.setSelectedItem(2); // Default stroke width
        strokeBox.addActionListener(e -> strokeSelector.accept((Integer) strokeBox.getSelectedItem()));
        Settings.add(new JMenuItem("Stroke Width")); // Optional label
        Settings.add(strokeBox);

        // Add menus to the menu bar
        this.add(Info);
        this.add(File);
        this.add(Tools);
        this.add(Settings);
    }
}

