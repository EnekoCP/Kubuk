package com.example.kubuk.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.R;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    EditText textEmail, textPasswd;
    Button loginBoton;
    RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textEmail = findViewById(R.id.textEmailLogin);
        textPasswd = findViewById(R.id.textPasswordLogin);

        request = Volley.newRequestQueue(getApplicationContext());

        loginBoton = findViewById(R.id.buttonLogin);

        loginBoton.setOnClickListener(view -> {
            if (validarDatos()){ //En caso de que todos los datos sean correctos:
                cargarWebService();
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
        
        //TODO: Modificar el php con la BBDD remota a usar
        String url = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/lgonzalez184/WEB/inicioSesion.php?email="
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
}