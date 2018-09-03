package fr.npuzzle;

import fr.npuzzle.data.Parameters;
import fr.npuzzle.data.ParsedPuzzle;
import fr.npuzzle.gui.Visualizer;
import fr.npuzzle.pathfinder.Heuristic;
import fr.npuzzle.pathfinder.PathResult;
import fr.npuzzle.pathfinder.Pathfinder;
import fr.npuzzle.pathfinder.SolverCheck;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main
{
    public static void main(String[] args)
    {
        InputFormatter formatter = new InputFormatter(args);

        System.out.println(Arrays.toString(args));

        if (formatter.getParameters().getStatus() != Parameters.ArgumentErrors.NONE)
        {
            System.err.println("error at arguments parsing (" + ParseTokenizer.getErrorMessage(formatter.getParameters().getStatus()) + ")");
            System.exit(-1);
        }

        if (formatter.getParameters().getRandomSize() > 2)
            formatter.getFiles().add("random");

        List<ParsedPuzzleMonad> parsedPuzzles =
                formatter.getFiles().stream().map(formatter::parseFile).collect(Collectors.toList());

        if (parsedPuzzles.stream().anyMatch(ParsedPuzzleMonad::isErrored))
        {
            for (ParsedPuzzleMonad monad : parsedPuzzles)
            {
                if (monad.isErrored())
                    System.err.println("Error while parsing puzzle.\n" + monad.getErrorMessage());
            }
            System.exit(-1);
        }

        List<ParsedPuzzle> puzzles =
                parsedPuzzles.stream().map(ParsedPuzzleMonad::getPuzzle).collect(Collectors.toList());
        Map<PathResult, Long> results = new LinkedHashMap<>();
        Heuristic heuristic = formatter.getParameters().getSpecifiedHeuristic().getHeuristic();
        for (ParsedPuzzle puzzle : puzzles)
        {
            ParsedPuzzle solution = Pathfinder.getSnailSolution(puzzle);

            if (!SolverCheck.isSolvable(puzzle, solution))
            {
                System.err.println("Puzzle has no valid solutions.");
                break;
            }

            long start = System.currentTimeMillis();
            results.put(Pathfinder.astar(puzzle, solution, heuristic), System.currentTimeMillis() - start);
        }

                try
        {
            OutputManagement.output(results, formatter.getFiles(), formatter.getParameters().getOutput());
        } catch (IOException e)
        {
            System.err.println("Error while writing output: " + e.getMessage());
        }

        if(formatter.getParameters().isVisualizerEnabled())
            Visualizer.start(results, formatter.getFiles());
    }
}
