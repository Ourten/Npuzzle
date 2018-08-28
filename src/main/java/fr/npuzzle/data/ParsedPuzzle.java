package fr.npuzzle.data;

import java.util.Arrays;
import java.util.Objects;

public class ParsedPuzzle
{
    public static final byte EMPTY = 0;

    private int     size;
    private byte[][] grid;

    public ParsedPuzzle(int size)
    {
        this.size = size;

        this.grid = new byte[size][];

        for (int i = 0; i < this.grid.length; i++)
        {
            this.grid[i] = new byte[size];
        }
    }

    public int getSize()
    {
        return size;
    }

    public byte[][] getGrid()
    {
        return grid;
    }

    public void setCell(int x, int y, int value)
    {
        if (value < 0)
            throw new RuntimeException("Cell cannot be assigned to a number less than 1.");
        this.grid[y][x] = (byte) value;
    }

    public void setEmpty(byte x, byte y)
    {
        this.grid[x][y] = 0;
    }

    public int getCell(int x, int y)
    {
        return this.grid[y][x];
    }

    public boolean isEmpty(byte x, byte y)
    {
        return this.getCell(x, y) == EMPTY;
    }

    public byte[] getRow(byte y)
    {
        return this.grid[y];
    }

    public byte[] getColumn(byte x)
    {
        byte[] column = new byte[this.size];

        for (int i = 0; i < column.length; i++)
            column[i] = this.grid[i][0];
        return column;
    }

    public ParsedPuzzle copy()
    {
        ParsedPuzzle copy = new ParsedPuzzle(this.size);

        for (int x = 0; x < this.getSize(); x++)
        {
            if (this.getSize() >= 0) System.arraycopy(this.grid[x], 0, copy.grid[x], 0, this.getSize());
        }
        return copy;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(this.size).append("x").append(this.size);

        builder.append("\n");
        for (byte[] row : this.grid)
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
        return getSize() == that.getSize() && gridEquals(that.getGrid());
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(getSize());
        result = 31 * result + Arrays.deepHashCode(getGrid());
        return result;
    }

    private boolean gridEquals(byte[][] otherGrid)
    {
        for (int x = 0; x < this.getSize(); x++)
        {
            for (int y = 0; y < this.getSize(); y++)
            {
                if (this.getGrid()[x][y] != otherGrid[x][y])
                    return false;
            }
        }
        return true;
    }
}
