package com.example.telemedic.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.telemedic.Adapter.FisicaAdapter;
import com.example.telemedic.Adapter.PsicologicaAdapter;
import com.example.telemedic.Login.Login;
import com.example.telemedic.R;
import com.example.telemedic.dto.Fisicas;
import com.example.telemedic.dto.Psicologicas;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class Lista_psicologicas_admin extends AppCompatActivity {

    Button btn_add;
    RecyclerView recyclerView;
    PsicologicaAdapter psicologicaAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    BottomNavigationView bottomNavigationView;
    private static ArrayList<Psicologicas> mArrayList = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_psicologicas_admin);
        setBottomNavigationView();

        firebaseAuth = FirebaseAuth.getInstance();


        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = (RecyclerView)findViewById(R.id.rv_lista_psicologicas);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        Query query = firebaseFirestore.collection("ProblemasPsicologicos");

        FirestoreRecyclerOptions<Psicologicas> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Psicologicas>().setQuery(query, Psicologicas.class).build();

        psicologicaAdapter = new PsicologicaAdapter(firestoreRecyclerOptions,this);
        psicologicaAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(psicologicaAdapter);



        btn_add = findViewById(R.id.btn_add_psicologica);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Lista_psicologicas_admin.this,Crear_psicologica.class));
            }
        });




    }

    @Override
    protected void onStart(){
        super.onStart();
        psicologicaAdapter.startListening();

    }

    @Override
    protected void onStop(){
        super.onStop();
        psicologicaAdapter.stopListening();
    }



    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationAdmin);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.menu_lista_fisicos);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_lista_fisicos:
                        startActivity(new Intent(Lista_psicologicas_admin.this, Menu_principal_admin.class));

                        overridePendingTransition(0,0);

                        return true;
                    case R.id.menu_lista_psicologicos:

                        return true;
                    case R.id.menu_lista_usuarios:
                        startActivity(new Intent(Lista_psicologicas_admin.this, Lista_usuarios.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_cerrarsesion,menu);
        return true;

    }


    public void accionCerrarSesion(MenuItem item){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(Lista_psicologicas_admin.this, Login.class));



    }




}