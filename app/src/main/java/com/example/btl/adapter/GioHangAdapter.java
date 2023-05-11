package com.example.btl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl.R;
import com.example.btl.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example.btl.activiti.CartActivity;
import com.example.btl.activiti.MainActivity;

public class GioHangAdapter extends BaseAdapter {

    CartActivity context;
    ArrayList<Cart> cartArrayList;
    int sl;
    int soLuongMoi,soLuongHienTai;
    int giaHienTai;
    int giaMoiNhat;

    public GioHangAdapter(CartActivity context, ArrayList<Cart> cartArrayList) {
        this.context = context;
        this.cartArrayList = cartArrayList;
    }

    @Override
    public int getCount() {
        return cartArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
      public  ImageView anhSanPhamGioHang;
      public  TextView tenSanPhamGioHang,giaSanPhamGioHang,tvSoLuongListGioHang;
      public Button btnThem,btnBot;
      public ImageButton huySanPham;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.giohang_listview_custom,null);

            viewHolder.anhSanPhamGioHang = (ImageView) view.findViewById(R.id.anhSanPhamGioHang);
            viewHolder.tenSanPhamGioHang = (TextView) view.findViewById(R.id.tenSanPhamGioHang);
            viewHolder.giaSanPhamGioHang = (TextView) view.findViewById(R.id.giaSanPhamGioHang);
            viewHolder.tvSoLuongListGioHang = (TextView) view.findViewById(R.id.tvSoLuongListGioHang);
            viewHolder.btnThem = (Button) view.findViewById(R.id.btnThem);
            viewHolder.btnBot = (Button) view.findViewById(R.id.btnBot);
            viewHolder.huySanPham = (ImageButton) view.findViewById(R.id.huySanPham);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        Cart cart = (Cart) getItem(position);
        viewHolder.tenSanPhamGioHang.setText(cart.getName());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        viewHolder.giaSanPhamGioHang.setText(decimalFormat.format(cart.getPrice())+" đ");
        Picasso.with(context).load(cart.getAvatar())
                .placeholder(R.drawable.quangcao4)
                .error(R.drawable.quangcao5)
                .into(viewHolder.anhSanPhamGioHang);
        viewHolder.tvSoLuongListGioHang.setText(cart.getSoLuong() + "");

        sl = Integer.parseInt(viewHolder.tvSoLuongListGioHang.getText().toString());
        if(sl <= 1){
            viewHolder.btnBot.setVisibility(View.INVISIBLE);
            viewHolder.btnThem.setVisibility(View.VISIBLE);
        }else if(sl > 1 ){
            viewHolder.btnBot.setVisibility(View.VISIBLE);
            viewHolder.btnThem.setVisibility(View.VISIBLE);
        }
        ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 soLuongMoi = Integer.parseInt(finalViewHolder.tvSoLuongListGioHang.getText().toString()) + 1;
                 soLuongHienTai = MainActivity.cartArrayList.get(position).getSoLuong();
                 giaHienTai = MainActivity.cartArrayList.get(position).getPrice();
                 MainActivity.cartArrayList.get(position).setSoLuong(soLuongMoi);
                 giaMoiNhat = (giaHienTai*soLuongMoi) /soLuongHienTai;
                 MainActivity.cartArrayList.get(position).setPrice(giaMoiNhat);
                 DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
                 finalViewHolder.giaSanPhamGioHang.setText(decimalFormat.format(giaMoiNhat)+" đ");
                 context.capNhapSlGioHang(cart.getId(),soLuongMoi,giaMoiNhat);
                 CartActivity.doDuLieu();
                 if(soLuongMoi <= 1 ) {
                     finalViewHolder.btnBot.setVisibility(View.INVISIBLE);
                     finalViewHolder.btnThem.setVisibility(View.VISIBLE);
                     finalViewHolder.tvSoLuongListGioHang.setText(String.valueOf(soLuongMoi));
                 }else {
                     finalViewHolder.btnBot.setVisibility(View.VISIBLE);
                     finalViewHolder.btnThem.setVisibility(View.VISIBLE);
                     finalViewHolder.tvSoLuongListGioHang.setText(String.valueOf(soLuongMoi));
                 }
            }
        });
        viewHolder.btnBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLuongMoi = Integer.parseInt(finalViewHolder.tvSoLuongListGioHang.getText().toString()) -1;
                soLuongHienTai = MainActivity.cartArrayList.get(position).getSoLuong();
                giaHienTai = MainActivity.cartArrayList.get(position).getPrice();
                MainActivity.cartArrayList.get(position).setSoLuong(soLuongMoi);
                giaMoiNhat = (giaHienTai*soLuongMoi) /soLuongHienTai;
                MainActivity.cartArrayList.get(position).setPrice(giaMoiNhat);
                DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
                finalViewHolder.giaSanPhamGioHang.setText(decimalFormat.format(giaMoiNhat)+" đ");
                context.capNhapSlGioHang(cart.getId(),soLuongMoi,giaMoiNhat);
                CartActivity.doDuLieu();
                finalViewHolder.tvSoLuongListGioHang.setText(String.valueOf(soLuongMoi));
                if(soLuongMoi <= 1 ) {
                    finalViewHolder.btnBot.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnThem.setVisibility(View.VISIBLE);
                    finalViewHolder.tvSoLuongListGioHang.setText(String.valueOf(soLuongMoi));
                }else {
                    finalViewHolder.btnBot.setVisibility(View.VISIBLE);
                    finalViewHolder.btnThem.setVisibility(View.VISIBLE);
                    finalViewHolder.tvSoLuongListGioHang.setText(String.valueOf(soLuongMoi));
                }
            }
        });
        viewHolder.huySanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //     MainActivity.gioHangArrayList.remove(position);
                 context.XoaDSGioHang(cart.getId());
                 notifyDataSetChanged();
                 CartActivity.doDuLieu();
            }
        });
        return view;
    }
}
