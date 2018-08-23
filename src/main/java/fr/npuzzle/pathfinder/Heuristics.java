package fr.npuzzle.pathfinder;

import fr.npuzzle.data.Cell;
import fr.npuzzle.data.ParsedPuzzle;

import java.util.Optional;

public class Heuristics
{
    public int outOfRowAndColumn(ParsedPuzzle current, ParsedPuzzle desired)
    {
        int outof = 0;

        for (int x = 0; x < current.getGrid().length; x++)
        {
            for (int y = 0; y < current.getGrid()[x].length; y++)
            {
                Optional<Cell> toCell = Cell.findCell(desired, current.getCell(x, y));

                if (toCell.isPresent())
                {
                    if (toCell.get().getX() != x)
                        outof++;
                    if (toCell.get().getY() != y)
                        outof++;
                }
            }
        }
        return outof;
    }

    public int hamming(ParsedPuzzle current, ParsedPuzzle desired)
    {
        int misplaced = 0;

        for (int x = 0; x < current.getGrid().length; x++)
        {
            for (int y = 0; y < current.getGrid()[x].length; y++)
            {
                if (current.getGrid()[x][y] != desired.getGrid()[x][y])
                    misplaced++;
            }
        }
        return misplaced;
    }

    public int manhattan(ParsedPuzzle current, ParsedPuzzle desired)
    {
        int distance = 0;

        for (int x = 0; x < current.getGrid().length; x++)
        {
            for (int y = 0; y < current.getGrid()[x].length; y++)
            {
                Optional<Cell> toCell = Cell.findCell(desired, current.getCell(x, y));

                if (toCell.isPresent())
                    distance += Math.abs(x - toCell.get().getX()) + Math.abs(y - toCell.get().getY());
            }
        }
        return distance;
    }
}
