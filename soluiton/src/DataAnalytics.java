import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataAnalytics {

    public String getAnalytics() throws IOException {
        // Указываю путь к файлу с данными которые буду читать
        var fileName = "data/format.json";
        // Создаю экземпляр класса ObjectMapper для того, чтобы прочитать json файл
        var objectMapper = new ObjectMapper();
        // Создаю список, куда добавляю заказы в виде объектов Java пока читаю файл
        List<Order> orders = Arrays.asList(objectMapper.readValue(new File(fileName), Order[].class));
        // Создаю мапу для хранения названий категорий и количества их появлений в заказах
        Map<String, Integer> counterNamesMap = new HashMap();
        // Цикл, который проходится во всем заказам. В нём цикл, который проходится по всем позициям заказа, берёт из
        // позиции имя категории и добавляет в мапу, также записывая количество появлений в заказах
        for (Order order : orders) {
            for (Item item : order.getItems()) {
                var categoryName = item.getCategory().getName();
                counterNamesMap.put(categoryName, counterNamesMap.getOrDefault(categoryName, 0) + 1);
            }
        }
        // Инициализирую переменную, которая будет содержать количество появлений самой востребованной категории.
        // Создаю цикл, который проходится по всем значениям из мапы и ищет наибольшее
        var mostRecentCategory = Integer.MIN_VALUE;
        for (int value : counterNamesMap.values()) {
            mostRecentCategory = Math.max(mostRecentCategory, value);
        }
        // Создаю список в котором буду содержаться категории с наибольшей востребованностью и с помощью цикла
        // прохожусь по мапе, сравнивая наибольшее значение со значением из мапы. Добавляю самые востребованные
        // категории в список
        List<String> popularCategoriesNames = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : counterNamesMap.entrySet()) {
            if (entry.getValue() == mostRecentCategory) {
                popularCategoriesNames.add(entry.getKey());
            }
        }
        //Сортирую названия(если их несколько) в алфавитном порядке
        Collections.sort(popularCategoriesNames);
        // Инициализирую переменную в которую записываю полученные категории в соответствии с требованиями
        var result = "{\"months\": " + objectMapper.writeValueAsString(popularCategoriesNames) + "}";
        // Возвращаю результат
        return result;
    }
}
