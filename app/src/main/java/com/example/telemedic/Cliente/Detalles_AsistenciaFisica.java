package com.example.telemedic.Cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telemedic.Admin.Crear_fisica;
import com.example.telemedic.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Detalles_AsistenciaFisica extends AppCompatActivity {

    Button btn_verDetalles_fisico;
    TextView nombre,recomendacion,preguntas,errores;
    Spinner etiqueta;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_asistencia_fisica);

        this.setTitle("Ver Detalles:");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String id = getIntent().getStringExtra("id_show");

        firebaseFirestore = FirebaseFirestore.getInstance();


        recomendacion = findViewById(R.id.tv_fisica_recomendacion);
        preguntas = findViewById(R.id.tv_fisica_preguntas);
        errores = findViewById(R.id.tv_fisica_errores);


        String recomendacionUser = recomendacion.getText().toString().trim();
        String preguntasUser = preguntas.getText().toString().trim();
        String erroresUser = errores.getText().toString().trim();

        if(recomendacionUser.isEmpty() || preguntasUser.isEmpty() || erroresUser.isEmpty() ){
            Toast.makeText(Detalles_AsistenciaFisica.this,"No hay datos para presentar", Toast.LENGTH_SHORT).show();

        }else{

            getFisico(id);

        }











    }





    private void getFisico(String id) {

        firebaseFirestore.collection("ProblemasFisicos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String recomendacionUser = documentSnapshot.getString("recomendacion");
                String preguntasUser = documentSnapshot.getString("preguntas");
                String erroresUser = documentSnapshot.getString("errores");

                recomendacion.setText(recomendacionUser);
                preguntas.setText(preguntasUser);
                errores.setText(erroresUser);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Detalles_AsistenciaFisica.this,"Error al conseguir los datos", Toast.LENGTH_SHORT).show();

            }
        });


    }




}


