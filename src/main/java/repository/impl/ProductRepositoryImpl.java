package repository.impl;

import model.DiscountCard;
import model.Product;
import org.springframework.stereotype.Repository;
import repository.DiscountCardRepository;
import repository.ProductRepository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class ProductRepositoryImpl implements ProductRepository {

    private static Connection conn;

    static {
        String url = null;
        String username = null;
        String password = null;

        try(InputStream in = ProductRepository.class
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

    @Override
    public List<Product> getAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM product");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Product product = new Product();
            product.setId(rs.getInt(1));
            product.setName(rs.getString(2));
            product.setPrice(rs.getDouble(3));
            product.setPromotional(rs.getBoolean(4));
            products.add(product);
        }
        return products;
    }

    @Override
    public Optional<Product> getById(Integer id) throws SQLException {
        Product product = new Product();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM product WHERE id = ?");
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()){
            product.setId(resultSet.getInt(1));
            product.setName(resultSet.getString(2));
            product.setPrice(resultSet.getDouble(3));
            product.setPromotional(resultSet.getBoolean(4));
        }
        ps.close();
        return Optional.of(product);
    }

    @Override
    public Product save(Product product) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO product values (?, ?, ?, ?)");
        ps.setInt(1, product.getId());
        ps.setString(2, product.getName());
        ps.setDouble(3, product.getPrice());
        ps.setBoolean(4, product.getPromotional());
        ps.execute();
        return product;
    }


}
