package activiti.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcuoikhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import activiti.LogIn;
import activiti.MainActivity;
import data.SQLiteHelper;
import model.ThongBao;

public class ChangePassword extends AppCompatActivity {

    Button btnTroVe, btnXacNhan;
    EditText edMatKhauCu, edMatKhauMoi, edMatKhauMoi2;
    FirebaseAuth firebaseAuth;
    TextView tvThongBaoDoiPassword;
    ProgressDialog progressDialog;
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btnTroVe = findViewById(R.id.btnTroLai);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        edMatKhauCu = findViewById(R.id.edMatKhauCu);
        edMatKhauMoi = findViewById(R.id.edMatKhauMoi);
        edMatKhauMoi2 = findViewById(R.id.edMatKhauMoi2);
        firebaseAuth = FirebaseAuth.getInstance();
        tvThongBaoDoiPassword = findViewById(R.id.tvThongBaoDoiPassword);
        progressDialog = new ProgressDialog(this);

        sqLiteHelper = new SQLiteHelper(this,"Data.sqlite1",null,5);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matKhauCu = edMatKhauCu.getText().toString();
                String matKhauMoi1 = edMatKhauMoi.getText().toString();
                String matKhauMoi2 = edMatKhauMoi2.getText().toString();

                if(TextUtils.isEmpty(matKhauCu) || TextUtils.isEmpty(matKhauMoi1) || TextUtils.isEmpty(matKhauMoi2)){
                    tvThongBaoDoiPassword.setText("Vui lòng nhập đủ thông tin");
                    return;
                }
                if(matKhauMoi1.length() < 6){
                    tvThongBaoDoiPassword.setText("Mật khẩu quá ngắn");
                    return;
                }
                if(matKhauMoi1.equals(matKhauMoi2) == false) {
                    tvThongBaoDoiPassword.setText("Mật khẩu không khớp");
                    return;
                }


                change(matKhauCu, matKhauMoi1);
             //   Intent intent = new Intent(getApplicationContext(), LogIn.class);
             //   startActivity(intent);
            }
        });

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void change(String old_pass, String new_pass) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userID = user.getUid();
        AuthCredential authCredential =EmailAuthProvider.getCredential(user.getEmail(),old_pass);
        progressDialog.show();
        user.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                          user.updatePassword(new_pass)
                                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                                      @Override
                                      public void onSuccess(Void aVoid) {
                                            String title = "Bạn vừa đổi mật khẩu thành công !";
                                            String ngayT = ngay();
                                            sqLiteHelper.QueryData("INSERT INTO ThongBao VALUES(null,'"+userID+"','"+title+"','"+ngayT+"')");
                                            Toast.makeText(getApplicationContext(),"Đổi thành công vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                                     //       MainActivity.thongBaoArrayList.add(0,new ThongBao("Bạn vừa đổi mật khẩu thành công !",ngay()));
                                            BackProgressDialog();
                                            Intent intent = new Intent(getApplicationContext(),LogIn.class);
                                            startActivity(intent);
                                      }
                                  })
                                  .addOnFailureListener(new OnFailureListener() {
                                      @Override
                                      public void onFailure(@NonNull Exception e) {
                                          tvThongBaoDoiPassword.setText("Không thành công");
                                      }
                                  });
                     }
                })
                 .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                         tvThongBaoDoiPassword.setText("Nhập đúng mật khẩu");
                    }
                });
    }
    public String ngay(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        String ngay = dateFormat.format(cal.getTime());
        return ngay;
    }
    private void BackProgressDialog(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        },2000);
    }
}