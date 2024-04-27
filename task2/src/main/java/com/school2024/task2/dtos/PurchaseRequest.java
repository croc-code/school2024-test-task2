package com.school2024.task2.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PurchaseRequest {
    @JsonProperty("ordered_at")
    private LocalDateTime orderedAt;

    @JsonProperty("items")
    private List<Item> items = new ArrayList<>();

    @Data
    public static class Item {
        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("category")
        private Category category = new Category();

        @Data
        public static class Category {
            @JsonProperty("id")
            private String id;

            @JsonProperty("name")
            private String name;
        }
    }
}
