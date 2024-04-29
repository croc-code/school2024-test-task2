package ru.baydak;

import ru.baydak.Utils.AnswerParser;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if(args.length < 1) {
            System.out.println("Usage: java Main <path_to_format.json>");
            System.exit(1);
        }
        String filePath = args[0];
        System.out.println(
                AnswerParser.generateAnswer(new File(filePath)));
    }
}
