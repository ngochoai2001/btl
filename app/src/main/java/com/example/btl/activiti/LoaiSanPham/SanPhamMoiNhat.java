package com.example.btl.activiti.LoaiSanPham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.example.btl.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.example.btl.activiti.CartActivity;
import com.example.btl.activiti.Profile.DanhSachYeuThich;
import com.example.btl.adapter.SanPhamGoiYAdapter;
import com.example.btl.model.MayAnh;

public class SanPhamMoiNhat extends AppCompatActivity {

    RecyclerView rvSanPhamMoiNhat2;
    String urlStrSP = "https://demo5667093.mockable.io/SanPhamHot";
    ArrayList<MayAnh> mayAnhList;
    Toolbar toolBarSanPhamMoi;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_moi_nhat);

        rvSanPhamMoiNhat2 = findViewById(R.id.rvSanPhamMoiNhat2);
        toolBarSanPhamMoi = findViewById(R.id.toolBarSanPhamMoi);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading, please wait...");

        setSupportActionBar(toolBarSanPhamMoi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog.show();
        new DataSanPhamMoi().execute();
        BackProgressDialog();

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
                Intent intent1 = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent1);
            case R.id.yeuThich:
                Intent intent2 = new Intent(getApplicationContext(), DanhSachYeuThich.class);
                startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

    class DataSanPhamMoi extends AsyncTask<Void,Void,String>{
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url =new URL(urlStrSP);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();

                int byteChar;

                while((byteChar=is.read()) != -1){
                    result+=(char) byteChar;
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String unused) {
            super.onPostExecute(unused);
            mayAnhList = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(result);
                for (int i=0;i < array.length();i++){
                    JSONObject jsonObject =array.getJSONObject(i);
                    int id =jsonObject.getInt("id");
                    String brand = jsonObject.getString("brand");
                    String name = jsonObject.getString("name");
                    int price =jsonObject.getInt("price");
                    int amount =jsonObject.getInt("amount");
                    String avatar =jsonObject.getString("avatar");
                    mayAnhList.add(new MayAnh(id, brand, name, price, amount, avatar));
                }
                SanPhamGoiYAdapter sanPhamGoiYAdapter = new SanPhamGoiYAdapter(mayAnhList,SanPhamMoiNhat.this);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2,RecyclerView.VERTICAL,false);
                rvSanPhamMoiNhat2.setLayoutManager(layoutManager);
                rvSanPhamMoiNhat2.setAdapter(sanPhamGoiYAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
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