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
import com.example.telemedic.Login.Login;
import com.example.telemedic.R;
import com.example.telemedic.dto.Fisicas;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class Menu_principal_admin extends AppCompatActivity {

    Button btn_add;
    RecyclerView recyclerView;
    FisicaAdapter fisicaAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    BottomNavigationView bottomNavigationView;

    private static ArrayList<Fisicas> mArrayList = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_admin);
        setBottomNavigationView();

        firebaseFirestore = FirebaseFirestore.getInstance();


        recyclerView = (RecyclerView)findViewById(R.id.rv_lista_fisicas);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        Query query = firebaseFirestore.collection("ProblemasFisicos");
/*
        firebaseFirestore.collection("ProblemasFisicos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()){
                    return;
                }else{
                    List<Fisicas> fis = queryDocumentSnapshots.toObjects(Fisicas.class);
                    mArrayList.addAll(fis);
                    for(Fisicas fi : fis){
                        Log.d("test","onsuccess: " + fi.getNombre());
                        Log.d("test","onsuccess: " + fi.getEtiqueta());
                        Log.d("test","onsuccess: " + fi.getErrores());
                    }

                }
            }
        });

 */
        //System.out.println(query.get().getResult().toString());

        FirestoreRecyclerOptions<Fisicas> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Fisicas>().setQuery(query, Fisicas.class).build();

        fisicaAdapter = new FisicaAdapter(firestoreRecyclerOptions,this);
        fisicaAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(fisicaAdapter);



        btn_add = findViewById(R.id.btn_add_fisica);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu_principal_admin.this,Crear_fisica.class));
            }
        });

    }



    @Override
    protected void onStart(){
        super.onStart();
        fisicaAdapter.startListening();

    }

    @Override
    protected void onStop(){
        super.onStop();
        fisicaAdapter.stopListening();
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
                        return true;
                    case R.id.menu_lista_psicologicos:
                        startActivity(new Intent(Menu_principal_admin.this, Lista_psicologicas_admin.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_lista_usuarios:
                        startActivity(new Intent(Menu_principal_admin.this, Lista_usuarios.class));
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
        startActivity(new Intent(Menu_principal_admin.this, Login.class));


        
    }








}