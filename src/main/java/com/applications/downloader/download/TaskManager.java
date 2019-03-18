package com.applications.downloader.download;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class TaskManager {

    private ExecutorService service;
    private CountDownLatch latch;
    private String finalFileName;

    TaskManager() {
        service = Executors.newCachedThreadPool(new TaskFactory());
    }

    void addWriteTasks(DownloadContext contexts[]) {
        latch = new CountDownLatch(contexts.length);
        for (DownloadContext context : contexts) {
            context.setLatch(latch);
            service.execute(new DownloadTask(context));
        }
    }

    void waitForCompletion() throws InterruptedException {
        System.out.println("downloading.... ");
        latch.await();
    }

    void addJoinTasks(File files[], int count) throws InterruptedException,
            ExecutionException {
        if (count == 1) {
            File file = new File(finalFileName);
            boolean b = files[0].renameTo(file);
            System.out.println("rename : " + b);
            if (files[0].exists()) {
                files[0].deleteOnExit();
            }
            return;
        }
        List<Future<File>> results = new ArrayList<>();
        for (int i = 0; i < files.length - 1; i += 2) {
            results.add(service.submit(new Joiner(files[i], files[i + 1])));
        }
        files = new File[count / 2];
        for (int i = 0; i < files.length; i++) {
            files[i] = results.get(i).get();
        }
        addJoinTasks(files, files.length);
    }

    void done() {
        service.shutdown();
    }

    void setFinalFileName(String finalFileName) {
        this.finalFileName = finalFileName;
    }
}
