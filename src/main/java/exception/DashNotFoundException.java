package exception;

public class DashNotFoundException extends RuntimeException{

    public DashNotFoundException(String message) {
        super(message);
    }
}
