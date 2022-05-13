/*
package com.example.kubuk.AddEditRecetas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.R;
import com.example.kubuk.User;

import org.json.JSONObject;

public class AddRecetaActivity extends AppCompatActivity implements Response.Listener<JSONObject> ,Response.ErrorListener{

    ImageButton guardar;
    EditText nombre,descripcion,ingredientes;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receta);


    guardar = findViewById(R.id.next);
    nombre = findViewById(R.id.nomRecetaEdit);
    descripcion = findViewById(R.id.descripcion);
    ingredientes = findViewById(R.id.ingredientes);

    queue = Volley.newRequestQueue(AddRecetaActivity.this);

    inicio();

    }

    private void inicio(){
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meterPrimerosDatos();
            }
        });
    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(this,"Siguiente paso", Toast.LENGTH_SHORT).show();

        Intent inicioApp = new Intent(this,AddRecetaActivity2.class);
        startActivity(inicioApp);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("ERRORRR" + error.toString());
        Toast.makeText(this,"Error en el registro", Toast.LENGTH_SHORT).show();

    }


    private void meterPrimerosDatos(){
        // Instantiate the RequestQueue.
        String url ="http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/ecalvo023/WEB/add1.php?name="+nombre.getText().toString()+"&descripcion="+descripcion.getText().toString()+
                "&ingredientes="+ingredientes.getText().toString()+"&user="+ User.getUsuario();

        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,this,this);

        queue.add(jsonRequest);
    }


}*/
