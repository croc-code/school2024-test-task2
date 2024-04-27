package com.krok.testtask;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderCategoriesFilter {

    /**
     * Метод для сортировки фмльтрации самых популярных категорий товаров
     * @param filePath - путь дло файла, который хотим распарсить
     * @param orderParser - интерфейс, который содержит метод для парсинга
     * @return - список самых популярных категорий с 1 по 31 декабря
     * @throws IOException
     */
    public static List<String> getMostPopularCategoriesFromDecemberOrders(
            String filePath, OrderParser orderParser) throws IOException {
        Map<String, Long> categoryCounts = orderParser.parse(filePath).stream()
                .filter(order -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(order.getOrderedAt());
                    return calendar.get(Calendar.MONTH) == Calendar.DECEMBER;
                })
                .flatMap(order -> order.getListOfItems().stream())
                .map(item -> item.getCategory().getName())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long maxCount = categoryCounts.values().stream()
                .max(Long::compare)
                .orElse(0L);

        return categoryCounts.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
    }
}
