package repository;

import model.DiscountCard;
import model.Product;

import java.util.Map;
import java.util.Optional;

public interface Repository <T, V>{

    T save(T t);
    Map<V, T> getAll();
    Optional<T> getById(V v);

}
