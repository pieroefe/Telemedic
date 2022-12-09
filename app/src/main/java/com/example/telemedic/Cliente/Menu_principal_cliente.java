package com.example.telemedic.Cliente;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

    Button btn_LlamaEmergencias;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        btn_LlamaEmergencias = findViewById(R.id.btn_Emergencia);

        btn_LlamaEmergencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int permiso = ContextCompat.checkSelfPermission(Menu_principal_cliente.this,Manifest.permission.CALL_PHONE);

                if(permiso != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(Menu_principal_cliente.this,"No tiene permisos de llamada", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(Menu_principal_cliente.this,new String[]{Manifest.permission.CALL_PHONE},255);

                }else{
                    String numero = "971551391";
                    String inicio = "tel:" + numero ;
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse(inicio));
                    startActivity(i);

                }



            }
        });





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

        Intent intent = new Intent(this, VerMapa.class);
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