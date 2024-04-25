package ru.croc.utils;

import ru.croc.models.Response;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {
    /**
     * Метод принимает JSON-файл и возвращает список категорий с максимальным количеством упоминаний
     * @param json файл в формате JSON
     * @return список категорий
     */
    public static List<String> getPopularCategory(File json) {
        if (json == null) {
            throw new IllegalArgumentException("JSON файл не существует");
        }

        Response response = JsonConverter.convertJsonToResponse(json);
        Map<String, Integer> categoriesCount = new HashMap<>();

        // Считаем количество упоминаний категорий
        response.getOrders().stream()
                .filter(order -> checkNewYearHolidays(order.getOrderedAt()))
                .forEach(order -> {
                    order.getItems().forEach(item -> {
                        categoriesCount.put(item.getCategory().getName(),
                                // Суммируем количество упоминаний по категориям
                                categoriesCount.getOrDefault(item.getCategory().getName(), 0) + 1);
            });
        });

        // Получаем максимальное количество упоминаний
        long maxCount = categoriesCount.values().stream().max(Integer::compareTo).orElse(0);

        // Возвращаем список категорий с максимальным количеством упоминаний
        return categoriesCount.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount) // Фильтруем все элементы категории с максимальным количеством упоминаний
                .map(Map.Entry::getKey)
                .sorted() // Сортируем по алфавиту
                .collect(Collectors.toList());
    }

    public static String generateReport(List<String> categories) {
        return JsonConverter.convertCategoriesToJson(categories);
    }

    private static LocalDateTime parseDate(String date) {
        return LocalDateTime.parse(date);
    }

    /**
     * Проверяет является ли дата предновогодней (1 - 31 декабря)
     * @param date дата в формате "yyyy-MM-dd"
     * @return true - является, false - не является
     */
    private static boolean checkNewYearHolidays(String date) {
        try {
            LocalDateTime orderDate = parseDate(date);
            return orderDate.getMonthValue() == 12;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
