package com.project.pill_so_good.layout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.project.pill_so_good.R;
import com.project.pill_so_good.camera.ImageInfo;
import com.project.pill_so_good.camera.Photo;
import com.project.pill_so_good.member.autoLogin.AutoLoginService;
import com.project.pill_so_good.member.logout.LogoutService;
import com.project.pill_so_good.member.memberInfo.UserInfoService;

import android.content.pm.PackageManager;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.RequiresApi;
import java.security.MessageDigest;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import java.security.NoSuchAlgorithmException;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LogoutService logoutService;
    private UserInfoService userInfoService;

    private Button logoutBtn, cameraBtn, galleryBtn, analyzeBtn, map;

    private Photo photo;
    private ImageInfo imageInfo;
    private ActivityResultLauncher<Intent> cameraResultLauncher;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutService = new LogoutService();
        userInfoService = new UserInfoService();
        photo = new Photo();
        imageInfo = ImageInfo.getInstance();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("키해시는 :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        setLogoutButton();
        setCameraBtn();
        setGalleryBtn();
        setAnalyzeBtn();
        clkmapbtn();
        setCameraResultLauncher();

    }
    private void clkmapbtn()
    {
        map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent를 사용하여 MapActivity를 시작
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setCameraResultLauncher() {
        cameraResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Bitmap bitmap = photo.afterTakePicture();
                            setImageView(bitmap);
                            imageInfo.setBitmap(bitmap);
                        }
                    }
                }
        );
    }

    private void setAnalyzeBtn() {
        analyzeBtn = findViewById(R.id.analyze_btn);
        analyzeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 분석 시작 코드
            }
        });
    }

    private void setGalleryBtn() {
        galleryBtn = findViewById(R.id.gallery_btn);
        galleryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 갤러리 이동 코드
            }
        });
    }

    private void setCameraBtn() {
        cameraBtn = findViewById(R.id.camera_btn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = photo.createImageFile(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                    } catch (IOException e) {

                    }

                    if (photoFile != null) {
                        Uri photoUri = FileProvider.getUriForFile(MainActivity.this, getPackageName(), photoFile);
                        photo.setPhotoUri(photoUri);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        cameraResultLauncher.launch(intent);
                    }
                }
            }
        });
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

    private void setImageView(Bitmap bitmap) {
        ImageView imageView = findViewById(R.id.result);
        imageView.setImageBitmap(bitmap);
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



    private void showToastMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
