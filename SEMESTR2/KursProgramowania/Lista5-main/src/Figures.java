package src;
// AWT and Swing for GUI components and event handling
import javax.swing.*; // Provides classes for building graphical user interfaces
import java.awt.*; // Provides classes for graphics, colors, and shapes
import java.awt.geom.*; // Provides classes for geometric shapes and transformations
import java.awt.Shape; // Represents a geometric shape
import java.awt.geom.Path2D; // Represents a geometric path

// Data structures
import java.util.*; // Provides utility classes like Vector and List
import java.util.List; // Represents an ordered collection of elements
import java.util.Vector; // Represents a dynamic array

// File I/O
import java.io.*; // Provides classes for file input and output

// Gson for JSON serialization and deserialization
import com.google.gson.Gson; // Provides methods for converting Java objects to JSON and vice versa
import com.google.gson.GsonBuilder; // Provides a builder for creating Gson instances

/**
 * Manages a collection of drawable shapes on the canvas.
 * Provides methods for adding, deleting, selecting, and saving/loading shapes.
 */
public class Figures {
    /** The collection of drawable shapes managed by this class. */
    private Vector<DrawableShape> shapes = new Vector<>();

    /**
     * Adds a circle to the collection of shapes.
     *
     * @param x            The x-coordinate of the circle's center.
     * @param y            The y-coordinate of the circle's center.
     * @param radius       The radius of the circle.
     * @param colorOutline The outline color of the circle.
     * @param colorFill    The fill color of the circle.
     * @param filled       Whether the circle is filled or only outlined.
     * @param strokeWidth  The stroke width of the circle's outline.
     */
    public void AddCircle(int x, int y, int radius, Color colorOutline, Color colorFill, boolean filled,
            int strokeWidth) {
        Shape shape = new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2);
        shapes.add(new DrawableShape(shape, colorOutline, colorFill, filled, strokeWidth));
    }

    /**
     * Adds a rectangle to the collection of shapes.
     *
     * @param x            The x-coordinate of the rectangle's top-left corner.
     * @param y            The y-coordinate of the rectangle's top-left corner.
     * @param width        The width of the rectangle.
     * @param height       The height of the rectangle.
     * @param colorOutline The outline color of the rectangle.
     * @param colorFill    The fill color of the rectangle.
     * @param filled       Whether the rectangle is filled or only outlined.
     * @param strokeWidth  The stroke width of the rectangle's outline.
     */
    public void AddRectangle(int x, int y, int width, int height, Color colorOutline, Color colorFill, boolean filled,
            int strokeWidth) {
        Shape shape = new Rectangle2D.Double(x, y, width, height);
        shapes.add(new DrawableShape(shape, colorOutline, colorFill, filled, strokeWidth));
    }

    /**
     * Adds a path to the collection of shapes.
     *
     * @param path         The geometric path to add.
     * @param colorOutline The outline color of the path.
     * @param colorFill    The fill color of the path.
     * @param filled       Whether the path is filled or only outlined.
     * @param strokeWidth  The stroke width of the path's outline.
     */
    public void AddPath(Shape path, Color colorOutline, Color colorFill, boolean filled, int strokeWidth) {
        shapes.add(new DrawableShape(path, colorOutline, colorFill, filled, strokeWidth));
    }

    /**
     * Returns the collection of drawable shapes.
     *
     * @return A vector containing all drawable shapes.
     */
    public Vector<DrawableShape> GetFigures() {
        return shapes;
    }

    /**
     * Deletes all selected shapes from the collection.
     */
    public void deleteSelected() {
        shapes.removeIf(shape -> shape.selected);
    }

    /**
     * Deselects all shapes in the collection.
     */
    public void deselectAll() {
        for (DrawableShape shape : shapes) {
            shape.setSelected(false);
        }
    }

    /**
     * Clears all shapes from the collection.
     */
    public void clear() {
        shapes.clear();
    }

    /**
     * Saves the collection of shapes to a JSON file.
     *
     * @param file The file to save the shapes to.
     */
    public void saveToJson(File file) {
        List<SerializableShape> serializableShapes = new ArrayList<>();
        for (DrawableShape ds : shapes) {
            serializableShapes.add(ds.toSerializableShape());
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(serializableShapes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads shapes from a JSON file into the collection.
     *
     * @param file The file to load the shapes from.
     */
    public void loadFromJson(File file) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(file)) {
            SerializableShape[] serializableShapes = gson.fromJson(reader, SerializableShape[].class);
            shapes.clear();
            for (SerializableShape ss : serializableShapes) {
                DrawableShape ds = fromSerializableShape(ss);
                if (ds != null) {
                    shapes.add(ds);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the collection of shapes to a file.
     *
     * @param file The file to save the shapes to.
     */
    public void saveToFile(File file) {
        try (Writer writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<SerializableShape> serializableShapes = new ArrayList<>();
            for (DrawableShape ds : shapes) {
                serializableShapes.add(ds.toSerializableShape());
            }
            gson.toJson(serializableShapes, writer);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage(), "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads shapes from a file into the collection.
     *
     * @param file The file to load the shapes from.
     */
    public void loadFromFile(File file) {
        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            SerializableShape[] serializableShapes = gson.fromJson(reader, SerializableShape[].class);
            shapes.clear();
            for (SerializableShape ss : serializableShapes) {
                DrawableShape ds = fromSerializableShape(ss);
                if (ds != null) {
                    shapes.add(ds);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading file: " + e.getMessage(), "Load Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Converts a SerializableShape to a DrawableShape.
     *
     * @param ss The SerializableShape to convert.
     * @return The corresponding DrawableShape, or null if the conversion fails.
     */
    private DrawableShape fromSerializableShape(SerializableShape ss) {
        Color outlineColor = Color.decode(ss.outlineColor);
        Color fillColor = Color.decode(ss.fillColor);
        boolean filled = ss.filled;
        int strokeWidth = ss.strokeWidth;

        switch (ss.type) {
            case "CIRCLE":
                if (ss.params.length == 3) {
                    int x = (int) ss.params[0];
                    int y = (int) ss.params[1];
                    int radius = (int) ss.params[2];
                    Shape shape = new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2);
                    return new DrawableShape(shape, outlineColor, fillColor, filled, strokeWidth);
                }
                break;
            case "RECTANGLE":
                if (ss.params.length == 4) {
                    int x = (int) ss.params[0];
                    int y = (int) ss.params[1];
                    int width = (int) ss.params[2];
                    int height = (int) ss.params[3];
                    Shape shape = new Rectangle2D.Double(x, y, width, height);
                    return new DrawableShape(shape, outlineColor, fillColor, filled, strokeWidth);
                }
                break;
            case "PATH":
                if (ss.params.length >= 4) {
                    Path2D.Double path = new Path2D.Double();
                    path.moveTo(ss.params[0], ss.params[1]);
                    for (int i = 2; i < ss.params.length; i += 2) {
                        path.lineTo(ss.params[i], ss.params[i + 1]);
                    }
                    // Close the path explicitly
                    path.closePath();
                    return new DrawableShape(path, outlineColor, fillColor, filled, strokeWidth);
                }
                break;
            default:
                break;
        }
        return null;
    }
}
