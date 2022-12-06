package com.example.telemedic.Cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telemedic.Login.Registro;
import com.example.telemedic.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class VerPerfil extends AppCompatActivity {

    Button btn_actualizar_perfil,btn_cancelar;
    TextView correo,codigo;
    EditText nombre;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil);

        this.setTitle("Agregar Problema Fisico");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra("id_show");

        firebaseFirestore = FirebaseFirestore.getInstance();

        correo = findViewById(R.id.tv_correo_perfil);
        codigo = findViewById(R.id.tv_codigo_perfil);
        nombre = findViewById(R.id.et_nombre_perfil_usuario);
        btn_actualizar_perfil = findViewById(R.id.btn_editar_perfil);
        btn_cancelar = findViewById(R.id.btn_cancelar_perfil);

        getPerfil(id);

        String correoUser = correo.getText().toString().trim();
        String codigoUser = codigo.getText().toString().trim();
        String nombreUser = nombre.getText().toString().trim();

        if(correoUser.isEmpty() || codigoUser.isEmpty() || nombreUser.isEmpty() ){
            Toast.makeText(VerPerfil.this,"No hay datos para presentar", Toast.LENGTH_SHORT).show();

        }else{

            getPerfil(id);

        }




        btn_actualizar_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreUser = nombre.getText().toString().trim();

                if(nombreUser.isEmpty() ){
                    Toast.makeText(VerPerfil.this,"No hay datos para presentar", Toast.LENGTH_SHORT).show();

                }else{

                    UpdatePerfil(nombreUser,id);

                }



            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(VerPerfil.this, Menu_principal_cliente.class);
                startActivity(intent);


            }
        });









    }

    private void UpdatePerfil(String nombreUser, String id) {

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombreUser);

        firebaseFirestore.collection("user").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Exito al editar", Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al actualizar", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void getPerfil(String id) {
        firebaseFirestore.collection("user").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String correoPerfil = documentSnapshot.getString("correo");
                String codigoPerfil = documentSnapshot.getString("codigo");
                String nombrePerfil = documentSnapshot.getString("nombre");

                correo.setText(correoPerfil);
                codigo.setText(codigoPerfil);
                nombre.setText(nombrePerfil);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al obtener los datos", Toast.LENGTH_SHORT).show();

            }
        });

    }
}