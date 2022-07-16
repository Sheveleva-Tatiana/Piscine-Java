package Day01.ex05;

public class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException(String msg) {
        super(msg);
    }
}
