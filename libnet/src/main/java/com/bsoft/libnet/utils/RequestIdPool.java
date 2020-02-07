package com.bsoft.libnet.utils;

/**
 * Created by kai.chen on 2017/4/14.
 */

public class RequestIdPool {
    //Flag
    private int curRequestId = 0;
    private volatile static RequestIdPool instance;

    private RequestIdPool() {
    }

    public static RequestIdPool getInstance() {
        if (instance == null) {
            synchronized (RequestIdPool.class) {
                if (instance == null) {
                    instance = new RequestIdPool();
                }
            }
        }
        return instance;
    }

    public synchronized int getRequestId() {
        curRequestId++;
        if (curRequestId == Integer.MAX_VALUE) {
            curRequestId = 0;
        }
        return curRequestId;
    }
}
