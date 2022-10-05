package vlad.skiba.javacore.consoleshop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class Catalog {

    private String catalogName;
    private List<Category> categories = new ArrayList<>();

    public Catalog(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}