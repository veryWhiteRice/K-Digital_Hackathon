package com.project.pill_so_good.layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.pill_so_good.R;
import com.project.pill_so_good.member.autoLogin.AutoLoginService;
import com.project.pill_so_good.member.dto.LoginInfo;
import com.project.pill_so_good.member.dto.UserInfo;
import com.project.pill_so_good.member.logout.LogoutService;
import com.project.pill_so_good.member.memberInfo.UserInfoService;

public class MainActivity extends AppCompatActivity {

    private LogoutService logoutService;
    private UserInfoService userInfoService;

    private Button logoutBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutService = new LogoutService();
        userInfoService = new UserInfoService();

        setLogoutButton();
    }


    private long backPressedTime;
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        long TIME_INTERVAL = 2000;
        if (backPressedTime + TIME_INTERVAL > currentTime) {
            super.onBackPressed();
            finish();
        }else {
            showToastMessage("뒤로 버튼을 두 번 눌러주세요");
        }

        backPressedTime = currentTime;
    }

    private void setLogoutButton() {
        logoutBtn = findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutService.logout();
                AutoLoginService.removeLoginInfo(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showToastMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
