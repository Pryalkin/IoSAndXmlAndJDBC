package service;

import model.Receipt;

public interface Service {

    Receipt parseTheRequest(String[] message);

}
