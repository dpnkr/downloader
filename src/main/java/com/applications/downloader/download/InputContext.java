package com.applications.downloader.download;

public class InputContext {

    private String urlPath;
    private int noParts;
    private boolean downloadInParts;
    private String outputPath;

    String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    int getNoParts() {
        return noParts;
    }

    public void setNoParts(int noParts) {
        this.noParts = noParts;
    }

    public boolean isDownloadInParts() {
        return downloadInParts;
    }

    public void setDownloadInParts(boolean downloadInParts) {
        this.downloadInParts = downloadInParts;
    }

    String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
}
