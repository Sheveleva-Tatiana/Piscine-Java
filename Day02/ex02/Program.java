package Day02.ex02;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class Program {

    private static Path path, nPath;

    public static void main(String[] args) {
        ProgramUtils.checkArgs(args);

        path = Paths.get(args[0].substring("--current-folder=".length()));
        ProgramUtils.checkPath(path);
        System.out.println(path);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            exeCommand(scanner);
        }
    }

    private static void exeCommand(Scanner scanner) {
        String str = scanner.nextLine();
        String[] answer = str.split("\\s+");

        if (answer.length == 1 && "exit".equals(answer[0])) {
            scanner.close();
            System.exit(0);
        } else if (answer.length == 1 && "ls".equals(answer[0])) {
            commandLS();
        } else if (answer.length == 2 && "cd".equals(answer[0])) {
            commandCD(answer[1]);
        } else if (answer.length == 3 && "mv".equals(answer[0])) {
            commandMV(answer[1], answer[2]);
        } else {
            System.out.println("UNKNOWN COMMAND");
        }
    }

    private static void commandMV(String what, String where) {
        Path source = null;

        try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
            for (Path tmp : files) {
                if (tmp.getFileName().toString().equals(what) && Files.isRegularFile(tmp)) {
                    source = tmp;
                    break;
                }
            }

            if (source == null) {
                System.err.println("mv: no such file: " + what);
                files.close();
                return;
            }

            if (isDirectory(where)) {
                Files.move(source, nPath.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.move(source, source.resolveSibling(Paths.get(where)));
            }
        } catch(IOException e) {
            System.err.println("Exception in method: commandMV");
            System.err.println(e.getMessage());
        }

    }

    private static void commandLS() {
        try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
            for (Path tmp : files) {
                long size;

                if (Files.isDirectory(tmp)) {
                    size = ProgramUtils.directorySize(tmp);
                } else {
                    size = Files.size(tmp);
                }
                System.out.println(tmp.getFileName() + " " + ProgramUtils.getSize(size));
            }
        } catch(IOException e) {
            System.out.println("Exception in method: commandLS");
            System.out.println(e.getMessage());
        }
    }

    private static void commandCD(String dir) {
        if (isDirectory(dir)) {
            path = nPath;
        } else {
            System.err.println("cd: no such directory: " + dir);
        }
        System.out.println(path);
    }

    private static boolean isDirectory(String strPath) {
        nPath = Paths.get(strPath);
        nPath = path.resolve(nPath).normalize();

        if (Files.isDirectory(nPath)) {
            return true;
        }
        return false;
    }
}
