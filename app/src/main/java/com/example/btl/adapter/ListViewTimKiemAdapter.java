package com.example.btl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.btl.R;

import java.util.ArrayList;
import java.util.List;

import com.example.btl.model.MayAnh;

public class ListViewTimKiemAdapter extends BaseAdapter implements Filterable {

    List<MayAnh> mayAnhList;
    List<MayAnh> mayAnhListTimKiem;
    Context context;

    public ListViewTimKiemAdapter(List<MayAnh> mayAnhList,List<MayAnh> mayAnhListTimKiem, Context context) {
        this.mayAnhList = mayAnhList;
        this.mayAnhListTimKiem = mayAnhListTimKiem;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mayAnhList.size();
    }

    @Override
    public Object getItem(int position) {
        return mayAnhList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_custom_lvtimkiem,parent,false);

        TextView nameHang = (TextView) view.findViewById(R.id.nameHang);

        MayAnh mayAnh = mayAnhList.get(position);

        nameHang.setText(mayAnh.getName());

        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
               //   List<MayAnh> mayAnhListTimKiem = new ArrayList<>();
               mayAnhListTimKiem = mayAnhList;
                }else {
                    List<MayAnh> list = new ArrayList<>();
                    for (MayAnh mayAnh : mayAnhList){
                         if(mayAnh.getName().toLowerCase().contains(strSearch.toLowerCase())){
                             list.add(mayAnh);
                         }
                    }
                    mayAnhListTimKiem = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mayAnhListTimKiem;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                 mayAnhListTimKiem = (List<MayAnh>) results.values;
                 notifyDataSetChanged();
            }
        };
    }
}
