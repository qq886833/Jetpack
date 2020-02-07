package com.bsoft.libnet.cache;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.bsoft.libnet.constants.NetConfig;
import okhttp3.Cache;

import java.io.File;


public class CacheBuilder {
    private String fileName = NetConfig.CACHE_DEFAULT_FILE_NAME;
    private long maxSize = NetConfig.CACHE_DEFAULT_MAX_SIZE;

    /**
     * 设置文件名
     *
     * @param fileName
     * @return
     */
    public CacheBuilder setFileName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            this.fileName = NetConfig.CACHE_DEFAULT_FILE_NAME;
        } else {
            this.fileName = fileName;
        }
        return this;
    }

    /**
     * 设置文件存储最大值
     *
     * @param maxSize
     * @return
     */
    public CacheBuilder setMaxSize(@IntRange(from = 1024) long maxSize) {
        if (maxSize < 1024) throw new IllegalArgumentException("maxSize < 1024");

        this.maxSize = maxSize;
        return this;
    }

    @SuppressWarnings("ConstantConditions")
    public Cache build(@NonNull Context context) {
        File file = new File(context.getApplicationContext().getCacheDir(), fileName);
        return new Cache(file, maxSize);
    }
}
