package repository;

import model.Product;
import model.ProductWarehouse;

import java.util.Optional;

public interface ProductWarehouseRepository extends Repository<ProductWarehouse, Long>{
    Optional<ProductWarehouse> getByProduct(Product product);
}
