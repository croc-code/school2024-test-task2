/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UsefulUtils;

import java.util.List;
import org.json.simple.JSONObject;

/**
 * Класс, который содержит различные полезные инструменты
 *
 * @author Pavel
 */
public class UsefulTools {

    private UsefulTools() {
    }

    /**
     * Метод для преобразования списка категорий к json формату
     *
     * @param list - список категорий
     * @return - Отформатированная строка, включающая все категории
     */
    public static String beautyCatigoriesOutput(List<String> list) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("categories", list);
        return jsonObject.toString();
    }
}
