package at.renehollander.interactivecanvas;

import at.renehollander.interactivecanvas.metrics.Metrics;
import at.renehollander.interactivecanvas.shape.Shape;
import at.renehollander.interactivecanvas.util.Util;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

public class MouseControl implements EventHandler<MouseEvent> {

    private InteractiveCanvas ia;

    private boolean resizing;
    private int corner;

    public MouseControl(InteractiveCanvas ia) {
        this.ia = ia;

        this.ia.setOnMousePressed(this);
        this.ia.setOnMouseDragged(this);
        this.ia.setOnMouseReleased(this);
        this.ia.setOnMouseClicked(this);
    }

    private void handlePressEvent(MouseEvent event) {
        if (event.isConsumed()) {
            return;
        }

        double x = event.getX();
        double y = event.getY();

        if (this.ia.getSelectedShape() != null) {
            for (int i = 0; i < 4; i++) {
                Point2D p = this.ia.getSelectedShape().getMetrics().getPoints()[i];
                Point2D xy1 = p.subtract(InteractiveCanvas.SELECTION_BOX_HALF_SIZE, InteractiveCanvas.SELECTION_BOX_HALF_SIZE).add(0, 0);
                Point2D xy2 = p.subtract(0, InteractiveCanvas.SELECTION_BOX_HALF_SIZE).add(InteractiveCanvas.SELECTION_BOX_HALF_SIZE, 0);
                Point2D xy3 = p.subtract(0, 0).add(InteractiveCanvas.SELECTION_BOX_HALF_SIZE, InteractiveCanvas.SELECTION_BOX_HALF_SIZE);
                Point2D xy4 = p.subtract(InteractiveCanvas.SELECTION_BOX_HALF_SIZE, 0).add(0, InteractiveCanvas.SELECTION_BOX_HALF_SIZE);

                xy1 = this.ia.getSelectedShape().getMetrics().getRotationMatrix().transform(xy1);
                xy2 = this.ia.getSelectedShape().getMetrics().getRotationMatrix().transform(xy2);
                xy3 = this.ia.getSelectedShape().getMetrics().getRotationMatrix().transform(xy3);
                xy4 = this.ia.getSelectedShape().getMetrics().getRotationMatrix().transform(xy4);

                if (Util.polygonContainsPoint(x, y, xy1, xy2, xy3, xy4)) {
                    this.resizing = true;
                    this.corner = i;
                    System.out.println(p);
                } else {
                    Shape target = this.ia.findTarget(event.getX(), event.getY());
                    this.ia.setSelectedShape(target);
                    this.ia.updateCanvas();
                }
            }
        } else {
            Shape target = this.ia.findTarget(event.getX(), event.getY());
            this.ia.setSelectedShape(target);
            this.ia.updateCanvas();
        }
        this.ia.lastMouseLocation = new Point2D(event.getX(), event.getY());
        event.consume();
    }

    private void handleDragEvent(MouseEvent event) {
        if (event.isConsumed()) {
            return;
        }
        double dx = this.ia.lastMouseLocation.getX() - event.getX();
        double dy = this.ia.lastMouseLocation.getY() - event.getY();
        if (resizing) {
            Shape shape = this.ia.getSelectedShape();
            Metrics m = shape.getMetrics();
            if (corner == 0) {
                m.setX(m.getX() - dx);
                m.setY(m.getY() - dy);
                //m.setWidth(m.getWidth() + dx);
                //m.setHeight(m.getHeight() + dy);
            } else if (corner == 2) {
                m.setWidth(m.getWidth() - dx);
                m.setHeight(m.getHeight() - dy);
            }
            this.ia.updateCanvas();
        } else {
            if (this.ia.getSelectedShape() != null) {
                if (ia.getScene().getCursor() != Cursor.MOVE) {
                    ia.getScene().setCursor(Cursor.MOVE);
                }

                Metrics m = this.ia.getSelectedShape().getMetrics();

                m.setX(m.getX() - dx);
                m.setY(m.getY() - dy);

                this.ia.updateCanvas();
            }
        }
        this.ia.lastMouseLocation = new Point2D(event.getX(), event.getY());
        event.consume();
    }

    private void handleReleaseEvent(MouseEvent event) {
        resizing = false;
        this.corner = -1;
        ia.getScene().setCursor(Cursor.DEFAULT);
    }

    private void handleClickEvent(MouseEvent event) {

    }

    @Override
    public void handle(MouseEvent event) {
        EventType<? extends MouseEvent> eventType = event.getEventType();
        if (eventType == MouseEvent.MOUSE_PRESSED) {
            this.handlePressEvent(event);
        } else if (eventType == MouseEvent.MOUSE_DRAGGED) {
            this.handleDragEvent(event);
        } else if (eventType == MouseEvent.MOUSE_RELEASED) {
            this.handleReleaseEvent(event);
        } else if (eventType == MouseEvent.MOUSE_CLICKED) {
            this.handleClickEvent(event);
        }
    }

}
