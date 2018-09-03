package fr.npuzzle.gui;

import fr.npuzzle.pathfinder.PathResult;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
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
        HBox root = new HBox();
        root.getStyleClass().add("root");

        Scene scene = new Scene(root, 1000, 400);

        stage.setTitle("npuzzle");
        stage.setScene(scene);
        stage.show();
    }
}
