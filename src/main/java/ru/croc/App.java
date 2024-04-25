package ru.croc;

import ru.croc.utils.ReportGenerator;

import java.io.File;

public class App {

    public static void main(String[] args) {
        System.out.println(ReportGenerator.generateReport(
                ReportGenerator.getPopularCategory(new File(args[0]))));
    }
}
