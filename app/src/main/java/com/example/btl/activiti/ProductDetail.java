package com.example.btl.activiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import com.example.btl.activiti.Profile.DanhSachYeuThich;
import com.example.btl.data.SQLiteHelper;
import com.example.btl.model.MayAnh;

public class ProductDetail extends AppCompatActivity {
    Toolbar toolBarChiTietSanPham;
    TextView chitietTenSP,chitietGiaSP;
    ImageView chitietAnhSP;
    Button btnThemVaoGio,btnMuaHang;
    ImageView btnDown,btnUp;
    TextView tvSoLuong;
    int soLuong = 1;
    String soluong;
    ImageButton btnThemVaoDSYThich;
    FirebaseUser firebaseUser;
    SQLiteHelper sqLiteHelper;
    String idAcount;

    int id=0;
    String brand;
    String name;
    int price;
    int amount;
    String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        sqLiteHelper = new SQLiteHelper(this,"Data.sqlite1",null,5);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        idAcount = firebaseUser.getUid();

        initView();
        getInformation();
        DieuChinhSoLuong();
        EventButton();

        setSupportActionBar(toolBarChiTietSanPham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initView() {
        toolBarChiTietSanPham = findViewById(R.id.toolBarChiTietSanPham);
        chitietTenSP = findViewById(R.id.chitietTenSP);
        chitietGiaSP = findViewById(R.id.chitietGiaSP);
        chitietAnhSP = findViewById(R.id.chitietAnhSP);
        btnThemVaoGio = findViewById(R.id.btnThemVaoGio);
        btnMuaHang = findViewById(R.id.btnMuaHang);
        btnDown = findViewById(R.id.btnDown);
        btnUp = findViewById(R.id.btnUp);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        btnThemVaoDSYThich = findViewById(R.id.btnThemVaoDSYThich);
    }
    private void getInformation() {
        MayAnh mayAnh = (MayAnh) getIntent().getSerializableExtra("thongtinsanpham");
        id = mayAnh.getId();
        brand = mayAnh.getBrand();
        name =mayAnh.getName();
        price = mayAnh.getPrice();
        amount=mayAnh.getAmount();
        avatar=mayAnh.getAvatar();

        chitietTenSP.setText(name);
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        chitietGiaSP.setText(decimalFormat.format(price)+" đ");
        Picasso.with(getApplicationContext()).load(avatar)
                .placeholder(R.drawable.quangcao4)
                .error(R.drawable.quangcao5)
                .into(chitietAnhSP);
    }

    private void EventButton() {
        btnThemVaoGio.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                   if(MainActivity.cartArrayList.size() > 0){
                       int sl = soLuong;
                       boolean exists = false;
                       for (int i = 0; i < MainActivity.cartArrayList.size() ; i++){
                           if(MainActivity.cartArrayList.get(i).getId() == id){
                               MainActivity.cartArrayList.get(i).setSoLuong(MainActivity.cartArrayList.get(i).getSoLuong() +sl);
                               MainActivity.cartArrayList.get(i).setPrice(price * MainActivity.cartArrayList.get(i).getSoLuong());
                               exists = true;
                           }
                       }
                       if (exists == false){
                           int SoLuong = soLuong;
                           int giaMoi = SoLuong * price;
                           sqLiteHelper.QueryData("INSERT INTO GioHang VALUES(null,'"+ idAcount +"','"+ id +"','"+ name +"','"+ giaMoi +"','"+ avatar +"','"+ SoLuong +"')");
                       }

                   }else{
                        int SoLuong = soLuong;
                        int giaMoi = SoLuong * price;
                        sqLiteHelper.QueryData("INSERT INTO GioHang VALUES(null,'"+ idAcount +"','"+ id +"','"+ name +"','"+ giaMoi +"','"+ avatar +"','"+ SoLuong +"')");
                   }
                  Toast.makeText(ProductDetail.this,"Đã thêm vào giỏ hàng",Toast.LENGTH_SHORT).show();
              }
          });
        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.cartArrayList.size() > 0){
                    int sl = soLuong;
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.cartArrayList.size() ; i++){
                        if(MainActivity.cartArrayList.get(i).getId() == id){
                            MainActivity.cartArrayList.get(i).setSoLuong(MainActivity.cartArrayList.get(i).getSoLuong() +sl);
                            MainActivity.cartArrayList.get(i).setPrice(price * MainActivity.cartArrayList.get(i).getSoLuong());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int SoLuong = soLuong;
                        int giaMoi = SoLuong * price;
                        sqLiteHelper.QueryData("INSERT INTO GioHang VALUES(null,'"+ idAcount +"','"+ id +"','"+ name +"','"+ giaMoi +"','"+ avatar +"','"+ SoLuong +"')");
                      //  MainActivity.gioHangArrayList.add(new GioHang(id,name,price,avatar,SoLuong));
                    }

                }else{
                    int SoLuong = soLuong;
                    int giaMoi = SoLuong * price;
                    sqLiteHelper.QueryData("INSERT INTO GioHang VALUES(null,'"+ idAcount +"','"+ id +"','"+ name +"','"+ giaMoi +"','"+ avatar +"','"+ SoLuong +"')");
                   // MainActivity.gioHangArrayList.add(new GioHang(id,name,price,avatar,SoLuong));
                }
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        btnThemVaoDSYThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.danhSachYeuThichList.size() > 0) {
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.danhSachYeuThichList.size(); i++) {
                        if (MainActivity.danhSachYeuThichList.get(i).getId() == id) {
                            exists = true;
                        }
                    }
                    if (exists == false) {
                        sqLiteHelper.QueryData("INSERT INTO DanhSachYeuThich VALUES(null,'"+ idAcount +"','"+ id +"','"+ brand +"','"+ name +"','"+ price +"','"+ amount +"','"+ avatar +"')");
      //                  MainActivity.danhSachYeuThichList.add(0,new MayAnh(id, brand, name, price, amount, avatar));
                    }
                } else {
       //             MainActivity.danhSachYeuThichList.add(0,new MayAnh(id, brand, name, price, amount, avatar));
                     sqLiteHelper.QueryData("INSERT INTO DanhSachYeuThich VALUES(null,'"+ idAcount +"','"+ id +"','"+ brand +"','"+ name +"','"+ price +"','"+ amount +"','"+ avatar +"')");
                }
                Toast.makeText(ProductDetail.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
            }
        });
    }

     private void DieuChinhSoLuong(){
          soluong = String.valueOf(soLuong);
          tvSoLuong.setText(soluong);
          btnDown.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(soLuong>2) {
                      soLuong = soLuong - 1;
                      soluong = String.valueOf(soLuong);
                      tvSoLuong.setText(soluong);
                  }else{
                      tvSoLuong.setText("0");
                  }
              }
          });
          btnUp.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  soLuong = soLuong + 1;
                  soluong = String.valueOf(soLuong);
                  tvSoLuong.setText(soluong);
              }
          });
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_co_gio_hang,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
                break;
            case  R.id.yeuThich:
                Intent intent1 = new Intent(getApplicationContext(),DanhSachYeuThich.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}