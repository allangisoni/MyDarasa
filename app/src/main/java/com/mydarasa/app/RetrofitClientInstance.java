package com.mydarasa.app;

import android.util.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL= "http://173.230.131.197:8086";
    private static String authToken = null;



    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){

           /** OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

            String swaggerString = "mydarasa:Magic@2019!";
            byte[] data = new byte[0];
            try {
                data = swaggerString.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String base64String = Base64.encodeToString(data, Base64.DEFAULT);
            authToken = "Basic" +" "+ base64String;
            //Create a new Interceptor.
            Interceptor headerAuthorizationInterceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    okhttp3.Request request = chain.request();
                    Headers headers = request.headers().newBuilder().add("Authorization", authToken).build();
                    request = request.newBuilder().headers(headers).build();
                    return chain.proceed(request);
                }
            };

            //Add the interceptor to the client builder.
            clientBuilder.addInterceptor(headerAuthorizationInterceptor);
 **/

            OkHttpClient mIntercepter = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + "b07462f9-5cab-43c9-aea2-1f5b7059aef0").build();
                    return chain.proceed(request);
                }

            }).build();



            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    String swaggerString = "mydarasa:Magic@2019!";
                    byte[] data = new byte[0];
                    try {
                        data = swaggerString.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String base64String = Base64.encodeToString(data, Base64.DEFAULT);
                    base64String = base64String.trim();
                   // authToken = "Basic" +" "+ base64String;

                   // authToken= Credentials.basic("ongakiharon@gmail.com")

                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Authorization", base64String)
                            .build();
                    return chain.proceed(request);
                }
               })
                    .build();




            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.client(mIntercepter)
                   // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
