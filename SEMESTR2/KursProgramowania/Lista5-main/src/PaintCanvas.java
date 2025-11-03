package src;

// AWT and Swing for GUI components and event handling
import javax.swing.*; // Provides classes for building graphical user interfaces
import java.awt.*; // Provides classes for graphics, colors, and shapes
import java.awt.event.*; // Provides classes for handling user input events
import java.awt.geom.*; // Provides classes for geometric shapes and transformations
import java.awt.BasicStroke; // Defines the stroke style for drawing shapes
import java.awt.Point; // Represents a point in 2D space
import java.awt.Rectangle; // Represents a rectangle in 2D space
import java.awt.geom.Path2D; // Represents a geometric path
import java.util.Vector; // Represents a dynamic array


/**
 * PaintCanvas is a custom JPanel that provides a drawing surface
 * for creating, selecting, transforming, and deleting shapes.
 * <p>
 * Supported tools include CIRCLE, RECTANGLE, and freehand PATH.
 * Shapes may be filled or outlined, colored, dragged, rotated,
 * and scaled via mouse and keyboard interactions.
 * </p>
 */
public class PaintCanvas extends JPanel {

    /** The collection of drawable shapes currently on the canvas. */
    private Vector<DrawableShape> shapes;

    /** The Figures model managing creation/deletion of shapes. */
    private Figures figures;

    /** The currently selected drawing tool (or NONE). */
    private Tool currentTool = Tool.NONE;

    /** The first click point used when drawing two-click shapes (circle/rectangle). */
    private Point firstClick = null;

    /** The Path2D being built in PATH tool mode. */
    private Path2D currentPath = null;

    /** The shape currently being dragged by the user, if any. */
    private DrawableShape draggedShape = null;

    /** The last mouse position during dragging, used to compute deltas. */
    private Point lastMousePosition = null;

    /** The last click point used for cycling through overlapping shapes. */
    private Point lastClickPoint = null;

    /** Index into overlapping shapes for cycling selection. */
    private int selectionCycleIndex = 0;

    /** Whether new shapes should be filled (true) or only outlined (false). */
    private boolean fillShape = false;

    /** Current outline color for new shapes. */
    private Color currentOutlineColor = Color.BLACK;

    /** Current fill color for new shapes. */
    private Color currentFillColor = Color.WHITE;

    /** Current stroke width (outline thickness) for new shapes. */
    private int currentStrokeWidth = 2;


