package service.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import repository.ProductWarehouseRepository;
import service.ProductWarehouseService;

public class ProductWarehouseServiceImpl implements ProductWarehouseService {

    private final ProductWarehouseRepository productWarehouseRepository;


    public ProductWarehouseServiceImpl(ProductWarehouseRepository productWarehouseRepository) {
        this.productWarehouseRepository = productWarehouseRepository;
    }
}
