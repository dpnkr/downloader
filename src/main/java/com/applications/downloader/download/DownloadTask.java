package com.applications.downloader.download;

import com.applications.downloader.Constants;
import com.applications.downloader.agents.Agent;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.CountDownLatch;

public class DownloadTask implements Runnable {

    private FileChannel oc;
    private Part part;
    private CountDownLatch latch;
    private Agent agent;

    DownloadTask(DownloadContext ctx) {
        this.oc = ctx.getWriteChannel();
        this.part = ctx.getPart();
        this.latch = ctx.getLatch();
        this.agent = ctx.getAgent();
    }

    private ReadableByteChannel getReadChannel() throws IOException {
        int rc = agent.sendRequest();
        if (rc == Constants.PARTIAL_RESPONSE_CODE) {
            System.out.println("response code for connection[" + part.getNo() + "] : " + rc);
            InputStream stream = agent.getInputStream();
            return Channels.newChannel(stream);
        } else {
            throw new UnexpectedResponseCode(rc);
        }
    }

    @Override
    public void run() {
        try (ReadableByteChannel ic = getReadChannel()) {
            long count = (part.getEnd() - part.getStart()) + 1L;
            while (count != 0) {
                count -= oc.transferFrom(ic, 0, count);
            }
        } catch (IOException e) {
            System.out.println("task" + part.getNo() + " [failed]");
            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            latch.countDown();
        }
        System.out.println("part" + part.getNo() + " > [completed]");
    }
}
