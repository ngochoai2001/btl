package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcuoikhoa.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import activiti.ChiTietSanPham;
import activiti.MainActivity;
import model.MayAnh;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHoder> {

    List<MayAnh> mayAnhList;
    TextView nameMayAnh,giaMayAnh;
    ImageView avatarMayAnh;
    Context context;
  //  ImageButton btThemVaoDSYT;

    int id=0;
    String brand;
    String name;
    int price;
    int amount;
    String avatar;


    public SanPhamAdapter(List<MayAnh> mayAnhList, Context context){
        this.mayAnhList = mayAnhList;
        this.context = context;
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_custom_sanphammoi,parent,false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.ViewHoder holder, int position) {
        MayAnh mayAnh = mayAnhList.get(position);

        nameMayAnh.setText(mayAnh.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giaMayAnh.setText("Giá:" + decimalFormat.format(mayAnh.getPrice()) + " đ ");
        Picasso.with(context).load(mayAnh.getAvatar())
                .placeholder(R.drawable.quangcao1)
                .error(R.drawable.quangcao2)
                .into(avatarMayAnh);

        id = mayAnh.getId();
        brand = mayAnh.getBrand();
        name =mayAnh.getName();
        price = mayAnh.getPrice();
        amount=mayAnh.getAmount();
        avatar=mayAnh.getAvatar();
    }

    @Override
    public int getItemCount() {
        return mayAnhList.size();
    }


    public class ViewHoder extends RecyclerView.ViewHolder{

           public ViewHoder(@NonNull View itemView){
               super(itemView);
               avatarMayAnh =(ImageView) itemView.findViewById(R.id.avatarMayAnh);
               nameMayAnh =(TextView) itemView.findViewById(R.id.nameMayAnh);
               giaMayAnh =(TextView) itemView.findViewById(R.id.giaMayAnh);
       //        btThemVaoDSYT = (ImageButton) itemView.findViewById(R.id.btThemVaoDSYT);

               itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       System.out.println("HiName = " + name);
                       Intent intent = new Intent(context, ChiTietSanPham.class);
                       intent.putExtra("thongtinsanpham",mayAnhList.get(getPosition()));
                       context.startActivity(intent);
                   }
               });
        }
    }

}
