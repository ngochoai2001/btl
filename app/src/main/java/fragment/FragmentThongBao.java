package fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectcuoikhoa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import activiti.MainActivity;
import adapter.ThongBaoAdapter;
import data.SQLiteHelper;
import model.ThongBao;

public class FragmentThongBao extends Fragment {
    public static FragmentThongBao newInstance() {

        Bundle args = new Bundle();

        FragmentThongBao fragment = new FragmentThongBao();
        fragment.setArguments(args);
        return fragment;
    }

    ListView lvThongBao;
    ThongBaoAdapter thongBaoAdapter;
    ArrayList<ThongBao> thongBaoArrayList;
    SQLiteHelper sqLiteHelper;
    FirebaseUser firebaseUser;
    String idAcount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_thongbao,container,false);
        lvThongBao = view.findViewById(R.id.lvThongBao);

        sqLiteHelper = new SQLiteHelper(view.getContext(),"Data.sqlite1",null,5);

        thongBaoAdapter = new ThongBaoAdapter(MainActivity.thongBaoArrayList);
   //     lvThongBao.setAdapter(thongBaoAdapter);
        getData();

        return view;
    }
    void getData(){
        MainActivity.thongBaoArrayList.clear();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        idAcount = firebaseUser.getUid();
        Cursor dataThongBao = sqLiteHelper.GetData("SELECT * FROM ThongBao WHERE IdAcount = '"+idAcount+"'");
        while (dataThongBao.moveToNext()){
            String tb = dataThongBao.getString(2);
            String n = dataThongBao.getString(3);
            MainActivity.thongBaoArrayList.add(0,new ThongBao(tb,n));
        }
        lvThongBao.setAdapter(thongBaoAdapter);
    }
}
