package service.impl;

import exception.*;
import model.DiscountCard;
import model.Product;
import model.ProductWarehouse;
import model.Receipt;
import repository.DiscountCardRepository;
import repository.ProductRepository;
import repository.ProductWarehouseRepository;
import repository.ReceiptRepository;
import service.ReceiptService;

import java.util.*;

import static constant.ExceptionConstant.*;

public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final DiscountCardRepository discountCardRepository;
    private final ProductRepository productRepository;
    private final ProductWarehouseRepository productWarehousesRepository;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository,
                              DiscountCardRepository discountCardRepository,
                              ProductRepository productRepository,
                              ProductWarehouseRepository productWarehousesRepository) {
        this.receiptRepository = receiptRepository;
        this.discountCardRepository = discountCardRepository;
        this.productRepository = productRepository;
        this.productWarehousesRepository = productWarehousesRepository;
    }

    @Override
    public Receipt parseTheRequest(String[] message) {
        Receipt receipt = new Receipt.Builder().build();
        List<ProductWarehouse> productWarehouses = new ArrayList<>();
        for (String el : message) {
            int dash = el.indexOf("-");
            if (dash == -1) throw new DashNotFoundException(DASH_NOT_FOUND);
            String in = el.substring(0, dash);
            if (Objects.equals(in, "card")) {
                String numberCard = el.substring(dash + 1);
                DiscountCard discountCard =  discountCardRepository.getByNameDiscountCard(numberCard)
                        .orElseThrow(() -> new DiscountCardNotFoundException(DISCOUNT_CARD_NOT_FOUND));
                receipt = new Receipt.Builder(receipt).setDiscountCard(discountCard).build();
            } else {
                if (!isNumber(in)) throw new IdNotFoundException(ID_NOT_FOUND);
                long id = Long.parseLong(in);
                String amountStr = el.substring(dash + 1);
                if (!isNumber(amountStr)) throw new AmountNotFoundException(AMOUNT_NOT_FOUND);
                int amount = Integer.parseInt(amountStr);
                Product product = productRepository.getById(id).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
                ProductWarehouse productWarehouse = productWarehousesRepository
                        .getByProduct(product)
                        .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
                if (productWarehouse.getAmount() >= amount){
                    int newAmount = productWarehouse.getAmount() - amount;
                    productWarehouse = new ProductWarehouse.Builder(productWarehouse).setAmount(newAmount).build();
                    productWarehousesRepository.save(productWarehouse);
                } else {
                    throw new AmountNotFoundInStockException(AMOUNT_NOT_FOUND_IN_STOCK);
                }
                productWarehouse = new ProductWarehouse.Builder(productWarehouse).setAmount(amount).build();
                productWarehouses.add(productWarehouse);
            }
        }
        receipt = new Receipt.Builder(receipt).setProductWarehouses(productWarehouses).setCreateDate(new Date()).build();
        return receiptRepository.save(receipt);
    }

    @Override
    public List<Receipt> getAll() {
        return receiptRepository.getAll().values().stream().toList();
    }

    private boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }
}
