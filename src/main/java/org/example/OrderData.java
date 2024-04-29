package org.example;

import java.util.List;

public class OrderData {

    private String ordered_at;
    private List<ItemData> items;

    public String getOrderedAt() {
        return ordered_at;
    }

    public void setOrderedAt(String ordered_at) {
        this.ordered_at = ordered_at;
    }

    public List<ItemData> getItems() {
        return items;
    }

    public void setItems(List<ItemData> items) {
        this.items = items;
    }
}
