package Day01.ex00;

import java.util.UUID;

public class Transaction {

    private final UUID identifier;
    private User recipient;
    private User sender;
    private Integer transferAmount;
    private Category transferCategory;

    enum Category {
        DEBIT, CREDIT
    }

    public Transaction(User recipient, User sender, Integer transferAmount, Category transferCategory) {
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        setTransferAmount(transferAmount);
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }

    public Category getTransferCategory() {
        return transferCategory;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void changeBalanceUsers(Integer transferAmount) {
        if (transferCategory == Category.CREDIT) {
            sender.setBalance(sender.getBalance() + transferAmount);
            recipient.setBalance(recipient.getBalance() - transferAmount);
        } else {
            sender.setBalance(sender.getBalance() - transferAmount);
            recipient.setBalance(recipient.getBalance() + transferAmount);
        }
    }

    public void setTransferAmount(Integer transferAmount) {
        if (this.transferCategory == Category.CREDIT && (transferAmount > 0 || sender.getBalance() < transferAmount)) {
            this.transferAmount = 0;
        } else if (this.transferCategory == Category.DEBIT && (transferAmount < 0 || sender.getBalance() < transferAmount)) {
            this.transferAmount = 0;
        } else {
            this.transferAmount = transferAmount;
            changeBalanceUsers(transferAmount);
        }
    }

    public void setTransferCategory(Category transferCategory) {
        this.transferCategory = transferCategory;
    }

    @Override
    public String toString() {
        return "Transaction: \n" +
                "identifier = " + identifier +
                "\nrecipient = " + recipient.getName() +
                "\nsender = " + sender.getName() +
                "\ntransferAmount = " + transferAmount +
                "\ntransferCategory = " + transferCategory;
    }
}
