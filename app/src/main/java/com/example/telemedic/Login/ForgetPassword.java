package com.example.telemedic.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telemedic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForgetPassword extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;



    EditText correo;
    Button btnCambiarContra;
    private String email = "" ;

    private ProgressDialog mDialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        correo = findViewById(R.id.et_recuperarcontra);
        btnCambiarContra = findViewById(R.id.btn_restablecerContra);

        mDialog = new ProgressDialog(this);

        btnCambiarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = correo.getText().toString().trim();

                if(!email.isEmpty()){
                    mDialog.setMessage("Espere un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();
                }
                else{
                    Toast.makeText( ForgetPassword.this, "Debe ingresar su correo", Toast.LENGTH_SHORT).show();
                }

                mDialog.dismiss();


            }
        });




    }


    private void resetPassword(){

        firebaseAuth.setLanguageCode("es");
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText( ForgetPassword.this, "Se ha enviado un correo para reestablecer tu contraseña", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText( ForgetPassword.this, "No se pudo enviar el correo de reestablecer contraseña", Toast.LENGTH_SHORT).show();

                }

            }
        });



    }



}