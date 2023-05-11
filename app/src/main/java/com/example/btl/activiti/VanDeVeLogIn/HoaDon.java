package com.example.btl.activiti.VanDeVeLogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.btl.activiti.MainActivity;

import com.example.btl.adapter.AdapterListSanPhamHoaDon;
import com.example.btl.data.SQLiteHelper;
import com.example.btl.model.User;

public class HoaDon extends AppCompatActivity implements ValueEventListener {

    ImageButton btnBackHoaDon;
    EditText tenHoaDon,dienThoaiHoaDon,diaChiHoaDon;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userID;
    TextView tongTienHoaDon;
    ListView lvHoaDon;
    Button btnXacNhanHoaDon;
    ProgressDialog progressDialog;
    Spinner spinnerChonGiaoHang;
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        tenHoaDon = findViewById(R.id.tenHoaDon);
        dienThoaiHoaDon = findViewById(R.id.dienThoaiHoaDon);
        diaChiHoaDon = findViewById(R.id.diaChiHoaDon);
        btnBackHoaDon = findViewById(R.id.btnBackHoaDon);
        tongTienHoaDon = findViewById(R.id.tongTienHoaDon);
        lvHoaDon = findViewById(R.id.lvHoaDon);
        btnXacNhanHoaDon = findViewById(R.id.btnXacNhanHoaDon);
        spinnerChonGiaoHang = findViewById(R.id.spinnerChonGiaoHang);

        sqLiteHelper = new SQLiteHelper(this,"Data.sqlite1",null,5);


        ArrayList<Cart> hangs = MainActivity.cartArrayList;
        AdapterListSanPhamHoaDon adapterListSanPhamHoaDon = new AdapterListSanPhamHoaDon(this, hangs);
        lvHoaDon.setAdapter(adapterListSanPhamHoaDon);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading, please wait...");

        btnBackHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<String> arrayType = new ArrayList<String>();
        arrayType.add("Thanh toán trực tiếp");
    //   arrayType.add("Thẻ ngân hàng");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChonGiaoHang.setAdapter(arrayAdapter);

        spinnerChonGiaoHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("information");
        databaseReference.addValueEventListener(this);
        Intent intent = getIntent();
        String tongTien = intent.getStringExtra("tongTien");
        tongTienHoaDon.setText("Tổng : " + tongTien);

        String tenHD = tenHoaDon.getText().toString().trim();
        String dienThoaiHD = dienThoaiHoaDon.getText().toString().trim();
        String diaChiHD = diaChiHoaDon.getText().toString().trim();

        btnXacNhanHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if(TextUtils.isEmpty(tenHD) || TextUtils.isEmpty(dienThoaiHD) || TextUtils.isEmpty(diaChiHD)){
                      Toast.makeText(getApplicationContext(),"Thiếu thông tin",Toast.LENGTH_LONG).show();
                  }
                  else {
                       openXacNhanThanhToan(Gravity.CENTER);
                       MainActivity.hoaDonArrayList.add(0,new com.example.btl.model.HoaDon(tenHoaDon.getText().toString().trim(),
                       dienThoaiHoaDon.getText().toString().trim(),
                       diaChiHoaDon.getText().toString().trim(), MainActivity.cartArrayList, tongTien,ngay()));
                       String title = "Bạn vừa đặt thành công đơn hàng có giá trị là "+ tongTien +" . Xin cảm ơn đã tin tưởng";
                       String ngayT = ngay();
                       sqLiteHelper.QueryData("INSERT INTO ThongBao VALUES(null,'"+userID+"','"+title+"','"+ngayT+"')");
                       sqLiteHelper.QueryData("DELETE FROM GioHang WHERE IdAcount = '"+userID+"'");

                   //    MainActivity.thongBaoArrayList.add(0,new ThongBao("Bạn vừa đặt thành công đơn hàng có giá trị là " + tongTien +" . Xin cảm ơn đã tin tưởng",ngay()));
                       MainActivity.cartArrayList = null;
                  }
            }
        });
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        User user = snapshot.getValue(User.class);
        tenHoaDon.setText(user.getName());
        dienThoaiHoaDon.setText(user.getPhone());
        diaChiHoaDon.setText(user.getAdress());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    private void openXacNhanThanhToan(int gravity){
         final Dialog dialog = new Dialog(this);
         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
         dialog.setContentView(R.layout.layout_hoa_don);

         Window window = dialog.getWindow();
         if(window == null){
             return;
         }
         window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
         window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

         WindowManager.LayoutParams windowAttributes = window.getAttributes();
         windowAttributes.gravity = gravity;
         window.setAttributes(windowAttributes);

         //check ấn ra ngoài có tắt không
     /*    if(Gravity.BOTTOM == gravity){
             dialog.setCancelable(true);
         }else {
             dialog.setCancelable(false);
         }  */

        Button btnTiepTucMuaSam = dialog.findViewById(R.id.btnTiepTucMuaSam);
        btnTiepTucMuaSam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
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