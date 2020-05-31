package com.example.firebaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class VentanaPrincipalActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button btnAgregarFecha;
    private Button btnMisFechas;
    private Button btnCerrarSesion;
    private CalendarView calendarAgregarFecha;
    // declaro el objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_principal);
        initialize();
    }

    private void initialize(){
        btnAgregarFecha  = (Button) findViewById(R.id.btn_AgregarFecha);
        btnMisFechas = (Button) findViewById(R.id.btn_MisFechas);
        btnCerrarSesion = (Button) findViewById(R.id.btn_cerrarSesion);

        // le agrego accion a los botones
        btnAgregarFecha.setOnClickListener(this);
        btnMisFechas.setOnClickListener(this);
        btnCerrarSesion.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();


    }
    private void abrirVentanaAgregarFecha() {
        Intent intent = new Intent(getApplication(), VentanaAgregarFechaActivity.class);
        startActivity(intent);
    }

    private void abrirVentanaMisFechas() {
        Intent intent = new Intent(getApplication(), VentanaMisFechasActivity.class);
        startActivity(intent);
    }


    private void cerrarSession() {
        firebaseAuth.signOut();
        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);
        //pongo un finish para no poder volver hacia atras
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_AgregarFecha:
                abrirVentanaAgregarFecha();
                break;
            case R.id.btn_MisFechas:
                abrirVentanaMisFechas();
                break;
            case R.id.btn_cerrarSesion:
                cerrarSession();
                break;
            default:

        }
    }


}
