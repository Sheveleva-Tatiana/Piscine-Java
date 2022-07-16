package Day01.ex01;

public class User {

    private final Integer identifier;
    private Integer balance;
    private String name;

    public User() {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.balance = 0;
    }

    public User(Integer balance, String name) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;

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
