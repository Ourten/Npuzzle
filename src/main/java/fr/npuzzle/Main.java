package fr.npuzzle;

import fr.npuzzle.data.Parameters;
import java.util.Arrays;
public class Main
{
    public static void main(String[] args)
    {
        InputFormatter formatter = new InputFormatter(args);

        System.out.println(Arrays.toString(args));
        if (formatter.getParameters().getStatus() == Parameters.ArgumentErrors.NONE)
        {
            OutputManagement.execute(formatter.getFiles(), formatter);
        }
        else
            System.out.printf("error at arguments parsing (" + ParseTokenizer.getErrorMessage(formatter.getParameters().getStatus()) + ")");
    }
}
