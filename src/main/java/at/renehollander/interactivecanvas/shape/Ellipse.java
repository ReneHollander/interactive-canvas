package at.renehollander.interactivecanvas.shape;

import at.renehollander.interactivecanvas.InteractiveCanvas;
import at.renehollander.interactivecanvas.metrics.EllipticMetrics;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Ellipse extends Shape {
    private Paint paint;

    public Ellipse(Color paint) {
        this(0, 0, 0, 0, paint);
    }

    public Ellipse(double x, double y, double width, double height, Paint paint) {
        super(new EllipticMetrics(x, y, width, height));
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    @Override
    public void draw(InteractiveCanvas ia) {
        ia.gc().setFill(this.getPaint());
        ia.gc().fillOval(this.getMetrics().getX(), this.getMetrics().getY(), this.getMetrics().getWidth(), this.getMetrics().getHeight());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Ellipse{");
        sb.append(super.toString());
        sb.append(", paint=").append(paint);
        sb.append('}');
        return sb.toString();
    }
}
