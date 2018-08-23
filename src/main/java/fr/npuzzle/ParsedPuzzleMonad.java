package fr.npuzzle;

import fr.npuzzle.data.ParsedPuzzle;

public class ParsedPuzzleMonad {
    public enum ErrorType {none, cannot_read_file, int_too_large, not_enough_valid_rows, negative_integer}

    ParsedPuzzle puzzle;
    ErrorType errorType;

    public String GetErrorMessage() {
        switch (errorType) {
            case cannot_read_file:
                return ("could not read given file");
            case int_too_large:
                return ("into the given file is a number over the limit of an integer, plaese reduce to 7 characters or less");
            case not_enough_valid_rows:
                return ("the given file does not contain enough valid rows to extract a puzzle out of it");
            case negative_integer:
                return ("negative integer found in file, only positive ones are accepted");
            default:
                return ("No error happened on parsing");
        }
    }

    ParsedPuzzleMonad(ParsedPuzzle puzzle) {
        this.puzzle = puzzle;
        errorType = ParsedPuzzleMonad.ErrorType.none;
    }

    ParsedPuzzleMonad(ParsedPuzzleMonad.ErrorType errorType) {
        this.errorType = errorType;
        this.puzzle = null;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType type) {
        this.errorType = type;
    }

    public ParsedPuzzle getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(ParsedPuzzle puzzle) {
        this.puzzle = puzzle;
    }
}
