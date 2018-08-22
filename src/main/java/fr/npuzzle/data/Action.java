package fr.npuzzle.data;

public enum Action
{
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private int xOffset, yOffset;

    Action(int xOffset, int yOffset)
    {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public State move(State from, Cell toMove)
    {
        ParsedPuzzle copy = from.getData().copy();
        ActionTaken action = new ActionTaken(this, toMove);

        copy.setCell(toMove.getX(), toMove.getY(), copy.getCell(toMove.getX() + this.xOffset, toMove.getY() + this.yOffset));
        copy.setCell(toMove.getX() + xOffset, toMove.getY() + yOffset, ParsedPuzzle.EMPTY);

        return new State(copy, from, action);
    }
}
