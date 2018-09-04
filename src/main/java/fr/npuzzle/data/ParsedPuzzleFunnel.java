package fr.npuzzle.data;

import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

public enum ParsedPuzzleFunnel implements Funnel<ParsedPuzzle>
{
    INSTANCE;

    @Override
    public void funnel(ParsedPuzzle from, PrimitiveSink into)
    {
        for (byte[] row : from.getGrid())
            into.putBytes(row);
    }
}
