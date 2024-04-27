package com.krok.testtask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krok.testtask.orderDetails.Order;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestTask {

    public static void main(String[] args) {
        try {
            List<String> categories = OrderCategoriesFilter.getMostPopularCategoriesFromDecemberOrders(
                    "format.json", (filePath) -> {
                        ObjectMapper mapper = new ObjectMapper();
                        InputStreamReader reader
                        = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
                        return mapper.readValue(reader, new TypeReference<List<Order>>() {
                        });

                    });
            System.out.println(UsefulTools.beautyCatigoriesOutput(categories));
        } catch (IOException ex) {
            Logger.getLogger(TestTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
