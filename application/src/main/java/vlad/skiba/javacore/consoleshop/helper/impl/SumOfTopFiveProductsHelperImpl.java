package vlad.skiba.javacore.consoleshop.helper.impl;

import vlad.skiba.javacore.consoleshop.helper.SumOfTopFiveProductsHelper;
import vlad.skiba.javacore.consoleshop.model.Category;
import vlad.skiba.javacore.consoleshop.model.Product;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class SumOfTopFiveProductsHelperImpl implements SumOfTopFiveProductsHelper {

    private static final int TOP_QUANTITY = 5;

    @Override
    public BigDecimal sumOfTopFiveProductsPrices(Category category) {
        return category.getProducts()
                .stream()
                .map(Product::getPrice)
                .sorted(Comparator.reverseOrder())
                .limit(TOP_QUANTITY)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}