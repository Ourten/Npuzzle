package fr.npuzzle.pathfinder;

import fr.npuzzle.data.State;

@FunctionalInterface
public interface Heuristic
{
    int apply(State current, State desired);
}
