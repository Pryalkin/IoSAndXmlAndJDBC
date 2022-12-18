package repository.impl;

import model.DiscountCard;
import model.Product;
import repository.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class RepositoryImpl implements Repository {

    private Long idProduct = 0L;
    private Long idDiscountCard = 0L;
    private Map<Long, Product> products = new HashMap<>();
    private Map<Long, DiscountCard> discountCards = new HashMap<>();

    @Override
    public Product saveProduct(Product product) {
        Product copyProduct = product;
        List<Product> productList = products.values().stream().filter(p -> p.equals(copyProduct)).collect(Collectors.toList());
        if (productList.isEmpty()){
            idProduct++;
            product.setId(idProduct);
            products.put(idProduct, product);
        } else {
            product = productList.get(0);
            products.remove(product.getId());
            products.put(product.getId(), product);
        }
        return product;
    }

    @Override
    public DiscountCard saveDiscountCard(DiscountCard discountCard) {
        DiscountCard copyDiscountCard = discountCard;
        List<DiscountCard> discountCardList = discountCards.values().stream().filter(p -> p.equals(copyDiscountCard)).collect(Collectors.toList());
        if (discountCardList.isEmpty()){
            idDiscountCard++;
            discountCard.setId(idDiscountCard);
            discountCards.put(idDiscountCard, discountCard);
        } else {
            discountCard = discountCardList.get(0);
            discountCards.remove(discountCard.getId());
            discountCards.put(discountCard.getId(), discountCard);
        }
        return discountCard;
    }

    @Override
    public Optional<Product> getByIdProduct(long id) {
        if (products.containsKey(id)){
            return Optional.of(products.get(id));
        }
        return Optional.empty();
    }

    @Override
    public Optional<DiscountCard> getByNameDiscountCard(String numberCard) {
        List<DiscountCard> dis = discountCards.values().stream().filter(d -> Objects.equals(d.getNumber(), numberCard)).collect(Collectors.toList());
        if (dis.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(dis.get(0));
    }
}
