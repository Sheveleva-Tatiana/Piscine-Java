package Day02.ex00;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ReaderSignature {
    private static final String SIGNATURE_FILE = "src/Day02/ex00/signatures.txt";

    public static Map<String, String> read() {
        FileInputStream fileInputStream;
        Map <String, String> signature = new HashMap<>();

        try {
            fileInputStream = new FileInputStream(SIGNATURE_FILE);
            Scanner newScanner = new Scanner(fileInputStream);

            while (newScanner.hasNextLine()) {
                String line = newScanner.nextLine();
                String[] lineArray = line.split(",");
                signature.put(lineArray[0], lineArray[1].replaceAll("\\s+", ""));
            }

            newScanner.close();
            fileInputStream.close();
        } catch (Exception error){
            System.err.println("File not found");
            System.exit(-1);
        }

        return signature;
    }
}
