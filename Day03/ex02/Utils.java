package Day03.ex02;

import java.util.Random;

public class Utils {
    private static int MAX_INT = 2001;
    private static int arraySize, threadsCount;
    private static int[] arrayNumber;
    private static FtThread[] threads;
    private static Printer printer;

    public static void printErr(String msg) {
        System.err.println(msg);
        System.exit(-1);
    }

    public static void checkArgs(String[] args) {
        if (args.length != 2 || !args[0].startsWith("--arraySize=")
                || !args[1].startsWith("--threadsCount=")) {
            printErr("Specify arguments using '--arraySize=' && '--threadsCount='");
        }

        arraySize = Integer.parseInt(args[0].substring("--arraySize=".length()));
        threadsCount = Integer.parseInt(args[1].substring("--threadsCount=".length()));

        if (arraySize > 2_000_000 || threadsCount < 1 || threadsCount > arraySize) {
            printErr("Illegal argument for arraySize or threadsCount");
        }
    }

    public static void letsStart() {
        arrayNumber = new int[arraySize];
        int parts;
        int compare = (int) Math.ceil((double)arraySize / threadsCount);
        if ((double)arraySize / compare > (double)threadsCount - 1) {
            parts = (int) Math.ceil((double) arraySize / threadsCount);
        } else {
            parts = (int) Math.floor((double) arraySize / threadsCount);
        }
        int lastPart = arraySize - (parts * (threadsCount - 1));
        threads = new FtThread[threadsCount];

        generateArrayOfNumbers();
        printer = new Printer(arrayNumber);

        defineThreads(parts, threadsCount - 1, 0, 0);
        defineThreads(lastPart, 1, parts * (threadsCount - 1), threadsCount - 1);

        startThreads();

        System.out.println("Sum by threads: " + printer.getSumAll());
    }

    private static void defineThreads(int range, int count, int startArray, int countThread) {
        int n = 0;
        for (int j = 0; j < count; j++) {
            int first = range * n++ + startArray;
            int last = first + range - 1;
            threads[countThread++] = new FtThread(countThread, first, last, printer);
        }
    }

    private static void startThreads() {
        try {
            for (int i = 0; i < threadsCount; i++) {
                threads[i].start();
                threads[i].join();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private static void generateArrayOfNumbers() {
        int sum = 0;
        Random random = new Random();

        for (int i = 0; i < arraySize; i++) {
            arrayNumber[i] = random.nextInt(MAX_INT) - 1000;
            sum += arrayNumber[i];
        }
        System.out.println("Sum: " + sum);
    }

}
