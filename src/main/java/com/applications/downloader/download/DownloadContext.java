package com.applications.downloader.download;

import com.applications.downloader.agents.Agent;

import java.nio.channels.FileChannel;
import java.util.concurrent.CountDownLatch;

class DownloadContext {

    private FileChannel writeChannel;
    private CountDownLatch latch;
    private Part part;
    private Agent agent;

    void setWriteChannel(FileChannel writeChannel) {
        this.writeChannel = writeChannel;
    }

    void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    void setPart(Part part) {
        this.part = part;
    }

    void setAgent(Agent agent) {
        this.agent = agent;
    }

    Part getPart() {
        return part;
    }

    FileChannel getWriteChannel() {
        return writeChannel;
    }

    CountDownLatch getLatch() {
        return latch;
    }

    Agent getAgent() {
        return agent;
    }
}
