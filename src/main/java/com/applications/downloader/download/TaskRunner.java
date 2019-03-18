package com.applications.downloader.download;

class TaskRunner extends Thread {
    TaskRunner(Runnable task, String taskName) {
        super(task, taskName);
    }
}
