package fr.npuzzle;

import fr.npuzzle.data.ActionTaken;
import fr.npuzzle.data.State;
import fr.npuzzle.pathfinder.PathResult;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OutputManagement
{
    private static String getSteps(PathResult result)
    {
        State state;
        String currentOutput = "";

        state = new State(result.getStart(), null, null);
        for (ActionTaken action : result.getMoves())
        {
            Optional<State> current = action.getAction().move(state);
            if (current.isPresent())
                state = current.get();
            currentOutput = currentOutput + "\n" + state.getData().toString();
        }
        return (currentOutput);
    }

    private static String getConsoleString(PathResult result, long time)
    {
        return String.format("Took: %s ms\n" +
                        "%s\n" +
                        "Complexity in Time: %s\n" +
                        "Complexity in Size: %s\n" +
                        "Moves: %s\n",
                NumberFormat.getIntegerInstance().format(time),
                result.getMoves(),
                NumberFormat.getIntegerInstance().format(result.getTimeComplexity()),
                NumberFormat.getIntegerInstance().format(result.getSizeComplexity()),
                result.getMoves().size());
    }

    public static void output(Map<PathResult, Long> results, List<String> files, File output) throws IOException
    {
        int index = 0;
        for (Map.Entry<PathResult, Long> entry : results.entrySet())
        {
            String consoleOutput = getConsoleString(entry.getKey(), entry.getValue());

            System.out.println("\nPuzzle: " + files.get(index) + "\n" + consoleOutput);

            Files.write(Paths.get(output.getAbsolutePath()),
                    (getSteps(entry.getKey()) + "\n" + consoleOutput + "*************************************").getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND);
            index++;
        }
    }
}
