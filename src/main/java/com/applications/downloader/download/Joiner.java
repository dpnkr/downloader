package com.applications.downloader.download;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.concurrent.Callable;

public class Joiner implements Callable<File> {

    private File f1;
    private File f2;

    Joiner(File f1, File f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public File call() throws IOException {
        long size = f1.length();
        FileChannel from = new FileInputStream(f2).getChannel();
        FileChannel to = new FileOutputStream(f1).getChannel();
        to.position(size);
        transfer(from, to);
        f2.deleteOnExit();
        return f1;
    }

    private void transfer(FileChannel from, FileChannel to) {
        long count;
        try {
            count = from.size();
            while (count != 0) {
                count -= from.transferTo(0, count, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                from.close();
                to.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
