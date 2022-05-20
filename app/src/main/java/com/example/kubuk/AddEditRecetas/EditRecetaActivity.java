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
import com.example.kubuk.myRecipes.MyRecipes;

import org.json.JSONArray;
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

        getDatos();

        inicio();

    }

    private void inicio(){

        Intent i = new Intent(EditRecetaActivity.this,MyRecipes.class);
        i.putExtra("usuario", User.getUsuario());
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDatos();
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDatos();
                startActivity(i);
            }
        });
    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(this,getString(R.string.deleteRecetaMssg), Toast.LENGTH_SHORT).show();

        Intent inicioApp = new Intent(this, MyRecipes.class);
       startActivity(inicioApp);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("ERRORRR" + error.toString());
        error.printStackTrace();
        Toast.makeText(this,getString(R.string.errorRecetaBorrarMssg), Toast.LENGTH_SHORT).show();

    }

    private void getDatos() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/getDatos2.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        System.out.println("AQUIIIIIIIIIIIIII" + response);

                        JSONArray jsonObject = null;
                        try {
                            jsonObject = new JSONArray(response);

                            String titulo = jsonObject.getJSONObject(0).getString("titulo");
                            nombre.setText(titulo);
                            String descripcion2 = jsonObject.getJSONObject(0).getString("preparacion");
                            descripcion.setText(descripcion2);
                            String ingredientes2 = jsonObject.getJSONObject(0).getString("ingredientes");
                            ingredientes.setText(ingredientes2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), getString(R.string.datosGet), Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), getString(R.string.errorServidor), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("name", "prueba" );
                parametros.put("user", User.getUsuario());

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updateDatos() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/update.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        System.out.println("AQUIIIIIIIIIIIIII" + response);

                        Toast.makeText(getApplicationContext(), getString(R.string.datosUpdated), Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("AQUIIIIIIIIIIIIII" + error.toString());

                System.out.println(nombre.getText().toString());
                System.out.println(descripcion.getText().toString());
                System.out.println(ingredientes.getText().toString());

                Toast.makeText(getApplicationContext(), getString(R.string.errorServidor), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("name", "prueba" );
                parametros.put("user", User.getUsuario());
                parametros.put("newName", nombre.getText().toString());
                parametros.put("descripcion", descripcion.getText().toString());
                parametros.put("ingredientes", ingredientes.getText().toString());


                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void deleteDatos(){
        // Instantiate the RequestQueue.
        String url ="http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/deleteReceta.php?name="+nombre.getText().toString()+"&user="+User.getUsuario();

        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,this,this);

        queue.add(jsonRequest);
    }

}