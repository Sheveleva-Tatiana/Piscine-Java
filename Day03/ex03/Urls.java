package Day03.ex03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Urls {
    private boolean isDownloaded = false;
    private int key = 1;
    private Map<Integer, String> urlsList = new TreeMap<>();

    public Urls() throws IOException {
        List<String> list = Files.readAllLines(Paths.get("/opt/goinfre/sshera/Piscine-Java/Day03/ex03/files_urls.txt"));
        list = list.stream().map(s -> s = s.trim()).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        if (list.size() == 0) {
            throw new RemoteException("There are no URLs in the file 'files_urls.txt'");
        }
        try {
            for (String str : list) {
                String[] keyValue = str.split("\\s+");
                urlsList.put(Integer.parseInt(keyValue[0]), keyValue[1]);
            }
        } catch (Exception e) {
            System.err.println("Wrong file");
            System.exit(-1);
        }
    }

    public synchronized boolean isDownloaded() {
        return isDownloaded;
    }

    public int getUrlsListIndex(String str) {
        Set<Map.Entry<Integer, String>> set = urlsList.entrySet();

        for (Map.Entry<Integer, String> entry: set) {
            if (entry.getValue().equals(str)) {
                return (int) entry.getKey();
            }
        }
        return -1;
    }

    public synchronized String getUrls() {
        if (!urlsList.containsKey(key)) {
            return null;
        }
        String str = urlsList.get(key++);
        if (!urlsList.containsKey(key)) {
            isDownloaded = true;
        }
        return str;
    }
}
