package com.sm.banitro.data.source.remote;

import com.sm.banitro.BuildConfig;
import com.sm.banitro.util.ConstantUtil;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }
            retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl(ConstantUtil.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}