package com.mydarasa.app;

import android.text.TextUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String API_BASE_URL = "http://173.230.131.197:8086";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private  static OkHttpClient mIntercepter = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + "efe78084-62bd-4589-b1a5-2338ee1e73d0").build();
                    return chain.proceed(request);
                }

            }).build();

    public static <S> S createService(Class<S> serviceClass) {
        //builder = builder.client(mIntercepter);
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, String username, String password) {
        if (username != null
                && password != null) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.connectTimeout(20, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .addInterceptor(interceptor);


                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}
