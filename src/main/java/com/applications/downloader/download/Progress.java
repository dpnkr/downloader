package com.applications.downloader.download;

public class Progress implements Runnable {

    private DownloadTask tasks[];
    private double contentLength;

    Progress(DownloadTask tasks[], double contentLength) {
        this.tasks = tasks;
        this.contentLength = contentLength;
    }

    @Override
    public void run() {
        double read = 0;
        while (read != contentLength) {
            read = 0;
            for (DownloadTask task : tasks) {
                read += task.getProgress();
            }
            double percent = (read / contentLength) * 10000;
            percent = Math.floor(percent);
            percent = percent / 100;
            System.out.print("                                 downloaded : " + percent + " % \r");
        }
        System.out.println("\n                                 download completed.");
    }
}
