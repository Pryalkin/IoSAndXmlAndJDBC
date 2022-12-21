package repository.impl;

import model.DiscountCard;
import model.Product;
import model.ProductWarehouse;
import org.springframework.stereotype.Repository;
import repository.ProductRepository;
import repository.ProductWarehouseRepository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class ProductWarehouseRepositoryImpl implements ProductWarehouseRepository {

    private static Connection conn;

    static {
        String url = null;
        String username = null;
        String password = null;

        try(InputStream in = ProductWarehouseRepository.class
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
    public ProductWarehouse save(ProductWarehouse productWarehouse) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO product_warehouse values (?, ?, ?)");
        ps.setInt(1, productWarehouse.getId());
        ps.setInt(2, productWarehouse.getProduct().getId());
        ps.setInt(3, productWarehouse.getAmount());
        ps.execute();
        return productWarehouse;
    }

    @Override
    public List<ProductWarehouse> getAll() throws SQLException {
        List<ProductWarehouse> productWarehouses = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM product_warehouse");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            ProductWarehouse productWarehouse = new ProductWarehouse();
            productWarehouse.setId(rs.getInt(1));
            int productId = rs.getInt(2);
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM product WHERE id = ?");
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setPrice(resultSet.getDouble(3));
                product.setPromotional(resultSet.getBoolean(4));
                productWarehouse.setProduct(product);
                preparedStatement.close();
            } else productWarehouse.setProduct(null);
            productWarehouse.setAmount(rs.getInt(3));
            productWarehouses.add(productWarehouse);
        }
        ps.close();
        return productWarehouses;
    }

    @Override
    public Optional<ProductWarehouse> getById(Integer id) throws SQLException {
        ProductWarehouse productWarehouse = new ProductWarehouse();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM product_warehouse WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            productWarehouse.setId(rs.getInt(1));
            int productId = rs.getInt(2);
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM product WHERE id = ?");
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setPrice(resultSet.getDouble(3));
                product.setPromotional(resultSet.getBoolean(4));
                productWarehouse.setProduct(product);
                preparedStatement.close();
            } else productWarehouse.setProduct(null);
            productWarehouse.setAmount(resultSet.getInt(3));
        }
        ps.close();
        return Optional.of(productWarehouse);
    }

    @Override
    public Optional<ProductWarehouse> getByProduct(Product product) {

        return Optional.empty();
    }
}
