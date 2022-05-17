package com.example.kubuk.AddEditRecetas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.R;
import com.example.kubuk.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class EditRecetaActivity extends AppCompatActivity implements Response.Listener<JSONObject> ,Response.ErrorListener{

    Button guardar,delete;
    EditText nombre,descripcion,ingredientes;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_receta);


        guardar = findViewById(R.id.save);
        delete = findViewById(R.id.delete);
        nombre = findViewById(R.id.nomRecetaEdit);
        descripcion = findViewById(R.id.descripcionEdit);
        ingredientes = findViewById(R.id.ingredientesEdit);



        queue = Volley.newRequestQueue(EditRecetaActivity.this);

        inicio();

    }

    private void inicio(){
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDatos();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDatos();
            }
        });
    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(this,"Actualizacion correcta", Toast.LENGTH_SHORT).show();

        //Intent inicioApp = new Intent(this,AddRecetaActivity.class);
       // startActivity(inicioApp);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("ERRORRR" + error.toString());
        error.printStackTrace();
        Toast.makeText(this,"Error en el update", Toast.LENGTH_SHORT).show();

    }

    private void getImagenes() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/getDatos.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("AQUIIIIIIIIIIIIII");

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("OBJECT : "+jsonObject.toString());

                        Toast.makeText(getApplicationContext(), "Datos obtenidos", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR EN LA CONEXION", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new Hashtable<String, String>();
                //parametros.put("Name",  );
                parametros.put("User", User.getUsuario());

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updateDatos(){
        // Instantiate the RequestQueue.
        String url ="http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/update.php?name="+nombre.getText().toString()+"&descripcion="+descripcion.getText().toString()+
                "&ingredientes="+ingredientes.getText().toString()+"&user="+User.getUsuario();

        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,this,this);

        queue.add(jsonRequest);
    }

    private void deleteDatos(){
        // Instantiate the RequestQueue.
        String url ="http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/delete.php?name="+nombre.getText().toString()+"&user="+User.getUsuario();

        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,this,this);

        queue.add(jsonRequest);
    }

}