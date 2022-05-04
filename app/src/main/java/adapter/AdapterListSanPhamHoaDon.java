package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcuoikhoa.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.GioHang;

public class AdapterListSanPhamHoaDon extends BaseAdapter {
    Context context;
    ArrayList<GioHang> gioHangArrayList;

    public AdapterListSanPhamHoaDon(Context context, ArrayList<GioHang> gioHangArrayList) {
        this.context = context;
        this.gioHangArrayList = gioHangArrayList;
    }

    @Override
    public int getCount() {
        return gioHangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return gioHangArrayList.get(position);
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
        GioHang gioHang = (GioHang) getItem(position);
        viewHolder.tenSanPhamHoaDon.setText(gioHang.getName());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        viewHolder.giaSanPhamHoaDon.setText(decimalFormat.format(gioHang.getPrice())+" đ");
        Picasso.with(context).load(gioHang.getAvatar())
                .placeholder(R.drawable.quangcao4)
                .error(R.drawable.quangcao5)
                .into(viewHolder.anhSanPhamHoaDon);
        viewHolder.tvSoLuongListHoaDon.setText("Số lượng : " + gioHang.getSoLuong());
        return view;
    }
}
