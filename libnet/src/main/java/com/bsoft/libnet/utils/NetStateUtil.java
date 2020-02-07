package com.bsoft.libnet.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


public class NetStateUtil {

    private Subject<Object> subject;
    private NetStateReceiver netStateReceiver;
    private volatile static NetStateUtil instance;

    public enum NetState {
        NET_NO, NET_2G, NET_3G, NET_4G, NET_WIFI, NET_UNKNOWN
    }

    private NetStateUtil() {
    }

    public static NetStateUtil getInstance() {
        if (instance == null) {
            synchronized (NetStateUtil.class) {
                if (instance == null) {
                    instance = new NetStateUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 观察网络状态
     *
     * @param context context
     * @return 网络状态数据源
     */
    @SuppressWarnings("ConstantConditions")
    public Observable<NetState> observe(@NonNull Context context) {
        if (subject == null) {
            subject = PublishSubject.create().toSerialized();
        }
        registerReceiver(context.getApplicationContext(), subject);
        return subject.cast(NetState.class);
    }

    /**
     * 取消观察
     *
     * @param context    context
     * @param disposable 取消观察
     */
    @SuppressWarnings("ConstantConditions")
    public void dispose(@NonNull Context context, @NonNull Disposable disposable) {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        if (!subject.hasObservers()) {
            unregisterReceiver(context);
        }
    }

    private void registerReceiver(@NonNull Context context, Subject<Object> subject) {
        if (netStateReceiver == null) {
            netStateReceiver = new NetStateReceiver(subject);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            context.getApplicationContext().registerReceiver(netStateReceiver, intentFilter);
        }
    }

    private void unregisterReceiver(@NonNull Context context) {
        if (netStateReceiver != null) {
            context.getApplicationContext().unregisterReceiver(netStateReceiver);
            netStateReceiver = null;
        }
    }

    /**
     * 获取当前网络状态
     *
     * @param context context
     * @return 当前网络状态
     */
    @SuppressWarnings("ConstantConditions")
    public static NetState getCurrentNetState(@NonNull Context context) {
        NetState netState = NetState.NET_NO;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    netState = NetState.NET_WIFI;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    switch (networkInfo.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            netState = NetState.NET_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            netState = NetState.NET_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            netState = NetState.NET_4G;
                            break;
                        default:
                            netState = NetState.NET_UNKNOWN;
                    }
                    break;
                default:
                    netState = NetState.NET_UNKNOWN;
                    break;
            }
        }
        return netState;
    }

    private class NetStateReceiver extends BroadcastReceiver {
        private Subject<Object> subject;

        NetStateReceiver(@NonNull Subject<Object> subject) {
            this.subject = subject;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
                subject.onNext(getCurrentNetState(context));
            }
        }
    }
}
