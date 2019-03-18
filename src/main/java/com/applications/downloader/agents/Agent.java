package com.applications.downloader.agents;

import com.applications.downloader.download.Request;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public abstract class Agent {

    private URL url;
    Request request;
    private URLConnection connection;

    Agent(URL url, Request request) {
        this.url = url;
        this.request = request;
    }

    URLConnection getConnection() throws IOException {
        return (connection = url.openConnection());
    }

    public InputStream getInputStream() throws IOException {
        return connection.getInputStream();
    }

    public abstract int sendRequest() throws IOException;

}
