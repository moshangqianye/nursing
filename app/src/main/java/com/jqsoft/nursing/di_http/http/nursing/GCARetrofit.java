package com.jqsoft.nursing.di_http.http.nursing;

import com.jqsoft.nursing.base.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class GCARetrofit {
    public static String BASE_URL = Constants.EMPTY_STRING;
    public static String BASE_URL_NEW = Constants.EMPTY_STRING;
    private static Retrofit retrofit;

    public GCARetrofit(OkHttpClient okHttpClient) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
    }


    public Retrofit getRetrofit() {
        return retrofit;
    }
}
