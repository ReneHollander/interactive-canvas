package at.renehollander.interactivecanvas.util;

import javafx.geometry.Point2D;

public class ShapeUtil {

    public static boolean pointInPolygon(Point2D[] points, Point2D test) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.length - 1; i < points.length; j = i++) {
            Point2D pi = points[i];
            Point2D pj = points[j];
            String in = "if ((pi.getY() > y) != (pj.getY() > y) && (x < (pj.getX() - pi.getX()) * (y - pi.getY()) / (pj.getY() - pi.getY()) + pi.getX())) {\n" +
                    "  result = !result;\n" +
                    "}";
            in = in.replace("pi", "p" + (i + 1));
            in = in.replace("pj", "p" + (j + 1));
            //System.out.println(in);
            System.out.println("i=" + i + ", j=" + j);
            if ((pi.getY() > test.getY()) != (pj.getY() > test.getY()) && (test.getX() < (pj.getX() - pi.getX()) * (test.getY() - pi.getY()) / (pj.getY() - pi.getY()) + pi.getX())) {
                result = !result;
            }
        }
        return result;
    }

}
