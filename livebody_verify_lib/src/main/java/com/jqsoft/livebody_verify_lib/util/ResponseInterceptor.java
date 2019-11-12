package com.jqsoft.livebody_verify_lib.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018-08-21.
 */

public class ResponseInterceptor implements Interceptor {
    public ResponseInterceptor() {
        super();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //移除头部参数
        request = request.newBuilder()
                .removeHeader("Transfer-Encoding")
                .build();
        Response response = chain.proceed(request);
        if (response.body() != null && response.body().contentType() != null) {
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            ResponseBody responseBody = ResponseBody.create(mediaType, content);
            return response.newBuilder().removeHeader("Transfer-Encoding").body(responseBody).build();
        } else {
            return response;
        }
    }

}
