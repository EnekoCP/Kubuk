package com.example.kubuk.login;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.Main.MenuMain;
import com.example.kubuk.R;

import java.util.Locale;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    EditText textEmail, textPasswd;
    Button loginBoton, registerBoton;
    RequestQueue request;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (savedInstanceState!= null) {
            language =savedInstanceState.getString("language");
        }
        setContentView(R.layout.activity_login);

        TextView eu=(TextView) findViewById(R.id.euskera);
        TextView en=(TextView) findViewById(R.id.ingles);
        TextView es=(TextView) findViewById(R.id.español);

        textEmail = findViewById(R.id.textEmailReg);
        textPasswd = findViewById(R.id.textPasswordReg1);

        request = Volley.newRequestQueue(getApplicationContext());

        loginBoton = findViewById(R.id.accederButton);

        loginBoton.setOnClickListener(view -> {
            if (validarDatos()){ //En caso de que todos los datos sean correctos:
                cargarWebService();
                Intent i= new Intent(LoginActivity.this, MenuMain.class);
                startActivity(i);
            }
        });

        registerBoton = findViewById(R.id.buttonRegistrarse);

        registerBoton.setOnClickListener(view -> {
            registrarse();
        });

        //euskera button (textView) clicked, change language
        eu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info-paso","Euskera");
                language="eu";
                changeLanguage();
                finish();
                startActivity(getIntent());
            }
        });

        // english button (textview) clicked change language
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info-paso","Ingelesa");
                language="en";
                changeLanguage();
                finish();
                startActivity(getIntent());
                //onStart();
            }
        });

        //spanish button (textview) clicked change language
        es.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info-paso","Castellano");
                language="es";
                changeLanguage();
                finish();
                startActivity(getIntent());
                // onStart();
            }
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

        String url = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/lgonzalez184/WEB/inicioSesionKubuk.php?email="
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
        //TODO
    }

    /** Called when the user taps the Registrarse button */
    private void registrarse(){
        finish();
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //metodo laguntzaile to change language
    private void changeLanguage(){

        Locale nuevaloc = new Locale(language);
        Locale.setDefault(nuevaloc);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);

        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("language",language );
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        language = savedInstanceState.getString("language");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i("CONTROL","login ON PAUSE");

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("CONTROL"," login ON START");


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("CONTROL","login ON RESUME");


    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i("CONTROL","login ON STOP");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("CONTROL","login ON DESTROY");

    }
}