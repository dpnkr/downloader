package com.applications.downloader.agents;

import com.applications.downloader.Constants;
import com.applications.downloader.download.Request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public final class HttpAgent extends Agent {

    HttpAgent(URL url, Request request) {
        super(url, request);
    }

    public int sendRequest() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) getConnection();
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(8000);
        connection.setRequestMethod(Constants.METHOD_GET);
        Map<String, String> properties = request.getProperties();
        Set<Map.Entry<String, String>> propSet = properties.entrySet();
        for (Map.Entry<String, String> pair : propSet) {
            connection.setRequestProperty(pair.getKey(), pair.getValue());
        }
        return connection.getResponseCode();
    }
}
