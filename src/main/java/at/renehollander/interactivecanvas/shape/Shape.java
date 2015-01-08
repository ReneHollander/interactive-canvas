package at.renehollander.interactivecanvas.shape;

import at.renehollander.interactivecanvas.InteractiveCanvas;
import at.renehollander.interactivecanvas.metrics.Metrics;

public abstract class Shape {

    private Metrics metrics;

    public Shape(Metrics metrics) {
        this.metrics = metrics;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public abstract void draw(InteractiveCanvas ia);

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("x=").append(this.getMetrics().getX());
        sb.append(", y=").append(this.getMetrics().getY());
        sb.append(", width=").append(this.getMetrics().getWidth());
        sb.append(", height=").append(this.getMetrics().getHeight());
        sb.append(", xMax=").append(this.getMetrics().getXMax());
        sb.append(", yMax=").append(this.getMetrics().getYMax());
        sb.append(", centerX=").append(this.getMetrics().getCenterX());
        sb.append(", centerY=").append(this.getMetrics().getCenterY());
        sb.append(", rotation=").append(this.getMetrics().getRotation());
        return sb.toString();
    }

}
