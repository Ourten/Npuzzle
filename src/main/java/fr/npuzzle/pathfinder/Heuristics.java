package fr.npuzzle.pathfinder;

import fr.npuzzle.data.Cell;
import fr.npuzzle.data.ParsedPuzzle;

import java.util.Optional;

public class Heuristics
{
    public static int outOfRowAndColumn(ParsedPuzzle current, ParsedPuzzle desired)
    {
        int outof = 0;

        for (int x = 0; x < current.getGrid().length; x++)
        {
            for (int y = 0; y < current.getGrid()[x].length; y++)
            {
                Optional<Cell> toCell = Cell.findCell(desired, (byte) current.getCell(x, y));

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

    public static int hamming(ParsedPuzzle current, ParsedPuzzle desired)
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

    public static int manhattan(ParsedPuzzle current, ParsedPuzzle desired)
    {
        int distance = 0;

        for (int x = 0; x < current.getGrid().length; x++)
        {
            for (int y = 0; y < current.getGrid()[x].length; y++)
            {
                if (current.getCell(x, y) == ParsedPuzzle.EMPTY)
                    continue;
                Optional<Cell> toCell = Cell.findCell(desired, (byte) current.getCell(x, y));

                if (toCell.isPresent())
                    distance += Math.abs(x - toCell.get().getX()) + Math.abs(y - toCell.get().getY());
            }
        }
        return distance;
    }

    public static int manhattenLinearConflict(ParsedPuzzle current, ParsedPuzzle desired)
    {
        int distance = 0;

        for (int x = 0; x < current.getGrid().length; x++)
        {
            for (int y = 0; y < current.getGrid()[x].length; y++)
            {
                if (current.getCell(x, y) == ParsedPuzzle.EMPTY)
                    continue;
                Optional<Cell> toCell = Cell.findCell(desired, (byte) current.getCell(x, y));

                if (toCell.isPresent())
                {
                    distance += Math.abs(x - toCell.get().getX()) + Math.abs(y - toCell.get().getY());

                    if (toCell.get().getX() == x)
                        distance += linearVertConflict(current, desired, new Cell(x, y), toCell.get(),
                                current.getSize());
                    if (toCell.get().getY() == y)
                        distance += linearHorConflict(current, desired, new Cell(x, y), toCell.get(),
                                current.getSize());
                }
            }
        }
        return distance;
    }

    private static int linearHorConflict(ParsedPuzzle currentPuzzle, ParsedPuzzle desiredPuzzle, Cell current,
                                         Cell desired, int size)
    {
        int conflicts = 0;
        int x = current.getX();
        int y = current.getY();
        while (++x < size)
        {
            Cell tmpCell = Cell.findCell(desiredPuzzle, (byte) currentPuzzle.getCell(x, y)).get();

            if (currentPuzzle.getCell(tmpCell.getX(), tmpCell.getY()) == 0)
                continue;

            if (y == tmpCell.getY())
            {
                if (tmpCell.getX() > desired.getX() && x < current.getX())
                    conflicts++;
                else if (tmpCell.getX() < desired.getX() && x > current.getX())
                    conflicts++;
            }
        }
        return (2 * conflicts);
    }

    private static int linearVertConflict(ParsedPuzzle currentPuzzle, ParsedPuzzle desiredPuzzle, Cell current,
                                          Cell desired, int size)
    {
        int conflicts = 0;
        int x = current.getX();
        int y = current.getY();
        while (++y < size)
        {
            Cell tmpCell = Cell.findCell(desiredPuzzle, (byte) currentPuzzle.getCell(x, y)).get();

            if (currentPuzzle.getCell(tmpCell.getX(), tmpCell.getY()) == 0)
                continue;

            if (x == tmpCell.getX())
            {
                if (tmpCell.getY() > desired.getY() && y < current.getY())
                    conflicts++;
                else if (tmpCell.getY() < desired.getY() && y > current.getY())
                    conflicts++;
            }
        }
        return (2 * conflicts);
    }
}
