package src;

// AWT and Swing for GUI components and event handling
import java.awt.*; // Provides classes for graphics, colors, and shapes
import java.awt.geom.*; // Provides classes for geometric shapes and transformations
import java.awt.Shape; // Represents a geometric shape
import java.awt.BasicStroke; // Defines the stroke style for drawing shapes
import java.awt.Point; // Represents a point in 2D space
import java.awt.Rectangle; // Represents a rectangle in 2D space
import java.awt.geom.Path2D; // Represents a geometric path
import java.awt.geom.PathIterator; // Provides iteration over the segments of a path

// Data structures
import java.util.*; // Provides utility classes like Vector and List
import java.util.List; // Represents an ordered collection of elements

/**
 * Represents a drawable shape on the canvas.
 * Contains the shape's geometry, colors, stroke width, and selection state.
 */
public class DrawableShape {
    /** The geometric shape (e.g., circle, rectangle, or path). */
    public Shape shape;

    /** The outline color of the shape. */
    public Color outlinecolor;

    /** The fill color of the shape. */
    public Color fillColor;

    /** Whether the shape is filled (true) or only outlined (false). */
    public boolean filled;

    /** The stroke width (outline thickness) of the shape. */
    public int strokeWidth;

    /** Whether the shape is currently selected. */
    public boolean selected;

    /**
     * Constructs a DrawableShape with the given attributes.
     *
     * @param shape         The geometric shape.
     * @param colorOutline  The outline color of the shape.
     * @param colorFill     The fill color of the shape.
     * @param filled        Whether the shape is filled.
     * @param strokeWidth   The stroke width of the shape.
     */
    public DrawableShape(Shape shape, Color colorOutline, Color colorFill, boolean filled, int strokeWidth) {
        this.shape = shape;
        this.outlinecolor = colorOutline;
        this.fillColor = colorFill;
        this.filled = filled;
        this.strokeWidth = strokeWidth;
        this.selected = false;
    }

    /**
     * Sets the selection state of the shape.
     *
     * @param selected Whether the shape is selected.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Checks if the given point is contained within the shape.
     *
     * @param p The point to check.
     * @return True if the point is within the shape, false otherwise.
     */
    public boolean contains(Point p) {
        return shape.contains(p);
    }

    /**
     * Gets the bounding rectangle of the shape.
     *
     * @return The bounding rectangle of the shape.
     */
    public Rectangle getBounds() {
        return shape.getBounds();
    }

    /**
     * Sets the fill color of the shape.
     *
     * @param color The new fill color.
     */
    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    /**
     * Sets the stroke width of the shape.
     *
     * @param width The new stroke width.
     */
    public void setStrokeWidth(int width) {
        this.strokeWidth = width;
    }

    /**
     * Sets the outline color of the shape.
     *
     * @param color The new outline color.
     */
    public void setOutlineColor(Color color) {
        this.outlinecolor = color;
    }

    /**
     * Draws the shape on the given Graphics2D context.
     *
     * @param g2d The Graphics2D context to draw on.
     */
    public void draw(Graphics2D g2d) {
        if (filled) {
            g2d.setColor(fillColor);
            g2d.fill(shape);
        }
        g2d.setColor(outlinecolor);
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.draw(shape);

        // Draw a red bounding box if the shape is selected
        if (selected) {
            g2d.setColor(Color.RED);
            g2d.draw(shape.getBounds());
        }
    }

    /**
     * Converts the DrawableShape to a SerializableShape for saving to JSON.
     *
     * @return The SerializableShape representation of this shape.
     */
    public SerializableShape toSerializableShape() {
        String type;
        double[] params;

        if (shape instanceof Ellipse2D.Double) {
            Ellipse2D.Double ellipse = (Ellipse2D.Double) shape;
            type = "CIRCLE";
            double centerX = ellipse.getCenterX();
            double centerY = ellipse.getCenterY();
            double radius = ellipse.width / 2;
            params = new double[] { centerX, centerY, radius };
        } else if (shape instanceof Rectangle2D.Double) {
            Rectangle2D.Double rect = (Rectangle2D.Double) shape;
            type = "RECTANGLE";
            params = new double[] { rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight() };
        } else if (shape instanceof Path2D.Double) {
            Path2D.Double path = (Path2D.Double) shape;

            // Ensure the path is closed before serializing
            path.closePath();

            PathIterator iterator = path.getPathIterator(null);
            List<Double> coordsList = new ArrayList<>();
            while (!iterator.isDone()) {
                double[] coords = new double[6];
                int typeSeg = iterator.currentSegment(coords);
                if (typeSeg != PathIterator.SEG_CLOSE) {
                    coordsList.add(coords[0]);
                    coordsList.add(coords[1]);
                }
                iterator.next();
            }
            type = "PATH";
            params = coordsList.stream().mapToDouble(Double::doubleValue).toArray();
        } else {
            type = "UNKNOWN";
            params = new double[] {};
        }

        return new SerializableShape(
                type,
                params,
                colorToHex(outlinecolor),
                colorToHex(fillColor),
                filled,
                strokeWidth);
    }

    /**
     * Converts a Color object to its hexadecimal string representation.
     *
     * @param color The Color object to convert.
     * @return The hexadecimal string representation of the color.
     */
    private String colorToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
