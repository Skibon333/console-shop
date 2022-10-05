package vlad.skiba.javacore.consoleshop.helper;

import vlad.skiba.javacore.consoleshop.model.Catalog;
import vlad.skiba.javacore.consoleshop.model.Category;
import vlad.skiba.javacore.consoleshop.model.Product;

import java.util.List;
import java.util.Map;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public interface CatalogInputOutputHelper {

    Catalog createCatalog();

    Category createCategory();

    Product createProduct(Long categoryId);

    void displayCatalog(Map<String, List<Product>> catalog);

}