package com.applications.downloader.download;

import com.applications.downloader.Constants;
import com.applications.downloader.agents.Agent;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.CountDownLatch;

public class DownloadTask implements Runnable, Constants {

    private FileChannel oc;
    private Part part;
    private CountDownLatch latch;
    private Agent agent;
    private long offset = 0;

    DownloadTask(DownloadContext ctx) {
        this.oc = ctx.getWriteChannel();
        this.part = ctx.getPart();
        this.latch = ctx.getLatch();
        this.agent = ctx.getAgent();
    }

    private ReadableByteChannel getReadChannel() throws IOException {
        int rc = agent.sendRequest();
        if (rc == PARTIAL_RESPONSE_CODE) {
            System.out.println("    task[" + part.getNo() + "] = success");
//            System.out.println("response code for connection[" + part.getNo() + "] : " + rc);
            InputStream stream = agent.getInputStream();
            return Channels.newChannel(stream);
        } else {
            throw new UnexpectedResponseCode(rc);
        }
    }

    double getProgress() {
        return (double) offset;
    }

    private void setProgress(long offset) {
        this.offset = offset;
    }

    @Override
    public void run() {
        try (ReadableByteChannel ic = getReadChannel()) {
            long count = (part.getEnd() - part.getStart()) + 1L;
            long chunk = count / CHUNK_COUNT;
            long readCount;
            while (count != 0) {
                readCount = oc.transferFrom(ic, offset, chunk);
                offset += readCount;
                count -= readCount;
                setProgress(offset);
            }
        } catch (IOException e) {
            System.out.println(" task" + part.getNo() + " [failed]. Reason : " + e.getMessage());
        } finally {
            try {
                oc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            latch.countDown();
        }
    }
}
