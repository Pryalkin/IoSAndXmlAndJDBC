import config.JavaConfig;
import model.DiscountCard;
import model.Product;
import model.ProductWarehouse;
import model.Receipt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.DiscountCardRepository;
import repository.ProductRepository;
import repository.ProductWarehouseRepository;
import repository.ReceiptRepository;
import service.ReceiptService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class RunnerClassName {

    private static String productCreation;

    static {
        Properties properties = new Properties();
        try (InputStream in = RunnerClassName.class.getClassLoader().getResourceAsStream("resources/app.properties")){
            properties.load(in);
            productCreation = properties.getProperty("productCreation");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
//        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        methodProductCreation(context);

        ReceiptService receiptService = context.getBean("receiptService", ReceiptService.class);
        if (args.length == 0){
            System.out.println("Запрос пустой!");
        } else {

            Receipt receipt = receiptService.parseTheRequest(args);
            System.out.println("--------------------------------------------------------------");
            System.out.println("DATE: " + receipt.getCreateDate());
            String qty = "QTY |";
            String description = "DESCRIPTION   |";
            String price = "PRICE |";
            String sum = "SUM   |";
            String auction = "AUCTION |";
            String discount = "DISCOUNT  |";
            String total = "TOTAL ";
            String line = qty + description + price + sum + auction + discount + total;
            System.out.println(line);
            receipt.getProductWarehouses().stream().forEach(pw -> {
                StringBuilder sb = new StringBuilder();
                double s = 0.0;

                String testLine = String.valueOf(pw.getAmount());
                int i = testLine.length();
                sb.append(testLine);
                for (int j = 0; j < qty.length()-i-1; j++) {
                    sb.append(" ");
                }
                sb.append("|");

                testLine = pw.getProduct().getName();
                i = testLine.length()/2;
                sb.append(testLine);
                for (int j = 0; j < description.length()-i-1; j++) {
                    sb.append(" ");
                }
                sb.append("|");

                testLine =  String.valueOf(pw.getProduct().getPrice());
                i = testLine.length();
                sb.append(testLine);
                for (int j = 0; j < price.length()-i-1; j++) {
                    sb.append(" ");
                }
                sb.append("|");

                s = pw.getAmount()*pw.getProduct().getPrice();
                testLine =  String.valueOf(s);
                i = testLine.length();
                sb.append(testLine);
                for (int j = 0; j < sum.length()-i-1; j++) {
                    sb.append(" ");
                }
                sb.append("|");

                if (pw.getProduct().getPromotional()){
                    sb.append("Акция   ");
                } else {
                    sb.append("        ");
                }
                sb.append("|");

                if (pw.getProduct().getPromotional() && pw.getAmount()>5){
                    sb.append("-10%      ");
                    s = s * 0.9;
                } else sb.append("          ");
                sb.append("|");

                sb.append(String.format("%.2f", s));

                System.out.println(sb);
            });
            System.out.println("--------------------------------------------------------------");
            double t = receipt.getProductWarehouses().stream()
                    .mapToDouble(pw -> {
                        if (pw.getProduct().getPromotional()){
                            return pw.getAmount()*pw.getProduct().getPrice()*0.9;
                        }
                        return pw.getAmount()*pw.getProduct().getPrice();
                    })
                    .sum();
            System.out.println("Общая сумма: " + String.format("%.2f", t));
            if (receipt.getDiscountCard() != null){
                System.out.println("Была предъявлена скидочная карта : " + receipt.getDiscountCard().getNumber());
                System.out.println("К ОПЛАТЕ: " +
                                           String.format("%.2f",
                                                         (t * ((100 - receipt.getDiscountCard().getDiscount())/100))));
            } else System.out.println("К ОПЛАТЕ: " + String.format("%.2f", t));
        }

    }

    private static void methodProductCreation(ApplicationContext context) {
        if (productCreation.equals("create")){
            System.out.println("Данные пересозданы.");
            Path pathDis = Paths.get("discount_card.xml");
            Path pathPr = Paths.get("product.xml");
            Path pathPw = Paths.get("product_warehouse.xml");
            Path pathR = Paths.get("receipt.xml");
            try {
                if (Files.exists(pathDis)) Files.delete(pathDis);
                if (Files.exists(pathPr)) Files.delete(pathPr);
                if (Files.exists(pathPw)) Files.delete(pathPw);
                if (Files.exists(pathR)) Files.delete(pathR);
            } catch (IOException e) {
                e.printStackTrace();
            }
            createProducts(context);
            createDiscountCard(context);
        } else if (productCreation.equals("update")){
            System.out.println("Данные обновлены.");
        } else System.out.println("Вы указали не верное значение в app.properties.");
    }

    private static void createDiscountCard(ApplicationContext context) {
        DiscountCardRepository discountCardRepository = context.getBean("discountCardRepository", DiscountCardRepository.class);
        DiscountCard discountCard = new DiscountCard.Builder().setNumber("1234").setDiscount(5.0).build();
        discountCard = discountCardRepository.save(discountCard);
        discountCard = new DiscountCard.Builder().setNumber("1254").setDiscount(7.0).build();
        discountCard = discountCardRepository.save(discountCard);
        discountCard = new DiscountCard.Builder().setNumber("1634").setDiscount(10.3).build();
        discountCard = discountCardRepository.save(discountCard);
    }

    private static void createProducts(ApplicationContext context) {
        ProductRepository productRepository = context.getBean("productRepository", ProductRepository.class);
        ProductWarehouseRepository productWarehouseRepository = context.getBean("productWarehouseRepository", ProductWarehouseRepository.class);

        Product product = new Product.Builder().setName("Шоколад").setPrice(2.34).build();
        product = productRepository.save(product);
        ProductWarehouse productWarehouse = new ProductWarehouse.Builder().setProduct(product).setAmount(17).build();
        productWarehouseRepository.save(productWarehouse);

        product = new Product.Builder().setName("Макороны").setPrice(3.35).setPromotional(true).build();
        product = productRepository.save(product);
        productWarehouse = new ProductWarehouse.Builder().setProduct(product).setAmount(11).build();
        productWarehouseRepository.save(productWarehouse);

        product = new Product.Builder().setName("Гречка").setPrice(4.19).setPromotional(true).build();
        product = productRepository.save(product);
        productWarehouse = new ProductWarehouse.Builder().setProduct(product).setAmount(9).build();
        productWarehouseRepository.save(productWarehouse);

        product = new Product.Builder().setName("Молоко").setPrice(1.62).build();
        product = productRepository.save(product);
        productWarehouse = new ProductWarehouse.Builder().setProduct(product).setAmount(12).build();
        productWarehouseRepository.save(productWarehouse);

        product = new Product.Builder().setName("Хлеб").setPrice(2.09).build();
        product = productRepository.save(product);
        productWarehouse = new ProductWarehouse.Builder().setProduct(product).setAmount(1).build();
        productWarehouseRepository.save(productWarehouse);

        product = new Product.Builder().setName("Масло").setPrice(4.11).build();
        product = productRepository.save(product);
        productWarehouse = new ProductWarehouse.Builder().setProduct(product).setAmount(6).build();
        productWarehouseRepository.save(productWarehouse);

        product = new Product.Builder().setName("Мороженое").setPrice(2.27).setPromotional(true).build();
        product = productRepository.save(product);
        productWarehouse = new ProductWarehouse.Builder().setProduct(product).setAmount(13).build();
        productWarehouseRepository.save(productWarehouse);
    }
}
