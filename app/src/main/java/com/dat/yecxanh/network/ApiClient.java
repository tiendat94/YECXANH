package com.dat.yecxanh.network;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit retrofit = null;
    static Interceptor myLog = new Interceptor() {
        private String bodyToString(final Request request) {
            try {
                final Request copy = request.newBuilder().build();
                RequestBody body = copy.body();
                if (body == null) {
                    return "EMPTY BODY";
                }

                final Buffer buffer = new Buffer();
                body.writeTo(buffer);
                return buffer.readUtf8();

            } catch (Exception e) {
                return "Can't read request body";
            }
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Log.e("App", "URL:" + request.url().toString());
            Log.e("App", "REQUEST:" + bodyToString(request));

            Response response = chain.proceed(request);

            ResponseBody responseBody = response.body();
            String responseBodyString = response.body().string();
            String logContent = null;
            if (responseBodyString.length() > 10000) {
                logContent = responseBodyString.substring(0, 10000);
            } else {
                logContent = responseBodyString;
            }

            Response newResponse = response.newBuilder()
                    .body(ResponseBody.create(responseBody.contentType(), responseBodyString.getBytes())).build();

            Log.e("App", "RESPONSE: " + logContent);

            return newResponse;
        }
    };

    public static ApiInterface getApiService(final Context context) {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            builder.addInterceptor(myLog);
            builder.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder();
                    // requestBuilder.addHeader("x-access-token", SharedPreference.get(context, Utils.Keys.TOKEN, null));
                    requestBuilder.removeHeader("Content-type");
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl("http://localhost")
                    .addConverterFactory(GsonConverterFactory.create(GsonProvider.getInstance()))
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
