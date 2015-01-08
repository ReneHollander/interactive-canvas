package at.renehollander.interactivecanvas.util;

import at.renehollander.interactivecanvas.shape.Rectangle;
import at.renehollander.interactivecanvas.shape.Shape;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.Random;

public class Util {

    private static final Random random = new Random();

    public static boolean polygonContainsPoint(double x, double y, Point2D p1, Point2D p2, Point2D p3, Point2D p4) {
        boolean result = false;
        if ((p1.getY() > y) != (p4.getY() > y) && (x < (p4.getX() - p1.getX()) * (y - p1.getY()) / (p4.getY() - p1.getY()) + p1.getX())) {
            result = !result;
        }
        if ((p2.getY() > y) != (p1.getY() > y) && (x < (p1.getX() - p2.getX()) * (y - p2.getY()) / (p1.getY() - p2.getY()) + p2.getX())) {
            result = !result;
        }
        if ((p3.getY() > y) != (p2.getY() > y) && (x < (p2.getX() - p3.getX()) * (y - p3.getY()) / (p2.getY() - p3.getY()) + p3.getX())) {
            result = !result;
        }
        if ((p4.getY() > y) != (p3.getY() > y) && (x < (p3.getX() - p4.getX()) * (y - p4.getY()) / (p3.getY() - p4.getY()) + p4.getX())) {
            result = !result;
        }
        return result;
    }

    public static Color randomColor() {
        return Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

}
