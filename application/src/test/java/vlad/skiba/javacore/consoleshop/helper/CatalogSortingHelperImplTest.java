package vlad.skiba.javacore.consoleshop.helper;

import vlad.skiba.javacore.consoleshop.model.Catalog;
import vlad.skiba.javacore.consoleshop.model.Product;
import vlad.skiba.javacore.consoleshop.helper.impl.CatalogSortingHelperImpl;
import vlad.skiba.javacore.consoleshop.parser.Order;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static vlad.skiba.javacore.consoleshop.helper.CatalogInitializingHelper.*;

@RunWith(MockitoJUnitRunner.class)
public class CatalogSortingHelperImplTest {

    private final CatalogSortingHelper CATALOG_SORTING_HELPER = new CatalogSortingHelperImpl();
    private Catalog catalog;

    @Before
    public void setUp() {
        catalog = CatalogInitializingHelper.initializeCatalog();
    }

    @Test
    public void testSortingCatalogByCriteria() {
        Map<String, List<Product>> actualSorting = CATALOG_SORTING_HELPER.sortCatalogByCriteria(catalog, getSortingCriteriaMap());
        Assert.assertEquals(getExpectedSorting(), actualSorting);
    }

    private Map<String, List<Product>> getExpectedSorting() {
        Map<String, List<Product>> sortedCatalog = new HashMap<>();
        List<Product> sortedSmartphones = new ArrayList<>();
        List<Product> sortedLaptops = new ArrayList<>();
        List<Product> sortedTablets = new ArrayList<>();
        sortedSmartphones.add(new Product("Smartphone", "Xiaomi Mi Mix 3", new BigDecimal("400.00"), "China"));
        sortedSmartphones.add(new Product("Smartphone", "Xiaomi Redmi 6A", new BigDecimal("500.00"), "China"));
        sortedSmartphones.add(new Product("Smartphone", "Samsung Galaxy A50", new BigDecimal("800.00"), "China"));
        sortedLaptops.add(new Product("Laptop", "Huawei MateBook X Pro", new BigDecimal("800.00"), "China"));
        sortedLaptops.add(new Product("Laptop", "Lenovo Legion Y530-15ICH", new BigDecimal("950.00"), "China"));
        sortedLaptops.add(new Product("Laptop", "Dell G3 700", new BigDecimal("1100.00"), "USA"));
        sortedLaptops.add(new Product("Laptop", "Asus ZenBook", new BigDecimal("1100.00"), "China"));
        sortedLaptops.add(new Product("Laptop", "Asus ROG", new BigDecimal("1300.00"), "China"));
        sortedLaptops.add(new Product("Laptop", "Asus ROG", new BigDecimal("1400.00"), "China"));
        sortedLaptops.add(new Product("Laptop", "Apple MacBook Air 13", new BigDecimal("2500.00"), "USA"));
        sortedLaptops.add(new Product("Laptop", "Apple MacBook Pro 15", new BigDecimal("2800.00"), "USA"));
        sortedTablets.add(new Product("Tablet", "Xiaomi", new BigDecimal("700.00"), "China"));
        sortedTablets.add(new Product("Tablet", "Asus", new BigDecimal("800.00"), "China"));
        sortedTablets.add(new Product("Tablet", "Samsung", new BigDecimal("900.00"), "South Korea"));
        sortedCatalog.put(LAPTOP_CATEGORY_NAME, sortedLaptops);
        sortedCatalog.put(TABLET_CATEGORY_NAME, sortedTablets);
        sortedCatalog.put(SMARTPHONE_CATEGORY_NAME, sortedSmartphones);
        return sortedCatalog;
    }

    private LinkedHashMap<String, Order> getSortingCriteriaMap() {
        LinkedHashMap<String, Order> sortingCriteriaMap = new LinkedHashMap<>();
        sortingCriteriaMap.put("name", Order.ASC);
        sortingCriteriaMap.put("price", Order.ASC);
        sortingCriteriaMap.put("manufacturer", Order.DESC);
        return sortingCriteriaMap;
    }

}