package com.example.btl.activiti.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.btl.R;

import com.example.btl.activiti.MainActivity;
import com.example.btl.adapter.DanhSachHoaDonAdapter;

public class DonHangDaMua extends AppCompatActivity {

    ImageButton btnBackLichSu;
    RecyclerView rcvDonHangDaMua;
    TextView tvThongBaoDHDM,tvKeoSang;
    DanhSachHoaDonAdapter danhSachHoaDonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_da_mua);

        btnBackLichSu = findViewById(R.id.btnBackLichSu);
        rcvDonHangDaMua = findViewById(R.id.rcvDonHangDaMua);
        tvThongBaoDHDM = findViewById(R.id.tvThongBaoDHDM);
        tvKeoSang = findViewById(R.id.tvKeoSang);

        btnBackLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 finish();
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1, RecyclerView.HORIZONTAL, false);
     //   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvDonHangDaMua.setLayoutManager(layoutManager);
        danhSachHoaDonAdapter = new DanhSachHoaDonAdapter();
        danhSachHoaDonAdapter.setDanhSachHoaDonAdapter(MainActivity.hoaDonArrayList,getApplicationContext());
        rcvDonHangDaMua.setAdapter(danhSachHoaDonAdapter);

        checkMuaDonNaoChua();
    }

    private void checkMuaDonNaoChua() {
        if(MainActivity.hoaDonArrayList.size() <= 0){
            danhSachHoaDonAdapter.notifyDataSetChanged();
            tvThongBaoDHDM.setVisibility(View.VISIBLE);
            tvKeoSang.setVisibility(View.INVISIBLE);
            rcvDonHangDaMua.setVisibility(View.INVISIBLE);
        }else{
            danhSachHoaDonAdapter.notifyDataSetChanged();
            tvThongBaoDHDM.setVisibility(View.INVISIBLE);
            tvKeoSang.setVisibility(View.VISIBLE);
            rcvDonHangDaMua.setVisibility(View.VISIBLE);
        }
    }
}