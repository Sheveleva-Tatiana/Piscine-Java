package Day03.ex03;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class FtThread extends Thread {
    private Urls urls;
    private int num;

    public FtThread(Urls urls, int num) {
        this.urls = urls;
        this.num = num;
    }

    @Override
    public void run() {
        while (!urls.isDownloaded()) {
            String str = urls.getUrls();
            byte dataBuffer[] = new byte[1024];
            int byteRead, fileNumber;
            File file;

            if (str != null) {
                file = getFile(str);

                try (BufferedInputStream input = new BufferedInputStream(new URL(str).openStream());
                     FileOutputStream output = new FileOutputStream(file)) {
                    fileNumber = urls.getUrlsListIndex(str);
                    System.out.println("Thread-" + num + " start download file number " + fileNumber);

                    while ((byteRead = input.read(dataBuffer)) != -1) {
                        output.write(dataBuffer, 0, byteRead);
                    }
                    System.out.println("Thread-" + num + " finish download file number " + fileNumber);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File getFile(String str) {
        File file;
        String[] strings = str.split("/");
        file = new File(strings[strings.length - 1]);
        return file;
    }
}


