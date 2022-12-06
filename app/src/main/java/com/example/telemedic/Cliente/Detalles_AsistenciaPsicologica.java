package com.example.telemedic.Cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telemedic.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Detalles_AsistenciaPsicologica extends AppCompatActivity {


    Button btn_verDetalles_psicologica;
    TextView nombre,recomendacion,preguntas,errores;

    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_asistencia_psicologica);


        this.setTitle("Ver Detalles:");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String id = getIntent().getStringExtra("id_show");

        firebaseFirestore = FirebaseFirestore.getInstance();


        recomendacion = findViewById(R.id.tv_psicologica_recomendacion);
        preguntas = findViewById(R.id.tv_psicologica_preguntas);
        errores = findViewById(R.id.tv_psicologica_errores);


        String recomendacionUser = recomendacion.getText().toString().trim();
        String preguntasUser = preguntas.getText().toString().trim();
        String erroresUser = errores.getText().toString().trim();

        if(recomendacionUser.isEmpty() || preguntasUser.isEmpty() || erroresUser.isEmpty() ){
            Toast.makeText(Detalles_AsistenciaPsicologica.this,"No hay datos para presentar", Toast.LENGTH_SHORT).show();

        }else{

            getPsicologica(id);

        }



    }

    private void getPsicologica(String id) {


        firebaseFirestore.collection("ProblemasPsicologicos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

                Toast.makeText(Detalles_AsistenciaPsicologica.this,"Error al conseguir los datos", Toast.LENGTH_SHORT).show();

            }
        });





    }
}