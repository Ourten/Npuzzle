package fr.npuzzle;

import fr.npuzzle.data.Parameters;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class ParseTokenizer
{

    public enum Token
    {
        FILE, GREED_TOKEN, UNIFORM_TOKEN, HEURISTIC_TOKEN, HEURISTIC_PARAMETER_TOKEN, OUTPUT_TOKEN,
        OUTPUT_PARAMETER_TOKEN, VISUALIZER_TOKEN, RANDOM_TOKEN, RANDOM_PARAMETER_TOKEN
    }

    private Token[]    tokens;
    private Parameters data;

    public ParseTokenizer(String[] arg, Parameters data)
    {
        int i = 0;
        tokens = new Token[arg.length];
        this.data = data;

        while (i < arg.length && data.getStatus() == Parameters.ArgumentErrors.NONE)
        {
            tokens[i] = identifyToken(data, i <= 0 ? Token.FILE : tokens[i - 1], arg[i], i + 1 == arg.length);
            i++;
        }
    }

    private boolean identifyHeuristicParameter(Parameters data, String param)
    {
        switch (param.toLowerCase())
        {
            case "manhattan":
                data.setSpecifiedHeuristic(Parameters.Heuristics.MANHATTAN);
                return (true);
            case "hamming":
                data.setSpecifiedHeuristic(Parameters.Heuristics.HAMMING);
                return (true);
            case "outofrowandcollumn":
                data.setSpecifiedHeuristic(Parameters.Heuristics.OUT_OF_ROW_AND_COLLUMN);
                return (true);
            case "manhattanlinearconflict":
                data.setSpecifiedHeuristic(Parameters.Heuristics.MANHATTAN_AND_LINEAR_CONFLICT);
                return (true);
            default:
                data.setStatus(Parameters.ArgumentErrors.WRONG_HEURISTIC_PARAMETER);
                return (false);
        }

    }

    private boolean identifyRandomSize(Parameters data, String param)
    {
        BigInteger size;

        if (param.matches("([^0-9])+"))
        {
            data.setStatus(Parameters.ArgumentErrors.WRONG_CHAR_FOUND_RANDOM);
            return (false);
        }
        if ((size = new BigInteger(param)).compareTo(new BigInteger("2")) <= 0 || (size = new BigInteger(param)).compareTo(new BigInteger("7")) > 0)
        {
            data.setStatus(Parameters.ArgumentErrors.INVALID_PUZZLE_SIZE);
            return (false);
        }
        data.setRandomSize(size.intValueExact());
        return (true);
    }

    private Token identifyToken(Parameters data, Token precedingToken, String param, boolean isLast)
    {
        if (param.equals("-g"))
        {
            if (data.isUniform())
                data.setStatus(Parameters.ArgumentErrors.CONFLICT_UNIFORM_GREEDY);
            data.setGreedy(true);
            return (Token.GREED_TOKEN);
        }
        else if (param.equals("-u"))
        {
            if (data.isGreedy())
                data.setStatus(Parameters.ArgumentErrors.CONFLICT_UNIFORM_GREEDY);
            data.setUniform(true);
            return (Token.UNIFORM_TOKEN);
        }
        else if (precedingToken == Token.FILE && param.equals("-h") && !isLast)
        {
            return (Token.HEURISTIC_TOKEN);
        }
        else if (precedingToken == Token.FILE && param.equals("-o") && !isLast)
        {
            if (data.getOutput() != null)
                data.setStatus(Parameters.ArgumentErrors.TWO_DEFINED_OUTPUT);
            return (Token.OUTPUT_TOKEN);
        }
        else if (param.equals("-v"))
        {
            data.setVisualizer(true);
            return (Token.VISUALIZER_TOKEN);
        }
        else if (precedingToken == Token.FILE && param.equals("-r") && !isLast)
        {
            return (Token.RANDOM_TOKEN);
        }
        else if (precedingToken == Token.HEURISTIC_TOKEN && identifyHeuristicParameter(data, param))
        {
            return (Token.HEURISTIC_PARAMETER_TOKEN);
        }
        else if (precedingToken == Token.OUTPUT_TOKEN)
        {
            try
            {
                File file = new File(param);
                file.delete();
                file.createNewFile();
                data.setOutput(file);
            } catch (IOException e) {
                data.setStatus(Parameters.ArgumentErrors.FILE_COULD_NOT_BE_WRITTEN);
            }
            return (Token.OUTPUT_PARAMETER_TOKEN);
        }
        else if (precedingToken == Token.RANDOM_TOKEN && identifyRandomSize(data, param))
        {
            return (Token.RANDOM_PARAMETER_TOKEN);
        }
        else return (Token.FILE);
    }

    public static String getErrorMessage(Parameters.ArgumentErrors error)
    {
        //todo
        return ("todo error message");
    }

    public Token[] getTokens()
    {
        return (this.tokens);
    }

    public Parameters getData()
    {
        return data;
    }

    public void setData(Parameters data)
    {
        this.data = data;
    }
}
