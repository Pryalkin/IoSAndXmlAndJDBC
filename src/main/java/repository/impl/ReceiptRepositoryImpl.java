package repository.impl;

import model.DiscountCard;
import model.Receipt;
import org.springframework.stereotype.Repository;
import repository.ReceiptRepository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class ReceiptRepositoryImpl implements ReceiptRepository {

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

    @Override
    public Receipt save(Receipt receipt) {

        return null;
    }

    @Override
    public List<Receipt> getAll() throws SQLException {
        List<Receipt> receipts = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("select * from receipt");
        return null;
    }

    @Override
    public Optional<Receipt> getById(Integer id) {

        return Optional.empty();
    }

}

