package amgoize;

import amgoize.service.OrdersAnalyzer;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        OrdersAnalyzer ordersAnalyzer = new OrdersAnalyzer();
        String result = ordersAnalyzer.getMostPopularCategories("format.json");
        System.out.println(result);
    }
}