package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projectcuoikhoa.R;

import java.util.ArrayList;

import model.ThongBao;

public class ThongBaoAdapter extends BaseAdapter {

    ArrayList<ThongBao> thongBaoArrayList;

    public ThongBaoAdapter(ArrayList<ThongBao> thongBaoArrayList) {
        this.thongBaoArrayList = thongBaoArrayList;
    }

    @Override
    public int getCount() {
        return thongBaoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return thongBaoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_thong_bao,parent,false);

        TextView tvThongBao = (TextView) view.findViewById(R.id.tvThongBao);
        TextView tvThongBaoNgayGio = (TextView) view.findViewById(R.id.tvThongBaoNgayGio);

        ThongBao thongBao = thongBaoArrayList.get(position);

        tvThongBao.setText(thongBao.getThongBao());
        tvThongBaoNgayGio.setText(thongBao.getNgayGio());

        return view;
    }
}
