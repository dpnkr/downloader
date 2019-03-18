package com.applications.downloader.download;

import com.applications.downloader.Constants;
import com.applications.downloader.agents.AgentFactory;
import com.applications.downloader.agents.UnsupportedProtocol;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;

class ResourceProvider {

    private String urlPath;
    private String filePath;
    private Part parts[];

    private String fileNames[];
    private String finalFileName;

    ResourceProvider(String urlPath, String filePath, Part parts[]) {
        this.urlPath = urlPath;
        this.filePath = filePath;
        this.parts = parts;
        this.fileNames = new String[parts.length];
    }

    private Request[] createRequests() {
        Request requests[] = new Request[parts.length];
        for (int i = 0; i < requests.length; i++) {
            requests[i] = new Request();
            requests[i].addRequestProperties(Constants.RANGE,
                    getRangeProperty(parts[i].getStart(), parts[i].getEnd()));
        }
        return requests;
    }

    DownloadContext[] getDownloadContexts() throws MalformedURLException,
            UnsupportedProtocol, FileNotFoundException {
        Request requests[] = createRequests();
        FileChannel channels[] = getChannelsToWrite();
        DownloadContext contexts[] = new DownloadContext[parts.length];
        for (int i = 0; i < parts.length; i++) {
            DownloadContext context = new DownloadContext();
            context.setPart(parts[i]);
            context.setWriteChannel(channels[i]);
            context.setAgent(AgentFactory.getAgent(urlPath, requests[i]));
            contexts[i] = context;
        }
        return contexts;
    }

    private String getRangeProperty(long startByte, long endByte) {
        return "bytes=" + startByte + "-" + endByte;
    }

    private FileChannel[] getChannelsToWrite() throws FileNotFoundException {
        FileChannel channels[] = new FileChannel[parts.length];
        int index = urlPath.lastIndexOf('/');
        for (int i = 0; i < channels.length; i++) {
            fileNames[i] = filePath + urlPath.substring(index + 1) + "_" + i;
            FileOutputStream fileOutputStream = new FileOutputStream(fileNames[i]);
            channels[i] = fileOutputStream.getChannel();
        }
        this.finalFileName = filePath + urlPath.substring(index + 1);
        return channels;
    }

    String getFinalFileName() {
        return finalFileName;
    }

    File[] getFiles() {
        File files[] = new File[parts.length];
        for (int i = 0; i < parts.length; i++) {
            files[i] = new File(fileNames[i]);
        }
        return files;
    }
}
