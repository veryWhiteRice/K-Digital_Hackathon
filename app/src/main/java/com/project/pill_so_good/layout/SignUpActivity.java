package com.project.pill_so_good.layout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.pill_so_good.R;
import com.project.pill_so_good.member.domain.Member;
import com.project.pill_so_good.member.signup.SignUpService;

public class SignUpActivity extends AppCompatActivity {

    private SignUpService signUpService;
    private EditText etEmail, etPassword, etCheckPassword, etAge;
    private RadioGroup rgGender, rgDivision;
    private Button registerBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpService = new SignUpService();

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        etCheckPassword = findViewById(R.id.password_check);
        etAge = findViewById(R.id.age);

        rgGender = findViewById(R.id.rg_gender);
        rgDivision = findViewById(R.id.rg_status);
        registerBtn = findViewById(R.id.register_btn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    signUpService.signUp(getMemberInfo(), SignUpActivity.this);
                } catch (IllegalArgumentException e) {
                    Toast.makeText(SignUpActivity.this, "모든 칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private Member getMemberInfo() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String checkPassword = etCheckPassword.getText().toString();
        int age = Integer.parseInt(etAge.getText().toString());

        String gender = getRadioButtonInfo(this.rgGender);
        String division = getRadioButtonInfo(this.rgDivision);

        return new Member(email, password, checkPassword, age, gender, division);
    }

    private String getRadioButtonInfo(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        return radioButton.getText().toString();
    }



}
