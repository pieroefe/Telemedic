package com.example.telemedic.Cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.telemedic.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class VerMapa extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    EditText txtLatitud, txtLongitud;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mapa);

        txtLatitud = findViewById(R.id.txtLatitud);
        txtLongitud = findViewById(R.id.txtLongitud);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);

        LatLng pucp = new LatLng(-12.0689502,-77.0806411);
        mMap.addMarker(new MarkerOptions().position(pucp).title("PUCP"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pucp));


    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        txtLatitud.setText(""+ latLng.latitude);
        txtLongitud.setText(""+ latLng.longitude);

        mMap.clear();
        LatLng destino = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(destino).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(destino));



    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

        txtLatitud.setText(""+ latLng.latitude);
        txtLongitud.setText(""+ latLng.longitude);

        mMap.clear();
        LatLng destino = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(destino).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(destino));


    }
}