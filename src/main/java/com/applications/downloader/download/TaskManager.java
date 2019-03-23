package com.applications.downloader.download;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class TaskManager {
    private ExecutorService service;

    private CountDownLatch latch;
    private String finalFileName;

    private DownloadTask tasks[];

    TaskManager() {
        service = Executors.newCachedThreadPool(new TaskFactory());
    }

    void addDownloadTasks(DownloadContext contexts[]) {
        latch = new CountDownLatch(contexts.length);
        tasks = new DownloadTask[contexts.length];
        for (int i = 0; i < contexts.length; i++) {
            contexts[i].setLatch(latch);
            run(tasks[i] = new DownloadTask(contexts[i]));
        }
    }

    private void run(Runnable task) {
        service.execute(task);
    }

    void waitForCompletion() throws InterruptedException {
        latch.await();
    }

    void addJoinTasks(File files[], int count) throws InterruptedException,
            ExecutionException {
        if (count == 1) {
            File file = new File(finalFileName);
            boolean renamed = files[0].renameTo(file);
            if (!renamed) {
                System.out.println("                                 File exists !");
            } else {
                System.out.println("                                 success");
            }
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

    void runProgress(long contentLength) {
        Progress progress = new Progress(tasks, contentLength);
        run(progress);
    }
}
