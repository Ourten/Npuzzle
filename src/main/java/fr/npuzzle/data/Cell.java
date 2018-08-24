package fr.npuzzle.data;

import java.util.Objects;
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
                if (puzzle.getCell(x, y) == value)
                    return Optional.of(new Cell(x, y));
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return getX() == cell.getX() &&
                getY() == cell.getY();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString()
    {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
