package com.project.pill_so_good.layout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.pill_so_good.R;
import com.project.pill_so_good.member.autoLogin.AutoLoginService;
import com.project.pill_so_good.member.dto.LoginInfo;
import com.project.pill_so_good.member.login.LoginService;

public class StartActivity extends AppCompatActivity {

    private LoginService loginService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        loginService = new LoginService();

        startAutoLogin();
    }

    private void startAutoLogin() {
        LoginInfo loginInfo = AutoLoginService.getLoginInfo(StartActivity.this);
        if (loginInfo.areFieldsNotEmpty()) {
            loginService.login(loginInfo.getEmail(), loginInfo.getPassword(), StartActivity.this, Boolean.FALSE);
        } else {
            startLoading();
        }
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
