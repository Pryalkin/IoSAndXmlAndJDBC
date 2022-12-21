package repository;

import model.DiscountCard;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Repository <T, V>{

    T save(T t) throws SQLException;
    List<T> getAll() throws SQLException;
    Optional<T> getById(V v) throws SQLException;

}
