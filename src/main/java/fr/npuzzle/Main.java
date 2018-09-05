package fr.npuzzle;

import fr.npuzzle.data.Parameters;
import fr.npuzzle.data.ParsedPuzzle;
import fr.npuzzle.gui.Visualizer;
import fr.npuzzle.pathfinder.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main
{
    public static Parameters PARAMETERS;

    public static void main(String[] args)
    {

        if (args.length <= 0)
        {
            System.out.println(ParseTokenizer.getUsage());
            return;
        }
        InputFormatter formatter = new InputFormatter(args);
        PARAMETERS = formatter.getParameters();

        System.out.println(Arrays.toString(args));

        if (PARAMETERS.getStatus() != Parameters.ArgumentErrors.NONE)
        {
            System.err.println("Error at arguments parsing (" + ParseTokenizer.getErrorMessage(PARAMETERS.getStatus()) + ")");
            System.out.println(ParseTokenizer.getUsage());
            System.exit(-1);
        }

        if (PARAMETERS.getRandomSize() > 2)
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
        Heuristic heuristic = PARAMETERS.getSpecifiedHeuristic().getHeuristic();

        int index = 0;
        for (ParsedPuzzle puzzle : puzzles)
        {
            ParsedPuzzle solution = Pathfinder.getSnailSolution(puzzle);

            if (!SolverCheck.isSolvable(puzzle, solution))
            {
                System.err.println("Puzzle " + formatter.getFiles().get(index) + " has no valid solutions.");
                System.exit(-1);
            }

            long start = System.currentTimeMillis();
            results.put(Pathfinder.astar(puzzle, solution, Heuristics::manhattenLinearConflict), System.currentTimeMillis() - start);
            index++;
        }

        try
        {
            OutputManagement.output(results, formatter.getFiles(), PARAMETERS.getOutput());
        } catch (IOException e)
        {
            System.err.println("Error while writing output: " + e.getMessage());
        }

        if (PARAMETERS.isVisualizerEnabled())
            Visualizer.start(results, formatter.getFiles());
    }
}
