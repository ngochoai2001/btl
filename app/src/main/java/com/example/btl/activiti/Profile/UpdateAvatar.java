package com.example.btl.activiti.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.example.btl.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.btl.activiti.MainActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.btl.activiti.MainActivity.MY_REQUEST_CODE;

public class UpdateAvatar extends AppCompatActivity {

    CircleImageView ciChangeAvatar;
    Button btnXacNhanDoiAnh;
    ImageButton btnBackAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_avatar);

        ciChangeAvatar = findViewById(R.id.ciChangeAvatar);
        btnXacNhanDoiAnh = findViewById(R.id.btnXacNhanDoiAnh);
        btnBackAvatar = findViewById(R.id.btnBackAvatar);

        setUserAvatar();
        initListener();

        btnBackAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUserAvatar() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        Glide.with(this).load(user.getPhotoUrl()).error(R.drawable.avatardefult1).into(ciChangeAvatar);
    }

    private void initListener() {
        ciChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  onClickRequestPermission();
            }
        });
    }

    private void onClickRequestPermission(){
        MainActivity mainActivity = (MainActivity) getApplicationContext();
        if(mainActivity == null){
            return;
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
           mainActivity.openGallery();
            return;
        }
        if(this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            mainActivity.openGallery();
        }else {
            String[] permisstions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            this.requestPermissions(permisstions,MY_REQUEST_CODE);
        }
    }

    public void setBitmapImageView(Bitmap bitmapImageView){
        ciChangeAvatar.setImageBitmap(bitmapImageView);
    }
}