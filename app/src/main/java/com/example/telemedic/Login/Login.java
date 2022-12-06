package com.example.telemedic.Login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.telemedic.Admin.Menu_principal_admin;
import com.example.telemedic.Cliente.ListaFisicos;
import com.example.telemedic.Cliente.Menu_principal_cliente;
import com.example.telemedic.R;
import com.example.telemedic.dto.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    EditText correo, contra;
    Button btn_iniciarSesion, btnRegistrarUsuario;




    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        correo = findViewById(R.id.et_correoLogin);
        contra = findViewById(R.id.et_contraLogin);
        btn_iniciarSesion = findViewById(R.id.btn_login_ingresar);

        btn_iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = correo.getText().toString().trim();
                String contraUser = contra.getText().toString().trim();
                String rol = "";

                if(emailUser.isEmpty() && contraUser.isEmpty()){
                    Toast.makeText(Login.this,"Ingrese los datos correspondientes", Toast.LENGTH_SHORT).show();

                }else
                    loginUser(emailUser,contraUser);


            }
        });



    }

    private void loginUser(String emailUser,String password){

        System.out.println("aqui esta la contra: " + password);



        firebaseAuth.signInWithEmailAndPassword(emailUser,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    firebaseAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (firebaseAuth.getCurrentUser().isEmailVerified()) {




                                firestore.collection("user").whereEqualTo("rol","admin").whereEqualTo("correo",emailUser).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                System.out.println("testeando");
                                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                finish();
                                                startActivity(new Intent(Login.this, Menu_principal_admin.class));
                                                Toast.makeText(Login.this, "Bienvenido Administrador", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }

                                    }
                                });

                                firestore.collection("user").whereEqualTo("rol","cliente").whereEqualTo("correo",emailUser).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                System.out.println("------------AQUI EMPIEZA LA PRUEBA----------------");
                                                Task ayuda = firestore.collection("user").whereEqualTo("rol","cliente").get();
                                                System.out.println("que me bota esto" + ayuda);
                                                document.getData().equals("rol");
                                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                finish();
                                                startActivity(new Intent(Login.this, Menu_principal_cliente.class));
                                                Toast.makeText(Login.this, "Bienvenido Cliente", Toast.LENGTH_SHORT).show();

                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }

                                    }
                                });









                                /*finish();
                                startActivity(new Intent(Login.this, Menu_principal_cliente.class));
                                Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();*/




                            } else {
                                Toast.makeText(Login.this, "Su cuenta no ha sido verificada, revise el spam de su correo", Toast.LENGTH_SHORT).show();
                            }

                        }


                    });
                } else {
                    Log.d("task", "ERROR EN REGISTRO - " + task.getException().getMessage());
                    Toast.makeText(Login.this, "Existe un error en el registro", Toast.LENGTH_SHORT).show();

                }


            }

        });









    }








    public void abrirRegistro(View view) {
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }





    public void abrirForgetPassword(View view) {
        Intent intent = new Intent(this, ForgetPassword.class);
        startActivity(intent);
    }




}