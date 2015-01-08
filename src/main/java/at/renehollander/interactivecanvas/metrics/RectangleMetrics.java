package at.renehollander.interactivecanvas.metrics;

public class RectangleMetrics extends Metrics {

    public RectangleMetrics() {
    }

    public RectangleMetrics(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public boolean contains(double x, double y) {
        return this.inBounds(x, y);
    }
}
