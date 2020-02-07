package com.bsoft.libnet.constants;

/**
 * Created by chenkai on 2018/3/13.
 */


public class NetConfig {
    //Cache
    public static final boolean CACHE_ENABLE = false;
    //缓存默认文件名
    public static final String CACHE_DEFAULT_FILE_NAME = "HttpCache";
    //缓存默认文件存储最大值
    public static final long CACHE_DEFAULT_MAX_SIZE = 1024 * 1024 * 16;
    //有网络时，缓存的默认生命周期
    public static final int CACHE_DEFAULT_MAX_AGE = 0;
    //无网络时，缓存的默认生命周期
    public static final int CACHE_DEFAULT_MAX_STALE = 60 * 60 * 24 * 7;

    public static final long DEFAULT_CONNECT_TIMEOUT = 20;
    public static final long DEFAULT_WRITE_TIMEOUT = 30;



}
