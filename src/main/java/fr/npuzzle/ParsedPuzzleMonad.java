package fr.npuzzle;

import fr.npuzzle.data.ParsedPuzzle;

public class ParsedPuzzleMonad
{
    public enum ErrorType
    {
        NONE, CANNOT_READ_FILE, INT_TOO_LARGE, NOT_ENOUGH_VALID_ROWS, NEGATIVE_INTEGER
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
                return ("into the given file is a number over the limit of an integer, plaese reduce to 7 characters " +
                        "or less");
            case NOT_ENOUGH_VALID_ROWS:
                return ("the given file does not contain enough valid rows to extract a puzzle out of it");
            case NEGATIVE_INTEGER:
                return ("negative integer found in file, only positive ones are accepted");
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
