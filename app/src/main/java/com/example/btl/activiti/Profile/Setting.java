package com.example.btl.activiti.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.btl.R;

public class Setting extends AppCompatActivity {

    TextView doimatkhau,doimmatkhau2,suathongtin,suathongtin2,lienhevoichungtoi,lienhevoichungtoi2;
    ImageButton btnBackSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btnBackSetting = findViewById(R.id.btnBackSetting);
        doimatkhau = findViewById(R.id.doimatkhau);
        doimmatkhau2 = findViewById(R.id.doimatkhau2);
        suathongtin = findViewById(R.id.suathongtin);
        suathongtin2 = findViewById(R.id.suathongtin2);
        lienhevoichungtoi = findViewById(R.id.lienhevoichungtoi);
        lienhevoichungtoi2 = findViewById(R.id.lienhevoichungtoi2);

        btnBackSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });
        doimmatkhau2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });
        suathongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SuaThongTin.class);
                startActivity(intent);
            }
        });
        suathongtin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SuaThongTin.class);
                startActivity(intent);
            }
        });
        lienhevoichungtoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LienHeVoiChungToi.class);
                startActivity(intent);
            }
        });
        lienhevoichungtoi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LienHeVoiChungToi.class);
                startActivity(intent);
            }
        });
    }
}