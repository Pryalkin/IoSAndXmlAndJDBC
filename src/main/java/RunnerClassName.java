import config.JavaConfig;
import model.DiscountCard;
import model.Product;
import model.ProductWarehouse;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import repository.DiscountCardRepository;
import repository.ProductRepository;
import repository.ProductWarehouseRepository;
import repository.ReceiptRepository;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@Component
@ComponentScan
public class RunnerClassName {

    private static String ddlAuto;
    private static Connection conn;

    static {
        String url = null;
        String username = null;
        String password = null;

        try(InputStream in = ReceiptRepository.class
                .getClassLoader().getResourceAsStream("app.properties")){
            Properties properties = new Properties();
            properties.load(in);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, username, password);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        Properties properties = new Properties();
        try (InputStream in = RunnerClassName.class.getClassLoader().getResourceAsStream("resources/app.properties")){
            properties.load(in);
            ddlAuto = properties.getProperty("ddl-auto");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, FileNotFoundException {
         ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
//        ApplicationContext context = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
//        createDB(context);
    }

    static void createDB(ApplicationContext context) throws SQLException, FileNotFoundException {

        try(Statement stmt = conn.createStatement();){
            if (ddlAuto.equals("drop")){
                ScriptRunner sr = new ScriptRunner(conn);
                Reader reader = new BufferedReader(new FileReader("src/main/resources/drop_tables.sql"));
                sr.runScript(reader);
                System.out.println("Таблицы успешно удалены!");
            } else if (ddlAuto.equals("create")){
                ScriptRunner sr = new ScriptRunner(conn);
                Reader reader = new BufferedReader(new FileReader("src/main/resources/create_tables.sql"));
                sr.runScript(reader);
                System.out.println("Таблицы успешно созданы!");
            } else if(ddlAuto.equals("complete")){
                createDiscountCard(context);
                createProducts(context);
                System.out.println("Таблицы успешно заполнены!");
            } else System.out.println("Никаких обновлений не произошло!");
        }
    }

    private static void createDiscountCard(ApplicationContext context) throws SQLException {
        DiscountCardRepository discountCardRepository = context.getBean("discountCardRepository", DiscountCardRepository.class);
        DiscountCard discountCard = new DiscountCard(1, "1234", 5.0);
        discountCardRepository.save(discountCard);
        discountCard = new DiscountCard(2, "1254",7.0);
        discountCardRepository.save(discountCard);
        discountCard = new DiscountCard(3, "1634",10.3);
        discountCardRepository.save(discountCard);
    }

    private static void createProducts(ApplicationContext context) throws SQLException {
        ProductRepository productRepository = context.getBean("productRepository", ProductRepository.class);
        ProductWarehouseRepository productWarehouseRepository = context.getBean("productWarehouseRepository", ProductWarehouseRepository.class);

        Product product = new Product(1, "Шоколад", 2.34, false);
        product = productRepository.save(product);
        ProductWarehouse productWarehouse = new ProductWarehouse(1, product,17);
        productWarehouseRepository.save(productWarehouse);

        product = new Product(2, "Макороны", 3.35, true);
        product = productRepository.save(product);
        productWarehouse = new ProductWarehouse(2, product, 11);
        productWarehouseRepository.save(productWarehouse);

        product = new Product(3, "Гречка", 4.19, true);
        product = productRepository.save(product);
        productWarehouse = new ProductWarehouse(3, product, 9);
        productWarehouseRepository.save(productWarehouse);

        product = new Product(4, "Молоко", 1.62, false);
        product = productRepository.save(product);
        productWarehouse = new ProductWarehouse(4, product, 12);
        productWarehouseRepository.save(productWarehouse);

        product = new Product(5, "Хлеб", 2.09, false);
        product = productRepository.save(product);
        productWarehouse = new ProductWarehouse(5, product, 1);
        productWarehouseRepository.save(productWarehouse);

        product = new Product(6, "Масло", 4.11, false);
        product = productRepository.save(product);
        productWarehouse = new ProductWarehouse(6, product, 6);
        productWarehouseRepository.save(productWarehouse);

        product = new Product(7, "Мороженое", 2.27, true);
        product = productRepository.save(product);
        productWarehouse = new ProductWarehouse(7, product, 13);
        productWarehouseRepository.save(productWarehouse);
    }
}
