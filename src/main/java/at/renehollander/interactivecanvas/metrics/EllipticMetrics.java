package at.renehollander.interactivecanvas.metrics;

import com.sun.javafx.geom.Ellipse2D;

public class EllipticMetrics extends Metrics {
    public EllipticMetrics() {
    }

    public EllipticMetrics(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public boolean contains(double x, double y) {
        // TODO fix
        if (this.getWidth() <= 0 || this.getHeight() <= 0) {
            return false;
        }

        double normx = (x - this.getX()) / this.getWidth() - 0.5d;
        double normy = (y - this.getY()) / this.getHeight() - 0.5d;
        return (normx * normx + normy * normy) < 0.25d;
    }
}
