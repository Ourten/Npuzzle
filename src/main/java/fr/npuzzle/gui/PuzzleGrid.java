package fr.npuzzle.gui;

import fr.npuzzle.data.ActionTaken;
import fr.npuzzle.data.ParsedPuzzle;
import fr.npuzzle.pathfinder.PathResult;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class PuzzleGrid extends GridPane
{
    private double     MAX_TIME = 20_000;
    private PathResult pathResult;
    private Label[][]  labelGrid;

    public PuzzleGrid(PathResult pathResult, SequentialTransition transition)
    {
        this.pathResult = pathResult;

        this.setMaxSize(50 * pathResult.getStart().getSize(), 50 * pathResult.getStart().getSize());

        this.labelGrid = new Label[pathResult.getStart().getSize()][];
        for (int x = 0; x < pathResult.getStart().getSize(); x++)
        {
            this.labelGrid[x] = new Label[pathResult.getStart().getSize()];
            for (int y = 0; y < pathResult.getStart().getSize(); y++)
            {
                Label label = new Label(pathResult.getStart().getCell(x, y) == ParsedPuzzle.EMPTY ? "" :
                        String.valueOf(pathResult.getStart().getCell(x, y)));
                label.getStyleClass().add("puzzle-label");

                if(pathResult.getEnd().getCell(x,y)== pathResult.getStart().getCell(x,y))
                    label.getStyleClass().add("complete");

                label.setPrefSize(50, 50);
                labelGrid[x][y] = label;
                this.add(label, x, y);

                label.setAlignment(Pos.CENTER);
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.CENTER);
            }
        }

        for (ActionTaken move : pathResult.getMoves())
        {
            Label from =
                    this.labelGrid[move.getFrom().getX() + move.getAction().getxOffset()][move.getFrom().getY() + move.getAction().getyOffset()];
            TranslateTransition translate =
                    new TranslateTransition(Duration.millis(MAX_TIME / pathResult.getMoves().size()), from);

            translate.setFromX(from.getTranslateX());
            translate.setFromY(from.getTranslateY());
            translate.setToX(from.getTranslateX() - move.getAction().getxOffset() * 50);
            translate.setToY(from.getTranslateY() - move.getAction().getyOffset() * 58);

            translate.setInterpolator(Interpolator.EASE_BOTH);
            translate.setCycleCount(1);
            translate.setOnFinished(e ->
            {
                Label to = labelGrid[move.getFrom().getX()][move.getFrom().getY()];
                to.setText(from.getText());
                from.setText("");
                from.setTranslateX(0);
                from.setTranslateY(0);

                if (pathResult.getEnd().getCell(move.getFrom().getX(), move.getFrom().getY()) == Integer.valueOf(to.getText()))
                    to.getStyleClass().add("complete");
                else
                    to.getStyleClass().remove("complete");
            });

            PauseTransition pauseTransition = new PauseTransition(Duration.millis(1));

            transition.getChildren().addAll(translate, pauseTransition);
        }
    }
}
