package org.example;

public class ItemData {

    private String id;
    private String name;
    private CategoryData category;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryData getCategory() {
        return category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(CategoryData category) {
        this.category = category;
    }
}

