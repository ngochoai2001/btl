package com.example.btl.activiti;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.btl.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.io.IOException;
import java.util.ArrayList;

import com.example.btl.fragment.FragmentChat;
import com.example.btl.fragment.FragmentProfile;
import com.example.btl.fragment.FragmentSearch;
import com.example.btl.fragment.FragmentThongBao;
import com.example.btl.fragment.Fragment_Home;
import com.example.btl.model.Cart;
import com.example.btl.model.HoaDon;
import com.example.btl.model.MayAnh;
import com.example.btl.model.Message;
import com.example.btl.model.ThongBao;

public class MainActivity extends AppCompatActivity {

    final private  FragmentProfile fragmentProfile = new FragmentProfile();
    public static final int MY_REQUEST_CODE =10 ;
    final private ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult
            (new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
               if(result.getResultCode() == RESULT_OK){
                   Intent intent= result.getData();
                   if(intent == null){
                       return;
                   }
                   Uri uri = intent.getData();
                   fragmentProfile.setmUri(uri);
                   try {
                       Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                       fragmentProfile.setBitmapImageView(bitmap);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
        }
    });


    ConnectNetwork connectNetwork;

    BottomNavigationView menu_navi;
    public static ArrayList<Cart> cartArrayList;
    public static ArrayList<MayAnh> danhSachYeuThichList;
    public static ArrayList<Message> messageArrayList;
    public static ArrayList<HoaDon> hoaDonArrayList;
    public static ArrayList<ThongBao> thongBaoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);

        connectNetwork = new ConnectNetwork();

        menu_navi = findViewById(R.id.menu_navi);
        if(cartArrayList != null){

        }else{
            cartArrayList = new ArrayList<>();
        }

        if(danhSachYeuThichList != null){

        }else {
            danhSachYeuThichList= new ArrayList<>();
        }

        if(messageArrayList != null){

        }else {
            messageArrayList= new ArrayList<>();
        }

        if(hoaDonArrayList != null){

        }else {
            hoaDonArrayList= new ArrayList<>();
        }

        if(thongBaoArrayList != null){

        }else {
            thongBaoArrayList= new ArrayList<>();
        }



        getFragment(Fragment_Home.newInstance());
        menu_navi.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                  switch (item.getItemId()){
                      case R.id.menuHome :
                          getFragment(Fragment_Home.newInstance());
                          break;
                      case R.id.search :
                          getFragment(FragmentSearch.newInstance());
                          break;
                      case R.id.menuChat :
                          getFragment(FragmentChat.newInstance());
                          break;
                      case R.id.menuThongBao :
                          getFragment(FragmentThongBao.newInstance());
                          break;
                      case R.id.menuProfile :
                          getFragment(fragmentProfile);
                  //      getFragment(FragmentProfile.newInstance());
                          break;
                  }
            }
        });

    }
    private void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_menu,fragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectNetwork,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(connectNetwork);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                 openGallery();
            }else {
                Toast.makeText(this,"Vui lòng cho quyền truy cập",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void openGallery(){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intentActivityResultLauncher.launch(Intent.createChooser(intent,"Chọn ảnh"));
    }
}