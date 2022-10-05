package vlad.skiba.javacore.consoleshop.helper.impl;

import vlad.skiba.javacore.consoleshop.helper.CatalogInputOutputHelper;
import vlad.skiba.javacore.consoleshop.model.Catalog;
import vlad.skiba.javacore.consoleshop.model.Category;
import vlad.skiba.javacore.consoleshop.model.Product;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.List;
import java.util.Map;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class CatalogInputOutputHelperImpl implements CatalogInputOutputHelper {

    private static final Scanner USER_INPUT_SCANNER = new Scanner(System.in);

    @Override
    public Catalog createCatalog() {
        System.out.println("Enter name of catalog: ");
        String catalogName = USER_INPUT_SCANNER.nextLine();
        return new Catalog(catalogName);
    }

    @Override
    public Category createCategory() {
        System.out.println("Enter category name: ");
        String categoryName = USER_INPUT_SCANNER.nextLine();
        return new Category(categoryName);
    }

    @Override
    public Product createProduct(Long categoryId) {
        System.out.println("Enter product name: ");
        String name = USER_INPUT_SCANNER.nextLine();
        System.out.println("Enter product description: ");
        String description = USER_INPUT_SCANNER.nextLine();
        System.out.println("Enter product price: ");
        BigDecimal price = new BigDecimal(USER_INPUT_SCANNER.nextLine());
        System.out.println("Enter product manufacturer: ");
        String manufacturer = USER_INPUT_SCANNER.nextLine();
        return new Product(name, description, price, manufacturer, categoryId);
    }

    @Override
    public void displayCatalog(Map<String, List<Product>> categoryNameToProductsMap) {
        String separator = "-----------------------------------------------------------------------------\n";
        StringBuilder message = new StringBuilder();
        message.append("Product catalog: \n");
        for (Map.Entry<String, List<Product>> categoryNameToProducts : categoryNameToProductsMap.entrySet()) {
            String categoryName = categoryNameToProducts.getKey();
            List<Product> products = categoryNameToProducts.getValue();
            message.append(separator);
            if (products.isEmpty()) {
                message.append(String.format("The category \"%s\" doesn't contain products! \n%s\n",
                        categoryName, separator));
            } else {
                message.append(String.format("The category \"%s\" contains the following products: \n%s\n",
                        categoryName, separator, products.toString()));
                for (Product product : products) {
                    message.append(product.toString());
                }
            }
        }
        message.append(separator);
        System.out.println(message);
    }

}