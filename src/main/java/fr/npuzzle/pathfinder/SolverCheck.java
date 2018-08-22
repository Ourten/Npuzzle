package fr.npuzzle.pathfinder;

import fr.npuzzle.data.ParsedPuzzle;

import java.util.Arrays;

public class SolverCheck
{
    public static boolean isSolvable(ParsedPuzzle puzzle)
    {
        int inversions = calcInversions(Arrays.stream(puzzle.getGrid()).flatMap(row -> Arrays.stream(row).boxed())
                .toArray(Integer[]::new));
        if (puzzle.getSize() % 2 != 0 && inversions % 2 != 0)
            return false;
        return puzzle.getSize() % 2 != 0 || inversions % 2 != 0;
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
