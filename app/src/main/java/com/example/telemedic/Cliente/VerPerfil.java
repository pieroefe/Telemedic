package com.example.telemedic.Cliente;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUriExposedException;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.telemedic.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class VerPerfil extends AppCompatActivity {

    Button btn_actualizar_perfil,btn_cancelar,btn_foto_perfil,btn_foto_perfil2,btn_remover;
    TextView correo;// = (TextView) findViewById(R.id.tv_correo_perfil);
    TextView codigo;//=  (TextView) findViewById(R.id.tv_codigo_perfil);
    EditText nombre;//=(EditText) findViewById(R.id.et_nombre_perfil_usuario);
    ImageView foto;
    private FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    String storage_path = "foto/*";

    private static final int COD_SEL_STORAGE = 200;
    private static final int COD_SEL_IMAGE = 300;
    private Uri image_url;

    String photo = "photo";
    String idd;

    File sdImageMainDirectory;
    Uri outputFileUri;

    Uri urifinal;

    String imageFilePath;

    boolean actualizar_foto = false;

    //boolean actualizar_foto;



    public static final int CAMERA_ACTION_CODE = 1888;
    ProgressDialog progressDialog;

    private File createImageFile() throws IOException{

        File stdir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile("fototelemedic",".jpeg",stdir);
        imageFilePath = file.getAbsolutePath();
        return file;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil);

        try{
            sdImageMainDirectory = createImageFile();

        }catch (IOException ex){

        }


        this.setTitle("Vemos el perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idd = getIntent().getStringExtra("id_show");

        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        correo = (TextView) findViewById(R.id.tv_correo_perfil);
        codigo = (TextView) findViewById(R.id.tv_codigo_perfil);
        nombre = (EditText) findViewById(R.id.et_nombre_perfil_usuario);
        foto = findViewById(R.id.iv_perfil);

        btn_actualizar_perfil = findViewById(R.id.btn_editar_perfil);
        btn_cancelar = findViewById(R.id.btn_cancelar_perfil);
        btn_foto_perfil = findViewById(R.id.btn_foto_perfil);
        btn_foto_perfil2 = findViewById(R.id.btn_foto_perfil2);

        progressDialog = new ProgressDialog(this);




        btn_foto_perfil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto();




                //progressDialog.dismiss();
            }
        });









        //getPerfil(idd);

        firebaseFirestore.collection("user").document(idd).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String correoPerfil = documentSnapshot.getString("correo");
                String codigoPerfil = documentSnapshot.getString("codigo");
                String nombrePerfil = documentSnapshot.getString("nombre");
                String fotoPerfil = documentSnapshot.getString("foto");
                System.out.println("fotoperfil:"+ fotoPerfil);

                correo.setText(correoPerfil);
                codigo.setText(codigoPerfil);
                nombre.setText(nombrePerfil);

                String correoUser = ((TextView) findViewById(R.id.tv_correo_perfil)).getText().toString().trim();
                String codigoUser = codigo.getText().toString().trim();
                String nombreUser = nombre.getText().toString().trim();

                System.out.println("prueba datos - correo " + correoUser + "codigo " + codigoUser + "nombre " + nombreUser);




                if(correoUser.isEmpty() || codigoUser.isEmpty() || nombreUser.isEmpty() ){
                    Toast.makeText(VerPerfil.this,"No hay datos para presentar", Toast.LENGTH_SHORT).show();

                }else{

                    //getPerfil(idd);

                }

                try{
                    if(!fotoPerfil.equals("")){

                        Toast.makeText(getApplicationContext(),"Cargando foto",Toast.LENGTH_SHORT).show();
                        //Glide.with(VerPerfil.this).load(fotoPerfil).into(foto);
                        try {
                            Picasso.with(VerPerfil.this).load(fotoPerfil).resize(150,150).into(foto);

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                }catch (Exception e){
                    Log.v("Error","e: "+e);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al obtener los datos", Toast.LENGTH_SHORT).show();

            }
        });








        btn_actualizar_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreUser = nombre.getText().toString().trim();
                String fotoUser = foto.toString();

                if(nombreUser.isEmpty() ){
                    Toast.makeText(VerPerfil.this,"No hay datos para presentar", Toast.LENGTH_SHORT).show();

                }else{

                    UpdatePerfil(nombreUser,fotoUser,idd);

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

        btn_foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                outputFileUri = FileProvider.getUriForFile(VerPerfil.this, VerPerfil.this.getPackageName()+".provider", sdImageMainDirectory);
                System.out.println("inicio ruta " + outputFileUri);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                if(intent.resolveActivity(getPackageManager()) != null){
                    try {
                        startActivityForResult(intent,CAMERA_ACTION_CODE);
                    }catch(FileUriExposedException e){
                        //startActivityForResult(intent,CAMERA_ACTION_CODE);
                    }

                    //startActivityForResult(intent,CAMERA_REQUEST);
                }else{
                    Toast.makeText(VerPerfil.this,"No se soporta esta accion", Toast.LENGTH_SHORT).show();
                }




            }
        });


















    }





    private void UpdatePerfil(String nombreUser,String fotoUser, String id) {

        System.out.println(actualizar_foto);

        String rute_storage_photo = storage_path + "" + foto + "" + firebaseAuth.getUid() +""+idd;
        StorageReference reference = storageReference.child(rute_storage_photo);

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombreUser);
        //map.put("foto",fotoUser);

        progressDialog.setMessage("Actualizando datos");
        progressDialog.show();







        firebaseFirestore.collection("user").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Exito al editar", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al actualizar", Toast.LENGTH_SHORT).show();

            }
        });

        if(actualizar_foto == true){
            actualizar_foto = false;
            reference.putFile(urifinal).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Test success: "+ urifinal);
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()){
                        System.out.println("Test while sucess" + urifinal);
                        if(uriTask.isSuccessful()){
                            uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    System.out.println("onsuccess-uri " + uri);

                                    HashMap<String,Object> map = new HashMap<>();
                                    map.put("foto",uri);
                                    firebaseFirestore.collection("user").document(idd).update(map);
                                    Toast.makeText(getApplicationContext(),"Foto Actulizada", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    finish();



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    e.printStackTrace();
                                    progressDialog.dismiss();
                                    finish();
                                }
                            });

                        }


                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(VerPerfil.this,"Error al cargar foto", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                    finish();
                }
            });
        }else{
            progressDialog.dismiss();
            finish();
        }

        //aqui









    }




    private void getPerfil(String id) {
        firebaseFirestore.collection("user").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String correoPerfil = documentSnapshot.getString("correo");
                String codigoPerfil = documentSnapshot.getString("codigo");
                String nombrePerfil = documentSnapshot.getString("nombre");
                String fotoPerfil = documentSnapshot.getString("foto");
                System.out.println("fotoperfil:"+ fotoPerfil);

                correo.setText(correoPerfil);
                codigo.setText(codigoPerfil);
                nombre.setText(nombrePerfil);

                try{
                    if(!fotoPerfil.equals("")){
                        Toast.makeText(getApplicationContext(),"Cargando foto",Toast.LENGTH_SHORT).show();

                        Picasso.with(VerPerfil.this).load(fotoPerfil).resize(150,150).into(foto);
                    }
                }catch (Exception e){
                    Log.v("Error","e: "+e);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al obtener los datos", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        //super.onActivityResult(requestCode,resultCode,data);
        String rute_storage_photo = storage_path + "" + foto + "" + firebaseAuth.getUid() +""+idd;
        StorageReference reference = storageReference.child(rute_storage_photo);



        Log.d("image_url","requestCode - RESULT_OK" + requestCode + "" + RESULT_OK);
        if(resultCode == RESULT_OK){
            if(requestCode == COD_SEL_IMAGE){
                actualizar_foto = true;
                urifinal = data.getData();
                Picasso.with(VerPerfil.this).load(urifinal).resize(150,150).into(foto);
                //subirfoto(urifinal);



            }
        }





        if(requestCode == CAMERA_ACTION_CODE && resultCode == RESULT_OK ) {

            actualizar_foto = true;





            /*

            Bundle bundle = data.getExtras();
            Bitmap finalphoto = (Bitmap) bundle.get("data");
            foto.setImageBitmap(finalphoto);

            Uri image_url = data.getData(); */

            System.out.println("test:" + imageFilePath);




            //File file = new File(Environment.getExternalStorageDirectory(), "fototelemedic.jpg");





            //Uri uri = FileProvider.getUriForFile(this, VerPerfil.this.getPackageName()+".provider", file);
            urifinal = Uri.fromFile(new File(imageFilePath));





            System.out.println("---AQUI HAY PRUEBAS---");
            System.out.println("esto es la foto" + urifinal);




            //ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            //finalphoto.compress(Bitmap.CompressFormat.PNG,100, bytes);
            //String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),finalphoto,"val",null);
            //System.out.println(path);

            //Uri image_url = Uri.parse(path);
            //foto.setImageURI(urifinal);
            Picasso.with(VerPerfil.this).load(urifinal).resize(150,150).into(foto);




            System.out.println("esto es la uri de la foto " + urifinal);






        }
        super.onActivityResult(requestCode,resultCode,data);











    }



    private void subirfoto(Uri image_url){
        progressDialog.setMessage("Actualizando foto");
        progressDialog.show();
        String route_storage_photo = storage_path + "" + photo +"" + firebaseAuth.getUid() + "" + idd;
        StorageReference reference = storageReference.child(route_storage_photo);
        reference.putFile(image_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isSuccessful());
                if(uriTask.isSuccessful()){
                    uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String download_uri = uri.toString();
                            HashMap <String, Object> map = new HashMap<>();
                            map.put("foto",download_uri);
                            firebaseFirestore.collection("user").document(idd).update(map);
                            Toast.makeText(VerPerfil.this,"Foto actualizada",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }



    private void uploadPhoto(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");

        startActivityForResult(i,COD_SEL_IMAGE);

    }















}