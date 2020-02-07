package com.bsoft.libnet.errorhandler;

import com.bsoft.libnet.model.BaseResponse;
import io.reactivex.functions.Function;

/**
 *
 *
 *
 * Description:Api层面的错误处理器
 */


public class ApiErrorHandler<T> implements Function<T,T> {
    @Override
    public T apply(T response) throws Exception {
        if (response instanceof BaseResponse && ((BaseResponse) response).getCode() != 200) {
            ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
            exception.code = ((BaseResponse) response).getCode();
            exception.message = ((BaseResponse) response).getMessage()!= null ? ((BaseResponse) response).getMessage()  : "";
            throw exception;  }
        return response;
    }
}
