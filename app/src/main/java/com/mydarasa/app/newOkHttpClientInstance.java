package com.mydarasa.app;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class newOkHttpClientInstance {

    public static class Builder {
        private HashMap<String, String> headers = new HashMap<>();
        private Context context;
        private MyServiceHolder myServiceHolder;

        public Builder(Context context, MyServiceHolder myServiceHolder) {
            this.context = context;
            this.myServiceHolder = myServiceHolder;
        }

        public Builder addHeader(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public OkHttpClient build() {
            TokenAuthenticator authenticator = new TokenAuthenticator(context, myServiceHolder);

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Interceptor.Chain chain) throws IOException {
                                    // Add default headers
                                    Request.Builder requestBuilder = chain.request().newBuilder();
                                            //.addHeader("accept", "*/*");
                                          //  .addHeader("accept-encoding:gzip", "gzip, deflate")
                                           // .addHeader("accept-language", "en-US,en;q=0.9");

                                    // Add additional headers
                                    Iterator it = headers.entrySet().iterator();

                                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                                        if (entry.getKey() != null && entry.getValue() != null) {
                                            requestBuilder.addHeader(entry.getKey(), entry.getValue());
                                        }
                                    }

                                    if (context != null) {
                                       PrefManager prefManager = new PrefManager(context);

                                        String token = prefManager.getAccessToken();

                                        if (token != null) {
                                            requestBuilder.addHeader("Authorization", "Bearer"+" "+ "da0c714e-0c55-4d19-ad51-f372bb42b52e");
                                        }
                                    }

                                    return chain.proceed(requestBuilder.build());
                                }
                            }
                    )
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS);

            okHttpClientBuilder.authenticator(authenticator);

            return okHttpClientBuilder.build();
        }
    }
}
