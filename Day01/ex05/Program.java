package Day01.ex05;

public class Program {
    public static void main(String[] args) {
        Menu menu = new Menu();
        if (args.length > 0 && args[0].equals("--profile=dev")) {
            menu.run(true);
        } else {
            menu.run(false);
        }
    }
}
