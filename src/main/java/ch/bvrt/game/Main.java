package ch.bvrt.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        final int winSize = 800;

        primaryStage.setTitle("Game");
        Canvas canvas = new Canvas(winSize, winSize);
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new Pane(canvas));
        primaryStage.setScene(scene);

        Game game = new Game(winSize);
        Thread gameThread = new Thread(() -> {
            game.Run(graphicsContext);
        });

        gameThread.start();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}