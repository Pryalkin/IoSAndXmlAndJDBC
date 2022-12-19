package exception;

public class AmountNotFoundInStockException extends RuntimeException{

    public AmountNotFoundInStockException(String message) {
        super(message);
    }
}
