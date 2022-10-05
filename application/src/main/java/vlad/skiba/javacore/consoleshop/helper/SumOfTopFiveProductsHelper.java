package vlad.skiba.javacore.consoleshop.helper;

import vlad.skiba.javacore.consoleshop.model.Category;

import java.math.BigDecimal;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public interface SumOfTopFiveProductsHelper {

    BigDecimal sumOfTopFiveProductsPrices(Category category);

}