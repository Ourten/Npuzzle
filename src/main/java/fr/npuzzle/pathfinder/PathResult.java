package fr.npuzzle.pathfinder;

import fr.npuzzle.data.ActionTaken;

import java.util.ArrayList;
import java.util.List;

public class PathResult
{
    private List<ActionTaken> moves;
    private int               timeComplexity, sizeComplexity;

    public PathResult()
    {
        this.moves = new ArrayList<>();
    }

    public List<ActionTaken> getMoves()
    {
        return moves;
    }

    public int getTimeComplexity()
    {
        return timeComplexity;
    }

    public int getSizeComplexity()
    {
        return sizeComplexity;
    }

    public void addTimeComplexity()
    {
        this.timeComplexity++;
    }

    public void addSizeComplexity(int sizeComplexity)
    {
        if (sizeComplexity > this.sizeComplexity)
            this.sizeComplexity = sizeComplexity;
    }
}
