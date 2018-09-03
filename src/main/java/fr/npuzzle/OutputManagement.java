package fr.npuzzle;

import fr.npuzzle.data.ActionTaken;
import fr.npuzzle.data.Parameters;
import fr.npuzzle.data.ParsedPuzzle;
import fr.npuzzle.data.State;
import fr.npuzzle.pathfinder.Heuristics;
import fr.npuzzle.pathfinder.PathResult;
import fr.npuzzle.pathfinder.Pathfinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;

public class OutputManagement
{
    private static String getSteps(ParsedPuzzleMonad currentPuzzle, PathResult result)
    {
        State state;
        String currentOutput = "";

        state = new State(currentPuzzle.getPuzzle(), null, null);
        for (ActionTaken action : result.getMoves())
        {
            Optional<State> current = action.getAction().move(state);
            if(current.isPresent())
                state = current.get();
            currentOutput = currentOutput + "\n" + state.getData().toString();
        }
        return (currentOutput);
    }

    private static String getConsoleString(PathResult result, long start) {
        String ret = "";
        ret = ret + "Took: " + NumberFormat.getIntegerInstance().format(System.currentTimeMillis() - start) +
                " ms\n";
        ret = ret + result.getMoves() + "\n";
        ret = ret + "Complexity in Time: " + NumberFormat.getIntegerInstance().format(result.getTimeComplexity()) + "\n";
        ret = ret + "Complexity in Size: " + NumberFormat.getIntegerInstance().format(result.getSizeComplexity()) + "\n";
        ret = ret + "Moves: " + result.getMoves().size() + "\n";
        return (ret);
    }

    public static void execute(List<String> files, InputFormatter formatter)
    {
        int i = 0;
        ParsedPuzzleMonad currentPuzzle;
        ParsedPuzzle solution;
        PathResult result;
        String currentOutput;
        long start;
        String tmp;

        if (formatter.getParameters().getRandomSize() > 2)
            files.add("random");
        while (i < files.size())
        {
            currentPuzzle = formatter.parseFile(files.get(i));
            currentOutput = "\n" + files.get(i) + " :";
            if (currentPuzzle.getErrorType() == ParsedPuzzleMonad.ErrorType.NONE)
            {
                solution = Pathfinder.getSnailSolution(currentPuzzle.getPuzzle());
                start = System.currentTimeMillis();
                result = Pathfinder.astar(currentPuzzle.getPuzzle(),
                        solution,
                        Heuristics::outOfRowAndColumn);
                if (formatter.getParameters().getOutput() != null)
                    currentOutput = currentOutput + getSteps(currentPuzzle, result);
                tmp = getConsoleString(result, start);
                System.out.printf("\n\n" + "Puzzle : " + files.get(i) + "\n" + tmp);
                currentOutput = currentOutput + "\n" + tmp;
            }
            else
            {
                currentOutput = currentOutput + "\n" + currentPuzzle.getErrorMessage();
                System.out.printf("\n" + currentPuzzle.getErrorMessage());
            }
            i++;
            System.out.printf("\n*************************************");
            try {
                Files.write(Paths.get(formatter.getParameters().getOutput().getAbsolutePath()), (currentOutput + "\n" + "*************************************").getBytes(), StandardOpenOption.APPEND);
            }catch (IOException e) {
                System.out.printf("\nERROR WRITING TO OUTPUT FILE.");
            }
        }
    }
}
