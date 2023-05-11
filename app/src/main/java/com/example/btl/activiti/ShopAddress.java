package com.example.btl.activiti;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.btl.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShopAddress extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageButton btnBackDiaChi;
    TextView diaChi1,diaChi2,diaChi3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_address);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnBackDiaChi = findViewById(R.id.btnBackDiaChi);
        diaChi1 = findViewById(R.id.diaChi1);
        diaChi2 = findViewById(R.id.diaChi2);
        diaChi3 = findViewById(R.id.diaChi3);

        btnBackDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng diaChiShop = new LatLng(21.056708, 105.729979);
        LatLng diaChiShop2 = new LatLng(21.094034, 105.702858);
        LatLng diaChiShop3 = new LatLng(21.030790, 105.850879);
        mMap.addMarker(new MarkerOptions().position(diaChiShop3).title("Huy Beo Camera").snippet("67 - Hoàn Kiếm - Hà Nội").icon(BitmapDescriptorFactory.defaultMarker()));
        mMap.addMarker(new MarkerOptions().position(diaChiShop2).title("Huy Beo Camera").snippet("69-Đường Nhuệ Giang-Tân Hội").icon(BitmapDescriptorFactory.defaultMarker()));
        mMap.addMarker(new MarkerOptions().position(diaChiShop).title("Huy Beo Camera").snippet("110-Phố Nhổn").icon(BitmapDescriptorFactory.defaultMarker()));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(diaChiShop).zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        diaChi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPosition cameraPosition1 = new CameraPosition.Builder().target(diaChiShop).zoom(15).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
            }
        });
        diaChi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPosition cameraPosition1 = new CameraPosition.Builder().target(diaChiShop2).zoom(15).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
            }
        });
        diaChi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPosition cameraPosition1 = new CameraPosition.Builder().target(diaChiShop3).zoom(15).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
            }
        });
    }
}