package fr.npuzzle.data;

import fr.npuzzle.pathfinder.Heuristic;

import java.io.File;

public class Parameters
{
    public enum Heuristics
    {
        NONE, MANHATTAN, HAMMING, OUT_OF_ROW_AND_COLLUMN, MANHATTAN_AND_LINEAR_CONFLICT
    }

    public enum ArgumentErrors
    {
        NONE, CONFLICT_UNIFORM_GREEDY, WRONG_HEURISTIC_PARAMETER, WRONG_CHAR_FOUND_RANDOM,
        INVALID_PUZZLE_SIZE, TWO_DEFINED_OUTPUT, FILE_COULD_NOT_BE_WRITTEN
    }

    private boolean        greedy             = false;
    private boolean        uniform            = false;
    private Heuristics     specifiedHeuristic = Heuristics.NONE;
    private File           output             = null;
    private boolean        visualizer         = false;
    private int            randomSize         = 0;
    private ArgumentErrors status             = ArgumentErrors.NONE;

    public ArgumentErrors getStatus()
    {
        return status;
    }

    public void setStatus(ArgumentErrors status)
    {
        this.status = status;
    }

    public boolean isGreedy()
    {
        return greedy;
    }

    public void setGreedy(boolean greedy)
    {
        this.greedy = greedy;
    }

    public boolean isUniform()
    {
        return uniform;
    }

    public void setUniform(boolean uniform)
    {
        this.uniform = uniform;
    }

    public Heuristics getSpecifiedHeuristic()
    {
        return specifiedHeuristic;
    }

    public void setSpecifiedHeuristic(Heuristics specifiedHeuristic)
    {
        this.specifiedHeuristic = specifiedHeuristic;
    }

    public File getOutput()
    {
        return output;
    }

    public void setOutput(File output)
    {
        this.output = output;
    }

    public boolean isVisualizerEnabled()
    {
        return visualizer;
    }

    public void setVisualizer(boolean visualizer)
    {
        this.visualizer = visualizer;
    }

    public int getRandomSize()
    {
        return randomSize;
    }

    public void setRandomSize(int randomSize)
    {
        this.randomSize = randomSize;
    }
}
