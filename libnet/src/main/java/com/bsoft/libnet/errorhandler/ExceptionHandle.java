package com.bsoft.libnet.errorhandler;

import android.net.ParseException;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libnet.R;
import com.google.gson.JsonParseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import retrofit2.HttpException;

import java.net.ConnectException;

/**
 *
 *
 */


public class ExceptionHandle {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    //Alt+Enter
    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.setMessage(ContextProvider.get().getApplication().getString(R.string.error_type_http_error)) ;
                    break;
            }
            return ex;
        }  else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.code);
            ex.setMessage (resultException.message);
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.setMessage(ContextProvider.get().getApplication().getString(R.string.error_type_parse_error));
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ERROR.NETWORD_ERROR);
            ex.setMessage(ContextProvider.get().getApplication().getString(R.string.error_type_connect_fail)) ;
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(e, ERROR.SSL_ERROR);
            ex.setMessage(ContextProvider.get().getApplication().getString(R.string.error_type_ssl_error)) ;
            return ex;
        } else if (e instanceof ConnectTimeoutException){
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.setMessage(ContextProvider.get().getApplication().getString(R.string.error_type_time_out)) ;
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.setMessage(ContextProvider.get().getApplication().getString(R.string.error_type_time_out)) ;
            return ex;
        }
        else {
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.setMessage(ContextProvider.get().getApplication().getString(R.string.error_type_unknown)) ;
            return ex;
        }
    }


    /**
     * 约定异常
     */
    public class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;
    }


    public static class ServerException extends RuntimeException {
        public int code;
        public String message;
    }
}

