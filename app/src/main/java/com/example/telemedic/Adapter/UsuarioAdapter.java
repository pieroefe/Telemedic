package com.example.telemedic.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemedic.Admin.Crear_fisica;
import com.example.telemedic.Cliente.VerPerfil;
import com.example.telemedic.R;
import com.example.telemedic.dto.Fisicas;
import com.example.telemedic.dto.Usuario;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class UsuarioAdapter extends FirestoreRecyclerAdapter<Usuario,UsuarioAdapter.Viewholder> {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public UsuarioAdapter(@NonNull FirestoreRecyclerOptions<Usuario> options, Activity activity) {
        super(options);
        this.activity = activity;
    }


    @Override
    protected void onBindViewHolder(@NonNull UsuarioAdapter.Viewholder viewholder, int i, @NonNull Usuario usuario) {



        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewholder.getAdapterPosition());

        final String id = documentSnapshot.getId();




        viewholder.nombre.setText(usuario.getNombre());
        viewholder.codigo.setText(usuario.getCodigo());
        viewholder.correo.setText(usuario.getCorreo());

        String foto = usuario.getFoto();

        try{
            if(!foto.equals("")){
                Toast.makeText(activity.getApplicationContext(),"Cargando foto",Toast.LENGTH_SHORT).show();
                System.out.println("prueba foto" + foto);

                Picasso.with(activity.getApplicationContext()).load(foto).resize(150,150).into(viewholder.foto);
            }
        }catch (Exception e){
            Log.v("Error","e: "+e);
        }







    }


    @NonNull
    @Override
    public UsuarioAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_usuario_single,parent,false);
        return new Viewholder(view);
    }


    public class Viewholder extends RecyclerView.ViewHolder {
        TextView nombre,codigo,correo;
        ImageView foto;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tv_nombre_usuario);
            codigo = itemView.findViewById(R.id.tv_codigo_usuario);
            correo = itemView.findViewById(R.id.tv_correo_usuario);
            foto = itemView.findViewById(R.id.iv_foto_usuario);


        }
    }




}
