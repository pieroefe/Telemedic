package com.example.telemedic.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemedic.Admin.Crear_fisica;
import com.example.telemedic.Cliente.Detalles_AsistenciaFisica;
import com.example.telemedic.R;
import com.example.telemedic.dto.Fisicas;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FisicaAdapterCliente extends FirestoreRecyclerAdapter<Fisicas,FisicaAdapterCliente.Viewholder> {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public FisicaAdapterCliente(@NonNull FirestoreRecyclerOptions<Fisicas> options, Activity activity) {
        super(options);
        this.activity = activity;
    }


    @Override
    protected void onBindViewHolder(@NonNull FisicaAdapterCliente.Viewholder viewholder, int i, @NonNull Fisicas fisicas) {



        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewholder.getAdapterPosition());

        final String id = documentSnapshot.getId();


        System.out.println(fisicas.getEtiqueta());

        viewholder.nombre.setText(fisicas.getNombre());
        viewholder.etiquetas.setText(fisicas.getEtiqueta());




        viewholder.btn_verDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, Detalles_AsistenciaFisica.class);
                intent.putExtra("id_show",id);
                activity.startActivity(intent);

            }
        });



    }


    @NonNull
    @Override
    public FisicaAdapterCliente.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cliente_fisicas_single,parent,false);
        return new Viewholder(view);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView etiquetas;
        Button btn_verDetalles;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tv_nombre_fisicas_cliente);
            etiquetas = itemView.findViewById(R.id.tv_etiqueta_fisicas_cliente);
            btn_verDetalles = itemView.findViewById(R.id.btn_verDetalles_fisicas);


        }
    }





}
