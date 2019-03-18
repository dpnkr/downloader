package com.applications.downloader.agents;

public final class UnsupportedProtocol extends Exception {
    UnsupportedProtocol(String protocol) {
        super("protocol : " + protocol + " is not supported");
    }
}
