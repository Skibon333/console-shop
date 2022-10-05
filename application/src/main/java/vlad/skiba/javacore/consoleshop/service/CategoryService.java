package vlad.skiba.javacore.consoleshop.service;

import vlad.skiba.javacore.consoleshop.model.Category;

import java.util.List;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public interface CategoryService {

    List<Category> findAll();

    void save(Category category);

}