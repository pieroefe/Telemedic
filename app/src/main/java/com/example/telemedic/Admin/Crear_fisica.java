package com.example.telemedic.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.telemedic.Login.Registro;
import com.example.telemedic.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Crear_fisica extends AppCompatActivity {

    Button btn_add_fisico;
    EditText nombre,recomendacion,preguntas,errores;
    Spinner etiqueta;
    private FirebaseFirestore firebaseFirestore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_fisica);

        this.setTitle("Agregar Problema Fisico");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra("id_edit");

        firebaseFirestore = FirebaseFirestore.getInstance();

        nombre = findViewById(R.id.et_fisico_nombre);
        recomendacion = findViewById(R.id.et_fisico_recomendacion);
        preguntas = findViewById(R.id.et_fisico_preguntas);
        errores = findViewById(R.id.et_fisico_errores);
        etiqueta = findViewById(R.id.spinner_etiqueta_fisica);
        btn_add_fisico = findViewById(R.id.btn_add_fisico_confirm);

        if(id == null || id == ""){

            btn_add_fisico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombreUser = nombre.getText().toString().trim();
                    String recomendacionUser = recomendacion.getText().toString().trim();
                    String preguntasUser = preguntas.getText().toString().trim();
                    String erroresUser = errores.getText().toString().trim();
                    String etiquetaUser = etiqueta.getSelectedItem().toString().trim();

                    if(nombreUser.isEmpty() || recomendacionUser.isEmpty() || preguntasUser.isEmpty() || erroresUser.isEmpty() || etiquetaUser.isEmpty()){
                        Toast.makeText(Crear_fisica.this,"Complete los datos correspondientes", Toast.LENGTH_SHORT).show();

                    }else{

                        postFisico(nombreUser,recomendacionUser,preguntasUser,erroresUser,etiquetaUser);

                    }

                }
            });

        }else{
            btn_add_fisico.setText("Actualizar");
            getFisica(id);

            btn_add_fisico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombreUser = nombre.getText().toString().trim();
                    String recomendacionUser = recomendacion.getText().toString().trim();
                    String preguntasUser = preguntas.getText().toString().trim();
                    String erroresUser = errores.getText().toString().trim();
                    String etiquetaUser = etiqueta.getSelectedItem().toString().trim();

                    if(nombreUser.isEmpty() || recomendacionUser.isEmpty() || preguntasUser.isEmpty() || erroresUser.isEmpty() || etiquetaUser.isEmpty()){
                        Toast.makeText(Crear_fisica.this,"Complete los datos correspondientes", Toast.LENGTH_SHORT).show();

                    }else{

                        updateFisico(nombreUser,recomendacionUser,preguntasUser,erroresUser,etiquetaUser, id);

                    }

                }
            });

        }









    }

    private void updateFisico(String nombreUser, String recomendacionUser, String preguntasUser, String erroresUser, String etiquetaUser, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombreUser);
        map.put("recomendacion", recomendacionUser);
        map.put("preguntas", preguntasUser);
        map.put("errores", erroresUser);
        map.put("etiqueta",etiquetaUser);

        firebaseFirestore.collection("ProblemasFisicos").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void postFisico(String nombreUser, String recomendacionUser, String preguntasUser, String erroresUser,String etiquetaUser) {

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombreUser);
        map.put("recomendacion", recomendacionUser);
        map.put("preguntas", preguntasUser);
        map.put("errores", erroresUser);
        map.put("etiqueta",etiquetaUser);


        firebaseFirestore.collection("ProblemasFisicos").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

    private void getFisica(String id){
        firebaseFirestore.collection("ProblemasFisicos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombreFisica = documentSnapshot.getString("nombre");
                String recomendacionFisica = documentSnapshot.getString("recomendacion");
                String preguntasFisica = documentSnapshot.getString("preguntas");
                String erroresFisica = documentSnapshot.getString("errores");



                String etiquetaFisica = documentSnapshot.getString("etiqueta");

                nombre.setText(nombreFisica);
                recomendacion.setText(recomendacionFisica);
                preguntas.setText(preguntasFisica);
                errores.setText(erroresFisica);









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