package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcuoikhoa.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import activiti.Cart;
import activiti.ChiTietSanPham;
import activiti.MainActivity;
import activiti.Profile.DanhSachYeuThich;
import model.MayAnh;

public class AdapterDanhSachYeuThich extends BaseAdapter {

    DanhSachYeuThich context;
    ArrayList<MayAnh> mayAnhArrayList;

    public AdapterDanhSachYeuThich(DanhSachYeuThich context, ArrayList<MayAnh> mayAnhArrayList) {
        this.context = context;
        this.mayAnhArrayList = mayAnhArrayList;
    }

    @Override
    public int getCount() {
        return mayAnhArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mayAnhArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public ImageView anhSanPhamYeuThich;
        public TextView tenSanPhamYeuThich,giaSanPhamYeuThich;
        public ImageButton huySanPhamYeuThich;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.danhsachyeuthich_custom_listview,null);

            viewHolder.anhSanPhamYeuThich = view.findViewById(R.id.anhSanPhamYeuThich);
            viewHolder.tenSanPhamYeuThich = view.findViewById(R.id.tenSanPhamYeuThich);
            viewHolder.giaSanPhamYeuThich = view.findViewById(R.id.giaSanPhamYeuThich);
            viewHolder.huySanPhamYeuThich = view.findViewById(R.id.huySanPhamYeuThich);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        MayAnh mayAnh = (MayAnh) getItem(position);
        viewHolder.tenSanPhamYeuThich.setText(mayAnh.getName());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        viewHolder.giaSanPhamYeuThich.setText(decimalFormat.format(mayAnh.getPrice())+" đ");
        Picasso.with(context).load(mayAnh.getAvatar())
                .placeholder(R.drawable.quangcao4)
                .error(R.drawable.quangcao5)
                .into(viewHolder.anhSanPhamYeuThich);
        viewHolder.huySanPhamYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    MainActivity.danhSachYeuThichList.remove(position);
                context.XoaDSYeuThich(mayAnh.getId());
                notifyDataSetChanged();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mayAnhArrayList.get(position));
                context.startActivity(intent);
            }
        });
        return view;
    }
}
