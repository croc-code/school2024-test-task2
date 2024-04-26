package ru.baydak.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.baydak.Models.Order;
import ru.baydak.Models.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class    MyParser {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static Response convertJsonToResponse(File json) {
        Response  response = null;
        try {
            response = new Response(mapper.readValue(json, new TypeReference<List<Order>>() {}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }




}
