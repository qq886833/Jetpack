package com.bsoft.libnet.model;

import androidx.annotation.NonNull;

public class DownloadingVo extends BaseVo {
    private long currentSize;
    private long totalSize;
    private boolean done;
    private String filePath;

    public DownloadingVo(long totalSize, @NonNull String filePath) {
        this.totalSize = totalSize;
        this.filePath = filePath;
    }

    public void set(long currentSize, boolean done) {
        this.currentSize = currentSize;
        this.done = done;
    }

    public long getCurrentSize() {
        return this.currentSize;
    }

    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public boolean isDone() {
        return this.done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
