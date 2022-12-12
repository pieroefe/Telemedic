package com.example.telemedic.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;

import com.example.telemedic.Adapter.FisicaAdapter;
import com.example.telemedic.Adapter.UsuarioAdapter;
import com.example.telemedic.Login.Login;
import com.example.telemedic.R;
import com.example.telemedic.dto.Fisicas;
import com.example.telemedic.dto.Usuario;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class Lista_usuarios extends AppCompatActivity {



    RecyclerView recyclerView;
    UsuarioAdapter usuarioAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;


    BottomNavigationView bottomNavigationView;


    private static ArrayList<Usuario> mArrayList = new ArrayList<>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);
        setBottomNavigationView();


        firebaseAuth = FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = (RecyclerView)findViewById(R.id.rv_usuarios);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        Query query = firebaseFirestore.collection("user");


        FirestoreRecyclerOptions<Usuario> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Usuario>().setQuery(query, Usuario.class).build();

        usuarioAdapter = new UsuarioAdapter(firestoreRecyclerOptions,this);
        usuarioAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(usuarioAdapter);











    }



    @Override
    protected void onStart(){
        super.onStart();
        usuarioAdapter.startListening();

    }

    @Override
    protected void onStop(){
        super.onStop();
        usuarioAdapter.stopListening();
    }















    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationAdmin);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.menu_lista_usuarios);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_lista_fisicos:
                        startActivity(new Intent(Lista_usuarios.this, Menu_principal_admin.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_lista_psicologicos:
                        startActivity(new Intent(Lista_usuarios.this, Lista_psicologicas_admin.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_lista_usuarios:
                        //startActivity(new Intent(Menu_principal_admin.this, Lista_usuarios.class));
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
        startActivity(new Intent(Lista_usuarios.this, Login.class));



    }




}