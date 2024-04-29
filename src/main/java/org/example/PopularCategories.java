package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopularCategories {

    private static final Logger logger = LoggerFactory.getLogger(PopularCategories.class);
    private static final int DECEMBER = 12;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public static void main(String[] args) {
        try {
            String fileName = "format.json";
            logger.info("Starting...");
            List<OrderData> orders = readDataFromJson(fileName, new TypeToken<List<OrderData>>() {
            }.getType());
            Map<String, Integer> categoryCounts = countCategories(orders);
            List<String> popularCategories = findPopularCategories(categoryCounts);
            String report = generateReport(popularCategories);
            logger.info(report);
        } catch (IOException e) {
            logger.error("Error executing main method", e);
        }
    }

    /**
     * Считывает данные из JSON-файла и десериализует их в указанный тип.
     *
     * @param fileName Имя JSON-файла.
     * @param type     Тип данных для десериализации.
     * @param <T>      Тип данных.
     * @return Десериализованные данные.
     * @throws IOException Если произошла ошибка ввода-вывода при чтении файла или разборе JSON.
     */
    public static <T> T readDataFromJson(String fileName, Type type) throws IOException {
        logger.debug("Reading JSON file: {}", fileName);
        Gson gson = new Gson();
        ClassLoader classLoader = PopularCategories.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return gson.fromJson(reader, type);
        } catch (IOException | JsonSyntaxException e) {
            logger.error("Error reading or parsing JSON file: {}", fileName, e);
            throw e;
        }
    }

    /**
     * Подсчитывает количество каждой категории в списке заказов, сделанных в декабре.
     *
     * @param orders Список заказов.
     * @return Map, содержащая имена категорий и их количество.
     */
    public static Map<String, Integer> countCategories(List<OrderData> orders) {
        logger.info("Counting categories...");
        Map<String, Integer> categoryCounts = new HashMap<>();
        for (OrderData order : orders) {
            if (order != null && isDataOfOrder(order)) {
                for (ItemData item : order.getItems()) {
                    if (item != null && item.getCategory() != null && item.getCategory().getName() != null) {
                        String categoryName = item.getCategory().getName().toLowerCase();
                        categoryCounts.put(categoryName, categoryCounts.getOrDefault(categoryName, 0) + 1);
                    } else {
                        logger.warn("Null or incomplete data found while counting categories in order: {}", order);
                    }
                }
            }
        }
        return categoryCounts;
    }

    /**
     * Проверяет, был ли заказ сделан в декабре.
     *
     * @param order Заказ для проверки.
     * @return true, если заказ был сделан в декабре, в противном случае - false.
     */
    private static boolean isDataOfOrder(OrderData order) {
        LocalDate orderedDate = LocalDate.parse(order.getOrderedAt(), DATE_FORMATTER);
        return orderedDate.getMonthValue() == DECEMBER;
    }

    /**
     * Находит самые популярные категории на основе заданных количеств категорий.
     *
     * @param categoryCounts Map, содержащая имена категорий и их количество.
     * @return List наиболее популярных категорий.
     */
    public static List<String> findPopularCategories(Map<String, Integer> categoryCounts) {
        logger.info("Finding popular categories...");
        int maxCount = categoryCounts.values().stream().max(Integer::compareTo).orElse(0);
        return categoryCounts.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
    }

    /**
     * Генерирует JSON-отчет на основе списка популярных категорий.
     *
     * @param popularCategories Список популярных категорий.
     * @return String JSON-отчета.
     */
    public static String generateReport(List<String> popularCategories) {
        logger.info("Generating report...");
        Gson gson = new Gson();
        return gson.toJson(Map.of("categories", popularCategories));
    }
}




