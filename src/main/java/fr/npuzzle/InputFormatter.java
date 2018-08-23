package fr.npuzzle;

import fr.npuzzle.data.ParsedPuzzle;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;

public class InputFormatter {

    public enum EvaluationMethod {methodA, methodB} //TODO

    static EvaluationMethod evalParam;
    static String[] args;

    public InputFormatter(String[] args) {
        this.args = args;
    }

    public static ParsedPuzzle GetRandomPuzzle() {
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
        while (y < size) {
            while (x < size) {
                puzzle.setCell(x, y, randomInstance.nextInt(20));
                x++;
            }
            y++;
            x = 0;
        }
        return (puzzle);
    }

    private static boolean FillLine(ParsedPuzzle puzzle, String[] words, int y) {
        int i;

        i = 0;
        if (y >= puzzle.getSize())
            return true;
        while (i < puzzle.getSize()) {
            if (words[i].length() > 7)
                return false;
            puzzle.setCell(i, y, Integer.parseInt(words[i]));
            i++;
        }
        return true;
    }

    private static String[] StringCleaner(String[] array) {
        List<String> list = new ArrayList<String>(Arrays.asList(array));
        list.removeAll(Collections.singleton(""));
        array = list.toArray(new String[0]);
        return array;
    }

    private static ParsedPuzzleMonad PuzzleParser(List<String> lines) {
        int i;
        int currentLine;
        boolean sizeDefined;
        String[] currentWords;
        ParsedPuzzle result;

        result = null;
        sizeDefined = false;
        currentLine = 0;
        i = 0;
        while (i < lines.size()) {
            if (lines.get(i).matches("([\\s\\S]*-[0-9][\\s\\S]*)+"))
                return (new ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType.negative_integer));
            lines.set(i, lines.get(i).replace('#', '\0'));
            if ((currentWords = StringCleaner(lines.get(i).split("([^0-9])+"))).length > 0) {
                if (sizeDefined == true && result.getSize() > currentWords.length && currentLine < result.getSize()) {
                    i++;
                    continue;
                }
                //System.out.printf("this line has %s words, it is (%s) (%s)\n", currentWords.length, lines.get(i), Arrays.toString(currentWords));
                if (!sizeDefined) {
                    result = new ParsedPuzzle(Integer.parseInt(currentWords[0]));
                    sizeDefined = true;
                } else {
                    if (FillLine(result, currentWords, currentLine) == false)
                        return (new ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType.int_too_large));
                    currentLine++;
                }
            }
            i++;
        }
        if (result != null && currentLine < result.getSize())
            return (new ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType.not_enough_valid_rows));
        return (new ParsedPuzzleMonad(result));
    }

    public static ParsedPuzzleMonad ParseFile(String file) {
        List<String> lines;

        lines = null;
        if (new File(file).canRead()) {
            try {
                lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
            } catch (IOException e) {
                new ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType.cannot_read_file);
            }
        } else {
            System.out.printf("\nerror at reading %s, continuing without it...\n", file);
            return (new ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType.cannot_read_file));
        }
        return (PuzzleParser(lines));
    }

    private static boolean isArgument(String arg) {
        //ajouter ici les comportements liés aux différents arguments, (retourner 1 si l'arg en est un)
        return (false);
    }

    public static List<String> GetFiles() {
        List<String> files;
        int i;

        i = 0;
        files = new Stack<String>();
        while (i < args.length) {
            if (!isArgument(args[i]))
                files.add(args[i]);
            i++;
        }
        return files;
    }
}
