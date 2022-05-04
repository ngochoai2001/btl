package activiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.projectcuoikhoa.R;

import FragmentMayAnh.FragmentMayAnhCanon;
import FragmentOngKinh.FragmentOngKinhCanon;
import FragmentOngKinh.FragmentOngKinhNikon;
import FragmentOngKinh.FragmentOngKinhSony;
import activiti.Profile.DanhSachYeuThich;

public class OngKinh extends AppCompatActivity {
    Toolbar toolBarLens;
    ImageView ivOngKinhCanon,ivOngKinhNikon,ivOngKinhSony,ivOngKinhSigma,ivOngKinhTamron,ivOngKinhFuji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ong_kinh);

        toolBarLens = findViewById(R.id.toolBarLens);
        ivOngKinhCanon = findViewById(R.id.ivOngKinhCanon);
        ivOngKinhNikon = findViewById(R.id.ivOngKinhNikon);
        ivOngKinhSony = findViewById(R.id.ivOngKinhSony);
        ivOngKinhFuji = findViewById(R.id.ivOngKinhFuji);
        ivOngKinhSigma = findViewById(R.id.ivOngKinhSigma);
        ivOngKinhTamron = findViewById(R.id.ivOngKinhTamrom);

        setSupportActionBar(toolBarLens);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragment(FragmentOngKinhCanon.newInstance());
        ivOngKinhCanon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(FragmentOngKinhCanon.newInstance());
            }
        });
        ivOngKinhNikon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(FragmentOngKinhNikon.newInstance());
            }
        });
        ivOngKinhSigma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(FragmentOngKinhCanon.newInstance());
            }
        });
        ivOngKinhSony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(FragmentOngKinhSony.newInstance());
            }
        });
        ivOngKinhTamron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(FragmentOngKinhCanon.newInstance());
            }
        });
        ivOngKinhFuji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(FragmentOngKinhCanon.newInstance());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_co_gio_hang,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.gioHang:
                Intent intent = new Intent(getApplicationContext(),Cart.class);
                startActivity(intent);
                break;
            case R.id.yeuThich:
                Intent intent1 = new Intent(getApplicationContext(), DanhSachYeuThich.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frOngKinh,fragment)
                .commit();
    }
}