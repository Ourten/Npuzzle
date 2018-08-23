package fr.npuzzle.data;

import java.util.Arrays;
import java.util.Objects;

public class ParsedPuzzle
{
    public static final int EMPTY = 0;

    private int     size;
    private int[][] grid;

    public ParsedPuzzle(int size)
    {
        this.size = size;

        this.grid = new int[size][];

        for (int i = 0; i < this.grid.length; i++)
        {
            this.grid[i] = new int[size];
        }
    }

    public int getSize()
    {
        return size;
    }

    public int[][] getGrid()
    {
        return grid;
    }

    public void setCell(int x, int y, int value)
    {
        if (value < 1)
            throw new RuntimeException("Cell cannot be assigned to a number less than 1.");
        this.grid[y][x] = value;
    }

    public void setEmpty(int x, int y)
    {
        this.grid[x][y] = 0;
    }

    public int getCell(int x, int y)
    {
        return this.grid[y][x];
    }

    public boolean isEmpty(int x, int y)
    {
        return this.getCell(x, y) == EMPTY;
    }

    public int[] getRow(int y)
    {
        return this.grid[y];
    }

    public int[] getColumn(int x)
    {
        int[] column = new int[this.size];

        for (int i = 0; i < column.length; i++)
            column[i] = this.grid[i][0];
        return column;
    }

    public ParsedPuzzle copy()
    {
        ParsedPuzzle copy = new ParsedPuzzle(this.size);

        for (int x = 0; x < this.grid.length; x++)
        {
            for (int y : this.grid[x])
                copy.grid[x][y] = this.grid[x][y];
        }
        return copy;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(this.size).append("x").append(this.size);

        builder.append("\n");
        for (int[] row : this.grid)
        {
            int index = 0;
            for (int cell : row)
            {
                if (index != 0)
                    builder.append(" ");
                builder.append(cell);
                index++;
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParsedPuzzle that = (ParsedPuzzle) o;
        return getSize() == that.getSize() &&
                Arrays.equals(getGrid(), that.getGrid());
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(getSize());
        result = 31 * result + Arrays.hashCode(getGrid());
        return result;
    }
}
