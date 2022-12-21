package repository;

import model.DiscountCard;

import java.util.Optional;

public interface DiscountCardRepository extends Repository<DiscountCard, Integer> {
    Optional<DiscountCard> getByNameDiscountCard(String numberCard);
}
