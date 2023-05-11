package com.example.btl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example.btl.model.Cart;

public class AdapterListSanPhamHoaDon extends BaseAdapter {
    Context context;
    ArrayList<Cart> cartArrayList;

    public AdapterListSanPhamHoaDon(Context context, ArrayList<Cart> cartArrayList) {
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
        ImageView anhSanPhamHoaDon;
        public TextView tenSanPhamHoaDon,giaSanPhamHoaDon,tvSoLuongListHoaDon;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_san_pham_trong_hoa_don,null);
            viewHolder.anhSanPhamHoaDon = (ImageView) view.findViewById(R.id.anhSanPhamHoaDon);
            viewHolder.tenSanPhamHoaDon = (TextView) view.findViewById(R.id.tenSanPhamHoaDon);
            viewHolder.giaSanPhamHoaDon = (TextView) view.findViewById(R.id.giaSanPhamHoaDon);
            viewHolder.tvSoLuongListHoaDon = (TextView) view.findViewById(R.id.tvSoLuongListHoaDon);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        Cart cart = (Cart) getItem(position);
        viewHolder.tenSanPhamHoaDon.setText(cart.getName());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        viewHolder.giaSanPhamHoaDon.setText(decimalFormat.format(cart.getPrice())+" đ");
        Picasso.with(context).load(cart.getAvatar())
                .placeholder(R.drawable.quangcao4)
                .error(R.drawable.quangcao5)
                .into(viewHolder.anhSanPhamHoaDon);
        viewHolder.tvSoLuongListHoaDon.setText("Số lượng : " + cart.getSoLuong());
        return view;
    }
}
