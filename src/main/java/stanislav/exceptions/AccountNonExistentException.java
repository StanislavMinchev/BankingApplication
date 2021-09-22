package stanislav.exceptions;

public class AccountNonExistentException extends Exception {

    public AccountNonExistentException(String message) {
        super(message);
    }
}
