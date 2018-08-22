package fr.npuzzle.data;

import java.util.Optional;

public class Cell
{
    private int x, y;

    public Cell(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public static Optional<Cell> findCell(ParsedPuzzle puzzle, Integer value)
    {
        for (int x = 0; x < puzzle.getGrid().length; x++)
        {
            for (int y = 0; y < puzzle.getGrid()[x].length; y++)
            {
                if (puzzle.getGrid()[x][y] == value)
                    return Optional.of(new Cell(x, y));
            }
        }
        return Optional.empty();
    }
}
