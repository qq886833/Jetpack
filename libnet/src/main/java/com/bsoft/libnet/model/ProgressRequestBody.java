package com.bsoft.libnet.model;

import io.reactivex.subjects.Subject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.*;

import java.io.IOException;

/**
 * Created by chenkai on 2018/3/14.
 */

public class ProgressRequestBody extends RequestBody {

    private RequestBody mDelegate;
    private Subject<Object> mSubject;
    private UploadingVo mProgressInfo;
    private BufferedSink mBufferedSink;

    public ProgressRequestBody(RequestBody delegate, UploadingVo uploading, Subject<Object> subject) {
        if (delegate == null) {
            throw new IllegalArgumentException("delegate = null");
        }
        if (subject == null) {
            throw new IllegalArgumentException("subject = null");
        }
        mDelegate = delegate;
        mSubject = subject;
        mProgressInfo = uploading;
    }

    @Override
    public long contentLength() throws IOException {
        return mDelegate.contentLength();
    }

    @Override
    public MediaType contentType() {
        return mDelegate.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (sink instanceof Buffer) {
            // Log Interceptor
            mDelegate.writeTo(sink);
            return;
        }
        if (mBufferedSink == null) {
            mBufferedSink = Okio.buffer(wrapSink(sink));
        }
        mDelegate.writeTo(mBufferedSink);
        mBufferedSink.flush();
    }

    private Sink wrapSink(Sink sink) {
        return new ForwardingSink(sink) {

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                mProgressInfo.setCurrentSize(mProgressInfo.getCurrentSize() + byteCount);
                mProgressInfo.setDone(false);
                mSubject.onNext(mProgressInfo);
            }
        };
    }
}
