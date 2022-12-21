package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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

public class JavaConfig{

    // Repository

    @Bean
    public DiscountCardRepository discountCardRepository(){
        return new DiscountCardRepositoryImpl();
    }

    @Bean
    public ProductRepository productRepository(){
        return new ProductRepositoryImpl();
    }

    @Bean
    public ReceiptRepository receiptRepository(){
        return new ReceiptRepositoryImpl();
    }

    @Bean
    public ProductWarehouseRepository productWarehouseRepository(){
        return new ProductWarehouseRepositoryImpl();
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

