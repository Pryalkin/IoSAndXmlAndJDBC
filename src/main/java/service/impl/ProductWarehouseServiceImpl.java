package service.impl;

import repository.ProductWarehouseRepository;
import service.ProductWarehouseService;

public class ProductWarehouseServiceImpl implements ProductWarehouseService {

    private final ProductWarehouseRepository productWarehouseRepository;


    public ProductWarehouseServiceImpl(ProductWarehouseRepository productWarehouseRepository) {
        this.productWarehouseRepository = productWarehouseRepository;
    }
}
