package ru.baydak.Models;

import java.util.List;

public class CategoriesAnswer {
    List<String> categories;

    public CategoriesAnswer(List<String> categories) {
        this.categories = categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }
}
