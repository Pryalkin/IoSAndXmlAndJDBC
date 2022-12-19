package service;

import model.Receipt;

import java.util.List;

public interface ReceiptService {

    Receipt parseTheRequest(String[] message);

    List<Receipt> getAll();
}
