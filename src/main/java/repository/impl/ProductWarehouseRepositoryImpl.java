package repository.impl;

import model.Product;
import model.ProductWarehouse;
import repository.ProductRepository;
import repository.ProductWarehouseRepository;
import xml.XMLParse;
import xml.factory.ScoreXMLFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ProductWarehouseRepositoryImpl implements ProductWarehouseRepository {

    private static ProductWarehouseRepository productWarehouseRepository;
    private static XMLParse<ProductWarehouse, Long> xmlParse = new ScoreXMLFactory().getXMLProductWarehouse();
    private Map<Long, ProductWarehouse> productWarehouses = xmlParse.get();
    private Long id = 0L;

    public static ProductWarehouseRepository getInstance() {
        if (productWarehouseRepository == null) {
            productWarehouseRepository = new ProductWarehouseRepositoryImpl();
            return productWarehouseRepository;
        }
        return productWarehouseRepository;
    }

    @Override
    public ProductWarehouse save(ProductWarehouse productWarehouse) {
        if (productWarehouse.getId() == 0){
            id++;
            productWarehouse.setId(id);
            productWarehouses.put(id, productWarehouse);
        } else {
            productWarehouses.remove(productWarehouse.getId());
            productWarehouses.put(productWarehouse.getId(), productWarehouse);
        }
        setProductsInXML(productWarehouses.values());
        return productWarehouse;
    }

    @Override
    public Map<Long, ProductWarehouse> getAll() {
        return productWarehouses;
    }

    @Override
    public Optional<ProductWarehouse> getById(Long aLong) {
        if (productWarehouses.containsKey(aLong)){
            return Optional.of(productWarehouses.get(aLong));
        }
        return Optional.empty();
    }

    private void setProductsInXML(Collection<ProductWarehouse> productWarehouses) {
        xmlParse.set(productWarehouses);
    }

    @Override
    public Optional<ProductWarehouse> getByProduct(Product product) {
        List<ProductWarehouse> pws = productWarehouses.values().stream().filter(pw -> Objects.equals(pw.getProduct(), product)).collect(Collectors.toList());
        if (pws.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(pws.get(0));
    }
}
