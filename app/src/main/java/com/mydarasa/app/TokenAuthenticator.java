package com.mydarasa.app;

import android.content.Context;
import android.util.Log;

import com.mydarasa.app.login.TokenDetails;
import com.mydarasa.app.refreshtoken.RefreshTokenModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Proxy;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    private Context context;
    private MyServiceHolder myServiceHolder;

    public TokenAuthenticator(Context context, MyServiceHolder myServiceHolder) {
        this.context = context;
        this.myServiceHolder = myServiceHolder;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        if (myServiceHolder == null) {
            return null;
        }

      PrefManager prefManager = new PrefManager(context);

        String refreshToken = prefManager.getRefreshToken();
        RefreshTokenModel refreshTokenModel = new RefreshTokenModel(refreshToken);

        retrofit2.Response retrofitResponse = myServiceHolder.get().getRefreshToken(refreshTokenModel).execute();

        if (retrofitResponse != null) {
            TokenDetails refreshTokenResponse = (TokenDetails) retrofitResponse.body();

            String newAccessToken = refreshTokenResponse.getAccessToken();

            return response.request().newBuilder()
                    .header("Authorization", newAccessToken)
                    .build();
        }

        return null;
    }
}
