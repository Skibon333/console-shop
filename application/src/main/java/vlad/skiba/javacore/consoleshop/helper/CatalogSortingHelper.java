package vlad.skiba.javacore.consoleshop.helper;

import vlad.skiba.javacore.consoleshop.model.Catalog;
import vlad.skiba.javacore.consoleshop.model.Product;
import vlad.skiba.javacore.consoleshop.parser.Order;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public interface CatalogSortingHelper {

    Map<String, List<Product>> sortCatalogByCriteria(Catalog catalog, LinkedHashMap<String, Order> sortingCriteriaMap);

}