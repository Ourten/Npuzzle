package fr.npuzzle;

import fr.npuzzle.data.ActionTaken;
import fr.npuzzle.data.Parameters;
import fr.npuzzle.data.ParsedPuzzle;
import fr.npuzzle.data.State;
import fr.npuzzle.pathfinder.Heuristic;
import fr.npuzzle.pathfinder.Heuristics;
import fr.npuzzle.pathfinder.PathResult;
import fr.npuzzle.pathfinder.Pathfinder;

import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;

public class Main
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
            currentOutput = currentOutput + "\n\n" + state.getData().toString();
        }
        return (currentOutput);
    }

    private static void execute(List<String> files, InputFormatter formatter)
    {
        int i = 0;
        ParsedPuzzleMonad currentPuzzle;
        ParsedPuzzle solution;
        PathResult result;
        String currentOutput;

        while (i < files.size())
        {
            currentPuzzle = formatter.parseFile(files.get(i));
            currentOutput = "\n" + files.get(i) + " :";
            if (currentPuzzle.getErrorType() == ParsedPuzzleMonad.ErrorType.NONE)
            {
                solution = Pathfinder.getSnailSolution(currentPuzzle.getPuzzle());
                result = Pathfinder.astar(currentPuzzle.getPuzzle(),
                        solution,
                        Heuristics::outOfRowAndColumn);
                if (formatter.getParameters().getOutput() != null)
                    currentOutput = currentOutput + getSteps(currentPuzzle, result);
            }
            else
            {
                currentOutput = currentOutput + "\n" + currentPuzzle.getErrorMessage();
            }
            i++;
            System.out.printf(currentOutput + "\n*************************************");
        }
    }

    public static void main(String[] args)
    {
        /*InputFormatter formatter = new InputFormatter(args);
        System.out.printf(formatter.getFiles().toString());
        ParsedPuzzleMonad temp = formatter.parseFile(formatter.getFiles().get(2));
        if (temp.getErrorType() == ParsedPuzzleMonad.ErrorType.NONE)
            System.out.printf(temp.getPuzzle().toString());
        else
            System.out.printf(temp.getErrorMessage());*/

        InputFormatter formatter = new InputFormatter(args);

        System.out.println(Arrays.toString(args));
        if (formatter.getParameters().getStatus() == Parameters.ArgumentErrors.NONE)
        {
            execute(formatter.getFiles(), formatter);

        }
        else
            System.out.printf("error at arguments parsing (" + ParseTokenizer.getErrorMessage(formatter.getParameters().getStatus()) + ")");
    }
}
