package amgoize.service;

import amgoize.entity.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReader {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new JavaTimeModule());
    }

    public List<Order> readOrders(String fileName) {
        try {
            List<Order> orders = MAPPER.readValue(new File(fileName), new TypeReference<List<Order>>() {});
            if (orders == null || orders.isEmpty()) {
                throw new RuntimeException("File " + fileName + " is empty or contains no orders");
            }
            return orders;
        } catch (IOException e) {
            System.err.printf("Couldn't read orders from file %s\n", fileName);
            throw new RuntimeException(e);
        }
    }
}

