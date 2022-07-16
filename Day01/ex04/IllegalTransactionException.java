package Day01.ex04;

public class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException(String msg) {
        super(msg);
    }
}
