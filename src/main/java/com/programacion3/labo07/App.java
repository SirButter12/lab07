package com.programacion3.labo07;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX App - Fixed Version
 * 
 */
public class App extends Application {

    private static final double TOTAL_DURATION_SECONDS = 12.0;
    private static final double PHASE_DURATION_SECONDS = TOTAL_DURATION_SECONDS / 4.0;

    private ParallelTransition mainParallelTransition;
    private PathTransition transA;
    private SequentialTransition transB;

    private Rectangle objA;
    private Ellipse objB;

    @Override
    public void start(Stage primaryStage) {
        Pane animationPane = new Pane();
        animationPane.setPrefSize(600, 400);

        double margin = 50.0;
        double width = 500.0;
        double height = 280.0;

        double ax = margin, ay = margin;
        double bx = margin + width, by = margin;
        double cx = margin + width, cy = margin + height;
        double dx = margin, dy = margin + height;

        Polyline rectangularPath = new Polyline(
            ax, ay,
            bx, by,
            cx, cy,
            dx, dy,
            ax, ay
        );
        rectangularPath.setStroke(Color.BLACK);
        rectangularPath.setStrokeWidth(2);

        objA = new Rectangle(15, 15, Color.DODGERBLUE);
        objA.setStroke(Color.BLACK);

        double centerX = margin + width / 2.0;
        double centerY = margin + height / 2.0;
        objB = new Ellipse(centerX, centerY, 50, 25);
        objB.setFill(Color.CORAL);
        objB.setStroke(Color.BLACK);
        
        animationPane.getChildren().addAll(rectangularPath, objA, objB);

        transA = new PathTransition();
        transA.setDuration(Duration.seconds(TOTAL_DURATION_SECONDS));
        transA.setPath(rectangularPath);
        transA.setNode(objA);
        transA.setCycleCount(1);

        FadeTransition fadeB = new FadeTransition(Duration.seconds(PHASE_DURATION_SECONDS), objB);
        fadeB.setFromValue(1.0);
        fadeB.setToValue(0.2);

        ScaleTransition scaleB = new ScaleTransition(Duration.seconds(PHASE_DURATION_SECONDS), objB);
        scaleB.setFromX(1.0);
        scaleB.setFromY(1.0);
        scaleB.setToX(2.0);
        scaleB.setToY(2.0);

        RotateTransition rotateB = new RotateTransition(Duration.seconds(PHASE_DURATION_SECONDS), objB);
        rotateB.setFromAngle(0.0);
        rotateB.setToAngle(360.0);

        TranslateTransition translateB = new TranslateTransition(Duration.seconds(PHASE_DURATION_SECONDS), objB);
        translateB.setByY(-80.0);

        PauseTransition delayEnd = new PauseTransition(Duration.seconds(1.0));

        transB = new SequentialTransition(fadeB, scaleB, rotateB, translateB, delayEnd);

        mainParallelTransition = new ParallelTransition(transA, transB);

        Button btnStart = new Button("Start");
        Button btnReset = new Button("Reset");
        Button btnExit = new Button("Exit");

        btnStart.setPrefWidth(80);
        btnReset.setPrefWidth(80);
        btnExit.setPrefWidth(80);

        btnStart.setOnAction(e -> {
            if (mainParallelTransition.getStatus() != Animation.Status.RUNNING) {
                mainParallelTransition.play();
            }
        });

        btnReset.setOnAction(e -> resetAnimation());

        btnExit.setOnAction(e -> Platform.exit());

        HBox buttonBox = new HBox(15, btnStart, btnReset, btnExit);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-padding: 15; -fx-border-color: black; -fx-border-width: 1px;");

        BorderPane root = new BorderPane();
        root.setCenter(animationPane);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 620, 480);
        primaryStage.setTitle("JavaFX Lab 07 - Path & Sequential Animations");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void resetAnimation() {
        mainParallelTransition.stop();

        objA.setTranslateX(0);
        objA.setTranslateY(0);

        objB.setOpacity(1.0);
        objB.setScaleX(1.0);
        objB.setScaleY(1.0);
        objB.setRotate(0.0);
        objB.setTranslateX(0);
        objB.setTranslateY(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}