package com.example.btl.activiti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.btl.R;
import com.example.btl.activiti.VanDeVeLogIn.HoaDon;
import com.example.btl.model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;

import com.example.btl.adapter.GioHangAdapter;
import com.example.btl.data.SQLiteHelper;

public class CartActivity extends AppCompatActivity {

    Toolbar toolBar;
    ListView lvGioHang;
    static TextView tvTongTien,tvThongBaoGioHang;
    GioHangAdapter gioHangAdapter;
    Button btnDatHang;
    FirebaseUser firebaseUser;
    SQLiteHelper sqLiteHelper;
    String idAcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        sqLiteHelper = new SQLiteHelper(this,"Data.sqlite1",null,5);

        initView();
        getData();
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkData();
        doDuLieu();

        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.cartArrayList.size() > 0){
                    Intent intent = new Intent(getApplicationContext(), HoaDon.class);
                    String chuyenTien = tvTongTien.getText().toString();
                    intent.putExtra("tongTien",chuyenTien);
                    startActivity(intent);
                }else {
                    tvThongBaoGioHang.setText("Chưa chọn sản phẩm !");
                }
            }
        });
    }


    private void initView() {
        toolBar = findViewById(R.id.toolBar);
        lvGioHang = findViewById(R.id.lvGioHang);
        tvTongTien = findViewById(R.id.tvTongTien);
        btnDatHang = findViewById(R.id.btnDatHang);
        tvThongBaoGioHang = findViewById(R.id.tvThongBaoGioHang);
        gioHangAdapter = new GioHangAdapter(CartActivity.this,MainActivity.cartArrayList);
    }
    public void getData() {
        MainActivity.cartArrayList.clear();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        idAcount = firebaseUser.getUid();
        Cursor dataGioHang = sqLiteHelper.GetData("SELECT * FROM GioHang WHERE IdAcount = '"+idAcount+"'");
        while (dataGioHang.moveToNext()){
            int idD = dataGioHang.getInt(2);
            String ten = dataGioHang.getString(3);
            int gia = dataGioHang.getInt(4);
            String avt = dataGioHang.getString(5);
            int sl = dataGioHang.getInt(6);
            MainActivity.cartArrayList.add(0,new Cart(idD,ten,gia,avt,sl));
        }
        lvGioHang.setAdapter(gioHangAdapter);
    }

    public void XoaDSGioHang(int id){
        sqLiteHelper.QueryData("DELETE FROM GioHang WHERE IdGioHang = '"+ id +"' and IdAcount = '"+idAcount+"'");
        getData();
    }

    public void capNhapSlGioHang(int id, int slMoi, int giaMoi){
        sqLiteHelper.QueryData("UPDATE GioHang SET sluong ='"+slMoi+"', giaGH = '"+giaMoi+"' WHERE IdGioHang = '"+ id +"' and IdAcount = '"+idAcount+"'");
        getData();
    }

    private void checkData() {
         if(MainActivity.cartArrayList.size() <= 0){
             gioHangAdapter.notifyDataSetChanged();
             tvThongBaoGioHang.setVisibility(View.VISIBLE);
             lvGioHang.setVisibility(View.INVISIBLE);
         }else{
             gioHangAdapter.notifyDataSetChanged();
             tvThongBaoGioHang.setVisibility(View.INVISIBLE);
             lvGioHang.setVisibility(View.VISIBLE);
         }
    }
    public static void doDuLieu() {
         long tongTien =0;
         for(int i = 0; i<MainActivity.cartArrayList.size() ; i++){
             tongTien += MainActivity.cartArrayList.get(i).getPrice();
         }
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(tongTien)+" đ");
    }
}
