package fr.npuzzle.gui;

import fr.npuzzle.pathfinder.PathResult;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class Visualizer extends Application
{
    private static Map<PathResult, Long> results;
    private static List<String>          files;

    public static void start(Map<PathResult, Long> results, List<String> files)
    {
        Visualizer.results = results;
        Visualizer.files = files;

        Application.launch();
    }

    @Override
    public void start(Stage stage)
    {
        StackPane root = new StackPane();
        root.getStyleClass().add("root");

        Scene scene = new Scene(root, 800, 800);
        scene.getStylesheets().add("/fr/npuzzle/css/main.css");

        stage.setTitle("npuzzle");
        stage.setScene(scene);
        stage.show();

        SequentialTransition transition = new SequentialTransition();
        for (Map.Entry<PathResult, Long> entry : results.entrySet())
        {
            GridPane grid = new PuzzleGrid(entry.getKey(), transition);
            root.getChildren().add(grid);
            StackPane.setAlignment(grid, Pos.CENTER);
        }

        Button start = new Button("START");
        start.setOnAction(e ->
        {
            transition.play();
            start.setDisable(true);
        });

        root.getChildren().add(start);
        StackPane.setAlignment(start, Pos.BOTTOM_CENTER);

        transition.setOnFinished(e ->
        {
            start.setText("COMPLETED");
        });
    }
}
