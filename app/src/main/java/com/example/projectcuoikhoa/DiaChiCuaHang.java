package com.example.projectcuoikhoa;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DiaChiCuaHang extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageButton btnBackDiaChi;
    TextView diaChi1,diaChi2,diaChi3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi_cua_hang);
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
  /*      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true); */
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