package com.applications.downloader.download;

import java.util.concurrent.ThreadFactory;

public class TaskFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        TaskRunner taskThread = new TaskRunner(r, r.toString());
        taskThread.setUncaughtExceptionHandler(new ExceptionHandler());
        return taskThread;
    }
}
