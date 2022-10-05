package vlad.skiba.javacore.consoleshop.model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class Category extends BaseEntity {

    private String name;
    private List<Product> products = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public Category(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        for (Product product : getProducts()) {
            message.append(product.toString());
        }
        return message.toString();
    }

}