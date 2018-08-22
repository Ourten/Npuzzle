package fr.npuzzle.data;

public class State
{
    private ParsedPuzzle data;
    private State father;
    private ActionTaken usedMove;

    public State(ParsedPuzzle data, State father, ActionTaken usedMove)
    {
        this.data = data;
        this.father = father;
        this.usedMove = usedMove;
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

    public State copy()
    {
        return new State(this.data.copy(), father, usedMove);
    }
}
