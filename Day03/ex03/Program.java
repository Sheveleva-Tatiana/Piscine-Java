package Day03.ex03;

import java.io.IOException;

public class Program {

    public static void main(String[] args) {
        try {
            initAndStartThreads(checkArgsAndGetThreadsCount(args));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void initAndStartThreads(int threadsCount) throws IOException {
        Urls urls = new Urls();
        FtThread[] threads = new FtThread[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new FtThread(urls, i + 1);
            threads[i].start();
        }
    }

    private static int checkArgsAndGetThreadsCount(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--threadsCount=")) {
            printErr("Specify argument using '--threadsCount='");
        }
        int threadsCount = Integer.parseInt(args[0].substring("--threadsCount=".length()));

        if (threadsCount < 1) {
            printErr("Invalid argument threadsCount. Must be greater than 0");
        }
        return threadsCount;
    }

    public static void printErr(String msg) {
        System.err.println(msg);
        System.exit(-1);
    }
}