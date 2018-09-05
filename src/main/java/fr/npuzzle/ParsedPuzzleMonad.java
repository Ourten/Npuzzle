package fr.npuzzle;

import fr.npuzzle.data.ParsedPuzzle;

public class ParsedPuzzleMonad
{
    public enum ErrorType
    {
        NONE, CANNOT_READ_FILE, TWO_SAME_INT, INT_TOO_LARGE, NOT_ENOUGH_VALID_ROWS, NEGATIVE_INTEGER, NO_ZERO_IN_PUZZLE, INVALID_PUZZLE_SIZE, NO_SPECIFIED_SIZE
    }

    private ParsedPuzzle puzzle;
    private ErrorType    errorType;

    public String getErrorMessage()
    {
        switch (errorType)
        {
            case CANNOT_READ_FILE:
                return ("could not read given file");
            case INT_TOO_LARGE:
                return ("the file contains a number over 99");
            case TWO_SAME_INT:
                return ("the file contains two identical numbers");
            case NOT_ENOUGH_VALID_ROWS:
                return ("the given file does not contain enough valid rows to extract a puzzle of given size out of it");
            case NEGATIVE_INTEGER:
                return ("negative integer found in file, only positive ones are accepted");
            case NO_ZERO_IN_PUZZLE:
                return ("no zero found in file, please add one");
            case NO_SPECIFIED_SIZE:
                return ("no eligible puzzle size token could be extracted, please add one");
            case INVALID_PUZZLE_SIZE:
                return ("detected puzzle size is out of bounds, it should be between 3 and 10 inclusive");
            default:
                return ("No error happened on parsing");
        }
    }

    ParsedPuzzleMonad(ParsedPuzzle puzzle)
    {
        this.puzzle = puzzle;
        errorType = ParsedPuzzleMonad.ErrorType.NONE;
    }

    ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType errorType)
    {
        this.errorType = errorType;
        this.puzzle = null;
    }

    public boolean isErrored()
    {
        return this.errorType != ErrorType.NONE;
    }

    public ErrorType getErrorType()
    {
        return errorType;
    }

    public void setErrorType(ErrorType type)
    {
        this.errorType = type;
    }

    public ParsedPuzzle getPuzzle()
    {
        return puzzle;
    }

    public void setPuzzle(ParsedPuzzle puzzle)
    {
        this.puzzle = puzzle;
    }
}
