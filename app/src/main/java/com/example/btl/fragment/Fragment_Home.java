package com.example.btl.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.activiti.OngKinh;
import com.example.btl.model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.example.btl.activiti.CartActivity;
import com.example.btl.activiti.LoaiSanPham.SanPhamHot;
import com.example.btl.activiti.LoaiSanPham.SanPhamMoiNhat;
import com.example.btl.activiti.MainActivity;
import com.example.btl.adapter.SanPhamGoiYAdapter;
import com.example.btl.adapter.ProductAdapter;
import com.example.btl.data.SQLiteHelper;
import com.example.btl.model.MayAnh;
import com.example.btl.model.User;

public class Fragment_Home extends Fragment implements ValueEventListener {

    int[] arrayQuangCao = {R.drawable.quangcao1,
            R.drawable.quangcao2,
            R.drawable.quangcao3,
            R.drawable.quangcao4,
            R.drawable.quangcao5,
            R.drawable.quangcao6};

    ArrayList<MayAnh> mayAnhList;
    String urlStrSPMoi = "https://demo5667093.mockable.io/SanPhamMoi";
    String urlStrSPHot = "https://demo5667093.mockable.io/SanPhamHot";
    String urlStrSPGoiY = "https://demo5667093.mockable.io/SanPhamHot";
    Button btnMenu,btnMayAnh,btnLens;
    ViewFlipper vfQuangCao;
    RecyclerView rvSanPhamMoi,rvsanPhamHot,rvSanPhamGoiY;
    ImageView btnCart;
    TextView tvMainXemSanPhamMoi,tvMainXemSanPhamHot,tvHello;
    View view ;
    TextView soLuongHangTrongGio;
    FirebaseUser firebaseUser;
    SQLiteHelper sqLiteHelper;
    String idAcount;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    public static Fragment_Home newInstance() {

        Bundle args = new Bundle();

        Fragment_Home fragment = new Fragment_Home();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment__home,container,false);

        btnCart = view.findViewById(R.id.btnCart);
        vfQuangCao = view.findViewById(R.id.vfQuangCao);
        rvSanPhamMoi =view.findViewById(R.id.rvSanPhamMoi);
        rvsanPhamHot  =view.findViewById(R.id.rvsanPhamHot);
        rvSanPhamGoiY=view.findViewById(R.id.rvSanPhamGoiY);
        btnMayAnh = view.findViewById(R.id.btnMayAnh);
        btnLens = view.findViewById(R.id.btnLens);
        tvMainXemSanPhamMoi = view.findViewById(R.id.tvMainXemSanPhamMoi);
        soLuongHangTrongGio = view.findViewById(R.id.soLuongHangTrongGio);
        tvMainXemSanPhamHot = view.findViewById(R.id.tvMainXemSanPhamHot);
        tvHello = view.findViewById(R.id.tvHello);
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading, please wait...");

        MainActivity.cartArrayList.clear();
        sqLiteHelper = new SQLiteHelper(view.getContext(),"Data.sqlite1",null,5);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("information");
        databaseReference.addValueEventListener(this);
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
        progressDialog.show();
        quangCao();
        new DataGetProductSPMoi().execute();
        new DataGetProductSPHot().execute();
        new DataGetProductSPGoiY().execute();
        BackProgressDialog();

        mayAnhList = new ArrayList<>();

        int soLuongHang = MainActivity.cartArrayList.size();
        if(soLuongHang < 1){
              soLuongHangTrongGio.setVisibility(View.INVISIBLE);
        }else {
        String sl = String.valueOf(soLuongHang);
        soLuongHangTrongGio.setText(sl);
        }

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(), CartActivity.class);
                startActivity(intent1);
            }
        });
        btnMayAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getContext(), com.example.btl.activiti.MayAnh.class);
                startActivity(intent2);
            }
        });
        btnLens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OngKinh.class);
                startActivity(intent);
            }
        });
        tvMainXemSanPhamMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SanPhamMoiNhat.class);
                startActivity(intent);
            }
        });
        tvMainXemSanPhamHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SanPhamHot.class);
                startActivity(intent);
            }
        });

        return view;
    }
    void quangCao(){
        for(int i = 0 ; i<arrayQuangCao.length;i++){
            ImageView imageView = new ImageView(view.getContext());
            imageView.setImageResource(arrayQuangCao[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            vfQuangCao.addView(imageView);
        }
        vfQuangCao.setFlipInterval(3000);
        vfQuangCao.setAutoStart(true);
        Animation animation_in = AnimationUtils.loadAnimation(getContext(),R.anim.slide_in_right);
        Animation animation_out = AnimationUtils.loadAnimation(getContext(),R.anim.slide_out_right);
        vfQuangCao.setInAnimation(animation_in);
        vfQuangCao.setOutAnimation(animation_out);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        User user = snapshot.getValue(User.class);
        Log.d("kiemtra",user.toString());
        tvHello.setText("Xin chào "+user.getName()+" !");
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    class DataGetProductSPMoi extends AsyncTask<Void,Void,String> {
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url =new URL(urlStrSPMoi);
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
                    if(id > 0 && id < 20) {
                        mayAnhList.add(new MayAnh(id, brand, name, price, amount, avatar));
                    }
                }
                ProductAdapter sanPhamMoiAdapter = new ProductAdapter(mayAnhList,getContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
                rvSanPhamMoi.setLayoutManager(linearLayoutManager);
                rvSanPhamMoi.setAdapter(sanPhamMoiAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    class DataGetProductSPHot extends AsyncTask<Void,Void,String>{
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url =new URL(urlStrSPHot);
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
                    if(id > 0 && id < 20) {
                        mayAnhList.add(new MayAnh(id, brand, name, price, amount, avatar));
                    }
                }
                ProductAdapter sanPhamMoiAdapter = new ProductAdapter(mayAnhList,getContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
                rvsanPhamHot.setLayoutManager(linearLayoutManager);
                rvsanPhamHot.setAdapter(sanPhamMoiAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    class DataGetProductSPGoiY extends AsyncTask<Void,Void,String>{
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url =new URL(urlStrSPGoiY);
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
                    if(id > 0 && id < 20) {
                        mayAnhList.add(new MayAnh(id, brand, name, price, amount, avatar));
                    }
                }
                SanPhamGoiYAdapter sanPhamGoiYAdapter = new SanPhamGoiYAdapter(mayAnhList,getContext());
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),2,RecyclerView.VERTICAL,false);

                rvSanPhamGoiY.setLayoutManager(layoutManager);
                rvSanPhamGoiY.setAdapter(sanPhamGoiYAdapter);
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
