import java.io.*;
import java.util.*;

class Cache {
    private final String FILENAME = "/tmp/cache.ser";
    Map<String, List<String>> cache;

    Cache() {
        try {
            File file = new File(FILENAME);
            if (!file.exists()){
                file.createNewFile();
                this.cache = new HashMap<>();
                return;
            }
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = in.readObject();
            if (obj instanceof HashMap)
                this.cache = new HashMap<>((HashMap<String, List<String>>)obj);
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        } catch (ClassCastException e) {
            this.cache = new HashMap<>();
        }
    }

    List<String> handleRequest(Request request) {
        switch (request.getAction()) {
            case SET:
                cache.put(request.getKey(), request.getValue());
                break;
            case GET:
                return cache.get(request.getKey());
            case LEFT_ADD:
                cache.computeIfAbsent(request.getKey(), l -> new ArrayList<>()).add(0, request.getAdd());
                break;
            case RIGHT_ADD:
                cache.computeIfAbsent(request.getKey(), l -> new ArrayList<>()).add(request.getAdd());
                break;
            case GET_ALL_KEYS:
                return new ArrayList<>(cache.keySet());
        }
        return new ArrayList<>();
    }

    void persist() {
        try {
            FileOutputStream fileOut = new FileOutputStream(new File(FILENAME));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.cache);
            out.close();
            fileOut.close();
            System.out.println("Serialized data saved to " + FILENAME);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
