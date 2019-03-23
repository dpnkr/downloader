package com.applications.downloader;

public interface Constants {

    String METHOD_GET = "GET";
    String TEST_OUTPUT_LOCATION = "C:\\Users\\Public\\Downloads\\";
    String ACCEPT_RANGES = "Accept-Ranges";
    String NONE = "none";
    String CONTENT_LENGTH = "Content-Length";
    String RANGE = "Range";
    int PARTIAL_RESPONSE_CODE = 206;
    String HTTP = "http";
    String HTTPS = "https";
    int PARTIAL_COUNT = 16;
    int CHUNK_COUNT = 100;
}
