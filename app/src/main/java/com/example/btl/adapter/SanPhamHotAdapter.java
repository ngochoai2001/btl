package com.example.btl.adapter;

import android.content.Context;
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

import com.example.btl.model.MayAnh;

public class SanPhamHotAdapter extends RecyclerView.Adapter<SanPhamHotAdapter.ViewHoder> {

    List<MayAnh> mayAnhList;
    ImageView avatarMayAnh1;
    TextView nameMayAnh1,giaMayAnh1;
    Context context;

    public  SanPhamHotAdapter(List<MayAnh> mayAnhList,Context context){
        this.mayAnhList  = mayAnhList;
        this.context = context;
    }

    @NonNull
    @Override
    public SanPhamHotAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_custom_sanphamhot,parent,false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamHotAdapter.ViewHoder holder, int position) {
        MayAnh mayAnh = mayAnhList.get(position);


            nameMayAnh1.setText(mayAnh.getName());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            giaMayAnh1.setText("Giá:" + decimalFormat.format(mayAnh.getPrice()) + " đ ");
            Picasso.with(context).load(mayAnh.getAvatar())
                    .placeholder(R.drawable.quangcao1)
                    .error(R.drawable.quangcao2)
                    .into(avatarMayAnh1);
            System.out.println(mayAnh.getAvatar() + mayAnh.getId());
    }

    @Override
    public int getItemCount() {
        return mayAnhList.size();
    }
    public class ViewHoder extends RecyclerView.ViewHolder{

        public ViewHoder(@NonNull View itemView){
            super(itemView);
            avatarMayAnh1 =(ImageView) itemView.findViewById(R.id.avatarMayAnh1);
            nameMayAnh1 =(TextView) itemView.findViewById(R.id.nameMayAnh1);
            giaMayAnh1 =(TextView) itemView.findViewById(R.id.giaMayAnh1);
        }
    }
}
