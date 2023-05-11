package com.example.btl.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import com.example.btl.activiti.ProductDetail;
import com.example.btl.model.MayAnh;

public class MayAnhAdapter extends RecyclerView.Adapter<MayAnhAdapter.ViewHoder> {
    List<MayAnh> mayAnhList;
    Context context;
    ImageView avatarMayAnh2;
    TextView nameMayAnh2,giaMayAnh2;

    public MayAnhAdapter(List<MayAnh> mayAnhList, Context context) {
        this.mayAnhList = mayAnhList;
        this.context = context;
    }

    @NonNull
    @Override
    public MayAnhAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_custom_sanphamgoiy2,parent,false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull MayAnhAdapter.ViewHoder holder, int position) {
        MayAnh mayAnh = mayAnhList.get(position);
        nameMayAnh2.setText(mayAnh.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giaMayAnh2.setText("Giá:" + decimalFormat.format(mayAnh.getPrice()) + " đ ");
        Picasso.with(context).load(mayAnh.getAvatar())
                .placeholder(R.drawable.quangcao1)
                .error(R.drawable.quangcao2)
                .into(avatarMayAnh2);
    }

    @Override
    public int getItemCount() {
        return mayAnhList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{

        public ViewHoder(@NonNull View itemView){
            super(itemView);
            avatarMayAnh2 =(ImageView) itemView.findViewById(R.id.avatarMayAnh2);
            nameMayAnh2 =(TextView) itemView.findViewById(R.id.nameMayAnh2);
            giaMayAnh2 =(TextView) itemView.findViewById(R.id.giaMayAnh2);
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
}
