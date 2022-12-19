package repository.impl;

import model.Product;
import repository.ProductRepository;
import xml.XMLParse;
import xml.factory.ScoreXMLFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepository {

    private static ProductRepository productRepository;
    private static XMLParse<Product, Long> xmlParse = new ScoreXMLFactory().getXMLProduct();
    private Long id = 0L;
    private Map<Long, Product> products = xmlParse.get();

    private ProductRepositoryImpl() {
    }

    public static ProductRepository getInstance() {
        if (productRepository == null) {
            productRepository = new ProductRepositoryImpl();
            return productRepository;
        }
        return productRepository;
    }

    @Override
    public Map<Long, Product> getAll() {
        return products;
    }

    @Override
    public Optional<Product> getById(Long aLong) {
        if (products.containsKey(aLong)){
            return Optional.of(products.get(aLong));
        }
        return Optional.empty();
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == 0){
            id++;
            product.setId(id);
            products.put(id, product);
        } else {
            products.remove(product.getId());
            products.put(product.getId(), product);
        }
        setProductsInXML(products.values());
        return product;
    }

    private void setProductsInXML(Collection<Product> products) {
        xmlParse.set(products);
    }
}
