package vlad.skiba.javacore.consoleshop.helper.impl;

import vlad.skiba.javacore.consoleshop.comparator.ProductComparator;
import vlad.skiba.javacore.consoleshop.helper.CatalogSortingHelper;
import vlad.skiba.javacore.consoleshop.model.Catalog;
import vlad.skiba.javacore.consoleshop.model.Category;
import vlad.skiba.javacore.consoleshop.model.Product;
import vlad.skiba.javacore.consoleshop.parser.Order;

import java.util.stream.Collectors;
import java.util.*;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class CatalogSortingHelperImpl implements CatalogSortingHelper {

    @Override
    public Map<String, List<Product>> sortCatalogByCriteria(Catalog catalog, LinkedHashMap<String, Order> sortingCriteriaMap) {
        Comparator<Product> comparator = new ProductComparator(sortingCriteriaMap);
        return catalog.getCategories()
                .stream()
                .collect(Collectors.toMap(
                        Category::getName,
                        category -> category.getProducts()
                                .stream()
                                .sorted(comparator)
                                .collect(Collectors.toList())));
    }

}