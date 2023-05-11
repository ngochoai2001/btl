package com.example.btl.activiti.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btl.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.btl.data.SQLiteHelper;
import com.example.btl.model.User;

public class SuaThongTin extends AppCompatActivity implements ValueEventListener {

    Button btnTroLaiSuaThongTin,btnXacNhanSuaThongTin;
    EditText edTenCu,edPhoneMoi,edAdressMoi;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userID;
    ProgressDialog progressDialog;
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thong_tin);

        btnTroLaiSuaThongTin = findViewById(R.id.btnTroLaiSuaThongTin);
        btnXacNhanSuaThongTin = findViewById(R.id.btnXacNhanSuaThongTin);
        edTenCu = findViewById(R.id.edTenCu);
        edPhoneMoi = findViewById(R.id.edPhoneMoi);
        edAdressMoi = findViewById(R.id.edAdressMoi);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading, please wait...");

        btnTroLaiSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 finish();
            }
        });

        sqLiteHelper = new SQLiteHelper(this,"Data.sqlite1",null,5);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("information");
        databaseReference.addValueEventListener(this);

        btnXacNhanSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if(firebaseUser == null){
                    return;
                }else {
                    firebaseDatabase.getReference().child("users").child(userID).child("information").child("name").setValue(edTenCu.getText().toString());
                    firebaseDatabase.getReference().child("users").child(userID).child("information").child("phone").setValue(edPhoneMoi.getText().toString());
                    firebaseDatabase.getReference().child("users").child(userID).child("information").child("adress").setValue(edAdressMoi.getText().toString());
                    Toast.makeText(getApplicationContext(), "Đổi thành công", Toast.LENGTH_SHORT).show();
        //            MainActivity.thongBaoArrayList.add(0,new ThongBao("Bạn vừa đổi thông tin thành công !",ngay()));
                    String title = "Bạn vừa đổi thông tin thành công !";
                    String ngayT = ngay();
                    sqLiteHelper.QueryData("INSERT INTO ThongBao VALUES(null,'"+userID+"','"+title+"','"+ngayT+"')");
                    finish();
                }
            }
        });

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        User user = snapshot.getValue(User.class);
        edTenCu.setText(user.getName());
        edPhoneMoi.setText(user.getPhone());
        edAdressMoi.setText(user.getAdress());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
    public String ngay(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        String ngay = dateFormat.format(cal.getTime());
        return ngay;
    }
}