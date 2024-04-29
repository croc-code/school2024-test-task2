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
        File file = new File("src/main/resources/format.json");
        System.out.println(AnswerParser.generateAnswer(file));
    }
}
