package vlad.skiba.javacore.consoleshop.service.impl;

import vlad.skiba.javacore.consoleshop.model.Category;
import vlad.skiba.javacore.consoleshop.repository.CategoryRepository;
import vlad.skiba.javacore.consoleshop.service.CategoryService;

import java.util.List;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

}