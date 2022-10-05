package vlad.skiba.javacore.consoleshop.helper;

import vlad.skiba.javacore.consoleshop.model.Catalog;
import vlad.skiba.javacore.consoleshop.model.Category;
import vlad.skiba.javacore.consoleshop.model.Product;

import java.math.BigDecimal;

public class CatalogInitializingHelper {

    public static final String LAPTOP_CATEGORY_NAME = "Laptop";
    public static final String TABLET_CATEGORY_NAME = "Tablet";
    public static final String SMARTPHONE_CATEGORY_NAME = "Smartphone";
    public static final BigDecimal EXPECTED_SUM_IN_LAPTOP = new BigDecimal("9100.00");
    public static final BigDecimal EXPECTED_SUM_IN_TABLET = new BigDecimal("2400.00");
    public static final BigDecimal EXPECTED_SUM_IN_SMARTPHONE = new BigDecimal("1700.00");

    public static Catalog initializeCatalog() {
        Catalog catalog = new Catalog("Catalog for testing");
        Category laptop = new Category(LAPTOP_CATEGORY_NAME);
        Category tablet = new Category(TABLET_CATEGORY_NAME);
        Category smartphone = new Category(SMARTPHONE_CATEGORY_NAME);
        catalog.getCategories().add(laptop);
        catalog.getCategories().add(tablet);
        catalog.getCategories().add(smartphone);
        laptop.getProducts().add(new Product("Laptop", "Apple MacBook Pro 15", new BigDecimal("2800.00"), "USA", 1L));
        laptop.getProducts().add(new Product("Laptop", "Apple MacBook Air 13", new BigDecimal("2500.00"), "USA", 1L));
        laptop.getProducts().add(new Product("Laptop", "Lenovo Legion Y530-15ICH", new BigDecimal("950.00"), "China", 1L));
        laptop.getProducts().add(new Product("Laptop", "Asus ROG", new BigDecimal("1400.00"), "China", 1L));
        laptop.getProducts().add(new Product("Laptop", "Asus ROG", new BigDecimal("1300.00"), "China", 1L));
        laptop.getProducts().add(new Product("Laptop", "Dell G3 700", new BigDecimal("1100.00"), "USA", 1L));
        laptop.getProducts().add(new Product("Laptop", "Asus ZenBook", new BigDecimal("1100.00"), "China", 1L));
        laptop.getProducts().add(new Product("Laptop", "Huawei MateBook X Pro", new BigDecimal("800.00"), "China", 1L));
        tablet.getProducts().add(new Product("Tablet", "Samsung", new BigDecimal("900.00"), "South Korea", 2L));
        tablet.getProducts().add(new Product("Tablet", "Xiaomi", new BigDecimal("700.00"), "China", 2L));
        tablet.getProducts().add(new Product("Tablet", "Asus", new BigDecimal("800.00"), "China", 2L));
        smartphone.getProducts().add(new Product("Smartphone", "Xiaomi Mi Mix 3", new BigDecimal("400.00"), "China", 3L));
        smartphone.getProducts().add(new Product("Smartphone", "Xiaomi Redmi 6A", new BigDecimal("500.00"), "China", 3L));
        smartphone.getProducts().add(new Product("Smartphone", "Samsung Galaxy A50", new BigDecimal("800.00"), "China", 3L));
        return catalog;
    }

}