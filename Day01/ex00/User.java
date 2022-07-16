package Day01.ex00;

public class User {

    private Integer identifier;
    private Integer balance;
    private String name;

    public User(Integer identifier, Integer balance, String name) {
        this.identifier = identifier;
        this.name = name;

        if (balance > 0) {
            this.balance = balance;
        } else {
            this.balance = 0;
        }
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
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
