package Day01.ex03;

public class TransactionNotFoundException extends RuntimeException{

    public TransactionNotFoundException(String msg){
        super(msg);
    }
}
