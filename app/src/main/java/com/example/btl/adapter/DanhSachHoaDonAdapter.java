package com.example.btl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;

import java.util.ArrayList;

import com.example.btl.model.HoaDon;

public class DanhSachHoaDonAdapter extends RecyclerView.Adapter<DanhSachHoaDonAdapter.DanhSachHoaDonViewHolder>{

    private ArrayList<HoaDon> hoaDonList;
    Context context;
    View view;
    AdapterListSanPhamHoaDon adapterListSanPhamHoaDon;

    public void setDanhSachHoaDonAdapter(ArrayList<HoaDon> hoaDonList, Context context) {
        this.hoaDonList = hoaDonList;
        notifyDataSetChanged();
        this.context = context;
    }

    @NonNull
    @Override
    public DanhSachHoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danh_sach_hoa_don,parent,false);
        return new DanhSachHoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachHoaDonViewHolder holder, int position) {
          HoaDon hoaDon = hoaDonList.get(position);
          if(hoaDonList == null){
              return;
          }
          holder.tenTrongDSHD.setText(hoaDon.getName());
          holder.dienThoaiTrongDSHD.setText(hoaDon.getPhone());
          holder.diaChiTrongDSHD.setText(hoaDon.getAdress());
          holder.tongTienTrongDSHD.setText("Tổng: " +hoaDon.getTongTien());
          holder.ngayMuaTrongDSHD.setText("Ngày mua: " +hoaDon.getNgayThang());

          adapterListSanPhamHoaDon = new AdapterListSanPhamHoaDon(context,hoaDon.getGioHangList());
          holder.rcvTrongDSHD.setAdapter(adapterListSanPhamHoaDon);
    }
    @Override
    public int getItemCount() {
        if(hoaDonList != null){
            return hoaDonList.size();
        }
        return 0;
    }
    public  class  DanhSachHoaDonViewHolder extends RecyclerView.ViewHolder{

        TextView tenTrongDSHD,dienThoaiTrongDSHD,diaChiTrongDSHD,tongTienTrongDSHD,ngayMuaTrongDSHD;
        ListView rcvTrongDSHD;

        public DanhSachHoaDonViewHolder(@NonNull View itemView) {
            super(itemView);

            tenTrongDSHD = itemView.findViewById(R.id.tenTrongDSHD);
            dienThoaiTrongDSHD = itemView.findViewById(R.id.dienThoaiTrongDSHD);
            diaChiTrongDSHD = itemView.findViewById(R.id.diaChiTrongDSHD);
            tongTienTrongDSHD = itemView.findViewById(R.id.tongTienTrongDSHD);
            rcvTrongDSHD = itemView.findViewById(R.id.rcvTrongDSHD);
            ngayMuaTrongDSHD = itemView.findViewById(R.id.ngayMuaTrongDSHD);
        }
    }
}

