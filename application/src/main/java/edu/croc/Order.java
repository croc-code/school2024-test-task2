package edu.croc;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public record Order(
        @SerializedName(value = "ordered_at")
        String orderedAt,
        List<Item> items
){
    public record Item(
        String id,
        String name,
        Category category
    ){
        public record Category (
            String id,
            String name
        ) {}
    }
}