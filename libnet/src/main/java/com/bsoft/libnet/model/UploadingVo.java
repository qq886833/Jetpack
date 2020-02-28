package com.bsoft.libnet.model;

import com.bsoft.libcommon.model.base.BaseVo;

public class UploadingVo extends BaseVo {
    private long currentSize;
    private long totalSize;
    private boolean done;

    public UploadingVo() {
    }

    public long getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
