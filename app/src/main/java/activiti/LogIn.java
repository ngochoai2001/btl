package activiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcuoikhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import activiti.Profile.SuaThongTin;
import activiti.VanDeVeLogIn.ResetPassword;
import fragment.FragmentProfile;
import model.User;

public class LogIn extends AppCompatActivity {

    Button btnDangNhap;
    TextView loginBtnDangKi,tvQuenMatKhau;
    EditText edTaiKhoan,edMatKhau;
    CheckBox cbcheckbox;
    FirebaseAuth mAuth;
    TextView tvThongBaoDangNhap;
    FragmentProfile fragmentProfile;
    FragmentTransaction fragmentTransaction;
    Bundle bundle;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        anhXa();
        progressDialog.setMessage("Loading, please wait...");
        
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        loginBtnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseContext(),DangKi.class);
                startActivity(intent1);
            }
        });
        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ResetPassword.class);
                startActivity(intent);
            }
        });

    }
    void anhXa(){
        btnDangNhap = findViewById(R.id.btnDangNhap);
        loginBtnDangKi = findViewById(R.id.login_btnDangKi);
        edTaiKhoan = findViewById(R.id.edTaiKhoan);
        edMatKhau = findViewById(R.id.edMatKhau);
        cbcheckbox = findViewById(R.id.cbcheckbox);
        mAuth = FirebaseAuth.getInstance();
        tvThongBaoDangNhap = findViewById(R.id.tvThongBaoDangNhap);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);
        progressDialog = new ProgressDialog(this);
    }
    private void logIn(){
        String user,password;
        user = edTaiKhoan.getText().toString();
        password = edMatKhau.getText().toString();

        if(TextUtils.isEmpty(user)){
               tvThongBaoDangNhap.setText("Vui lòng nhập tài khoản !");
               return;
        }
        if(TextUtils.isEmpty(password)){
            tvThongBaoDangNhap.setText("Vui lòng nhập mật khẩu !");
            return;
        }
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(user,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(intent);
                }else{
                    tvThongBaoDangNhap.setText("Sai email hoặc mật khẩu!");
                }
            }
        });
    }
}