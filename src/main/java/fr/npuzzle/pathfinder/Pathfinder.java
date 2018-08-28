package fr.npuzzle.pathfinder;

import fr.npuzzle.data.Action;
import fr.npuzzle.data.Cell;
import fr.npuzzle.data.ParsedPuzzle;
import fr.npuzzle.data.State;

import java.util.*;

public class Pathfinder
{
    public static PathResult astar(ParsedPuzzle current, ParsedPuzzle desired, Heuristic heuristic)
    {
        PathResult result = new PathResult();

        PriorityQueue<State> openSet = new PriorityQueue<>(Comparator.comparingInt(
                (State state) ->
                {
                    if (state.getCost() == -1)
                        state.setCost(state.getAncestorCount() + heuristic.apply(state.getData(), desired));
                    return state.getCost();
                }));
        Set<ParsedPuzzle> closedSet = new HashSet<>();

        openSet.add(new State(current, null, null));
        while (!openSet.isEmpty())
        {
            State currentNode = openSet.poll();
            closedSet.add(currentNode.getData());

            if (currentNode.getData().equals(desired))
            {
                while (currentNode.getUsedMove() != null)
                {
                    result.getMoves().add(currentNode.getUsedMove());
                    currentNode = currentNode.getFather();
                }
                Collections.reverse(result.getMoves());
                return result;
            }

            for (Action action : Action.values())
            {
                if (currentNode.getUsedMove() != null && currentNode.getUsedMove().getAction() == action.reverse())
                    continue;
                action.move(currentNode).ifPresent(state ->
                {
                    if (!closedSet.contains(state.getData()))
                    {
                        openSet.add(state);
                        result.addTimeComplexity();
                        result.addSizeComplexity(openSet.size());
                    }
                });
            }
        }
        return result;
    }

    public static ParsedPuzzle getSnailSolution(ParsedPuzzle puzzle)
    {
        ParsedPuzzle solution = new ParsedPuzzle(puzzle.getSize());

        List<Integer> values = new ArrayList<>();

        for (byte[] row : puzzle.getGrid())
            for (int value : row)
                values.add(value);

        values.remove(new Integer(0));

        int max = values.stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
        values.add(max);
        Collections.sort(values);

        int i, k = 0, l = 0;
        int maxX = puzzle.getSize();
        int maxY = puzzle.getSize();

        int current = 0;
        while (k < maxX && l < maxY)
        {
            for (i = l; i < maxY; ++i)
            {
                solution.setCell(i, k, values.get(current));
                current++;
            }
            k++;

            for (i = k; i < maxX; ++i)
            {
                solution.setCell(maxY - 1, i, values.get(current));
                current++;
            }
            maxY--;

            if (k < maxX)
            {
                for (i = maxY - 1; i >= l; --i)
                {
                    solution.setCell(i, maxX - 1, values.get(current));
                    current++;
                }
                maxX--;
            }

            if (l < maxY)
            {
                for (i = maxX - 1; i >= k; --i)
                {
                    solution.setCell(l, i, values.get(current));
                    current++;
                }
                l++;
            }
        }

        Cell empty = Cell.findCell(solution, (byte) max).get();
        solution.setCell(empty.getX(), empty.getY(), 0);
        return solution;
    }
}