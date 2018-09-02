package fr.npuzzle;

import fr.npuzzle.data.Parameters;
import fr.npuzzle.data.ParsedPuzzle;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class InputFormatter
{

    private List<String>   files;
    private ParseTokenizer tokenizer;
    private Parameters     parameters;
    private boolean        hasZero = false;

    public InputFormatter(String[] args)
    {
        int i = 0;
        ParseTokenizer.Token[] tokens;
        this.files = new ArrayList<String>();

        this.tokenizer = new ParseTokenizer(args, new Parameters());
        tokens = this.tokenizer.getTokens();
        this.parameters = this.tokenizer.getData();

        if (this.parameters.getStatus() == Parameters.ArgumentErrors.NONE)
        {
            while (i < args.length)
            {
                if (tokens[i] == ParseTokenizer.Token.FILE)
                    this.files.add(args[i]);
                i++;
            }
        }
    }

    public ParsedPuzzle getRandomPuzzle(int size)
    {
        Random randomInstance;
        ParsedPuzzle puzzle;
        int x;
        int y;
        int tmp;
        HashSet<Integer> set;

        set = new HashSet<Integer>();
        x = 0;
        y = 0;
        randomInstance = new Random();
        size = 3 + randomInstance.nextInt(2);
        puzzle = new ParsedPuzzle(size);
        while (y < size)
        {
            while (x < size)
            {
                tmp = -1;
                while (set.contains(tmp) || tmp <= 0)
                     tmp = randomInstance.nextInt(99);
                puzzle.setCell(x, y, tmp);
                set.add(tmp);
                x++;
            }
            y++;
            x = 0;
        }
        puzzle.setCell(randomInstance.nextInt(size), randomInstance.nextInt(size), 0);
        return (puzzle);
    }

    private ParsedPuzzleMonad.ErrorType fillLine(ParsedPuzzle puzzle, String[] words, int y)
    {
        int i;
        BigInteger number;
        HashSet<Integer> set;

        i = 0;
        set = new HashSet<Integer>();
        if (y >= puzzle.getSize())
            return ParsedPuzzleMonad.ErrorType.NONE;
        while (i < puzzle.getSize())
        {
            number = new BigInteger(words[i]);
            if (number.compareTo(new BigInteger("100")) >= 0)
                return ParsedPuzzleMonad.ErrorType.INT_TOO_LARGE;
            //todo next condition do not work, will debug another day
            if (set.contains(number.intValueExact()))
                return ParsedPuzzleMonad.ErrorType.TWO_SAME_INT;
            set.add(number.intValueExact());
            if (number.intValueExact() == 0)
                hasZero = true;
            puzzle.setCell(i, y, Integer.parseInt(words[i]));
            i++;
        }
        return ParsedPuzzleMonad.ErrorType.NONE;
    }

    private String[] stringCleaner(String[] array)
    {
        List<String> list = new ArrayList<>(Arrays.asList(array));
        list.removeAll(Collections.singleton(""));
        array = list.toArray(new String[0]);
        return array;
    }

    private ParsedPuzzleMonad puzzleParser(List<String> lines)
    {
        int i;
        int currentLine;
        boolean sizeDefined;
        String[] currentWords;
        ParsedPuzzle result;
        ParsedPuzzleMonad.ErrorType tmp;

        result = null;
        sizeDefined = false;
        currentLine = 0;
        i = 0;
        while (i < lines.size())
        {
            if (lines.get(i).matches("([\\s\\S]*-[0-9][\\s\\S]*)+"))
                return (new ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType.NEGATIVE_INTEGER));
            lines.set(i, lines.get(i).replace('#', '\0'));
            if ((currentWords = stringCleaner(lines.get(i).split("([^0-9])+"))).length > 0)
            {
                if (sizeDefined && result.getSize() > currentWords.length && currentLine < result.getSize())
                {
                    i++;
                    continue;
                }
                if (!sizeDefined)
                {
                    result = new ParsedPuzzle(Integer.parseInt(currentWords[0]));
                    sizeDefined = true;
                }
                else
                {
                    tmp = fillLine(result, currentWords, currentLine);
                    if (tmp != ParsedPuzzleMonad.ErrorType.NONE)
                        return (new ParsedPuzzleMonad(tmp));
                    currentLine++;
                }
            }
            i++;
        }
        if (result != null && currentLine < result.getSize())
            return (new ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType.NOT_ENOUGH_VALID_ROWS));
        if (!hasZero)
            return (new ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType.NO_ZERO_IN_PUZZLE));
        return (new ParsedPuzzleMonad(result));
    }

    public ParsedPuzzleMonad parseFile(String file)
    {
        List<String> lines;

        lines = null;
        if (new File(file).canRead())
        {
            try
            {
                lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
            } catch (IOException e)
            {
                new ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType.CANNOT_READ_FILE);
            }
        }
        else
        {
            return (new ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType.CANNOT_READ_FILE));
        }
        return (lines == null ? new ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType.CANNOT_READ_FILE) :
                puzzleParser(lines));
    }

    public List<String> getFiles()
    {
        return (this.files);
    }

    public Parameters getParameters()
    {
        return (this.tokenizer.getData());
    }
}
