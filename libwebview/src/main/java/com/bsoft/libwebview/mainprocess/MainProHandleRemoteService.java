package com.bsoft.libwebview.mainprocess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.bsoft.libbasic.utils.log.LogUtil;

public class MainProHandleRemoteService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int pid = android.os.Process.myPid();
        LogUtil.d("", String.format("MainProHandleRemoteService: %s", "当前进程ID为："+pid+"----"+"客户端与服务端连接成功，服务端返回BinderPool.BinderPoolImpl 对象"));
        return CommandsManager.getsInstance();
    }
}
