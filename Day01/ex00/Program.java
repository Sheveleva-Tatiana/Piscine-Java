package Day01.ex00;

public class Program {
    public static void main(String[] args) {
        User user1 = new User(25, 500, "Bob");
        System.out.println("Информация о пользователях до проведения операции: \n" +user1);
        User user2 = new User(26, 1500, "Bill");
        System.out.println("\n" + user2);


        System.out.println("\nИнформация о заявленных операциях:");

        Transaction trans1 = new Transaction(user1, user2, 250, Transaction.Category.DEBIT);
        System.out.println(trans1);
        Transaction trans2 = new Transaction(user1, user2, -500, Transaction.Category.CREDIT);
        System.out.println("\n" + trans2);

        Transaction trans3 = new Transaction(user1, user2, 250, Transaction.Category.CREDIT);
        System.out.println("\n" + trans3);
        Transaction trans4 = new Transaction(user1, user2, -250, Transaction.Category.DEBIT);
        System.out.println("\n" + trans4);

        System.out.println("\nИнформация о пользователях после проведения операции: \n" + user1);
        System.out.println("\n" + user2);


    }
}
