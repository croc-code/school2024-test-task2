import com.google.gson.reflect.TypeToken;
import org.example.OrderData;
import org.example.PopularCategories;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PopularCategoriesTest {

    private static final Logger logger = LoggerFactory.getLogger(PopularCategoriesTest.class);

    @Test
    void testCountCategories() {
        logger.debug("Running testCountCategories...");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("одежда", 2);
        expected.put("бытовая техника", 1);
        try {
            List<OrderData> orders = PopularCategories.readDataFromJson("testFormat.json", new TypeToken<List<OrderData>>() {
            }.getType());
            Map<String, Integer> actual = PopularCategories.countCategories(orders);
            assertEquals(expected, actual);
        } catch (IOException e) {
            logger.error("Error executing testCountCategories", e);
        }
    }

    @Test
    void testFindPopularCategories() {
        logger.debug("Running testFindPopularCategories...");
        Map<String, Integer> categories = Map.of(
                "Category1", 7,
                "Category2", 7,
                "Category3", 2,
                "Category4", 4
        );
        List<String> expected = List.of("Category1", "Category2");
        List<String> actual = PopularCategories.findPopularCategories(categories);
        assertEquals(expected, actual);
    }

    @Test
    void testReport() {
        logger.debug("Running testReport...");
        List<String> popularCategories = List.of("Category1");
        String expected = "{\"categories\":[\"Category1\"]}";
        String actual = PopularCategories.generateReport(popularCategories);
        assertEquals(expected, actual);
    }
}
