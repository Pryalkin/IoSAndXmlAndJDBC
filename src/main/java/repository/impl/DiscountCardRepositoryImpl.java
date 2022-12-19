package repository.impl;

import model.DiscountCard;
import repository.DiscountCardRepository;
import xml.XMLParse;
import xml.factory.ScoreXMLFactory;

import java.util.*;
import java.util.stream.Collectors;

public class DiscountCardRepositoryImpl implements DiscountCardRepository {

    private static DiscountCardRepository discountCardRepository;
    private Long id = 0L;
    private static XMLParse<DiscountCard, Long> xmlParse = new ScoreXMLFactory().getXMLDiscountCard();
    private Map<Long, DiscountCard> discountCards = xmlParse.get();

    private DiscountCardRepositoryImpl() {
    }

    public static DiscountCardRepository getInstance() {
        if (discountCardRepository == null) {
            discountCardRepository = new DiscountCardRepositoryImpl();
            return discountCardRepository;
        }
        return discountCardRepository;
    }

    @Override
    public DiscountCard save(DiscountCard discountCard) {
        if (discountCard.getId() == 0){
            id++;
            discountCard.setId(id);
            discountCards.put(id, discountCard);
        } else {
            discountCards.remove(discountCard.getId());
            discountCards.put(discountCard.getId(), discountCard);
        }
        setDiscountCardInXML(discountCards.values());
        return discountCard;
    }

    @Override
    public Map<Long, DiscountCard> getAll() {
        return discountCards;
    }

    @Override
    public Optional<DiscountCard> getById(Long aLong) {
        if (discountCards.containsKey(aLong)){
            return Optional.of(discountCards.get(aLong));
        }
        return Optional.empty();
    }

    private void setDiscountCardInXML(Collection<DiscountCard> discountCards) {
        xmlParse.set(discountCards);
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

