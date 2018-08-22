package fr.npuzzle.pathfinder;

import fr.npuzzle.data.Cell;
import fr.npuzzle.data.State;

import java.util.Optional;

public class Heuristics
{
    public int hamming(State current, State desired)
    {
        int misplaced = 0;

        for (int x = 0; x < current.getData().getGrid().length; x++)
        {
            for (int y = 0; y < current.getData().getGrid()[x].length; y++)
            {
                if(current.getData().getGrid()[x][y] != desired.getData().getGrid()[x][y])
                    misplaced++;
            }
        }
        return misplaced;
    }

    public int manhattan(State current, State desired)
    {
        int distance = 0;

        for (int x = 0; x < current.getData().getGrid().length; x++)
        {
            for (int y = 0; y < current.getData().getGrid()[x].length; y++)
            {
                Optional<Cell> toCell = Cell.findCell(desired.getData(), current.getData().getCell(x, y));

                if (toCell.isPresent())
                    distance += Math.abs(x - toCell.get().getX()) + Math.abs(y - toCell.get().getY());
            }
        }
        return distance;
    }
}
