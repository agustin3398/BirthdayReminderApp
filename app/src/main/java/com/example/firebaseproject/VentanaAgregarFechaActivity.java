package com.example.firebaseproject;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebaseproject.modelo.FechasDeCumpleanios;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class VentanaAgregarFechaActivity extends AppCompatActivity {

    // instancio obejtos de mi vista
    private CompactCalendarView compactCalendar;
    private TextView textDiaSeleccionado;

    //instancio mis objetos dentro de la vista del popUp
    EditText nombrePersona;
    EditText edadPersona;
    EditText fechaSeleccionada;
    EditText ideasParaRegalo;
    Button btnCerrarPopupFechaSel;
    Button btnGuardarDatosPopUpFechaSel;

    //dialogo para la fecha seleccionada
    private Dialog dialogoPopUpFechaSeleccionada;

    // instancion mi objeto base de datos firebase
    private DatabaseReference dbFechaCumpleanios;

    // instancio mi usuario actual
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_fecha);
        initialize();
    }

    private void initialize() {

        // inicializo objetos de mi vista
        compactCalendar = (CompactCalendarView) findViewById(R.id.compactCalendarView);
        textDiaSeleccionado = (TextView) findViewById(R.id.textDiaSeleccionado);
        dialogoPopUpFechaSeleccionada = new Dialog(this);
        dbFechaCumpleanios = FirebaseDatabase.getInstance().getReference("Fechas de Cumpleaños");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                textDiaSeleccionado.setText(dateClicked.toString());
                abrirFechaPopUpFechaSel();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                SimpleDateFormat dataFormat = new SimpleDateFormat("MMMM - YYYY", Locale.getDefault());
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(dataFormat.format(firstDayOfNewMonth));
            }
        });

    }

    public void abrirFechaPopUpFechaSel() {

        dialogoPopUpFechaSeleccionada.setContentView(R.layout.fecha_seleccionada_popup);
        dialogoPopUpFechaSeleccionada.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnCerrarPopupFechaSel = (Button) dialogoPopUpFechaSeleccionada.findViewById(R.id.buttonClosePopUp);
        btnGuardarDatosPopUpFechaSel = (Button) dialogoPopUpFechaSeleccionada.findViewById(R.id.buttonGuardarDatosPopUp);

        btnCerrarPopupFechaSel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogoPopUpFechaSeleccionada.dismiss();
            }
        });

        btnGuardarDatosPopUpFechaSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarFechaDeCumpleanios();
            }
        });

        dialogoPopUpFechaSeleccionada.show();
    }


    public void registrarFechaDeCumpleanios() {
        // inicializo objetos de mi popUpFechaSeleccionada
        nombrePersona = (EditText) dialogoPopUpFechaSeleccionada.findViewById(R.id.txtNombrePersona);
        edadPersona = (EditText) dialogoPopUpFechaSeleccionada.findViewById(R.id.txtEdadPersona);
        fechaSeleccionada = (EditText) dialogoPopUpFechaSeleccionada.findViewById(R.id.txtFechaSeleccionada);
        ideasParaRegalo = (EditText) dialogoPopUpFechaSeleccionada.findViewById(R.id.txtIdeasRegalo);

        String userId = firebaseUser.getUid();
        String nombrePersonaCumpl = nombrePersona.getText().toString();
        String edadPersonaCumpl = edadPersona.getText().toString();
        String fechaSeleccionadaCumpl = fechaSeleccionada.getText().toString();
        String ideasRegaloCumpl = ideasParaRegalo.getText().toString();

        if (!TextUtils.isEmpty(nombrePersonaCumpl) && !TextUtils.isEmpty(edadPersonaCumpl) && !TextUtils.isEmpty(fechaSeleccionadaCumpl)) {
            String id = dbFechaCumpleanios.push().getKey();
            FechasDeCumpleanios fechas = new FechasDeCumpleanios(id, userId, nombrePersonaCumpl, edadPersonaCumpl, fechaSeleccionadaCumpl, ideasRegaloCumpl);
            dbFechaCumpleanios.child("FechasCumpleanios").child(id).setValue(fechas);

            //muestro mensaje de guardar fecha y cierro popUp
            Toast.makeText(this, "Fecha Guardada", Toast.LENGTH_LONG).show();
            dialogoPopUpFechaSeleccionada.dismiss();

        } else {
            Toast.makeText(this, "Debe Rellenar los Campos Vacios", Toast.LENGTH_LONG).show();
        }
    }
}
