package vlad.skiba.javacore.consoleshop.service.impl;

import vlad.skiba.javacore.consoleshop.model.Product;
import vlad.skiba.javacore.consoleshop.repository.ProductRepository;
import vlad.skiba.javacore.consoleshop.service.ProductService;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

}