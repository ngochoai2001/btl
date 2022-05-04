package activiti.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.projectcuoikhoa.R;

import java.util.ArrayList;
import java.util.List;

import activiti.MainActivity;
import adapter.DanhSachHoaDonAdapter;
import model.HoaDon;

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