package com.bsoft.libnet.api;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.bsoft.libnet.apiservice.DownloadService;
import com.bsoft.libnet.base.NetworkApi;
import com.bsoft.libnet.constants.HttpConstants;
import com.bsoft.libnet.model.DownloadingVo;
import com.bsoft.libnet.observer.BaseDownLoadObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.*;


public class NetDownLoadApi extends NetworkApi {



    private static volatile NetDownLoadApi sInstance;

    public static NetDownLoadApi getInstance() {
        if (sInstance == null) {
            synchronized (NetDownLoadApi.class) {
                if (sInstance == null) {
                    sInstance = new NetDownLoadApi();
                }
            }
        }
        return sInstance;
    }

    public static  <T> T getService(Class<T> service) {
        return getInstance().getRetrofit().create(service);
    }

    @Override
    protected Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                return chain.proceed(builder.build());
            }
        };
    }

    @Override
    public String getCurrentUrl() {
        return HttpConstants.httpDownloadUrl;
    }


    /**
     * 默认普通模式
     *
     * @param
     * @param url
     * @param filePath
     * @param observer
     */
    @SuppressLint("CheckResult")
    public void download(@NonNull String url, @NonNull String filePath,
                         BaseDownLoadObserver observer) {
        Observable observable = download( url, filePath);


        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 下载
     *
     * @param url      下载网络地址
     * @param filePath 本地存储路径
     * @return
     */
    @SuppressLint("CheckResult")
    private Observable<DownloadingVo> download(@NonNull String url, final String filePath) {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url = null");
        }
        if (TextUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("filePath = null");
        }
        final Subject<DownloadingVo> subject = PublishSubject.create();
        final Disposable[] disposable = new Disposable[1];

        Observable<ResponseBody> observable = getService(DownloadService.class)
                .downloadFile(url)
                .subscribeOn(Schedulers.io());


        observable.subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable[0] = d;
                subject.onSubscribe(d);
            }

            @Override
            public void onNext(ResponseBody value) {
                writeResponseBodyToDisk(disposable[0], value, filePath, subject);
            }

            @Override
            public void onError(Throwable e) {
                subject.onError(e);
            }

            @Override
            public void onComplete() {
                subject.onComplete();
            }
        });
        return subject;
    }

    /**
     * 把响应体写入到磁盘
     *
     * @param body     响应体
     * @param filePath 文件路径
     * @param subject  subject
     * @return 是否写入成功
     */
    private void writeResponseBodyToDisk(@NonNull Disposable disposable, @NonNull ResponseBody body, @NonNull String filePath, @NonNull Subject<DownloadingVo> subject) {
        File file = new File(filePath);
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = body.byteStream();
            outputStream = new FileOutputStream(file);

            byte[] fileReader = new byte[4096];

            long currentSize = 0;
            long totalSize = body.contentLength();
            DownloadingVo downloading = new DownloadingVo(totalSize, filePath);
            while (true) {
                if (!subject.hasObservers()) {
                    disposable.dispose();
                }
                int byteCount = inputStream.read(fileReader);
                if (byteCount == -1) {
                    break;
                }
                outputStream.write(fileReader, 0, byteCount);
                currentSize += byteCount;
                downloading.set(currentSize, currentSize == totalSize);
                subject.onNext(downloading);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
