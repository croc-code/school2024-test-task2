package amgoize.service;

import amgoize.entity.MostPopularCategories;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoriesWriter {
    private final ObjectMapper objectMapper;

    public String writeCategories(MostPopularCategories mostPopularCategories) {
        try {
            return objectMapper.writeValueAsString(mostPopularCategories);
        } catch (JsonProcessingException e) {
            System.err.printf("Couldn't convert object to json %s\n", mostPopularCategories);
            throw new RuntimeException(e);
        }
    }
}
