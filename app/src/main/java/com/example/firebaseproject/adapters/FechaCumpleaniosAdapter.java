package com.example.firebaseproject.adapters;

/*
* esta clase va a tener la respnsabilidad de establecer los valores que obtenemos
* de nuestra base de datos para mostrarlos en la lista */


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseproject.R;
import com.example.firebaseproject.modelo.FechasDeCumpleanios;

import java.util.ArrayList;

public class FechaCumpleaniosAdapter extends RecyclerView.Adapter<FechaCumpleaniosAdapter.ViewHolder> {

    private int resource;
    private ArrayList<FechasDeCumpleanios> listaFechasCumples;

    public FechaCumpleaniosAdapter(ArrayList<FechasDeCumpleanios> listaFechaCumples, int resource){
        this.listaFechasCumples = listaFechaCumples;
        this.resource = resource;
    }


    /* Metodo donde se crea la vista*/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new ViewHolder(view);
    }

    /*Defino los datos que quiero que se muestre en mi vista */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FechasDeCumpleanios fechasDeCumpleanios = listaFechasCumples.get(position);

        holder.txtNombrePersonaCumple.setText(fechasDeCumpleanios.getNombrePersona());
        holder.txtEdadPersonaCumple.setText(fechasDeCumpleanios.getEdadPersona());
        holder.txtFechaPersonaCumple.setText(fechasDeCumpleanios.getFechaCumpleanios());
        holder.txtIdeasRegaloCumple.setText(fechasDeCumpleanios.getIdeasParaRegalar());




    }

    /*Retorna el numero de vistas que estoy obteniendo*/
    @Override
    public int getItemCount() {
        return listaFechasCumples.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNombrePersonaCumple;
        private TextView txtEdadPersonaCumple;
        private TextView txtFechaPersonaCumple;
        private TextView txtIdeasRegaloCumple;
        public View view;

        public ViewHolder (View view){
            super(view);
            this.view = view;
            this.txtNombrePersonaCumple = (TextView) view.findViewById(R.id.fechasDeCumpleaniosNombrePersona);
            this.txtEdadPersonaCumple = (TextView) view.findViewById(R.id.fechasDeCumpleaniosEdadPersona);
            this.txtFechaPersonaCumple = (TextView) view.findViewById(R.id.fechasDeCumpleaniosPersona);
            this.txtIdeasRegaloCumple = (TextView) view.findViewById(R.id.fechasDeCumpleaniosIdeas);
        }

    }
}
