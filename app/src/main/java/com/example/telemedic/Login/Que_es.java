package com.example.telemedic.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.telemedic.R;

public class Que_es extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_es);
    }


    public void VamosLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }


}