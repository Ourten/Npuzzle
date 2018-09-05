package fr.npuzzle.gui;

import fr.npuzzle.pathfinder.PathResult;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

        if (!files.isEmpty() && !results.isEmpty())
            Application.launch();
        else
            System.err.println("Cannot start visualizer without input!");
    }

    @Override
    public void start(Stage stage)
    {
        StackPane root = new StackPane();
        root.getStyleClass().add("root");

        Scene scene = new Scene(root, 350, 350);
        scene.getStylesheets().add("/fr/npuzzle/css/main.css");

        stage.setTitle("npuzzle");
        stage.setScene(scene);
        stage.show();

        String titleString = files.get(0);

        if (titleString.contains("/"))
            titleString = titleString.substring(titleString.lastIndexOf("/") + 1);
        Label title = new Label(titleString);
        root.getChildren().add(title);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        title.setId("title-label");

        SequentialTransition transition = new SequentialTransition();

        boolean first = true;
        for (Map.Entry<PathResult, Long> entry : results.entrySet())
        {
            if (!first)
                break;
            first = false;
            GridPane grid = new PuzzleGrid(entry.getKey(), transition);
            root.getChildren().add(grid);
            StackPane.setAlignment(grid, Pos.CENTER);
        }

        Button start = new Button("START");
        start.setId("start");
        start.setOnAction(e ->
        {
            transition.play();
            start.setDisable(true);
        });

        root.getChildren().add(start);
        StackPane.setAlignment(start, Pos.BOTTOM_CENTER);

        transition.setOnFinished(e ->
        {
            root.getStyleClass().add("complete");
            start.setText("COMPLETED");
        });
    }
}
