package com.example.firebaseproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // definimos los objeto de la vista
    private EditText textEmail;
    private EditText textPassowrd;
    private Button btnRegistrar;
    private Button btnIngresar;
    private ProgressDialog progressDialog;
    private TextView textForgotPassword;
    private ImageButton btnInfo;

    // creo dialogo para abrir popup
    private Dialog dialogoPopUp;

    // declaro el objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    //declaro mi objeto usario de base da datos
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    /* Hago esto para abrir la ventana principal si el usuario no cerro su session
     * para evitar que se logue cada vez que inicie la aplicacion*/
    @Override
    public void onStart() {
        super.onStart();

        if (firebaseUser != null) {
            Intent intent = new Intent(this, VentanaPrincipalActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }

    private void initialize() {
        // inicializo el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //Referenciamos los objetos de la vista
        textEmail = (EditText) findViewById(R.id.txtNombre);
        textPassowrd = (EditText) findViewById(R.id.txtPassword);
        btnRegistrar = (Button) findViewById(R.id.btn_registrar);
        btnIngresar = (Button) findViewById(R.id.btn_ingresar);
        textForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        btnInfo = (ImageButton) findViewById(R.id.btn_info);

        progressDialog = new ProgressDialog(this);
        dialogoPopUp = new Dialog(this);

        //le agrego accion al boton
        btnRegistrar.setOnClickListener(this);
        btnIngresar.setOnClickListener(this);
        textForgotPassword.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
    }

    private void registrarUsuario() {
        String email = textEmail.getText().toString().trim();
        String password = textPassowrd.getText().toString().trim();

        // Verifico que los campos de texto no esten vacias
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Debe ingresar un correo valido", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando registro...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Se ha registrado el usuario " + textEmail.getText() + " correctamente.", Toast.LENGTH_LONG).show();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) { //esto lo hago por si ya tengo un usuario registrado con el mismo correo
                        Toast.makeText(MainActivity.this, "El usuario " + textEmail.getText() + " ya se encuentra registrado.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error al registrar usuario, verifique que el correo esta correcto.", Toast.LENGTH_LONG).show();
                    }
                }
                progressDialog.dismiss();
            }
        });
    }

    private void ingresarUsuario() {
        String email = textEmail.getText().toString().trim();
        String password = textPassowrd.getText().toString().trim();

        // Verifico que los campos de texto no esten vacias
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Debe ingresar un correo valido", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Ingresando...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "El usuario no se encuentra registrado o la contraseña ingresada es incorrecta.", Toast.LENGTH_LONG).show();
                } else {
                    // termino mi actividad asi luego de ingresar no permito que vuelvan a mi login.
                    finish();

                    Intent intent = new Intent(getApplication(), VentanaPrincipalActivity.class);
                    startActivity(intent);
                }
                progressDialog.dismiss();
            }
        });
    }

    private void olvideContrasenia() {
        String email = textEmail.getText().toString().trim();

        // el campo email no puede estar vacio
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Debe ingresar un correo valido", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.getException() instanceof FirebaseAuthUserCollisionException) { //hago esto para verificar que cuando presione olvide contraseña, el correo este registrado
                    Toast.makeText(MainActivity.this, "El usuario no se encuentra registrado.", Toast.LENGTH_LONG).show();
                }
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Hemos enviado un email a " + textEmail.getText(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "El email " + textEmail.getText() + " no se encuentra registrado.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void abrirPopUp() {

        Button btnCerrarPopUp;

        dialogoPopUp.setContentView(R.layout.popup);
        dialogoPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnCerrarPopUp = (Button) dialogoPopUp.findViewById(R.id.buttonClosePopUp);
        btnCerrarPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoPopUp.dismiss();
            }
        });
        dialogoPopUp.show();
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_registrar:
                registrarUsuario();
                break;
            case R.id.btn_ingresar:
                ingresarUsuario();
                break;
            case R.id.txtForgotPassword:
                olvideContrasenia();
                break;
            case R.id.btn_info:
                abrirPopUp();
                break;
            default:
        }

    }


}
