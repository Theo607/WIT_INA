package src;

/**
 * Represents a serializable version of a shape.
 * This class is used for saving and loading shapes to/from JSON files.
 */
public class SerializableShape {
    /** The type of the shape (e.g., "CIRCLE", "RECTANGLE", "PATH"). */
    String type;

    /** The parameters defining the shape (e.g., coordinates, dimensions). */
    double[] params;

    /** The outline color of the shape in hexadecimal format (e.g., "#FF0000"). */
    String outlineColor;

    /** The fill color of the shape in hexadecimal format (e.g., "#00FF00"). */
    String fillColor;

    /** Whether the shape is filled (true) or only outlined (false). */
    boolean filled;

    /** The stroke width (outline thickness) of the shape. */
    int strokeWidth;

    /**
     * Constructs a SerializableShape with the given attributes.
     *
     * @param type          The type of the shape (e.g., "CIRCLE", "RECTANGLE", "PATH").
     * @param params        The parameters defining the shape (e.g., coordinates, dimensions).
     * @param outlineColor  The outline color of the shape in hexadecimal format.
     * @param fillColor     The fill color of the shape in hexadecimal format.
     * @param filled        Whether the shape is filled (true) or only outlined (false).
     * @param strokeWidth   The stroke width (outline thickness) of the shape.
     */
    public SerializableShape(String type, double[] params, String outlineColor, String fillColor, boolean filled,
            int strokeWidth) {
        this.type = type;
        this.params = params;
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
        this.filled = filled;
        this.strokeWidth = strokeWidth;
    }
}
