package Day01.ex05;

import java.io.StringWriter;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private TransactionsService facade;
    private Scanner scanner;

    public Menu() {
        facade = new TransactionsService();
        scanner = new Scanner(System.in);
    }

    public void run(boolean answer) {
        System.out.println();
        while(true) {
            if (answer == true) {
                welcomeMsgDev();
                forkAnswerDev(getAnswerUser());
            } else {
                welcomeMsg();
                forkAnswer(getAnswerUser());
            }
        }
    }

    private int getAnswerUser() {
        String getStr = scanner.nextLine().trim();
        int answer;
        try {
            answer = Integer.parseInt(getStr);
            if (answer < 1 || answer > 7) {
                throw new RuntimeException("Invalid action. Enter valid number: ");
            }
        } catch (RuntimeException ex) {
            System.out.println(ex);
            answer = getAnswerUser();
        }
        return answer;
    }

    private void welcomeMsg() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        System.out.println("5. Finish execution");
    }

    private void welcomeMsgDev() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        System.out.println("5. DEV - remove a transfer by ID ");
        System.out.println("6. DEV - check transfer validity");
        System.out.println("7. Finish execution");
    }

    private void forkAnswer(int answer) {
        switch (answer) {
            case 1 -> addUser();
            case 2 -> getBalance();
            case 3 -> transfer();
            case 4 -> viewTransactions();
            case 5 -> System.exit(0);
        }
    }

    private void forkAnswerDev(int answer) {
        switch (answer) {
            case 1 -> addUser();
            case 2 -> getBalance();
            case 3 -> transfer();
            case 4 -> viewTransactions();
            case 5 -> devRemoveTransferById();
            case 6 -> checkTransfers();
            case 7 -> System.exit(0);
        }
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");
        String input = scanner.nextLine().trim();

        try {
            String[] arrayInput = input.split("\\s+");
            if (arrayInput.length != 2) {
                throw new RuntimeException("Invalid data");
            }
            String name = arrayInput[0];
            int balance = Integer.parseInt(arrayInput[1]);
            User user = new User(balance, name);
            facade.addUser(user);
            System.out.println("User with id = " + user.getIdentifier() + " is added");
            System.out.println("---------------------------------------------------------");
        } catch (RuntimeException ex) {
            System.out.println(ex);
            addUser();
        }
    }

    private void getBalance() {
        System.out.println("Enter a user ID");
        try {
            String input = scanner.nextLine().trim();
            int id = Integer.parseInt(input);
            int balance = facade.getUserBalance(id);
            String name = facade.userList.getUserById(id).getName();
            System.out.println(name + " - " + balance);
            System.out.println("---------------------------------------------------------");
        } catch (RuntimeException ex) {
            System.out.println(ex);
        }
    }

    private void transfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        try {
            String input = scanner.nextLine().trim();
            String[] arrayAnswer = input.split("\\s+");
            if (arrayAnswer.length != 3) {
                throw new RuntimeException("Wrong input");
            }
            int idSender = Integer.parseInt(arrayAnswer[0]);
            int idRecipient = Integer.parseInt(arrayAnswer[1]);
            int amount = Integer.parseInt(arrayAnswer[2]);
            facade.executeTransaction(idSender, idRecipient, amount);
            System.out.println("The transfer is completed");
            System.out.println("---------------------------------------------------------");
        } catch (RuntimeException ex) {
            System.out.println(ex);
        }
    }

    private String hgetCategory(Transaction transaction) {
        if (transaction.getTransferCategory() == Transaction.Category.DEBIT) {
            return "From ";
        }
        return "To ";
    }

    private String hgetCategory2(Transaction transaction) {
        if (transaction.getTransferCategory() == Transaction.Category.DEBIT) {
            return "from ";
        }
        return "to ";
    }

    private int hgetIdUser(Transaction transaction) {
        if (transaction.getTransferCategory() == Transaction.Category.DEBIT) {
            return transaction.getSender().getIdentifier();
        }
        return transaction.getRecipient().getIdentifier();
    }

    private String hgetNameOfTrans(Transaction transaction) {
        if (transaction.getTransferCategory() == Transaction.Category.DEBIT) {
            return transaction.getSender().getName();
        }
        return transaction.getRecipient().getName();
    }

    private void viewTransactions() {
        System.out.println("Enter a user ID");
        try {
            String input = scanner.nextLine().trim();
            int id = Integer.parseInt(input);
            Transaction[] transactions = facade.getTransactionList(id);
            if (transactions == null) {
                throw new TransactionNotFoundException("User with id = " + id + " hasn't transactions.");
            }
            for (int i = 0; i < transactions.length; i++) {
                int idUser = hgetIdUser(transactions[i]);
                String category = hgetCategory(transactions[i]);
                String name = hgetNameOfTrans(transactions[i]);
                System.out.print(category + name + "(id = " + idUser + ") ");
                System.out.print(transactions[i].getTransferAmount());
                System.out.println(" with id = " + transactions[i].getIdentifier());
            }
            System.out.println("---------------------------------------------------------");
        } catch (RuntimeException ex) {
            System.out.println(ex);
        }
    }

    private Transaction hgetTransaction(Transaction[] transactions, UUID idTransaction) {
        if (transactions == null) {
            throw new TransactionNotFoundException("Transaction with id = " + idTransaction + " not found.");
        }
        for (int i = 0; i < transactions.length; i++) {
            if (transactions[i].getIdentifier().equals(idTransaction)) {
                return transactions[i];
            }
        }
        return null;
    }

    private void devRemoveTransferById() {
        System.out.println("Enter a user ID and a transfer ID");
        String input = scanner.nextLine().trim();
        try {
            String[] arrayInput = input.split("\\s+");
            if (arrayInput.length != 2) {
                throw new RuntimeException("Wrong input");
            }
            int idUser = Integer.parseInt(arrayInput[0]);
            UUID idTransaction = UUID.fromString(arrayInput[1]);
            Transaction transaction = hgetTransaction(facade.getTransactionList(idUser), idTransaction);
            if (transaction == null) {
                throw new TransactionNotFoundException("Transaction with id = " + idTransaction + "not found. User id = " + idUser);
            }
            facade.removeTransactionById(idTransaction, idUser);
            String name = hgetNameOfTrans(transaction);
            System.out.print("Transfer " + hgetCategory(transaction) + name);
            System.out.println("(id = " + hgetIdUser(transaction) + ") " + transaction.getTransferAmount() + " removed");
            System.out.println("---------------------------------------------------------");
        } catch (RuntimeException ex) {
            System.out.println(ex);
        }
    }

    private User getUserHolder(Transaction transaction) {
        UsersArrayList listUsers = facade.getUserList();

        for (int i = 0; i < listUsers.getUserCount(); i++) {
            Transaction[] listTrans = listUsers.getUserByIndex(i).getTransactionsList().toArray();
            for (int j = 0; listTrans != null && j < listTrans.length; j++) {
                if (listTrans[j].getIdentifier().equals(transaction.getIdentifier())) {
                    return listUsers.getUserByIndex(i);
                }
            }
        }
        return null;
    }

    private void checkTransfers() {
        System.out.println("Check results: ");
        Transaction[] unpairedTransaction = facade.getInvalidTransaction();
        if (unpairedTransaction != null) {
            for (int i = 0; i < unpairedTransaction.length; i++) {
                User userHolder = getUserHolder(unpairedTransaction[i]);
                String nameSender = hgetNameOfTrans(unpairedTransaction[i]);
                int idSender = hgetIdUser(unpairedTransaction[i]);
                UUID idTransfer = unpairedTransaction[i].getIdentifier();
                int amount = unpairedTransaction[i].getTransferAmount();
                System.out.print(userHolder.getName() + "(id = " + userHolder.getIdentifier() + ")");
                System.out.print(" has an unacknowledged transfer id = " + idTransfer);
                System.out.println(" " + hgetCategory2(unpairedTransaction[i]) + nameSender + "(id = " + idSender + ") for " + amount);
            }
            return;
        }
        System.out.println("All good! Relax! :) ");
    }
}
