package com.example.kubuk.ListaCompra;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.R;

public class ActualizarCheckBox extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener{
    RequestQueue request;
    String email,elemento;
    boolean marcado;
    Context context;
    AlertDialog dialog;

    public ActualizarCheckBox(String e, boolean m, Context c,String el){
        this.email=e;
        this.marcado=m;
        this.context=c;
        this.elemento=el;
    }
    protected void onCreate(Bundle var1) {

        super.onCreate(var1);
        Bundle extra=getIntent().getExtras();
        Log.i("ha entrado","en actualizar");
        email=extra.getString("usuario");

        marcado= extra.getBoolean("marcado");
        cambiarMarcado();
        actualizarMarcado();
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorServidor), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(String response) {
        Log.i("bien el actualizar",response);


    }
    private void cambiarMarcado(){
        if((marcado)){
            marcado= (false);
        }
        else{
            marcado= (true);
        }
    }


    public void actualizarMarcado() {
        request = Volley.newRequestQueue(context.getApplicationContext());
        cambiarMarcado();
        String url = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/actualizarcheckbox.php?usuario=" + email+"&marcado="+marcado+"&elemento="+elemento;
        url = url.replace(" ", "%20");
        Log.i("url",url);
        StringRequest var1 = new StringRequest(Request.Method.GET, url, this, this);
        request.add(var1);
    }
}
