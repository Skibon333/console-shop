package vlad.skiba.javacore.consoleshop.repository;

import vlad.skiba.javacore.consoleshop.model.Category;
import vlad.skiba.javacore.consoleshop.model.Product;
import vlad.skiba.javacore.consoleshop.exception.RepositoryException;
import vlad.skiba.javacore.consoleshop.connection.ConnectionManager;
import vlad.skiba.javacore.consoleshop.repository.impl.CategoryRepositoryImpl;
import vlad.skiba.javacore.consoleshop.repository.impl.ProductRepositoryImpl;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Collection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;

@RunWith(MockitoJUnitRunner.class)
public class ProductRepositoryImplTest {

    private static final String CATEGORY_NAME = "Category for tests";
    private static final String PRODUCT_NAME = "Product for tests";
    private static final String PRODUCT_DESCRIPTION = "Product description for tests";
    private static final BigDecimal PRODUCT_PRICE = new BigDecimal("2000");
    private static final String PRODUCT_MANUFACTURER = "China";
    private static final String DELETE_CATEGORY_BY_NAME_SQL = "DELETE FROM categories WHERE name = '" + CATEGORY_NAME + "';";
    private static final String DELETE_PRODUCT_BY_NAME_SQL = "DELETE FROM products WHERE name = '" + PRODUCT_NAME + "';";
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        categoryRepository = new CategoryRepositoryImpl();
        productRepository = new ProductRepositoryImpl();
    }

    @After
    public void cleanUp() {
        deleteDataUsedForTesting();
    }

    @Test
    public void testSaving() {
        Category category = new Category(CATEGORY_NAME);
        categoryRepository.save(category);
        Product product = new Product(PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_PRICE, PRODUCT_MANUFACTURER, category.getId());
        productRepository.save(product);
        Assert.assertTrue(checkProductExistenceByName(CATEGORY_NAME, PRODUCT_NAME));
    }

    private boolean checkProductExistenceByName(String categoryName, String productName) {
        return categoryRepository.findAll()
                .stream()
                .filter(category -> category.getName().equals(categoryName))
                .map(Category::getProducts)
                .flatMap(Collection::stream)
                .map(Product::getName)
                .anyMatch(name -> name.equals(productName));
    }

    private void deleteDataUsedForTesting() {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_PRODUCT_BY_NAME_SQL);
            statement.executeUpdate(DELETE_CATEGORY_BY_NAME_SQL);
            connection.commit();
        } catch (SQLException ex) {
            throw new RepositoryException("The products hasn't been deleted from the database! ", ex);
        }
    }

}