package amgoize.entity;

import java.util.List;

public class MostPopularCategories {
    private List<String> categories;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString(){
        return "Categories:" + categories;
    }

}
