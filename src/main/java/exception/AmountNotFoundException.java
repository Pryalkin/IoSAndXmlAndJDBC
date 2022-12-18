package exception;

public class AmountNotFoundException extends RuntimeException{

    public AmountNotFoundException(String message) {
        super(message);
    }
}
