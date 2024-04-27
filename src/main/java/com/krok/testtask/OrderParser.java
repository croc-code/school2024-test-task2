package com.krok.testtask;

import com.krok.testtask.orderDetails.Order;
import java.io.IOException;
import java.util.List;

/**
 * Интерфейс, который содержит один метод для парсинга json с данными
 * @author Pavel
 */
public interface OrderParser {
    public  List<Order> parse(String filePath) throws IOException;
}