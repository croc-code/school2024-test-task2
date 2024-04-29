package edu.croc;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Collectors;
import java.util.*;

public class Task {

    public static void main(String[] args){

        File file = new File(".").toPath().toAbsolutePath().getParent().resolve("application/src/main/resources/input.json").toFile();

        try(Reader reader = new FileReader(file)){

            PrintWriter out = new PrintWriter(System.out);

            //Десериализация данных из JSON файла
            Gson gson = new Gson();
            Order[] orders = gson.fromJson(reader, Order[].class);

            HashMap<String, Integer> table = getCategoriesAndCount(orders);

            //Сортировка таблицы в порядке возрастания значений (количества товаров данной категории)
            LinkedHashMap<String, Integer> sortedTable = table.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (oldValue, newValue) -> oldValue,
                    LinkedHashMap::new
                ));

            //Получение самой популярной категории
            var polledLastEntry = sortedTable.pollLastEntry();
            List<String> popularCategories = new ArrayList<>();
            popularCategories.add(polledLastEntry.getKey());

            //Получение всех категорий, имеющих такое же количество товаров
            while (Objects.equals(polledLastEntry.getValue(), sortedTable.lastEntry().getValue())){
                polledLastEntry = sortedTable.pollLastEntry();
                popularCategories.add(polledLastEntry.getKey());
            }

            out.print("{«categories»: [");
            for (int i = 0; i < popularCategories.size(); i++) {
                out.print("\"" + popularCategories.get(i) + "\"");
                if (i < popularCategories.size() - 1) {
                    out.print(", ");
                }
            }
            out.println("]}");

            out.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Метод для получения таблицы в формате: Категория - Количество товаров данной категории, купленных в декабре
    private static HashMap<String, Integer> getCategoriesAndCount(Order[] orders) {
        HashMap<String, Integer> table = new HashMap<>();

        for(Order ord : orders){
            Month month = LocalDateTime.parse(ord.orderedAt()).getMonth();
            if(month.equals(Month.DECEMBER)){
                for(Order.Item item : ord.items()){
                    String categoryName = item.category().name();
                    int count = table.getOrDefault(categoryName,0) + 1;
                    table.put(categoryName, count);
                }
            }
        }
        return table;
    }
}
