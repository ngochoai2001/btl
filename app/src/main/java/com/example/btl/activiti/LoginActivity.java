package com.example.btl.activiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.databinding.ActivityLogInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.btl.activiti.VanDeVeLogIn.ResetPassword;
import com.example.btl.fragment.FragmentProfile;

public class LoginActivity extends AppCompatActivity {
    ActivityLogInBinding binding;

    FirebaseAuth mAuth;
    FragmentProfile fragmentProfile;
    FragmentTransaction fragmentTransaction;
    Bundle bundle;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initView();
        progressDialog.setMessage("Loading, please wait...");

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        binding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent1);
            }
        });
        binding.tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ResetPassword.class);
                startActivity(intent);
            }
        });

    }
    void initView(){
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }
    private void logIn(){
        String user,password;
        user = binding.eUsername.getText().toString();
        password = binding.ePassword.getText().toString();

        if(TextUtils.isEmpty(user)){
            binding.tvLoginResponse.setText("Vui lòng nhập tài khoản !");
               return;
        }
        if(TextUtils.isEmpty(password)){
            binding.tvLoginResponse.setText("Vui lòng nhập mật khẩu !");
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
                    binding.tvLoginResponse.setText("Sai email hoặc mật khẩu!");
                }
            }
        });
    }
}