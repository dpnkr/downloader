package com.applications.downloader.download;

import java.util.Map;
import java.util.WeakHashMap;

public class Request {

    private Map<String, String> properties = new WeakHashMap<>();

    void addRequestProperties(String key, String value) {
        properties.put(key, value);
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
