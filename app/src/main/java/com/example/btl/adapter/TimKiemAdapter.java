package com.example.btl.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.btl.activiti.ProductDetail;
import com.example.btl.model.MayAnh;

public class TimKiemAdapter extends RecyclerView.Adapter<TimKiemAdapter.SearchViewHolder> implements Filterable {

    private List<MayAnh> mayAnhList;
    private List<MayAnh> mayAnhListOld;
    Context context;

    public TimKiemAdapter(List<MayAnh> mayAnhList,Context context){
        this.mayAnhList = mayAnhList;
        this.mayAnhListOld = mayAnhList;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
          MayAnh mayAnh = mayAnhList.get(position);
          if (mayAnh == null){
              return;
          }

        holder.tenTimKiem.setText(mayAnh.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giaTimKiem.setText("Giá:" + decimalFormat.format(mayAnh.getPrice()) + " đ ");
        Picasso.with(context).load(mayAnh.getAvatar())
                .placeholder(R.drawable.quangcao1)
                .error(R.drawable.quangcao2)
                .into(holder.imgTimKiem);
    }

    @Override
    public int getItemCount() {
        if (mayAnhList != null){
            return mayAnhList.size();
        }
        return 0;
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder{

        ImageView imgTimKiem;
        TextView tenTimKiem,giaTimKiem;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTimKiem = itemView.findViewById(R.id.imgTimKiem);
            tenTimKiem = itemView.findViewById(R.id.tenTimKiem);
            giaTimKiem = itemView.findViewById(R.id.giaTimKiem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetail.class);
                    intent.putExtra("thongtinsanpham",mayAnhList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if(search.isEmpty()){
                    mayAnhList = mayAnhListOld;
                }else {
                    List<MayAnh> list = new ArrayList<>();
                    for(MayAnh mayAnh : mayAnhListOld){
                        if(mayAnh.getName().toLowerCase().contains(search.toLowerCase())){
                            list.add(mayAnh);
                        }
                    }
                    mayAnhList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mayAnhList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                 mayAnhList = (List<MayAnh>) results.values;
                 notifyDataSetChanged();
            }
        };
    }
}
