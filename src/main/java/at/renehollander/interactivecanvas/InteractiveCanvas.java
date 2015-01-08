package at.renehollander.interactivecanvas;

import at.renehollander.interactivecanvas.shape.Shape;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public class InteractiveCanvas extends Canvas {

    public static final boolean DEBUG_MODE = true;

    public static final Paint SELECTION_STROKE_COLOR = Color.BLACK;
    public static final double SELECTION_STROKE_WIDTH = 2;
    public static final Paint SELECTION_BOX_COLOR = Color.GRAY;
    public static final double SELECTION_BOX_SIZE = 10;
    public static final double SELECTION_BOX_HALF_SIZE = SELECTION_BOX_SIZE / 2;

    private List<Shape> shapes;
    private Shape selectedShape;

    public InteractiveCanvas() {
        this(0, 0);
    }

    protected Point2D lastMouseLocation;

    public InteractiveCanvas(double width, double height) {
        super(width, height);
        this.shapes = new ArrayList<Shape>();

        new MouseControl(this);
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(Shape selectedShape) {
        this.selectedShape = selectedShape;
    }

    public List<Shape> getShapes() {
        return this.shapes;
    }

    public void updateCanvas() {
        this.gc().clearRect(0, 0, this.getWidth(), this.getHeight());
        this.getShapes().forEach(this::renderShape);
    }

    private Color[] randomColors = new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.VIOLET};

    private void renderShape(Shape shape) {
        this.gc().save();

        this.gc().translate(shape.getMetrics().getCenterX(), shape.getMetrics().getCenterY());
        this.gc().rotate(shape.getMetrics().getRotation());
        this.gc().translate(-shape.getMetrics().getCenterX(), -shape.getMetrics().getCenterY());

        shape.draw(this);

        if (this.getSelectedShape() == shape) {
            this.drawSelection(shape);
        }

        this.gc().restore();
        if (DEBUG_MODE) {
            this.gc().save();

            this.gc().setLineWidth(2);
            this.gc().setStroke(Color.VIOLET);
            this.gc().beginPath();
            this.gc().moveTo(shape.getMetrics().getTransformedP1().getX(), shape.getMetrics().getTransformedP1().getY());
            for (Point2D p : shape.getMetrics().getTransformedPoints()) {
                this.gc().lineTo(p.getX(), p.getY());
            }
            this.gc().closePath();
            this.gc().stroke();

            this.gc().setLineWidth(5);
            this.gc().setStroke(Color.BLUE);
            this.gc().strokeLine(shape.getMetrics().getTransformedP1().getX(), shape.getMetrics().getTransformedP1().getY(), shape.getMetrics().getTransformedP1().getX(), shape.getMetrics().getTransformedP1().getY());
            this.gc().setStroke(Color.ORANGE);
            this.gc().strokeLine(shape.getMetrics().getTransformedP2().getX(), shape.getMetrics().getTransformedP2().getY(), shape.getMetrics().getTransformedP2().getX(), shape.getMetrics().getTransformedP2().getY());
            this.gc().setStroke(Color.YELLOW);
            this.gc().strokeLine(shape.getMetrics().getTransformedP3().getX(), shape.getMetrics().getTransformedP3().getY(), shape.getMetrics().getTransformedP3().getX(), shape.getMetrics().getTransformedP3().getY());
            this.gc().setStroke(Color.GREEN);
            this.gc().strokeLine(shape.getMetrics().getTransformedP4().getX(), shape.getMetrics().getTransformedP4().getY(), shape.getMetrics().getTransformedP4().getX(), shape.getMetrics().getTransformedP4().getY());

            this.gc().restore();

            this.gc().save();

            for (int i = 0; i < shape.getMetrics().getPoints().length; i++) {
                Point2D p = shape.getMetrics().getPoints()[i];

                Point2D xy1 = p.subtract(InteractiveCanvas.SELECTION_BOX_HALF_SIZE, InteractiveCanvas.SELECTION_BOX_HALF_SIZE).add(0, 0);
                Point2D xy2 = p.subtract(0, InteractiveCanvas.SELECTION_BOX_HALF_SIZE).add(InteractiveCanvas.SELECTION_BOX_HALF_SIZE, 0);
                Point2D xy3 = p.subtract(0, 0).add(InteractiveCanvas.SELECTION_BOX_HALF_SIZE, InteractiveCanvas.SELECTION_BOX_HALF_SIZE);
                Point2D xy4 = p.subtract(InteractiveCanvas.SELECTION_BOX_HALF_SIZE, 0).add(0, InteractiveCanvas.SELECTION_BOX_HALF_SIZE);

                xy1 = shape.getMetrics().getRotationMatrix().transform(xy1);
                xy2 = shape.getMetrics().getRotationMatrix().transform(xy2);
                xy3 = shape.getMetrics().getRotationMatrix().transform(xy3);
                xy4 = shape.getMetrics().getRotationMatrix().transform(xy4);

                this.gc().setLineWidth(5);
                this.gc().setStroke(randomColors[0]);
                this.gc().strokeLine(xy1.getX(), xy1.getY(), xy1.getX(), xy1.getY());
                this.gc().setStroke(randomColors[1]);
                this.gc().strokeLine(xy2.getX(), xy2.getY(), xy2.getX(), xy2.getY());
                this.gc().setStroke(randomColors[2]);
                this.gc().strokeLine(xy3.getX(), xy3.getY(), xy3.getX(), xy3.getY());
                this.gc().setStroke(randomColors[3]);
                this.gc().strokeLine(xy4.getX(), xy4.getY(), xy4.getX(), xy4.getY());

            }
            this.gc().restore();
        }
    }

    public void drawSelection(Shape shape) {
        this.gc().setStroke(SELECTION_STROKE_COLOR);
        this.gc().setLineWidth(SELECTION_STROKE_WIDTH);
        this.gc().strokeRect(shape.getMetrics().getX(), shape.getMetrics().getY(), shape.getMetrics().getWidth(), shape.getMetrics().getHeight());
        this.gc().setFill(SELECTION_BOX_COLOR);
        this.gc().fillRect(shape.getMetrics().getX() - SELECTION_BOX_HALF_SIZE, shape.getMetrics().getY() - SELECTION_BOX_HALF_SIZE, SELECTION_BOX_SIZE, SELECTION_BOX_SIZE);
        this.gc().fillRect(shape.getMetrics().getXMax() - SELECTION_BOX_HALF_SIZE, shape.getMetrics().getY() - SELECTION_BOX_HALF_SIZE, SELECTION_BOX_SIZE, SELECTION_BOX_SIZE);
        this.gc().fillRect(shape.getMetrics().getX() - SELECTION_BOX_HALF_SIZE, shape.getMetrics().getYMax() - SELECTION_BOX_HALF_SIZE, SELECTION_BOX_SIZE, SELECTION_BOX_SIZE);
        this.gc().fillRect(shape.getMetrics().getXMax() - SELECTION_BOX_HALF_SIZE, shape.getMetrics().getYMax() - SELECTION_BOX_HALF_SIZE, SELECTION_BOX_SIZE, SELECTION_BOX_SIZE);
    }

    public Shape findTarget(double x, double y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (shape.getMetrics().contains(x, y)) {
                return shape;
            }
        }
        return null;
    }

    public GraphicsContext gc() {
        return this.getGraphicsContext2D();
    }


}
