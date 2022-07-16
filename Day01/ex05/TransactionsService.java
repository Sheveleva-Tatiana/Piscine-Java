package Day01.ex05;

import java.util.UUID;

public class TransactionsService {
    TransactionsList transactionsList = new TransactionsLinkedList();
    UsersList userList = new UsersArrayList();
    private TransactionsLinkedList invalidTransactionList = new TransactionsLinkedList();

    public void addUser(User user) {
        this.userList.addUser(user);
    }

    public int getUserBalance(int id) {
        return userList.getUserById(id).getBalance();
    }

    public int getUserBalance(User user) {
        return user.getBalance();
    }

    public void executeTransaction(int senderId, int recipientId, int amount) {
        User sender = userList.getUserById(senderId);
        User recipient = userList.getUserById(recipientId);

        if (senderId == recipientId || amount < 0 || sender.getBalance() < amount) {
            throw new IllegalTransactionException("Illegal Transaction.");
        }

        Transaction credit = new Transaction(sender, recipient, -amount, Transaction.Category.CREDIT);
        Transaction debit = new Transaction(sender, recipient, amount, Transaction.Category.DEBIT);

        debit.setIdentifier(credit.getIdentifier());

        recipient.getTransactionsList().addTransaction(debit);
        sender.getTransactionsList().addTransaction(credit);

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
    }

    public Transaction[] getTransactionList(int userId) {
        return userList.getUserById(userId).getTransactionsList().toArray();
    }

    public void removeTransactionById(UUID transactionId, int userId) {
        userList.getUserById(userId).getTransactionsList().removeTransactionById(transactionId);
    }

    public Transaction[] getInvalidTransaction() {
        TransactionsLinkedList listTransOfAllUsers = getAllTransactionList();

        TransactionsLinkedList result = new TransactionsLinkedList();
        Transaction[] arrayFirst = listTransOfAllUsers.toArray();
        if (arrayFirst != null) {
            int sizeArray = arrayFirst.length;
            for (int i = 0; i < sizeArray; i++) {
                int count = 0;
                for (int j = 0; j < sizeArray; j++) {
                    if (arrayFirst[i].getIdentifier().equals(arrayFirst[j].getIdentifier())) {
                        count++;
                    }
                }
                if (count != 2) {
                    result.addTransaction(arrayFirst[i]);
                }
            }
        }
        return result.toArray();
    }

    public TransactionsLinkedList getAllTransactionList() {
        TransactionsLinkedList listTransOfAllUsers = new TransactionsLinkedList();

        for (int i = 0; i < userList.getUserCount(); i++) {
            User user = userList.getUserByIndex(i);
            if (user != null) {
                int size = user.getTransactionsList().getSize();
                for (int j = 0; j < size; j++) {
                    listTransOfAllUsers.addTransaction(user.getTransactionsList().toArray()[j]);
                }
            }
        }
        return listTransOfAllUsers;
    }

    public UsersArrayList getUserList() {
        UsersArrayList list = new UsersArrayList();
        for (int i = 0; i < userList.getUserCount(); i++) {
            list.addUser(userList.getUserByIndex(i));
        }
        return list;
    }
}
