package com.example.telemedic.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemedic.Cliente.Detalles_AsistenciaFisica;
import com.example.telemedic.Cliente.Detalles_AsistenciaPsicologica;
import com.example.telemedic.R;
import com.example.telemedic.dto.Fisicas;
import com.example.telemedic.dto.Psicologicas;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PsicologicaAdapterCliente extends FirestoreRecyclerAdapter<Psicologicas,PsicologicaAdapterCliente.Viewholder> {



    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public PsicologicaAdapterCliente(@NonNull FirestoreRecyclerOptions<Psicologicas> options, Activity activity) {
        super(options);
        this.activity = activity;
    }


    @Override
    protected void onBindViewHolder(@NonNull PsicologicaAdapterCliente.Viewholder viewholder, int i, @NonNull Psicologicas psicologicas) {



        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewholder.getAdapterPosition());

        final String id = documentSnapshot.getId();




        viewholder.nombre.setText(psicologicas.getNombre());





        viewholder.btn_verDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, Detalles_AsistenciaPsicologica.class);
                intent.putExtra("id_show",id);
                activity.startActivity(intent);

            }
        });



    }


    @NonNull
    @Override
    public PsicologicaAdapterCliente.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cliente_psicologicas_single,parent,false);
        return new Viewholder(view);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView nombre;

        Button btn_verDetalles;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tv_nombre_psicologicas_cliente);

            btn_verDetalles = itemView.findViewById(R.id.btn_verDetalles_psicologicas);


        }
    }






}
