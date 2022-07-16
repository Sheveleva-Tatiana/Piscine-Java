package Day01.ex05;

public class User {

    private final Integer identifier;
    private Integer balance;
    private String name;
    private TransactionsLinkedList transactionsList;

    public User() {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.balance = 0;
        this.transactionsList = new TransactionsLinkedList();
    }

    public User(Integer balance, String name) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.transactionsList = new TransactionsLinkedList();

        if (balance > 0) {
            this.balance = balance;
        } else {
            this.balance = 0;
        }
    }

    public void setBalance(Integer balance) {
        if (balance > 0) {
            this.balance = balance;
        } else {
            this.balance = 0;
        }
    }

    public TransactionsLinkedList getTransactionsList() {
        return transactionsList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public Integer getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User name = " + name +
                "\nidentifier = " + identifier +
                "\nbalance = " + balance;
    }
}
