package ru.croc.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.croc.models.Order;
import ru.croc.models.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonConverter {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Response convertJsonToResponse(File json) {
        Response  response = null;
        try {
            // Корневой элемент JSON'а это массив заказов, поэтому преобразуем его в список
            response = new Response(mapper.readValue(json, new TypeReference<List<Order>>() {}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static String convertCategoriesToJson(List<String> categories) {
        return "{categories: " + categories + "}";
    }

    public static ObjectMapper getObjectMapper() {
        return mapper;
    }
}
