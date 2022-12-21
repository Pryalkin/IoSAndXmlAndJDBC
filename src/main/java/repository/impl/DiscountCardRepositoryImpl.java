package repository.impl;

import model.DiscountCard;
import model.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import repository.DiscountCardRepository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class DiscountCardRepositoryImpl implements DiscountCardRepository {

    private static Connection conn;

    static {
        String url = null;
        String username = null;
        String password = null;

        try(InputStream in = DiscountCardRepository.class
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
    public DiscountCard save(DiscountCard discountCard) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO discount_card values (?, ?, ?)");
        ps.setInt(1, discountCard.getId());
        ps.setString(2, discountCard.getNumber());
        ps.setDouble(3, discountCard.getDiscount());
        ps.execute();
        return discountCard;
    }

    @Override
    public List<DiscountCard> getAll() throws SQLException {
        List<DiscountCard> discountCards = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM discount_card");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            DiscountCard discountCard = new DiscountCard();
            discountCard.setId(rs.getInt(1));
            discountCard.setNumber(rs.getString(2));
            discountCard.setDiscount(rs.getDouble(3));
            discountCards.add(discountCard);
        }
        return discountCards;
    }

    @Override
    public Optional<DiscountCard> getById(Integer id) throws SQLException {
        DiscountCard discountCard = new DiscountCard();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM discount_card WHERE id = ?");
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()){
            discountCard.setId(resultSet.getInt(1));
            discountCard.setNumber(resultSet.getString(2));
            discountCard.setDiscount(resultSet.getDouble(3));
        }
        ps.close();
        return Optional.of(discountCard);
    }

    @Override
    public Optional<DiscountCard> getByNameDiscountCard(String numberCard) {

        return Optional.empty();
    }
}

