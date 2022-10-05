package vlad.skiba.javacore.consoleshop.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class Product extends BaseEntity {

    private String name;
    private String description;
    private BigDecimal price;
    private String manufacturer;
    private Long categoryId;

    public Product(String name, String description, BigDecimal price, String manufacturer) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    public Product(String name, String description, BigDecimal price, String manufacturer, Long categoryId) {
        this(name, description, price, manufacturer);
        this.categoryId = categoryId;
    }

    public Product(Long id, String name, String description, BigDecimal price, String manufacturer, Long categoryId) {
        this(name, description, price, manufacturer, categoryId);
        super.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(price, product.price) &&
                Objects.equals(manufacturer, product.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, manufacturer);
    }

    @Override
    public String toString() {
        return String.format("Name: %s \nDescription: %s \nPrice: %.2f$ \nManufacturer: %s \n\n",
                getName(), getDescription(), getPrice(), getManufacturer());
    }

}