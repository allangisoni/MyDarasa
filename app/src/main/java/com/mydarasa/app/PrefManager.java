package com.mydarasa.app;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences loginPref;
    SharedPreferences accessTokenPref;
    SharedPreferences refreshTokenPref;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor loginEditor;
    SharedPreferences.Editor accessTokenEditor;
    SharedPreferences guardianPref;
    SharedPreferences.Editor guardianEditor;
    SharedPreferences.Editor refreshTokenEditor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "mydarasa-welcome";

    private static final String LOGIN_PREF = "mydarasa_login";


    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_LOGGED_IN = "IsUserLoggedIn";

    private static final String ACCESS_TOKEN_PREF = "Mydarasa_accessToken";
    private static final String SESSION_ACCESS_TOKEN = "CurrToken";

    private static final String GUAARDIAN_NO_PREF = "Mydarasa_guardianNo";
    private static final String GUARDIAN_USERID = "1";

    private static final String REFRESH_TOKEN_PREF = "Mydarasa_refreshToken";
    private static final String SESSION_REFRESH_TOKEN = "refreshToken";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        loginPref= context.getSharedPreferences(LOGIN_PREF, PRIVATE_MODE);
        editor = pref.edit();
        loginEditor = loginPref.edit();
        accessTokenPref = context.getSharedPreferences(ACCESS_TOKEN_PREF,   PRIVATE_MODE);
        accessTokenEditor = accessTokenPref.edit();
        guardianPref = context.getSharedPreferences(GUAARDIAN_NO_PREF, PRIVATE_MODE);
        guardianEditor = guardianPref.edit();
        refreshTokenPref = context.getSharedPreferences(REFRESH_TOKEN_PREF, PRIVATE_MODE);
        refreshTokenEditor = refreshTokenPref.edit();

    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {

        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setIsLoggedIn(boolean isLoggedIn){
        loginEditor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        loginEditor.commit();
    }




    public boolean isUserLoggedIn(){
        return loginPref.getBoolean(IS_LOGGED_IN, false);
    }

    public void setAccessToken(String accessToken){
        accessTokenEditor.putString(SESSION_ACCESS_TOKEN, accessToken);
        accessTokenEditor.commit();
    }

    public String getAccessToken(){
        return accessTokenPref.getString(SESSION_ACCESS_TOKEN,"CurrToken" );
    }


    public void setRefreshToken(String refreshToken){
        refreshTokenEditor.putString(SESSION_REFRESH_TOKEN, refreshToken);
        refreshTokenEditor.commit();
    }

    public String getRefreshToken(){
        return  refreshTokenPref.getString(SESSION_REFRESH_TOKEN, "refreshToken");
    }

    public void setGuardianId(String guardianId){
        guardianEditor.putString(GUARDIAN_USERID, guardianId);
        guardianEditor.commit();
    }

    public String getGuardianId(){
        return guardianPref.getString(GUARDIAN_USERID, "0");
    }
}
