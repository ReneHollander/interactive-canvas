package at.renehollander.interactivecanvas.metrics;

import at.renehollander.interactivecanvas.util.Util;
import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;

/**
 * BoundingBox of a Shape
 *
 * @author Rene Hollander
 */
public abstract class Metrics {

    private double x;
    private double y;
    private double width;
    private double height;
    private double xMax;
    private double yMax;

    private double rotation;
    private Rotate rotationMatrix;

    private Point2D p1;
    private Point2D p2;
    private Point2D p3;
    private Point2D p4;

    private Point2D transformedP1;
    private Point2D transformedP2;
    private Point2D transformedP3;
    private Point2D transformedP4;

    public Metrics() {
        this(0, 0, 0, 0);
    }

    public Metrics(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.calculate();
    }

    public void calculate() {
        this.xMax = this.x + this.width;
        this.yMax = this.y + this.height;

        this.p1 = new Point2D(x, y);
        this.p2 = new Point2D(this.xMax, y);
        this.p3 = new Point2D(this.xMax, this.yMax);
        this.p4 = new Point2D(x, this.yMax);

        if (this.rotationMatrix == null) {
            this.rotationMatrix = new Rotate(this.rotation, this.getCenterX(), this.getCenterY());
        } else {
            this.rotationMatrix.setAngle(this.rotation);
            this.rotationMatrix.setPivotX(this.getCenterX());
            this.rotationMatrix.setPivotY(this.getCenterY());
        }

        this.transformedP1 = this.rotationMatrix.transform(this.getP1());
        this.transformedP2 = this.rotationMatrix.transform(this.getP2());
        this.transformedP3 = this.rotationMatrix.transform(this.getP3());
        this.transformedP4 = this.rotationMatrix.transform(this.getP4());
    }

    public double getCenterX() {
        return this.x + this.width / 2;
    }

    public double getCenterY() {
        return this.y + this.height / 2;
    }

    public boolean inBounds(double x, double y) {
        if (this.getWidth() <= 0 || this.getHeight() <= 0) {
            return false;
        }

        return Util.polygonContainsPoint(x, y, transformedP1, transformedP2, transformedP3, transformedP4);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        this.calculate();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        this.calculate();
    }

    public void setCoords(Point2D point) {
        this.setX(point.getX());
        this.setY(point.getY());
    }

    public Point2D getCoords() {
        return new Point2D(this.getX(), this.getY());
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
        this.calculate();
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        this.calculate();
    }

    public double getXMax() {
        return xMax;
    }

    public double getYMax() {
        return yMax;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
        this.calculate();
    }

    public Rotate getRotationMatrix() {
        return rotationMatrix;
    }

    public Point2D getP1() {
        return p1;
    }

    public Point2D getP2() {
        return p2;
    }

    public Point2D getP3() {
        return p3;
    }

    public Point2D getP4() {
        return p4;
    }

    public Point2D getTransformedP1() {
        return transformedP1;
    }

    public Point2D getTransformedP2() {
        return transformedP2;
    }

    public Point2D getTransformedP3() {
        return transformedP3;
    }

    public Point2D getTransformedP4() {
        return transformedP4;
    }

    public Point2D[] getPoints() {
        return new Point2D[]{p1, p2, p3, p4};
    }

    public Point2D[] getTransformedPoints() {
        return new Point2D[]{transformedP1, transformedP2, transformedP3, transformedP4};
    }

    public abstract boolean contains(double x, double y);


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Metrics)) return false;

        Metrics metrics = (Metrics) o;

        if (Double.compare(metrics.height, height) != 0) return false;
        if (Double.compare(metrics.rotation, rotation) != 0) return false;
        if (Double.compare(metrics.width, width) != 0) return false;
        if (Double.compare(metrics.x, x) != 0) return false;
        if (Double.compare(metrics.xMax, xMax) != 0) return false;
        if (Double.compare(metrics.y, y) != 0) return false;
        if (Double.compare(metrics.yMax, yMax) != 0) return false;
        if (p1 != null ? !p1.equals(metrics.p1) : metrics.p1 != null) return false;
        if (p2 != null ? !p2.equals(metrics.p2) : metrics.p2 != null) return false;
        if (p3 != null ? !p3.equals(metrics.p3) : metrics.p3 != null) return false;
        if (p4 != null ? !p4.equals(metrics.p4) : metrics.p4 != null) return false;
        if (rotationMatrix != null ? !rotationMatrix.equals(metrics.rotationMatrix) : metrics.rotationMatrix != null)
            return false;
        if (transformedP1 != null ? !transformedP1.equals(metrics.transformedP1) : metrics.transformedP1 != null)
            return false;
        if (transformedP2 != null ? !transformedP2.equals(metrics.transformedP2) : metrics.transformedP2 != null)
            return false;
        if (transformedP3 != null ? !transformedP3.equals(metrics.transformedP3) : metrics.transformedP3 != null)
            return false;
        if (transformedP4 != null ? !transformedP4.equals(metrics.transformedP4) : metrics.transformedP4 != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(width);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(height);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(xMax);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(yMax);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rotation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (rotationMatrix != null ? rotationMatrix.hashCode() : 0);
        result = 31 * result + (p1 != null ? p1.hashCode() : 0);
        result = 31 * result + (p2 != null ? p2.hashCode() : 0);
        result = 31 * result + (p3 != null ? p3.hashCode() : 0);
        result = 31 * result + (p4 != null ? p4.hashCode() : 0);
        result = 31 * result + (transformedP1 != null ? transformedP1.hashCode() : 0);
        result = 31 * result + (transformedP2 != null ? transformedP2.hashCode() : 0);
        result = 31 * result + (transformedP3 != null ? transformedP3.hashCode() : 0);
        result = 31 * result + (transformedP4 != null ? transformedP4.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Metrics{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", xMax=").append(xMax);
        sb.append(", yMax=").append(yMax);
        sb.append(", rotation=").append(rotation);
        sb.append(", rotationMatrix=").append(rotationMatrix);
        sb.append(", p1=").append(p1);
        sb.append(", p2=").append(p2);
        sb.append(", p3=").append(p3);
        sb.append(", p4=").append(p4);
        sb.append(", transformedP1=").append(transformedP1);
        sb.append(", transformedP2=").append(transformedP2);
        sb.append(", transformedP3=").append(transformedP3);
        sb.append(", transformedP4=").append(transformedP4);
        sb.append('}');
        return sb.toString();
    }
}