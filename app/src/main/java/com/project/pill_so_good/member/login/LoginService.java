package com.project.pill_so_good.member.login;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.pill_so_good.layout.LoginActivity;
import com.project.pill_so_good.layout.MainActivity;
import com.project.pill_so_good.member.autoLogin.AutoLoginService;

public class LoginService {

    private final FirebaseAuth firebaseAuth;
    private final DatabaseReference databaseReference;

    public LoginService() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void login(String email, String password, AppCompatActivity activity, Boolean isCheckAutoLogin) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (isCheckAutoLogin)
                        AutoLoginService.setLoginInfo(activity, email, password);

                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                } else {
                    Toast.makeText(activity, "로그인 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


