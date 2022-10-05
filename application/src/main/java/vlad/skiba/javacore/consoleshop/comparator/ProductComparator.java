package vlad.skiba.javacore.consoleshop.comparator;

import vlad.skiba.javacore.consoleshop.exception.ApplicationGenericException;
import vlad.skiba.javacore.consoleshop.model.Product;
import vlad.skiba.javacore.consoleshop.parser.Order;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Comparator;
import java.util.Map;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class ProductComparator implements Comparator<Product> {

    private LinkedHashMap<String, Order> sortingCriteriaMap;

    public ProductComparator(LinkedHashMap<String, Order> sortingCriteriaMap) {
        this.sortingCriteriaMap = sortingCriteriaMap;
    }

    @Override
    public int compare(Product p1, Product p2) {
        int result = 0;
        for (Map.Entry<String, Order> fieldNameAndOrder : sortingCriteriaMap.entrySet()) {
            String fieldName = fieldNameAndOrder.getKey();
            Order order = fieldNameAndOrder.getValue();
            Comparable c1 = getFieldValueForComparison(p1, fieldName);
            Comparable c2 = getFieldValueForComparison(p2, fieldName);
            result = c1.compareTo(c2);
            if (result != 0) {
                if (order == Order.DESC) {
                    result = -result;
                }
                break;
            }
        }
        return result;
    }

    private Comparable getFieldValueForComparison(Product product, String fieldName) {
        try {
            Field field = Product.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (Comparable) field.get(product);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new ApplicationGenericException("No such field found in Product or accessing error: ", ex);
        }
    }

}