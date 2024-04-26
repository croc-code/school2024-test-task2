package ru.baydak;

import ru.baydak.Utils.AnswerParser;

import java.io.File;
import java.util.Objects;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ClassLoader classLoader = App.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("format.json")).getFile());

        System.out.println(AnswerParser.generateAnswer(file));
    }
}
