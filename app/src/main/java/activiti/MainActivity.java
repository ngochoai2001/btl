package activiti;

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

import com.example.projectcuoikhoa.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.io.IOException;
import java.util.ArrayList;

import activiti.Profile.UpdateAvatar;
import fragment.FragmentChat;
import fragment.FragmentProfile;
import fragment.FragmentSearch;
import fragment.FragmentThongBao;
import fragment.Fragment_Homee;
import model.GioHang;
import model.HoaDon;
import model.MayAnh;
import model.Message;
import model.ThongBao;

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
    public static ArrayList<GioHang> gioHangArrayList;
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
        if(gioHangArrayList != null){

        }else{
            gioHangArrayList = new ArrayList<>();
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



        getFragment(Fragment_Homee.newInstance());
        menu_navi.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                  switch (item.getItemId()){
                      case R.id.menuHome :
                          getFragment(Fragment_Homee.newInstance());
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