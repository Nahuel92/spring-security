package org.nahuel.rodriguez.springsecurityfirsthandson.services;

import org.nahuel.rodriguez.springsecurityfirsthandson.entities.Product;
import org.nahuel.rodriguez.springsecurityfirsthandson.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Collection<Product> findAll() {
        return productRepository.findAll();
    }


}
