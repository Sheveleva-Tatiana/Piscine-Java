package Day02.ex00;

import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Program {

    public static void main(String[] args){

        Map <String, String> signature = ReaderSignature.read();

        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();

        while (!inputLine.equals("42")) {
            FinderSignature.analysisSignature(inputLine, signature);
            inputLine = scanner.nextLine();
        }

        scanner.close();
    }
}