package com.example.btl.activiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.btl.model.User;

public class RegisterActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_register);
        initView();

        binding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    void initView(){
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }
    private void register() {
        String username, password,password2,name,phone,email,adress;

        username = binding.eUser.getText().toString();
        password = binding.ePassword.getText().toString();
        password2 = binding.ePassword2.getText().toString();
        name = binding.eFullname.getText().toString();
        phone = binding.ePhone.getText().toString();
        adress = binding.eAddress.getText().toString();
        if(TextUtils.isEmpty(username) ||
                TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(password2) ||
                TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(adress)){
            binding.txtReponse.setText("Vui lòng nhập đủ thông tin !");
            return;
        }
        progressDialog.show();
        if(password.length() > 5) {
            if (password.equals(password2) == true) {
                mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            User user = new User(username,password,name,phone,adress);

                            firebaseDatabase =FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference();
                            
                            String id = task.getResult().getUser().getUid();

                            databaseReference.child("users").child(id).child("information").setValue(user);
                            openConfirmRegister(Gravity.CENTER);
                    //        startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Không thành công ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                binding.txtReponse.setText("Không khớp mật khẩu");
                return;
            }
        }else {
            binding.txtReponse.setText("Mật khẩu quá ngắn");
            return;
        }
    }

    private void openConfirmRegister(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_regiter_successfull);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        Button btLogin = dialog.findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }
}