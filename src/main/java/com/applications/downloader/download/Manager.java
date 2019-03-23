package com.applications.downloader.download;

import com.applications.downloader.Constants;
import com.applications.downloader.agents.UnsupportedProtocol;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class Manager {

    private InputContext ctx;
    private boolean spd = false;
    private long contentLength;

    public Manager(InputContext ctx) {
        this.ctx = ctx;
    }

    private void preCheck() throws IOException {
        System.out.println("supports partial downloading : " + (spd = supportsPartialDownload()));
        if (!spd) {
            System.out.println("download will be done via single connection");
        }
        System.out.println("content length :" + getContentLength());
    }

    public void exec() throws IOException, InterruptedException, ExecutionException,
            UnsupportedProtocol {

        preCheck();
        Part parts[] = spd ? calculateParts(ctx.getNoParts(), getContentLength()) :
                calculateParts(1, getContentLength());
        ResourceProvider rv = new ResourceProvider(ctx.getUrlPath(), ctx.getOutputPath(), parts);
        TaskManager manager = new TaskManager();
        manager.addDownloadTasks(rv.getDownloadContexts());
        manager.runProgress(contentLength);
        manager.waitForCompletion();
        manager.setFinalFileName(rv.getFinalFileName());
        manager.addJoinTasks(rv.getFiles(), ctx.getNoParts());
        manager.done();
    }

    private URLConnection getConnection() throws IOException {
        URL url = new URL(ctx.getUrlPath());
        return url.openConnection();
    }

    private boolean supportsPartialDownload() throws IOException {
        String supports = getConnection().getHeaderField(Constants.ACCEPT_RANGES);
        if (supports == null) return false;
        return !Constants.NONE.equalsIgnoreCase(supports);
    }

    private long getContentLength() throws IOException {
        String length = getConnection().getHeaderField(Constants.CONTENT_LENGTH);
        if (length == null) {
            throw new IOException("Content-Length header not present.");
        }
        return contentLength = Long.parseLong(length);
    }

    private Part[] calculateParts(int count, long contentLength) {
        if (count == 1) return new Part[]{
                new Part(0, contentLength - 1, 0)};
        Part parts[] = new Part[count];
        long perPart = (contentLength / count) - 1;
        long prev = -1;
        for (int i = 0; i < count; i++) {
            parts[i] = new Part(prev + 1, prev + perPart, i);
            prev = prev + perPart;
        }
        parts[count - 1].setEnd(contentLength - 1);
        return parts;
    }
}
