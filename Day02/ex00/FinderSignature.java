package Day02.ex00;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

public class FinderSignature {
    private static final char[] HEX = "0123456789ABCDEF".toCharArray();

    public static void analysisSignature( String inputLine, Map<String, String> signature) {

        try (FileInputStream inputFile = new FileInputStream(inputLine)) {
            byte[] bytes = new byte[8];
            inputFile.read(bytes, 0, 8);
            String fileSignature = bytesToHex(bytes);
            findSignature(fileSignature, signature);
        } catch (Exception error) {
            System.err.println("File not find.");
        }
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX[v >>> 4];
            hexChars[j * 2 + 1] = HEX[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static void findSignature(String signature, Map<String, String> signatureMap){

        try (FileOutputStream fileOutputStream = new FileOutputStream("result.txt", true)) {

            for (Map.Entry<String, String> fileSignature : signatureMap.entrySet()) {
                if (signature.contains(fileSignature.getValue())) {
                    fileOutputStream.write(fileSignature.getKey().getBytes());
                    fileOutputStream.write('\n');
                    System.out.println("PROCESSED");
                    return;
                }
            }
            System.out.println("UNDEFINED");
        } catch (Exception error) {
            System.err.println("File not found");
        }
    }
}
