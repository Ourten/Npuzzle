package fr.npuzzle.pathfinder;

import fr.npuzzle.data.ParsedPuzzle;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SolverCheck
{
    public static boolean isSolvable(ParsedPuzzle start, ParsedPuzzle desired)
    {
        int startInversions = calcInversions(Arrays.stream(start.getGrid()).flatMap(row -> Arrays.stream(row).boxed())
                .toArray(Integer[]::new));
        int desiredInversions =
                calcInversions(Arrays.stream(desired.getGrid()).flatMap(row -> Arrays.stream(row).boxed())
                        .toArray(Integer[]::new));

        if (start.getSize() % 2 == 0)
        {
            startInversions += Arrays.stream(start.getGrid())
                    .flatMap(row -> Arrays.stream(row).boxed()).collect(Collectors.toList())
                    .indexOf(ParsedPuzzle.EMPTY) / start.getSize();
            desiredInversions += Arrays.stream(desired.getGrid())
                    .flatMap(row -> Arrays.stream(row).boxed()).collect(Collectors.toList())
                    .indexOf(ParsedPuzzle.EMPTY) / start.getSize();
        }

        return (startInversions % 2 == desiredInversions % 2);
    }

    private static int calcInversions(Integer... values)
    {
        int inversions = 0;

        for (int i = 0; i < values.length; i++)
        {
            if (i == values.length - 1)
                break;

            if (values[i] < values[i + 1])
                inversions++;
        }
        return inversions;
    }
}
