package Day03.ex01;

public class Program {

    public static void main(String[] args) {

        if (args.length != 1 || !args[0].startsWith("--count=")) {
            System.err.println("Specify a count argument using '--count='");
            System.exit(-1);
        }
        try {
            Printer printer = new Printer();
            int count = getCount(args[0]);
            new Thread(new Egg(count, printer)).start();
            new Thread(new Hen(count, printer)).start();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static int getCount(String num) {
        int count = 0;
        try {
            count = Integer.parseInt(num.substring("--count=".length()));
            if (count <= 0) {
                throw new RuntimeException("Incorrect count: " + count);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return count;
    }
}
