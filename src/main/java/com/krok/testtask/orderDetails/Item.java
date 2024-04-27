package com.krok.testtask.orderDetails;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Item {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("category")
    private Category category;
 
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }
}
