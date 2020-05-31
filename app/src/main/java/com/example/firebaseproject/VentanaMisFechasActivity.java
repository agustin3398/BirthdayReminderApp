package com.example.firebaseproject;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseproject.adapters.FechaCumpleaniosAdapter;
import com.example.firebaseproject.modelo.FechasDeCumpleanios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VentanaMisFechasActivity extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private FechaCumpleaniosAdapter fechaCumpleaniosAdapter;
    private RecyclerView recyclerView;
    private ArrayList<FechasDeCumpleanios> listasFechaCumples = new ArrayList<>();

    // dialogo para poder abrir y editar mi fecha
    private Dialog dialogoEditarFecha;

    // instancio mi card view para editarla
    private CardView cardViewFechas;

    //instancio mis objetos dentro de la vista del popUp
    EditText nombrePersona;
    EditText edadPersona;
    EditText fechaSeleccionada;
    EditText ideasParaRegalo;
    Button btnCerrarPopupFechaSel;
    Button btnGuardarDatosPopUpFechaSel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_fechas);
        initilize();
        getFechasDeCumpleFromFirebase();
    }

    private void initilize() {
        dialogoEditarFecha = new Dialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Fechas de Cumpleaños");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_viewMisFechas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        // TODO card view en null
//        cardViewFechas.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alPresionarFecha();
//            }
//        });


    }

    private void getFechasDeCumpleFromFirebase() {
        // hago esto para obtener solo los datos de mi usuario logueado y no todos los de mi base de datos.
        Query query = databaseReference.child("FechasCumpleanios").orderByChild("userId").equalTo(firebaseUser.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // verifico si el nodo "FechasCumpleanios" existe
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        // obtengo de cada objeto los atributos
                        String nombrePersona = ds.child("nombrePersona").getValue().toString();
                        String edadPersona = ds.child("edadPersona").getValue().toString();
                        String fechaCumple = ds.child("fechaCumpleanios").getValue().toString();
                        String ideas = ds.child("ideasParaRegalar").getValue().toString();
                        listasFechaCumples.add(new FechasDeCumpleanios(ds.getKey(), firebaseUser.getUid(), nombrePersona, edadPersona, fechaCumple, ideas));
                    }

                    fechaCumpleaniosAdapter = new FechaCumpleaniosAdapter(listasFechaCumples, R.layout.fechas_cumpleanios_view);
                    recyclerView.setAdapter(fechaCumpleaniosAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void alPresionarFecha() {

        dialogoEditarFecha.setContentView(R.layout.fecha_seleccionada_popup);
        dialogoEditarFecha.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnCerrarPopupFechaSel = (Button) dialogoEditarFecha.findViewById(R.id.buttonClosePopUp);
        btnGuardarDatosPopUpFechaSel = (Button) dialogoEditarFecha.findViewById(R.id.buttonGuardarDatosPopUp);

        btnCerrarPopupFechaSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoEditarFecha.dismiss();
            }
        });

        btnGuardarDatosPopUpFechaSel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editarFechaDeCumpleanios();
            }

        });

        dialogoEditarFecha.show();

    }

    //TODO Terminar, editar datos y sobreescirblos en bd firebase
    public void editarFechaDeCumpleanios() {
        // inicializo objetos de mi popUpFechaSeleccionada
        nombrePersona = (EditText) dialogoEditarFecha.findViewById(R.id.txtNombrePersona);
        edadPersona = (EditText) dialogoEditarFecha.findViewById(R.id.txtEdadPersona);
        fechaSeleccionada = (EditText) dialogoEditarFecha.findViewById(R.id.txtFechaSeleccionada);
        ideasParaRegalo = (EditText) dialogoEditarFecha.findViewById(R.id.txtIdeasRegalo);


        String nombrePersonaCumpl = nombrePersona.getText().toString();
        String edadPersonaCumpl = edadPersona.getText().toString();
        String fechaSeleccionadaCumpl = fechaSeleccionada.getText().toString();
        String ideasRegaloCumpl = ideasParaRegalo.getText().toString();


    }

}
