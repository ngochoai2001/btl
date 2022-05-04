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
import FragmentMayAnh.FragmentMayAnhFuji;
import FragmentMayAnh.FragmentMayAnhNikon;
import FragmentMayAnh.FragmentMayAnhSony;
import activiti.Profile.DanhSachYeuThich;

public class MayAnh extends AppCompatActivity {

    Toolbar toolBarMayAnh;
    ImageView ivCanon,ivNikon,ivSony,ivFuji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_may_anh);

        toolBarMayAnh = findViewById(R.id.toolBarMayAnh);
        ivCanon = findViewById(R.id.ivCanon);
        ivNikon = findViewById(R.id.ivNikon);
        ivSony = findViewById(R.id.ivSony);
        ivFuji = findViewById(R.id.ivFuji);

        setSupportActionBar(toolBarMayAnh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragment(FragmentMayAnhCanon.newInstance());
        ivCanon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(FragmentMayAnhCanon.newInstance());
            }
        });
        ivNikon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(FragmentMayAnhNikon.newInstance());
            }
        });
        ivSony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(FragmentMayAnhSony.newInstance());
            }
        });
        ivFuji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(FragmentMayAnhFuji.newInstance());
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
                .replace(R.id.frMayAnh,fragment)
                .commit();
    }

}