package com.example.telemedic.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telemedic.Admin.Crear_fisica;
import com.example.telemedic.R;
import com.example.telemedic.dto.Fisicas;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

public class FisicaAdapter extends FirestoreRecyclerAdapter<Fisicas,FisicaAdapter.Viewholder> {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FisicaAdapter(@NonNull FirestoreRecyclerOptions<Fisicas> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull Viewholder viewholder, int i, @NonNull Fisicas fisicas) {



        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewholder.getAdapterPosition());

        final String id = documentSnapshot.getId();


        System.out.println(fisicas.getEtiqueta());

        viewholder.nombre.setText(fisicas.getNombre());
        viewholder.etiquetas.setText(fisicas.getEtiqueta());


        viewholder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFisicas(id);

            }
        });

        viewholder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Crear_fisica.class);
                intent.putExtra("id_edit",id);
                activity.startActivity(intent);

            }
        });





    }

    private void deleteFisicas(String id) {
        firebaseFirestore.collection("ProblemasFisicos").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity,"Se ha eliminado el problema con exito", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity,"Error al eliminar", Toast.LENGTH_SHORT).show();

            }
        });



    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_fisica_single,parent,false);
        return new Viewholder(view);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView etiquetas;
        ImageView btn_delete,btn_edit;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tv_nombre_fisicas);
            etiquetas = itemView.findViewById(R.id.sp_etiquetas_fisicas);
            btn_delete = itemView.findViewById(R.id.btn_delete_fisica);
            btn_edit = itemView.findViewById(R.id.btn_edit_fisica);

        }
    }
}
