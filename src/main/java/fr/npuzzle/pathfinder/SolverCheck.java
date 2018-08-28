package fr.npuzzle.pathfinder;

import fr.npuzzle.data.ParsedPuzzle;

import java.util.Arrays;

public class SolverCheck
{
    public static boolean isSolvable(ParsedPuzzle start, ParsedPuzzle desired)
    {
        int startInversions = calcInversions(from2D(start.getGrid()));
        int desiredInversions = calcInversions(from2D(desired.getGrid()));

        if (start.getSize() % 2 == 0)
        {
            startInversions += Arrays.asList(from2D(start.getGrid())).indexOf(ParsedPuzzle.EMPTY) / start.getSize();
            desiredInversions += Arrays.asList(from2D(desired.getGrid())).indexOf(ParsedPuzzle.EMPTY) / start.getSize();
        }

        return (startInversions % 2 == desiredInversions % 2);
    }

    private static Byte[] from2D(byte[][] matrix)
    {
        Byte[] result = new Byte[matrix.length * matrix.length];

        int k = 0;
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[i].length; j++)
                result[k++] = matrix[i][j];
        }
        return result;
    }

    private static int calcInversions(Byte... values)
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
