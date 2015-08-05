package ntut.edu.lab1323.mitrastar.service;

import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DownloadCache {
    private static DownloadCache instance;
    private Map<String, File> fileMap;

    private DownloadCache() {
        this.fileMap = new HashMap<>();
    }

    public static DownloadCache getInstance() {
        if (instance == null) {
            instance = new DownloadCache();
        }

        return instance;
    }

    public String addFile(File file) {
        String key = this.createKey();
        this.fileMap.put(key, file);
        return key;
    }

    public File getFile(String key) {
        if (key != null && this.fileMap.containsKey(key)) {
            return this.fileMap.get(key);
        }
        return null;
    }


    private String createKey() {
        UUID uuid = UUID.randomUUID();
        String key = uuid.toString();
        Log.e("Create Key", key);
        return key;
    }
}
