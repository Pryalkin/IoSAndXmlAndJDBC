package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import repository.DiscountCardRepository;
import repository.ProductRepository;
import repository.ProductWarehouseRepository;
import repository.ReceiptRepository;
import repository.impl.DiscountCardRepositoryImpl;
import repository.impl.ProductRepositoryImpl;
import repository.impl.ProductWarehouseRepositoryImpl;
import repository.impl.ReceiptRepositoryImpl;
import service.DiscountCardService;
import service.ProductService;
import service.ProductWarehouseService;
import service.ReceiptService;
import service.impl.DiscountCardServiceImpl;
import service.impl.ProductServiceImpl;
import service.impl.ProductWarehouseServiceImpl;
import service.impl.ReceiptServiceImpl;

public class JavaConfig {

    // Repository

    @Bean
    public DiscountCardRepository discountCardRepository(){
        return DiscountCardRepositoryImpl.getInstance();
    }

    @Bean
    public ProductRepository productRepository(){
        return ProductRepositoryImpl.getInstance();
    }

    @Bean
    public ReceiptRepository receiptRepository(){
        return ReceiptRepositoryImpl.getInstance();
    }

    @Bean
    public ProductWarehouseRepository productWarehouseRepository(){
        return ProductWarehouseRepositoryImpl.getInstance();
    }

    // Service

    @Bean
    public ProductService productService(@Autowired ProductRepository productRepository){
        return new ProductServiceImpl(productRepository);
    }

    @Bean
    public DiscountCardService discountCardService(@Autowired DiscountCardRepository discountCardRepository){
        return new DiscountCardServiceImpl(discountCardRepository);
    }

    @Bean
    public ReceiptService receiptService(@Autowired ReceiptRepository receiptRepository,
                                         @Autowired DiscountCardRepository discountCardRepository,
                                         @Autowired ProductRepository productRepository,
                                         @Autowired ProductWarehouseRepository productWarehousesRepository){
        return new ReceiptServiceImpl(receiptRepository, discountCardRepository, productRepository, productWarehousesRepository);
    }

    @Bean
    public ProductWarehouseService productWarehouseService(@Autowired ProductWarehouseRepository productWarehouseRepository){
        return new ProductWarehouseServiceImpl(productWarehouseRepository);
    }

}

