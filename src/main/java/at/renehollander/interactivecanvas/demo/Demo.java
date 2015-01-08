package at.renehollander.interactivecanvas.demo;

import at.renehollander.interactivecanvas.InteractiveCanvas;
import at.renehollander.interactivecanvas.shape.Rectangle;
import at.renehollander.interactivecanvas.shape.Shape;
import at.renehollander.interactivecanvas.util.Util;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class Demo extends Application {

    public static void main(String[] args) {
        System.out.println(com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());
        System.out.println(System.getProperties().get("javafx.runtime.version"));
        Application.launch(Demo.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        InteractiveCanvas interactiveCanvas = new InteractiveCanvas(800, 600);
        Scene scene = new Scene(new Pane(interactiveCanvas));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Demo");
        primaryStage.show();

        Random r = new Random();
        Shape shape = new Rectangle(100, 100, 200, 200, Util.randomColor());
        shape.getMetrics().setRotation(20);
        interactiveCanvas.getShapes().add(shape);
/*
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            Shape shape = shapeFactory.createRectangle(r.nextInt(800), r.nextInt(600), r.nextInt(100), r.nextInt(100), Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            shape.getMetrics().setRotation(r.nextInt(360));
            interactiveCanvas.getShapes().add(shape);
        }
*/
        interactiveCanvas.updateCanvas();

/*
        Shape shape1 = new Ellipse(50, 50, 100, 200, Color.BLUE);
        shape1.getMetrics().setRotation(20);
        interactiveCanvas.getShapes().add(shape1);

        Shape shape2 = new Rectangle(300, 300, 100, 200, Color.RED);
        shape2.getMetrics().setRotation(45);
        interactiveCanvas.getShapes().add(shape2);
*/
        interactiveCanvas.updateCanvas();
        /*Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                shape1.getMetrics().setRotation(shape1.getMetrics().getRotation() + 1);
                shape2.getMetrics().setRotation(shape2.getMetrics().getRotation() + 1);
                Platform.runLater(interactiveCanvas::updateCanvas);
            }
        });
        t.setDaemon(true);
        t.start();
        */
    }
}
