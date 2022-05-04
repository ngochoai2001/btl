package activiti.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectcuoikhoa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import activiti.Cart;
import activiti.MainActivity;
import adapter.AdapterDanhSachYeuThich;
import data.SQLiteHelper;
import model.MayAnh;

public class DanhSachYeuThich extends AppCompatActivity {

    ImageButton btnBackYeuThich,btnGioHang;
    ListView lvYeuThich;
    TextView tvThongBaoDanhSachYeuThich;
    AdapterDanhSachYeuThich adapterDanhSachYeuThich;
    FirebaseUser firebaseUser;
    SQLiteHelper sqLiteHelper;
    String idAcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_yeu_thich);

        sqLiteHelper = new SQLiteHelper(this,"Data.sqlite1",null,5);

        btnBackYeuThich = findViewById(R.id.btnBackYeuThich);
        btnGioHang = findViewById(R.id.btnGioHang);
        lvYeuThich = findViewById(R.id.lvYeuThich);
        tvThongBaoDanhSachYeuThich = findViewById(R.id.tvThongBaoDanhSachYeuThich);
        adapterDanhSachYeuThich = new AdapterDanhSachYeuThich(this,MainActivity.danhSachYeuThichList);

        getData();
        checkData();

        btnBackYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), Cart.class);
               startActivity(intent);
            }
        });
        
    }

    private void getData() {
        MainActivity.danhSachYeuThichList.clear();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        idAcount = firebaseUser.getUid();
        Cursor dataDSYT = sqLiteHelper.GetData("SELECT * FROM DanhSachYeuThich WHERE IdAcount = '"+idAcount+"'");
        while (dataDSYT.moveToNext()){
            int idD = dataDSYT.getInt(2);
            String hang = dataDSYT.getString(3);
            String ten = dataDSYT.getString(4);
            int gia = dataDSYT.getInt(5);
            int sl = dataDSYT.getInt(6);
            String avt = dataDSYT.getString(7);
            MainActivity.danhSachYeuThichList.add(0,new MayAnh(idD,hang,ten,gia,sl,avt));
        }
        lvYeuThich.setAdapter(adapterDanhSachYeuThich);
    }

    public void XoaDSYeuThich(int id){
           sqLiteHelper.QueryData("DELETE FROM DanhSachYeuThich WHERE IdMayAnh = '"+ id +"' and IdAcount = '"+idAcount+"'");
           getData();
    }

    private void checkData() {
        if(MainActivity.danhSachYeuThichList.size() <= 0){
            adapterDanhSachYeuThich.notifyDataSetChanged();
            tvThongBaoDanhSachYeuThich.setVisibility(View.VISIBLE);
            lvYeuThich.setVisibility(View.INVISIBLE);
        }else{
            adapterDanhSachYeuThich.notifyDataSetChanged();
            tvThongBaoDanhSachYeuThich.setVisibility(View.INVISIBLE);
            lvYeuThich.setVisibility(View.VISIBLE);
        }
    }
}