package com.bsoft.libbasic.thirdpart.fastsharedpreferences.io;

import android.content.Context;
import android.util.Log;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hailiangliao on 2018/3/16.
 */

public class ReadWriteManager {

    private static final String TAG = "ReadWriteManager";
    private static final String DEFAULT_DIR_PATH = "fast_sp";

    public static String getFilePath(Context context, String name) {
        return context.getFilesDir().getAbsolutePath()
                + File.separator
                + DEFAULT_DIR_PATH
                + File.separator
                + name;
    }

    private String filePath;
    private String lockFilePath;

    public ReadWriteManager(Context context, String name) {
        this.filePath = getFilePath(context, name);
        this.lockFilePath = getFilePath(context, name + ".lock");
//        Log.d("ReadWriteManager", "filePath: " + filePath);
        prepare();
    }

    public void write(Object obj) {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        Lock lock = null;
        try {
            prepare();
            lock = new Lock(lockFilePath).lock();
            Log.d(TAG, "start write file: " + filePath);
            fos = new FileOutputStream(filePath);
            oos = new ObjectOutputStream(new BufferedOutputStream(fos));
            oos.writeObject(obj);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.d(TAG, "finish write file: " + filePath);
            IoUtil.closeSilently(oos);
            IoUtil.closeSilently(fos);
            if (lock != null) {
                lock.release();
            }
        }
    }

    public Object read() {
        if (!IoUtil.isFileExist(filePath)) {
            return null;
        }
        ObjectInputStream ois = null;
        FileInputStream fis = null;
        Lock lock = null;
        try {
            lock = new Lock(lockFilePath).lock();
            fis = new FileInputStream(filePath);
            ois = new ObjectInputStream(new BufferedInputStream(fis));
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IoUtil.closeSilently(ois);
            IoUtil.closeSilently(fis);
            if (lock != null) {
                lock.release();
            }
        }
    }

    private void prepare() {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
    }

    private static class Lock {

        private static final Map<String, ReentrantLock> THREAD_LOCK_MAP = new HashMap<>();

        private static ReentrantLock getLock(String key) {
            synchronized (THREAD_LOCK_MAP) {
                if (!THREAD_LOCK_MAP.containsKey(key)) {
                    THREAD_LOCK_MAP.put(key, new ReentrantLock());
                }
                return THREAD_LOCK_MAP.get(key);
            }
        }

        private final String lockFilePath;
        private FileOutputStream fos;
        private FileChannel channel;
        private FileLock fileLock;
        private ReentrantLock threadLock;

        public Lock(String lockFilePath) {
            this.lockFilePath = lockFilePath;
            this.threadLock = getLock(lockFilePath);
            File file = new File(this.lockFilePath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public Lock lock() throws IOException {
            threadLock.lock();
            fos = new FileOutputStream(lockFilePath);
            channel = fos.getChannel();
            fileLock = channel.lock();
            return this;
        }

        public void release() {
            if (fileLock != null) {
                try {
                    fileLock.release();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            IoUtil.closeSilently(channel);
            IoUtil.closeSilently(fos);
            threadLock.unlock();
        }
    }
}
