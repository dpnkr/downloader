package com.applications.downloader.download;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Caught exception : " + e.getMessage() + " for " + t.getName());
    }
}
