package repository;

import model.DiscountCard;
import model.Product;

import java.util.Optional;

public interface Repository {
    Product saveProduct(Product product);
    DiscountCard saveDiscountCard(DiscountCard discountCard);

    Optional<Product> getByIdProduct(long id);

    Optional<DiscountCard> getByNameDiscountCard(String numberCard);
}
