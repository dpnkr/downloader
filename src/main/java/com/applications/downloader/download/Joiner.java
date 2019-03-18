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

        File f3 = new File(f1.getParent() + File.separator + f1.getName() + ".join");

        FileChannel from1 = new FileInputStream(f1).getChannel();
        FileChannel from2 = new FileInputStream(f2).getChannel();

        FileChannel to = new FileOutputStream(f3).getChannel();

        transfer(from1, to);
        to.position(from1.size());
        transfer(from2, to);

        from1.close();
        from2.close();
        to.close();

        f1.deleteOnExit();
        f2.deleteOnExit();

        return f3;
    }

    private void transfer(FileChannel from, FileChannel to) throws IOException {
        long count = from.size();
        while (count != 0) {
            count -= from.transferTo(0, count, to);
        }
    }
}
