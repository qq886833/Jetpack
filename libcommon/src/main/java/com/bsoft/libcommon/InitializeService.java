package com.bsoft.libcommon;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import com.bsoft.libbasic.context.ContextProvider;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;


public class InitializeService extends IntentService {
        private static final String ACTION_INIT_WHEN_APP_CREATE = "com.bsoft.component.service.action.INIT";

        public InitializeService() {

            super("InitializeService");

        }

        public static void start(Context context) {

            Intent intent = new Intent(context, InitializeService.class);

            intent.setAction(ACTION_INIT_WHEN_APP_CREATE);

            context.startService(intent);

        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {

            Log.e("InitializeService", "onHandleIntent");
            LiveEventBus.config().supportBroadcast(getApplicationContext()).lifecycleObserverAlwaysActive(true);

            FileDownloader.setupOnApplicationOnCreate(ContextProvider.get().getApplication())
                    .connectionCreator(new FileDownloadUrlConnection
                            .Creator(new FileDownloadUrlConnection.Configuration()
                            .connectTimeout(15_000) // set connection timeout.
                            .readTimeout(15_000) // set read timeout.
                    )).commit();

        }

    @Override
    public void onCreate() {
        Log.e("InitializeService", "onCreate");
        super.onCreate();
    }

    @Override
        public IBinder onBind(Intent intent){
            return super.onBind(intent);
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId){
            Log.e("InitializeService", "onStartCommand");
            return super.onStartCommand(intent,flags,startId);
        }

        @Override
        public void setIntentRedelivery(boolean enabled){
            super.setIntentRedelivery(enabled);
        }

        @Override
        public void onDestroy(){
            Log.e("InitializeService", "onDestroy");
            super.onDestroy();
        }
    }
