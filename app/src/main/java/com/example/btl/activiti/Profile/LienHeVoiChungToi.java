package com.example.btl.activiti.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.btl.R;

public class LienHeVoiChungToi extends AppCompatActivity {

    ImageButton btnBackLienHe;
    ImageView logoFaceBook,logoYoutube,logoInSta,logoWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he_voi_chung_toi);

        btnBackLienHe = findViewById(R.id.btnBackLienHe);
        logoFaceBook = findViewById(R.id.logoFaceBook);
        logoYoutube = findViewById(R.id.logoYoutube);
        logoInSta = findViewById(R.id.logoInSta);
        logoWebsite = findViewById(R.id.logoWebsite);

        btnBackLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        logoFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.facebook.com/mayanhgiatot");
            }
        });
        logoYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.youtube.com/channel/UCdQ6eXlp6ITMB085IVRz2Hg");
            }
        });
        logoInSta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.instagram.com/");
            }
        });

        logoWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 gotoUrl("https://mayanhcuhanoi.com/");
            }
        });
    }
    private void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}