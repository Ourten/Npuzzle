package fr.npuzzle;

import fr.npuzzle.data.ParsedPuzzle;

public class Main {

    public static void main(String[] args)
    {
        InputFormatter formatter = new InputFormatter(args);
        System.out.printf(formatter.GetFiles().toString());
        ParsedPuzzleMonad temp = formatter.ParseFile(formatter.GetFiles().get(2));
        if (temp.errorType == ParsedPuzzleMonad.ErrorType.none)
            System.out.printf(temp.puzzle.toString());
        else
            System.out.printf(temp.GetErrorMessage());
    }
}
