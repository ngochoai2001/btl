package activiti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.projectcuoikhoa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import data.SQLiteHelper;

public class SplashActivity extends AppCompatActivity {

    SQLiteHelper sqLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },1500);

        sqLiteHelper = new SQLiteHelper(this,"Data.sqlite1",null,5);
        sqLiteHelper.QueryData("CREATE TABLE IF NOT EXISTS TinNhan2(Id INTEGER PRIMARY KEY AUTOINCREMENT,IdAcount VARCHAR(100), mesage NVARCHAR(200))");
        sqLiteHelper.QueryData("CREATE TABLE IF NOT EXISTS ThongBao(Id INTEGER PRIMARY KEY AUTOINCREMENT,IdAcount VARCHAR(100),tin NVARCHAR(200),ngay NVARCHAR(100))");
        sqLiteHelper.QueryData("CREATE TABLE IF NOT EXISTS DanhSachYeuThich(Id INTEGER PRIMARY KEY AUTOINCREMENT,IdAcount VARCHAR(100),IdMayAnh INTEGER,hang NVARCHAR(100),ten NVARCHAR(200),gia INTEGER,soluong INTEGER, avt NVARCHAR(200))");
        sqLiteHelper.QueryData("CREATE TABLE IF NOT EXISTS GioHang(Id INTEGER PRIMARY KEY AUTOINCREMENT,IdAcount VARCHAR(100),IdGioHang INTEGER,tenGH NVARCHAR(200),giaGH INTEGER,avtGH NVARCHAR(200),sluong INTEGER)");
    }

    private void nextActivity(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent intent = new Intent(this,LogIn.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}