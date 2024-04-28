import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Класс, формирующий отчёт с наиболее популярными перед Новым Годом категориями.
 *
 * @author Yakodan (Кодинцев Д.М.)
 */
public class Report {



    /**
     * Основной метод класса. Формирует строку в формате JSON, содержащую отчётс популярными категориями.
     * @param input путь к файлу с данными о зказах.
     * @return Cтрока в формате JSON, содержащая отчётс популярными категориями.
     */
    public static String generateReport(String input) {
        HashMap<String, Integer> categories = new HashMap<>();
        JSONArray orders = new JSONArray();

        // Парсим входной файл
        try (FileReader reader = new FileReader(input)) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            orders = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // Считаем категории в покупках
        for (Object ord : orders) {
            JSONObject order = (JSONObject) ord;
            String month = getOrderMonth(order);

            // Выбираем только покупки в декабре
            if (month.equals("12")) {
                JSONArray items = (JSONArray) order.get("items");
                getOrderCategories(items, categories);
            }
        }

        JSONArray popularCategories = getPopularCategories(categories);
        JSONObject report = new JSONObject();
        report.put("categories", popularCategories);

        return report.toString();
    }

    /**
     * Извлекает месяц из даты покупки.
     * @param order объект {@link JSONObject}, содержащий информацию о покупке.
     * @return месяц покупки.
     */
    private static String getOrderMonth(JSONObject order) {
        String date = (String) order.get("ordered_at");
        return date.substring(5, 7);
    }

    /**
     * Пополняет {@link HashMap}, в которой содержится название категории и количество покупок в ней.
     * @param items массив {@link JSONArray}, содержащий объекты {@link JSONObject} из корзины заказа
     * @param categories {@link HashMap}, в который добавляются название категории
     *                   и количество покупок в данной категории
     */
    private static void getOrderCategories(JSONArray items, HashMap<String, Integer> categories) {

        for (Object it : items) {
            JSONObject item = (JSONObject) it;
            JSONObject category = (JSONObject) item.get("category");
            String categoryName = (String) category.get("name");

            if (categories.containsKey(categoryName)) {
                categories.put(categoryName, categories.get(categoryName) + 1); // +1 покупка в категории
            } else {
                categories.put(categoryName, 1); // Первая покупка в категории
            }
        }
    }

    /**
     * Формирует массив {@link JSONArray}, содержащий наиболее популярные категории.
     * @param categories {@link HashMap}, в который добавляются название категории
     *                         и количество покупок в данной категории.
     * @return массив {@link JSONArray}, содержащий наиболее популярные категории.
     */
    private static JSONArray getPopularCategories(HashMap<String, Integer> categories) {
        JSONArray popularCategories = new JSONArray();
        int max = Integer.MIN_VALUE;

        for (Map.Entry<String, Integer> entry : categories.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                popularCategories.clear(); // Сбрасываем массив, если находится более популярная категория
                popularCategories.add(entry.getKey());
            } else if (entry.getValue() == max) {
                popularCategories.add(entry.getKey()); // Добавляем настолько же популярную на данный момент категорию
            }
        }

        Collections.sort(popularCategories); // Сортируем в алфавитном порядке в соответсвие с условием

        return popularCategories;
    }

}
