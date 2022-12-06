package com.example.telemedic.Cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.telemedic.Adapter.FisicaAdapter;
import com.example.telemedic.Adapter.FisicaAdapterCliente;
import com.example.telemedic.Admin.Crear_fisica;
import com.example.telemedic.Admin.Menu_principal_admin;
import com.example.telemedic.Admin.WrapContentLinearLayoutManager;
import com.example.telemedic.R;
import com.example.telemedic.dto.Fisicas;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ListaFisicos extends AppCompatActivity {

    Button btn_verDetalles;
    RecyclerView recyclerView;
    FisicaAdapterCliente fisicaAdapterCliente;
    FirebaseFirestore firebaseFirestore;

    private static ArrayList<Fisicas> mArrayList = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_fisicos);


        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = (RecyclerView)findViewById(R.id.rv_fisicas_cliente);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        Query query = firebaseFirestore.collection("ProblemasFisicos");


        FirestoreRecyclerOptions<Fisicas> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Fisicas>().setQuery(query, Fisicas.class).build();

        fisicaAdapterCliente = new FisicaAdapterCliente(firestoreRecyclerOptions,this);
        fisicaAdapterCliente.notifyDataSetChanged();
        recyclerView.setAdapter(fisicaAdapterCliente);






    }

    @Override
    protected void onStart(){
        super.onStart();
        fisicaAdapterCliente.startListening();

    }

    @Override
    protected void onStop(){
        super.onStop();
        fisicaAdapterCliente.stopListening();
    }



}