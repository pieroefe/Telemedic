package com.example.telemedic.Cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.telemedic.Adapter.FisicaAdapterCliente;
import com.example.telemedic.Adapter.PsicologicaAdapterCliente;
import com.example.telemedic.Admin.WrapContentLinearLayoutManager;
import com.example.telemedic.R;
import com.example.telemedic.dto.Fisicas;
import com.example.telemedic.dto.Psicologicas;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ListaPsicologicos extends AppCompatActivity {

    Button btn_verDetalles;
    RecyclerView recyclerView;
    PsicologicaAdapterCliente psicologicaAdapterCliente;
    FirebaseFirestore firebaseFirestore;

    private static ArrayList<Psicologicas> mArrayList = new ArrayList<>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_psicologicos);

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = (RecyclerView)findViewById(R.id.rv_psicologica_cliente);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        Query query = firebaseFirestore.collection("ProblemasPsicologicos");


        FirestoreRecyclerOptions<Psicologicas> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Psicologicas>().setQuery(query, Psicologicas.class).build();

        psicologicaAdapterCliente = new PsicologicaAdapterCliente(firestoreRecyclerOptions,this);
        psicologicaAdapterCliente.notifyDataSetChanged();
        recyclerView.setAdapter(psicologicaAdapterCliente);




    }


    @Override
    protected void onStart(){
        super.onStart();
        psicologicaAdapterCliente.startListening();

    }

    @Override
    protected void onStop(){
        super.onStop();
        psicologicaAdapterCliente.stopListening();
    }


}