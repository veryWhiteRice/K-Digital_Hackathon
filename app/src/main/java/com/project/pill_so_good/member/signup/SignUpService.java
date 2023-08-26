package com.project.pill_so_good.member.signup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.pill_so_good.member.domain.Member;

public class SignUpService {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public SignUpService() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }


    public void signUp(Member member, AppCompatActivity activity) {
        firebaseAuth.createUserWithEmailAndPassword(member.getEmail(), member.getPassword()).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    member.setEmailId(firebaseUser.getUid());
                    member.setEmailId(firebaseUser.getEmail());

                    databaseReference.child("Member").child(firebaseUser.getUid()).setValue(member);
                    showToast("회원가입이 완료되었습니다.", activity);
                    activity.finish();
                } else {
                    showToast("회원가입이 실패했습니다.", activity);
                }
            }
        });
    }

    private void showToast(String message, AppCompatActivity activity) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
