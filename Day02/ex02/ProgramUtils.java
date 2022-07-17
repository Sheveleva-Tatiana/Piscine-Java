package Day02.ex02;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ProgramUtils {
    public static long directorySize(Path tmpPath) {
        long size = 0;

        try (DirectoryStream<Path> files = Files.newDirectoryStream(tmpPath)) {
            for (Path tmp : files) {
                if (Files.isDirectory(tmp)) {
                    size += directorySize(tmp);
                } else {
                    size += Files.size(tmp);
                }
            }
        } catch (IOException e) {
            System.out.println("Exception in method: directorySize");
            System.out.println(e.getMessage());
        }
        return size;
    }

    public static String getSize(long size) {
        return "" + (size / 1000) + " KB";
    }

    public static void checkPath(Path path) {
        if (!path.isAbsolute()) {
            System.err.println("The current path isn't absolute");
            System.exit(-1);
        }

        if (!Files.isDirectory(path)) {
            System.err.println("The current path doesn't point to directory");
            System.exit(-1);
        }
    }

    public static void checkArgs(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--current-folder=")) {
            System.err.println("An absolute path wasn't specified as a program argument");
            System.err.println("Specify an absolute path using '--current-folder='");
            System.exit(-1);
        }
    }
}
