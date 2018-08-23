package fr.npuzzle.pathfinder;

import fr.npuzzle.data.ParsedPuzzle;

@FunctionalInterface
public interface Heuristic
{
    int apply(ParsedPuzzle current, ParsedPuzzle desired);
}
