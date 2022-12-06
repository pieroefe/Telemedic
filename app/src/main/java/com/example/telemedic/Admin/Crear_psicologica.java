package com.example.telemedic.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.telemedic.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Crear_psicologica extends AppCompatActivity {


    Button btn_add_psicologica;
    EditText nombre, recomendacion, preguntas, errores;

    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_psicologica);
        this.setTitle("Agregar Problema Psicologico");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String id = getIntent().getStringExtra("id_edit");

        firebaseFirestore = FirebaseFirestore.getInstance();

        nombre = findViewById(R.id.et_psicologica_nombre);
        recomendacion = findViewById(R.id.et_psicologica_recomendacion);
        preguntas = findViewById(R.id.et_psicologica_preguntas);
        errores = findViewById(R.id.et_psicologica_errores);

        btn_add_psicologica = findViewById(R.id.btn_add_psicologica_confirm);


        if (id == null || id == "") {

            btn_add_psicologica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombreUser = nombre.getText().toString().trim();
                    String recomendacionUser = recomendacion.getText().toString().trim();
                    String preguntasUser = preguntas.getText().toString().trim();
                    String erroresUser = errores.getText().toString().trim();


                    if (nombreUser.isEmpty() || recomendacionUser.isEmpty() || preguntasUser.isEmpty() || erroresUser.isEmpty()) {
                        Toast.makeText(Crear_psicologica.this, "Complete los datos correspondientes", Toast.LENGTH_SHORT).show();

                    } else {

                        postPsicologico(nombreUser, recomendacionUser, preguntasUser, erroresUser);

                    }

                }
            });

        } else {
            btn_add_psicologica.setText("Actualizar");
            getPsicologico(id);

            btn_add_psicologica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombreUser = nombre.getText().toString().trim();
                    String recomendacionUser = recomendacion.getText().toString().trim();
                    String preguntasUser = preguntas.getText().toString().trim();
                    String erroresUser = errores.getText().toString().trim();


                    if (nombreUser.isEmpty() || recomendacionUser.isEmpty() || preguntasUser.isEmpty() || erroresUser.isEmpty()) {
                        Toast.makeText(Crear_psicologica.this, "Complete los datos correspondientes", Toast.LENGTH_SHORT).show();

                    } else {

                        updatePsicologico(nombreUser, recomendacionUser, preguntasUser, erroresUser, id);

                    }

                }
            });

        }


    }


    private void updatePsicologico(String nombreUser, String recomendacionUser, String preguntasUser, String erroresUser, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombreUser);
        map.put("recomendacion", recomendacionUser);
        map.put("preguntas", preguntasUser);
        map.put("errores", erroresUser);


        firebaseFirestore.collection("ProblemasPsicologicos").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Exito al editar", Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void postPsicologico(String nombreUser, String recomendacionUser, String preguntasUser, String erroresUser) {

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombreUser);
        map.put("recomendacion", recomendacionUser);
        map.put("preguntas", preguntasUser);
        map.put("errores", erroresUser);



        firebaseFirestore.collection("ProblemasPsicologicos").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {



                Toast.makeText(getApplicationContext(),"Exito al registrar", Toast.LENGTH_SHORT).show();
                finish();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al registrar", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getPsicologico(String id){
        firebaseFirestore.collection("ProblemasPsicologicos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombrePsicologica = documentSnapshot.getString("nombre");
                String recomendacionPsicologica = documentSnapshot.getString("recomendacion");
                String preguntasPsicologica = documentSnapshot.getString("preguntas");
                String erroresPsicologica = documentSnapshot.getString("errores");



                nombre.setText(nombrePsicologica);
                recomendacion.setText(recomendacionPsicologica);
                preguntas.setText(preguntasPsicologica);
                errores.setText(erroresPsicologica);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al obtener los datos", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return false;
    }




}