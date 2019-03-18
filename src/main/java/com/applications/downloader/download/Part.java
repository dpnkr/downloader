package com.applications.downloader.download;

class Part {

    private long start;
    private long end;
    private int no;

    Part(long start, long end, int no) {
        this.start = start;
        this.end = end;
        this.no = no;
    }

    long getStart() {
        return start;
    }

    long getEnd() {
        return end;
    }

    public String toString() {
        return no + "[" + start + "-" + end + "]";
    }

    int getNo() {
        return no;
    }

    void setEnd(long end) {
        this.end = end;
    }
}
