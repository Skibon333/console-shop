package vlad.skiba.javacore.consoleshop.repository.impl;

import vlad.skiba.javacore.consoleshop.model.Category;
import vlad.skiba.javacore.consoleshop.model.Product;
import vlad.skiba.javacore.consoleshop.repository.CategoryRepository;
import vlad.skiba.javacore.consoleshop.repository.Repository;
import vlad.skiba.javacore.consoleshop.exception.RepositoryException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class CategoryRepositoryImpl extends Repository<Category> implements CategoryRepository {

    private static final String SELECT_CATEGORIES_SQL = "SELECT * FROM categories;";
    private static final String SELECT_PRODUCTS_SQL = "SELECT * FROM products;";
    private static final String INSERT_CATEGORY_SQL = "INSERT INTO categories (name) VALUES (?);";
    private static final String CATEGORY_ID_FIELD_NAME = "id";
    private static final String CATEGORY_NAME_FIELD_NAME = "name";
    private static final String PRODUCT_ID_FIELD_NAME = "id";
    private static final String PRODUCT_NAME_FIELD_NAME = "name";
    private static final String PRODUCT_DESCRIPTION_FIELD_NAME = "description";
    private static final String PRODUCT_PRICE_FIELD_NAME = "price";
    private static final String PRODUCT_MANUFACTURER_FIELD_NAME = "manufacturer";
    private static final String PRODUCT_CATEGORY_ID_FIELD_NAME = "category_id";

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        doInNewTransaction(connection -> {
            try (PreparedStatement getCategoriesStatement = connection.prepareStatement(SELECT_CATEGORIES_SQL,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
                 PreparedStatement getProductsStatement = connection.prepareStatement(SELECT_PRODUCTS_SQL,
                         ResultSet.TYPE_SCROLL_SENSITIVE,
                         ResultSet.CONCUR_UPDATABLE);
                 ResultSet categoriesResultSet = getCategoriesStatement.executeQuery();
                 ResultSet productsResultSet = getProductsStatement.executeQuery()) {
                while (categoriesResultSet.next()) {
                    Category category = createCategory(categoriesResultSet);
                    category.setProducts(findAllProductsByCategoryId(productsResultSet, category.getId()));
                    categories.add(category);
                    productsResultSet.first();
                }
            }
        });
        return categories;
    }

    @Override
    public void save(Category category) {
        doInNewTransaction(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORY_SQL, RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, category.getName());
                preparedStatement.executeUpdate();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        category.setId(generatedKeys.getLong(1));
                    }
                }
            }
        });
    }

    private Category createCategory(ResultSet categoriesResultSet) {
        try {
            Long id = categoriesResultSet.getLong(CATEGORY_ID_FIELD_NAME);
            String name = categoriesResultSet.getString(CATEGORY_NAME_FIELD_NAME);
            return new Category(id, name);
        } catch (SQLException ex) {
            throw new RepositoryException("Failed to create the category! ", ex);
        }
    }

    private Product createProduct(ResultSet productsResultSet) {
        try {
            Long id = productsResultSet.getLong(PRODUCT_ID_FIELD_NAME);
            String name = productsResultSet.getString(PRODUCT_NAME_FIELD_NAME);
            String description = productsResultSet.getString(PRODUCT_DESCRIPTION_FIELD_NAME);
            BigDecimal price = productsResultSet.getBigDecimal(PRODUCT_PRICE_FIELD_NAME);
            String manufacturer = productsResultSet.getString(PRODUCT_MANUFACTURER_FIELD_NAME);
            Long categoryId = productsResultSet.getLong(PRODUCT_CATEGORY_ID_FIELD_NAME);
            return new Product(id, name, description, price, manufacturer, categoryId);
        } catch (SQLException ex) {
            throw new RepositoryException("Failed to create the product! ", ex);
        }
    }

    private List<Product> findAllProductsByCategoryId(ResultSet productsResultSet, Long categoryId) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (productsResultSet.next()) {
            if (categoryId.equals(productsResultSet.getLong(PRODUCT_CATEGORY_ID_FIELD_NAME))) {
                products.add(createProduct(productsResultSet));
            }
        }
        return products;
    }

}