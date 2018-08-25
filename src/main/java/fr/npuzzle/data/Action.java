package fr.npuzzle.data;

import java.util.Optional;

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

    public Action reverse()
    {
        switch (this)
        {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
        }
        return UP;
    }

    public Optional<State> move(State from)
    {
        Cell toMove = Cell.findCell(from.getData(), ParsedPuzzle.EMPTY).orElse(null);

        if (toMove == null || toMove.getX() + this.xOffset >= from.getData().getSize() ||
                toMove.getX() + this.xOffset < 0 || toMove.getY() + this.yOffset >= from.getData().getSize() ||
                toMove.getY() + this.yOffset < 0)
            return Optional.empty();
        ParsedPuzzle copy = from.getData().copy();
        ActionTaken action = new ActionTaken(this, toMove);

        copy.setCell(toMove.getX(), toMove.getY(), copy.getCell(toMove.getX() + this.xOffset,
                toMove.getY() + this.yOffset));
        copy.setCell(toMove.getX() + xOffset, toMove.getY() + yOffset, ParsedPuzzle.EMPTY);

        return Optional.of(new State(copy, from, action));
    }
}