    /**
     * Constructs a PaintCanvas bound to the given Figures model.
     * <p>
     * This constructor initializes mouse, mouse-motion, key, and
     * mouse-wheel listeners to handle user interaction for drawing,
     * selecting, dragging, transforming, and deleting shapes.
     * </p>
     *
     * @param figures the Figures instance that manages shape data
     */
    public PaintCanvas(Figures figures) {
        this.figures = figures;
        this.shapes = figures.GetFigures();
        setBackground(Color.WHITE);

        // Mouse listener: handles mouse press (selection, drawing, context menu),
        // and mouse release (stop dragging).
        addMouseListener(new MouseAdapter() {
            /**
             * Called when the mouse is pressed. Handles:
             * - right-click for context menu,
             * - drawing-mode clicks,
             * - selection and cycling through overlapping shapes,
             * - beginning drag of a selected shape.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();

                // Show context menu on right-click
                if (SwingUtilities.isRightMouseButton(e)) {
                    showContextMenu(p);
                    return;
                }

                // If a drawing tool is active, handle as drawing click
                if (currentTool != Tool.NONE) {
                    handleClickForTool(p);
                    return;
                }

                // No tool: perform shape selection
                boolean hit = false;
                if (p.equals(lastClickPoint)) {
                    // Cycle through shapes under the same click point
                    Vector<DrawableShape> hits = new Vector<>();
                    for (int i = shapes.size() - 1; i >= 0; i--) {
                        DrawableShape ds = shapes.get(i);
                        if (ds.contains(p)) {
                            hits.add(ds);
                        }
                    }
                    if (!hits.isEmpty()) {
                        selectionCycleIndex = (selectionCycleIndex + 1) % hits.size();
                        figures.deselectAll();
                        hits.get(selectionCycleIndex).setSelected(true);
                        hit = true;
                    }
                } else {
                    // First click: select topmost shape or prepare for cycling
                    lastClickPoint = p;
                    selectionCycleIndex = 0;
                    for (int i = shapes.size() - 1; i >= 0; i--) {
                        DrawableShape ds = shapes.get(i);
                        if (ds.contains(p)) {
                            figures.deselectAll();
                            ds.setSelected(true);
                            hit = true;
                            break;
                        }
                    }
                }

                // If no existing shape was hit, start drawing if a tool is set
                if (!hit) {
                    figures.deselectAll();
                    handleClickForTool(p);
                }

                // If a shape is selected at this point, begin dragging it
                for (int i = shapes.size() - 1; i >= 0; i--) {
                    DrawableShape ds = shapes.get(i);
                    if (ds.selected && ds.contains(p)) {
                        draggedShape = ds;
                        lastMousePosition = p;
                        break;
                    }
                }

                repaint();
            }

            /**
             * Called when the mouse is released. Stops any ongoing drag.
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                draggedShape = null;
                lastMousePosition = null;
            }
        });

        // Mouse-motion listener: handles dragging of selected shapes.
        addMouseMotionListener(new MouseMotionAdapter() {
            /**
             * Called when the mouse is dragged. Moves the dragged shape
             * by the delta from the last mouse position.
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedShape != null && lastMousePosition != null) {
                    int dx = e.getX() - lastMousePosition.x;
                    int dy = e.getY() - lastMousePosition.y;
                    AffineTransform at = AffineTransform.getTranslateInstance(dx, dy);
                    draggedShape.shape = at.createTransformedShape(draggedShape.shape);
                    lastMousePosition = e.getPoint();
                    repaint();
                }
            }
        });

        // Key listener: handles Escape (cancel tool), Delete (remove), Enter (finish path),
        // and R/E for rotation.
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            /**
             * Called when a key is pressed. Supports:
             * - ESC: cancel current tool
             * - DELETE: delete selected shapes
             * - ENTER (in PATH tool): close and add path
             * - R/E: rotate selected shapes by ±15°
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setCurrentTool(Tool.NONE);
                }
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    figures.deleteSelected();
                    repaint();
                }
                if (currentTool == Tool.PATH && e.getKeyCode() == KeyEvent.VK_ENTER && currentPath != null) {
                    currentPath.closePath();
                    figures.AddPath(currentPath, currentOutlineColor, currentFillColor, fillShape, currentStrokeWidth);
                    currentPath = null;
                    firstClick = null;
                    currentTool = Tool.NONE;
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    rotateSelectedShapes(1); // 15° clockwise
                } else if (e.getKeyCode() == KeyEvent.VK_E) {
                    rotateSelectedShapes(-1); // 15° counter-clockwise
                }
            }
        });

        // Mouse-wheel listener: handles scaling of selected shapes.
        addMouseWheelListener(e -> {
            int rotation = e.getWheelRotation();
            double scaleFactor = (rotation < 0) ? 1.1 : 0.9;
            for (DrawableShape ds : shapes) {
                if (ds.selected) {
                    Rectangle bounds = ds.getBounds();
                    double cx = bounds.getCenterX();
                    double cy = bounds.getCenterY();
                    AffineTransform at = AffineTransform.getTranslateInstance(cx, cy);
                    at.scale(scaleFactor, scaleFactor);
                    at.translate(-cx, -cy);
                    ds.shape = at.createTransformedShape(ds.shape);
                }
            }
            repaint();
        });
    }

    /**
     * Returns the Figures model associated with this canvas.
     *
     * @return the Figures instance managing shapes
     */
    public Figures getFigures() {
        return figures;
    }

    /**
     * Sets whether new shapes should be filled or only outlined.
     *
     * @param fillShape true to fill new shapes, false for outlines only
     */
    public void setFillShape(boolean fillShape) {
        this.fillShape = fillShape;
    }

    /**
     * Sets the current drawing tool.
     *
     * @param tool the Tool enum value (NONE, CIRCLE, RECTANGLE, PATH)
     */
    public void setCurrentTool(Tool tool) {
        this.currentTool = tool;
        this.firstClick = null;
        this.currentPath = null;
        figures.deselectAll();
        repaint();
        requestFocusInWindow();
    }

    /**
     * Sets the outline color for subsequently drawn shapes.
     *
     * @param c the Color to use for new shape outlines
     */
    public void setCurrentColor(Color c) {
        this.currentOutlineColor = c;
    }

    /**
     * Sets the fill color for subsequently drawn shapes.
     *
     * @param c the Color to use for new shape fills
     */
    public void setCurrentFillColor(Color c) {
        this.currentFillColor = c;
    }

    /**
     * Sets the stroke width (outline thickness) for subsequently drawn shapes.
     *
     * @param w the stroke width in pixels
     */
    public void setCurrentStrokeWidth(int w) {
        this.currentStrokeWidth = w;
    }

    /**
     * Paints all shapes and, if in PATH tool mode, the current in-progress path.
     *
     * @param g the Graphics context for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Draw each existing shape
        for (DrawableShape ds : shapes) {
            ds.draw(g2d);
        }
        // If drawing a freehand path, draw its current outline
        if (currentTool == Tool.PATH && currentPath != null) {
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(currentStrokeWidth));
            g2d.draw(currentPath);
        }
    }

    /**
     * Rotates each selected shape around its center by the specified angle.
     *
     * @param angleDegrees angle in degrees (positive = clockwise, negative = ccw)
     */
    private void rotateSelectedShapes(double angleDegrees) {
        for (DrawableShape ds : shapes) {
            if (ds.selected) {
                Rectangle bounds = ds.getBounds();
                double cx = bounds.getCenterX();
                double cy = bounds.getCenterY();
                AffineTransform at = AffineTransform.getTranslateInstance(cx, cy);
                at.rotate(Math.toRadians(angleDegrees));
                at.translate(-cx, -cy);
                ds.shape = at.createTransformedShape(ds.shape);
            }
        }
        repaint();
    }

    /**
     * Displays a context menu at the given point for the first selected shape.
     * <p>
     * Allows toggling fill, choosing fill color, and choosing outline color.
     * </p>
     *
     * @param location the point (in canvas coordinates) to show the menu
     */
    private void showContextMenu(Point location) {
        for (DrawableShape ds : shapes) {
            if (ds.selected) {
                JCheckBox fillCheck = new JCheckBox("Fill shape", ds.filled);
                JButton fillBtn = new JButton("Select fill color");
                JButton outlineBtn = new JButton("Select outline color");

                final Color[] fillColor = { ds.fillColor != null ? ds.fillColor : currentFillColor };
                final Color[] outlineColor = { ds.outlinecolor };

                fillBtn.addActionListener(e -> {
                    Color chosen = JColorChooser.showDialog(null, "Choose Fill Color", fillColor[0]);
                    if (chosen != null) fillColor[0] = chosen;
                });
                outlineBtn.addActionListener(e -> {
                    Color chosen = JColorChooser.showDialog(null, "Choose Outline Color", outlineColor[0]);
                    if (chosen != null) outlineColor[0] = chosen;
                });

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(fillCheck);
                panel.add(fillBtn);
                panel.add(outlineBtn);

                int result = JOptionPane.showConfirmDialog(
                        null, panel, "Shape Options", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    ds.filled = fillCheck.isSelected();
                    ds.fillColor = fillColor[0];
                    ds.outlinecolor = outlineColor[0];
                    repaint();
                }
                break; // Only show menu for the first selected shape
            }
        }
    }

    /**
     * Handles mouse clicks when a drawing tool is active.
     * <p>
     * - For CIRCLE and RECTANGLE: first click sets start corner/center,
     *   second click finishes shape creation.
     * - For PATH: each click extends the current path.
     * </p>
     *
     * @param p the point where the mouse was clicked
     */
    private void handleClickForTool(Point p) {
        switch (currentTool) {
            case CIRCLE:
                if (firstClick == null) {
                    // First click records center of circle
                    firstClick = p;
                } else {
                    // Second click sets radius and adds circle
                    int dx = p.x - firstClick.x;
                    int dy = p.y - firstClick.y;
                    int radius = (int) Math.sqrt(dx * dx + dy * dy);
                    figures.AddCircle(
                        firstClick.x, firstClick.y, radius,
                        currentOutlineColor, currentFillColor,
                        fillShape, currentStrokeWidth
                    );
                    shapes.lastElement().fillColor = currentFillColor;
                    firstClick = null;
                    currentTool = Tool.NONE;
                }
                break;
            case RECTANGLE:
                if (firstClick == null) {
                    // First click records one corner
                    firstClick = p;
                } else {
                    // Second click defines opposite corner and adds rectangle
                    int x = Math.min(firstClick.x, p.x);
                    int y = Math.min(firstClick.y, p.y);
                    int w = Math.abs(p.x - firstClick.x);
                    int h = Math.abs(p.y - firstClick.y);
                    figures.AddRectangle(
                        x, y, w, h,
                        currentOutlineColor, currentFillColor,
                        fillShape, currentStrokeWidth
                    );
                    shapes.lastElement().fillColor = currentFillColor;
                    firstClick = null;
                    currentTool = Tool.NONE;
                }
                break;
            case PATH:
                if (currentPath == null) {
                    // Start a new path
                    currentPath = new Path2D.Double();
                    currentPath.moveTo(p.x, p.y);
                } else {
                    // Continue the existing path
                    currentPath.lineTo(p.x, p.y);
                }
                break;
            default:
                // No drawing tool active
                break;
        }
        repaint();
    }
}

