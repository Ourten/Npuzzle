package fr.npuzzle.data;

import java.util.Objects;

public class State
{
    private ParsedPuzzle data;
    private State        father;
    private ActionTaken  usedMove;
    private int          cost;

    public State(ParsedPuzzle data, State father, ActionTaken usedMove)
    {
        this.data = data;
        this.father = father;
        this.usedMove = usedMove;

        this.cost = -1;
    }

    public ParsedPuzzle getData()
    {
        return data;
    }

    public State getFather()
    {
        return father;
    }

    public ActionTaken getUsedMove()
    {
        return usedMove;
    }

    public int getAncestorCount()
    {
        if (this.getFather() != null)
            return getFather().getAncestorCount() + 1;
        return 0;
    }

    public int getCost()
    {
        return cost;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }

    public State copy()
    {
        return new State(this.data.copy(), father, usedMove);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(getData(), state.getData()) &&
                Objects.equals(getFather(), state.getFather()) &&
                Objects.equals(getUsedMove(), state.getUsedMove());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getData(), getFather(), getUsedMove());
    }
}
