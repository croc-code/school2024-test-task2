package ru.baydak.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.baydak.Filters.MyFilter;
import ru.baydak.Models.CategoriesAnswer;

import java.io.File;

public class AnswerParser {
    public static String generateAnswer(File json) {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoriesAnswer categoriesAnswer = new CategoriesAnswer(MyFilter.getPopularCategory(json));
        String jsonAnswer = null;
        try {
            jsonAnswer = objectMapper.writeValueAsString(categoriesAnswer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //
        return jsonAnswer;
    }
}
