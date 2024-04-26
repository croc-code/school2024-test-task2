package ru.baydak.Filters;

import ru.baydak.Models.Response;
import ru.baydak.Utils.MyParser;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyFilter {
    public static List<String> getPopularCategory(File json) {

        Response response = MyParser.convertJsonToResponse(json);
        Map<String, Integer> categoriesCount = new HashMap<>();

        response.getOrders().stream()
                .filter(order -> validateDate(order.getOrderedAt()))
                .forEach(order -> {
                     order.getItems().forEach(item -> {
                        categoriesCount.put(item.getCategory().getName(),
                                categoriesCount.getOrDefault(item.getCategory().getName(), 0) + 1);
                    });
                });

        long maxCount = categoriesCount.values().stream().max(Integer::compareTo).orElse(0);

        return categoriesCount.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
    }
    private static boolean validateDate(String date) {
        try {
            LocalDateTime orderDate = LocalDateTime.parse(date);
            // Проверяем год, хотя по тз и не нужно, НО (тренды 2003) != (трендам 2023)
            // Соответсвенно, можно сделать выборку динамически последних 5 лет от нынешнего времени
            // но это нужно доказать аналилически, поэтому оставил только 2023 год!!!
            return orderDate.getMonthValue() == 12 && orderDate.getYear() == 2023;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


}