package com.applications.downloader.download;

import java.io.IOException;

final class UnexpectedResponseCode extends IOException {
    UnexpectedResponseCode(int responseCode) {
        super("response code [" + responseCode + "]");
    }
}
