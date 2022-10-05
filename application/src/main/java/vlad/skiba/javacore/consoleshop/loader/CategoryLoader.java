package vlad.skiba.javacore.consoleshop.loader;

import vlad.skiba.javacore.consoleshop.model.Category;

import java.util.List;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public interface CategoryLoader {

    List<Category> loadFromPackage(String packageForCategory);

}