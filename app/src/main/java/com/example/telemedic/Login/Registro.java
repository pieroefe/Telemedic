package com.example.telemedic.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.telemedic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;


    Button btn_registrar;
    EditText nombre,codigo,correo,contra,confirma;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        getSupportActionBar().setTitle("Registro de usuario");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        nombre = findViewById(R.id.et_nombre_registro);
        codigo = findViewById(R.id.et_codigo_registro);
        correo = findViewById(R.id.et_correo_registro);
        contra = findViewById(R.id.et_contra_registro);
        confirma = findViewById(R.id.et_confirm_registro);
        btn_registrar= findViewById(R.id.btn_registrar);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameUser = nombre.getText().toString().trim();
                String codigoUser = codigo.getText().toString().trim();
                String correoUser = correo.getText().toString().trim();
                String contraUser = contra.getText().toString().trim();
                String confirmaUser = confirma.getText().toString().trim();
                String rolUser = "cliente";
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";

                boolean correoValido = true;
                boolean passwordValido = true;
                boolean codigoValido = true;
                boolean nombreValido = true;

                if( nameUser.length()>15 ){
                    Toast.makeText(Registro.this,"Su nombre no puede tener mas de 15 caracteres", Toast.LENGTH_SHORT).show();
                    nombreValido = false;

                }

                if(codigoUser.length()>8){
                    Toast.makeText(Registro.this,"Su código debe tener ser 8 números", Toast.LENGTH_SHORT).show();
                    codigoValido =false;
                }

                if(codigoUser.matches("[0-9]")){
                    Toast.makeText(Registro.this,"Su código debe ser solo números", Toast.LENGTH_SHORT).show();
                    codigoValido =false;
                }

                if(!correoUser.matches(emailPattern)){
                    Toast.makeText(Registro.this,"Ingrese un correo valido", Toast.LENGTH_SHORT).show();
                    correoValido = false;
                }

                if(contraUser.length()<6){
                    Toast.makeText(Registro.this,"Su contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    passwordValido =false;

                }

                if(contraUser != confirmaUser){
                    Toast.makeText(Registro.this,"Vuelva a colocar la contraseña", Toast.LENGTH_SHORT).show();
                    passwordValido =false;

                }

                if(nameUser.isEmpty() || codigoUser.isEmpty() || correoUser.isEmpty() || contraUser.isEmpty() || confirmaUser.isEmpty()) {
                    Toast.makeText(Registro.this, "Complete los datos correspondientes", Toast.LENGTH_SHORT).show();
                    nombreValido = false;
                    codigoValido =false;
                    correoValido = false;
                    passwordValido =false;

                }



                if(nombreValido && codigoValido && correoValido && passwordValido){
                    registerUser(nameUser,codigoUser ,correoUser , contraUser,confirmaUser,rolUser);

                }


            }
        });



    }

    private void registerUser(String nameUser, String codigoUser, String correoUser, String contraUser, String confirmaUser, String rolUser) {

        firebaseAuth.createUserWithEmailAndPassword(correoUser,contraUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                firebaseAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        String id = firebaseAuth.getCurrentUser().getUid();
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",id);
                        map.put("nombre",nameUser);
                        map.put("codigo",codigoUser);
                        map.put("correo",correoUser);
                        map.put("contra",contraUser);
                        map.put("rol",rolUser);

                        firebaseFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                finish();
                                startActivity(new Intent( Registro.this,Login.class));
                                Toast.makeText(Registro.this,"Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Registro.this,"Error al hacer el registro", Toast.LENGTH_SHORT).show();


                            }
                        });

















                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Registro.this,"Error al enviar correo de verificación", Toast.LENGTH_SHORT).show();

                    }
                });

            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registro.this,"Error al registrar", Toast.LENGTH_SHORT).show();


            }
        });


    }


    public void GoLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }




}