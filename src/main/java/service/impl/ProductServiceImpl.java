package service.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import repository.ProductRepository;
import service.ProductService;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
