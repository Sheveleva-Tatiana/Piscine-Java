package Day01.ex01;

public class Program {
    public static void main(String[] args) {
        User[] users = new User[26];
        for (int i = 0; i < 25; i++) {
            users[i] = new User((i * 1000), ("User" + (i + 1)));
            System.out.println(users[i]);
        }
        System.out.println("Last ID: " + UserIdsGenerator.getIdentifier());
    }
}
