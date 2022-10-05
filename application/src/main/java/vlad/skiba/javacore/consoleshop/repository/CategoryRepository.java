package vlad.skiba.javacore.consoleshop.repository;

import vlad.skiba.javacore.consoleshop.model.Category;

import java.util.List;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public interface CategoryRepository {

    List<Category> findAll();

    void save(Category category);

}