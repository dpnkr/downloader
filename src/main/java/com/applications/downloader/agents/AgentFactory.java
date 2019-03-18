package com.applications.downloader.agents;

import com.applications.downloader.Constants;
import com.applications.downloader.download.Request;

import java.net.MalformedURLException;
import java.net.URL;

public class AgentFactory implements Constants {

    public static Agent getAgent(String urlPath, Request request) throws MalformedURLException,
            UnsupportedProtocol {
        URL url = new URL(urlPath);
        String protocol = url.getProtocol();
        switch (protocol) {
            case HTTPS: {
            }
            case HTTP: {
                return new HttpAgent(url, request);
            }
            default: {
                throw new UnsupportedProtocol(protocol);
            }
        }
    }
}
