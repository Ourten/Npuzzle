package fr.npuzzle.data;

public class ActionTaken
{
    private Action action;
    private Cell from;

    public ActionTaken(Action action, Cell from)
    {
        this.action = action;
        this.from = from;
    }

    public Action getAction()
    {
        return action;
    }

    public Cell getFrom()
    {
        return from;
    }
}
