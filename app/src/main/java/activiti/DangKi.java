package activiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcuoikhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fragment.FragmentProfile;
import model.User;

public class DangKi extends AppCompatActivity {

    Button dangkiBtnDangKi;
    EditText dkTaiKhoan,dkMatKhau,dkMatKhau2,userName,userPhone,userAdress;
    TextView tvThongBaoDangKi;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_dang_ki);
        anhXa();

        dangkiBtnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKi();
            }
        });
    }

    void anhXa(){
        dangkiBtnDangKi = findViewById(R.id.dangki_btnDangKi);
        dkTaiKhoan = findViewById(R.id.dkTaiKhoan);
        dkMatKhau  = findViewById(R.id.dkMatKhau);
        dkMatKhau2 = findViewById(R.id.dkMatKhau2);
        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        userAdress = findViewById(R.id.userAdress);
        tvThongBaoDangKi = findViewById(R.id.tvThongBaoDangKi);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }
    private void dangKi() {
        String dkuser, dkpassword,dkpassword2,name,phone,email,adress;
        dkuser = dkTaiKhoan.getText().toString();
        dkpassword = dkMatKhau.getText().toString();
        dkpassword2 = dkMatKhau2.getText().toString();
        name = userName.getText().toString();
        phone = userPhone.getText().toString();
        adress = userAdress.getText().toString();
        if(TextUtils.isEmpty(dkuser) ||
                TextUtils.isEmpty(dkpassword) ||
                TextUtils.isEmpty(dkpassword2) ||
                TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(adress)){
            tvThongBaoDangKi.setText("Vui lòng nhập đủ thông tin !");
            return;
        }
        progressDialog.show();
        if(dkpassword.length() > 5) {
            if (dkpassword.equals(dkpassword2) == true) {
                mAuth.createUserWithEmailAndPassword(dkuser, dkpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), LogIn.class);
                            User user = new User(dkuser,dkpassword,name,phone,adress);

                            firebaseDatabase =FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference();
                            
                            String id = task.getResult().getUser().getUid();

                            databaseReference.child("users").child(id).child("information").setValue(user);
                            openXacNhanDangKi(Gravity.CENTER);
                    //        startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Không thành công ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                tvThongBaoDangKi.setText("Không khớp mật khẩu");
                return;
            }
        }else {
            tvThongBaoDangKi.setText("Mật khẩu quá ngắn");
            return;
        }
    }

    private void openXacNhanDangKi(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dang_ki_thanh_cong);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        Button btnVaoDangNhap = dialog.findViewById(R.id.btnVaoDangNhap);
        btnVaoDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LogIn.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }
}