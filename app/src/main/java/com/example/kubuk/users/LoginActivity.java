package com.example.kubuk.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.Main.MenuMain;
import com.example.kubuk.Main.ServicioFirebase;
import com.example.kubuk.R;
import com.example.kubuk.User;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    EditText textEmail, textPasswd;
    Button loginBoton, registerBoton;
    RequestQueue request;
    ServicioFirebase firebase=new ServicioFirebase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        textEmail = findViewById(R.id.textEmailReg);
        textPasswd = findViewById(R.id.textPasswordReg1);



        request = Volley.newRequestQueue(getApplicationContext());

        loginBoton = findViewById(R.id.accederButton);

        loginBoton.setOnClickListener(view -> {
            if (validarDatos()){ //En caso de que todos los datos sean correctos:
                cargarWebService();
            }
        });

        registerBoton = findViewById(R.id.buttonRegistrarse);

        registerBoton.setOnClickListener(view -> {
            registrarse();
        });



    }

    /** Método utilizado para validar los datos del formulario de inicio de sesión */
    private boolean validarDatos() {

        boolean valido = true;

        //Validamos el email
        String email = textEmail.getText().toString();
        Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        if (email.equals("")) { //Si el email está vacío
            Toast.makeText(getApplicationContext(), getString(R.string.emailVacio), Toast.LENGTH_SHORT).show();
            textEmail.setText("");
            valido = false;
        } else if (!patternEmail.matcher(email).matches()) { //Si el email no es correcto
            Toast.makeText(getApplicationContext(), getString(R.string.emailNoValido), Toast.LENGTH_SHORT).show();
            textEmail.setText("");
            valido = false;
        }

        //Validamos la contraseña
        String passwd = textPasswd.getText().toString();
        if (passwd.equals("")) { //Si el EditText de Password está vacío
            Toast.makeText(getApplicationContext(), getString(R.string.passwdVacia), Toast.LENGTH_SHORT).show();
            textPasswd.setText("");
            valido = false;
        } else if (passwd.length() < 8 || passwd.length() > 16) { //Si el password no cumple la longitud mínima o máxima
            Toast.makeText(getApplicationContext(), getString(R.string.passwdLarga), Toast.LENGTH_SHORT).show();
            textPasswd.setText("");
            valido = false;
        }

        return valido;
    }

    /** Método utilizado para validar el inicio de sesión con los datos que existen en la BBDD remota */
    private void cargarWebService() {

        String url = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/inicioSesionKubuk.php?email="
                +textEmail.getText().toString() + "&passwd=" +textPasswd.getText().toString();

        url = url.replace(" ", "%20");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this,this);
        request.add(stringRequest);

    }
    
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorServidor), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(String response) {
        Log.d("Respuesta", response.trim());
        String respuesta = response.trim();
        switch (respuesta){
            case "Login_ok":
                User.setUsuario(textEmail.getText().toString());

                firebase.generarToken();
                saveToken();

                Intent i= new Intent(LoginActivity.this, MenuMain.class);
                i.putExtra("usuario",textEmail.getText().toString());
                i.putExtra("login","true");
                startActivity(i);
                finish();
                Log.i("LOGIN", "Login Ok");
                break;
            case "Login_emailnoexiste":
                Toast.makeText(getApplicationContext(), getString(R.string.usuarioNoexiste), Toast.LENGTH_SHORT).show();
                Log.i("LOGIN", "Email no existe");
                break;
            case "Login_passwdnotvalid":
                Toast.makeText(getApplicationContext(),  getString(R.string.contraseñaIncorrecta), Toast.LENGTH_SHORT).show();
                Log.i("LOGIN", "Contraseña incorrecta");
                break;
        }

    }

    /** Called when the user taps the Registrarse button */
    private void registrarse(){
        finish();
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void saveToken() {
        String url = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/notificacionValoracion.php?user="
                +User.getUsuario() + "&fromToken=" +firebase.getToken()+"&funcion=save";

        url = url.replace(" ", "%20");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this,this);

        request.add(stringRequest);

    }






}