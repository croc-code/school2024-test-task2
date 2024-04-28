package amgoize.service;

import amgoize.entity.Item;
import amgoize.entity.MostPopularCategories;
import amgoize.entity.Order;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Month;
import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class OrdersAnalyzer {
    private JsonReader jsonReader;
    private CategoriesWriter categoriesWriter;

    public OrdersAnalyzer() {
        ObjectMapper objectMapper = new ObjectMapper();
        jsonReader = new JsonReader();
        categoriesWriter = new CategoriesWriter(objectMapper);
    }



    public String getMostPopularCategories(String fileName) throws IOException {
        MostPopularCategories mostPopularCategories =
                getMostPopularCategories(jsonReader.readOrders(fileName));

        return categoriesWriter.writeCategories(mostPopularCategories);
    }


    private MostPopularCategories getMostPopularCategories(List<Order> orders) {
        var frequencies = orders.stream()
                .filter(this::orderedInDecember)
                .flatMap(order -> order.getItems().stream())
                .map(Item::getCategory)
                .collect(groupingBy(identity(), counting()));
        var maxFrequency = frequencies.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
                .getValue();
        var mostPopularCategories = frequencies.entrySet().stream()
                .filter(entry -> entry.getValue().equals(maxFrequency))
                .map(entry -> entry.getKey().getName())
                .sorted(String::compareTo)
                .toList();

        MostPopularCategories popularCategories = new MostPopularCategories();
        popularCategories.setCategories(mostPopularCategories);
        return popularCategories;
    }

    private boolean orderedInDecember(Order order) {
        return Month.DECEMBER.equals(order.getOrderedAt().getMonth());
    }
}
