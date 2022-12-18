package service.impl;

import exception.*;
import model.DiscountCard;
import model.Product;
import model.ProductWarehouse;
import model.Receipt;
import repository.Repository;
import service.Service;

import java.util.*;

import static constant.ExceptionConstant.*;

public class ServiceImpl implements Service{

    private final Repository repository;

    public ServiceImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Receipt parseTheRequest(String[] message) {
        Receipt receipt = new Receipt();
        List<ProductWarehouse> productWarehouses = new ArrayList<>();
        Arrays.stream(message).forEach(el -> {
            int dash = el.indexOf("-");
            if (dash == -1) throw new DashNotFoundException(DASH_NOT_FOUND);
            String in = el.substring(0, dash);
            if (Objects.equals(in, "card")){
                String numberCard = el.substring(dash+1);
                DiscountCard discountCard = repository.getByNameDiscountCard(numberCard)
                        .orElseThrow(() -> new DiscountCardNotFoundException(DISCOUNT_CARD_NOT_FOUND));
                receipt.setDiscountCard(discountCard);
            } else {
                if (!isNumber(in)) throw new IdNotFoundException(ID_NOT_FOUND);
                long id = Long.parseLong(in);
                String amountStr = el.substring(dash+1);
                if (!isNumber(amountStr)) throw new AmountNotFoundException(AMOUNT_NOT_FOUND);
                int amount = Integer.parseInt(amountStr);
                Product product = repository.getByIdProduct(id).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
                ProductWarehouse productWarehouse = new ProductWarehouse(product, amount);
                productWarehouses.add(productWarehouse);
            }
        });
        receipt.setProductWarehouses(productWarehouses);
        receipt.setCreateDate(new Date());
        return receipt;
    }

    private boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }

}
