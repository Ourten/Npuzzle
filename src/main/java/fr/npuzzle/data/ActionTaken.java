package fr.npuzzle.data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionTaken that = (ActionTaken) o;
        return getAction() == that.getAction() &&
                Objects.equals(getFrom(), that.getFrom());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getAction(), getFrom());
    }

    @Override
    public String toString()
    {
        return "ActionTaken{" +
                "action=" + action +
                ", from=" + from +
                '}';
    }
}
