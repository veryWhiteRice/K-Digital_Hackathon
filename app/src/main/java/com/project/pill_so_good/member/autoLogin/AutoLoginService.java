package com.project.pill_so_good.member.autoLogin;

import android.content.Context;
import android.content.SharedPreferences;

import com.project.pill_so_good.member.dto.LoginInfo;

public class AutoLoginService {

    private static final String AUTO_LOGIN = "auto_login";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    public static void setLoginInfo(Context context, String email, String password) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);

        editor.apply();
    }

    public static LoginInfo getLoginInfo(Context context) {
        SharedPreferences preferences = getPreferences(context);
        return new LoginInfo(preferences.getString(EMAIL, ""), preferences.getString(PASSWORD, ""));
    }

    public static void removeLoginInfo(Context context) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(AUTO_LOGIN, Context.MODE_PRIVATE);
    }
}
