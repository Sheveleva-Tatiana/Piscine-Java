package Day01.ex03;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User user1 = new User(5000, "User1");
        User user2 = new User(1000, "User2");

        TransactionsLinkedList listOfTransactionUser1 = user1.getTransactionsList();

        Transaction tr1 = new Transaction(user1, user2, -300, Transaction.Category.CREDIT);
        Transaction tr2 = new Transaction(user1, user2, -500, Transaction.Category.CREDIT);
        Transaction tr3 = new Transaction(user1, user2, 300, Transaction.Category.DEBIT);
        Transaction tr4 = new Transaction(user1, user2, 500, Transaction.Category.DEBIT);

        listOfTransactionUser1.addTransaction(tr1);
        listOfTransactionUser1.addTransaction(tr2);
        listOfTransactionUser1.addTransaction(tr3);
        listOfTransactionUser1.addTransaction(tr4);

        System.out.println("Пользователь User1 осуществил " + user1.getTransactionsList().getSize() + " транзакции(й).");

        System.out.println("Удалили третью транзацию: ");
        listOfTransactionUser1.removeTransactionById(tr3.getIdentifier());


        System.out.println("Попытаемся удалить несуществующую транзакцию: ");
        try {
            listOfTransactionUser1.removeTransactionById(UUID.randomUUID());
        } catch (TransactionNotFoundException ex) {
            System.out.println(ex);
        }

        System.out.println("Переведем список в массив и выведем на печать: ");
        Transaction[] arrayOfTransaction = listOfTransactionUser1.toArray();

        for (Transaction item : arrayOfTransaction) {
            item.printTransferInfo();
        }


    }
}
