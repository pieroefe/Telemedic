package com.example.telemedic.Cliente;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.telemedic.Admin.Lista_psicologicas_admin;
import com.example.telemedic.Admin.Menu_principal_admin;
import com.example.telemedic.Login.Login;
import com.example.telemedic.Login.Registro;
import com.example.telemedic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Menu_principal_cliente extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();



    }

    public void IrAsistenciaFisica(View view) {

        Intent intent = new Intent(this, ListaFisicos.class);
        startActivity(intent);
    }
    public void IrAsistenciaPsicologica(View view) {

        Intent intent = new Intent(this, ListaPsicologicos.class);
        startActivity(intent);
    }


    public void IrMapa(View view) {

        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }
    public void Ir911(View view) {

        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_cerrarsesion_cliente,menu);
        return true;

    }





    public void cerrarSesionCliente(MenuItem item){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(Menu_principal_cliente.this, Login.class));



    }

    public void verPerfil(MenuItem item){

        String correo = firebaseAuth.getCurrentUser().getEmail();


        firebaseFirestore.collection("user").whereEqualTo("correo", correo).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                final String id = document.getId();


                                Intent intent = new Intent(Menu_principal_cliente.this, VerPerfil.class);
                                intent.putExtra("id_show",id);
                                startActivity(intent);

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });














    }



}