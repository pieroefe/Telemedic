package com.example.telemedic.Cliente;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.telemedic.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class VerMapa extends AppCompatActivity implements OnMapReadyCallback {


    GoogleMap mMap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mapa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);















    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

        System.out.println("testmapa" + R.drawable.ic_universidad);


        BitmapDescriptor bd = bitmapDescriptorFromVector(this, R.drawable.ic_universidad);
        BitmapDescriptor bd2 = bitmapDescriptorFromVector(this, R.drawable.ic_hospital);

        LatLng pucp = new LatLng(-12.0689502,-77.0806411);
        //mMap.addMarker(new MarkerOptions().position(pucp).title("PUCP").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_universidad )));
        mMap.addMarker(new MarkerOptions().position(pucp).title("PUCP").icon(bd));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pucp));

        LatLng hospital = new LatLng(-12.0719884,-77.0610385);
        //mMap.addMarker(new MarkerOptions().position(hospital).title("SantaRosa").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_hospital)));
        mMap.addMarker(new MarkerOptions().position(hospital).title("SantaRosa").icon(bd2));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hospital));

        // Centrar Marcadores
        LatLngBounds.Builder constructor = new LatLngBounds.Builder();

        constructor.include(pucp);
        constructor.include(hospital);

        LatLngBounds limites = constructor.build();

        int ancho = getResources().getDisplayMetrics().widthPixels;
        int alto = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (ancho * 0.12); // 25% de espacio (padding) superior e inferior

        CameraUpdate centrarmarcadores = CameraUpdateFactory.newLatLngBounds(limites, ancho, alto, padding);

        mMap.animateCamera(centrarmarcadores);





    }
}