package fr.npuzzle;

import fr.npuzzle.data.ParsedPuzzle;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class InputFormatter
{

    private String[] args;

    public InputFormatter(String[] args)
    {
        this.args = args;
    }

    public ParsedPuzzle getRandomPuzzle()
    {
        Random randomInstance;
        ParsedPuzzle puzzle;
        int x;
        int y;
        int size;

        x = 0;
        y = 0;
        randomInstance = new Random();
        size = 3 + randomInstance.nextInt(2);
        puzzle = new ParsedPuzzle(size);
        while (y < size)
        {
            while (x < size)
            {
                puzzle.setCell(x, y, randomInstance.nextInt(20));
                x++;
            }
            y++;
            x = 0;
        }
        return (puzzle);
    }

    private boolean fillLine(ParsedPuzzle puzzle, String[] words, int y)
    {
        int i;

        i = 0;
        if (y >= puzzle.getSize())
            return true;
        while (i < puzzle.getSize())
        {
            if (words[i].length() > 7)
                return false;
            puzzle.setCell(i, y, Integer.parseInt(words[i]));
            i++;
        }
        return true;
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
                    if (!fillLine(result, currentWords, currentLine))
                        return (new ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType.INT_TOO_LARGE));
                    currentLine++;
                }
            }
            i++;
        }
        if (result != null && currentLine < result.getSize())
            return (new ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType.NOT_ENOUGH_VALID_ROWS));
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

    private static boolean isArgument(String arg)
    {
        //ajouter ici les comportements liés aux différents arguments, (retourner 1 si l'arg en est un)
        return (false);
    }

    public List<String> getFiles()
    {
        List<String> files;
        int i;

        i = 0;
        files = new Stack<>();
        while (i < args.length)
        {
            if (!isArgument(args[i]))
                files.add(args[i]);
            i++;
        }
        return files;
    }
}
