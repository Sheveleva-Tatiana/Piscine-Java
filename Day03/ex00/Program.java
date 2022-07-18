package Day03.ex00;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--count=")) {
            System.err.println("Specify a count argument using '--count='");
            System.exit(-1);
        }
        try {
            int count = Integer.parseInt(args[0].substring("--count=".length()));

            if (count <= 0) {
                System.err.println("Incorrect count: " + count);
                System.exit(-1);
            }

            Egg egg = new Egg(count);
            Hen hen = new Hen(count);

            egg.start();
            hen.start();
            egg.join();
            hen.join();

            for (int i = 0; i < count; i++) {
                System.out.println("Human");
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
