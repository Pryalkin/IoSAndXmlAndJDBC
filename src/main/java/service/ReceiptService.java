package service;

import model.Receipt;

import java.sql.SQLException;
import java.util.List;

public interface ReceiptService {

    Receipt parseTheRequest(String[] message) throws SQLException;

    List<Receipt> getAll() throws SQLException;

    void message(String s);
}
