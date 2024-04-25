package org.test;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {
    private static final DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-d[-]['T'HH:mm:ss.SSS]")
            .toFormatter();

    public static void main(String[] args) throws IOException {
        String mostPopularCategories = getPopularCategories(
                "src\\main\\resources\\package.json",
                "2023-12-1",
                "2023-12-31"
        );
        System.out.printf("Самые популярные категории товаров в период с 1 по 31 декабря 2023 года:\n%s", mostPopularCategories);
    }

    public static String getPopularCategories(String jsonFilePath, String startDate, String endDate) throws IOException {
        LocalDate startDateObj = LocalDate.parse(startDate, dateFormatter);
        LocalDate endDateObj = LocalDate.parse(endDate, dateFormatter);

        Map<String, Integer> categoriesMap = new HashMap<>();

        try (FileReader reader = new FileReader(jsonFilePath)) {
            JsonArray purchases = JsonParser.parseReader(reader).getAsJsonArray();

            purchases.forEach(purchaseElement -> {
                JsonObject purchaseObject = purchaseElement.getAsJsonObject();
                LocalDate orderDate = LocalDate.parse(purchaseObject.get("ordered_at").getAsString(), dateFormatter);
                if (orderDate.isAfter(startDateObj) && orderDate.isBefore(endDateObj)) {
                    JsonArray items = purchaseObject.getAsJsonArray("items");
                    items.forEach(itemElement -> {
                        JsonObject itemObject = itemElement.getAsJsonObject();
                        JsonObject category = itemObject.getAsJsonObject("category");
                        String categoryName = category.get("name").toString();
                        categoriesMap.merge(categoryName, 1, Integer::sum);
                    });
                }
            });
        }

        int maxValueOfPurchases = categoriesMap.values().stream().max(Integer::compareTo).orElse(0);
        List<String> mostPopularCategories = categoriesMap.entrySet().stream()
                .filter(entry -> entry.getValue() == maxValueOfPurchases)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();

        return String.format("{\"categories\":[%s]}", String.join(", ", mostPopularCategories));
    }
}