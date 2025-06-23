package app;
import src.*;
// AWT and Swing for GUI components and event handling
import javax.swing.*; // Provides classes for building graphical user interfaces
import javax.swing.Timer; // Provides a timer for scheduling tasks

/**
 * The main class for the Paint application.
 * <p>
 * This class initializes the application window, sets up the canvas, menu bar, 
 * and starts the main event loop for the application.
 * </p>
 */
public class Paint {

    /**
     * The entry point of the Paint application.
     * <p>
     * This method creates the main application window, initializes the canvas 
     * and menu bar, and starts a timer to continuously repaint the canvas.
     * </p>
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create the main application window
        JFrame frame = new JFrame("Paint");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        // Initialize the Figures model to manage shapes
        Figures figures = new Figures();

        // Create the drawing canvas and bind it to the Figures model
        PaintCanvas canvas = new PaintCanvas(figures);

        // Create the menu bar and bind it to the canvas for tool and color selection
        PaintMenuBar menuBar = new PaintMenuBar(
                canvas::setCurrentTool, 
                canvas::setCurrentColor,
                canvas::setCurrentFillColor, 
                canvas::setCurrentStrokeWidth, 
                canvas
        );

        // Set up the menu bar and add the canvas to the frame
        frame.setJMenuBar(menuBar);
        frame.add(canvas);

        // Start a timer to repaint the canvas at regular intervals (60 FPS)
        new Timer(16, e -> canvas.repaint()).start();

        // Make the application window visible
        frame.setVisible(true);
    }
}