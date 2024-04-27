package com.krok.testtask.orderDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

public class Order {

    @JsonProperty("ordered_at")
    private Date orderedAt;
    @JsonProperty("items")
    private List<Item> items;

    public Date getOrderedAt() {
        return orderedAt;
    }

    public List<Item> getListOfItems() {
        return items;
    }
}
