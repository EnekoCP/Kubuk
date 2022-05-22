package com.example.kubuk.ListaCompra;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class EliminarElementoListaC extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener{
    private String email;
    private String elemento;
    RequestQueue request;
    public EliminarElementoListaC(String e, String elem, RequestQueue req){
        this.email=e;
        this.elemento=elem;
        request= req;
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Log.i("ha salido bien",response);
    }


    public void eliminarElem(){
        String url = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/eliminarelemListaC.php?email="
                +email + "&elemento=" +elemento;

        url = url.replace(" ", "%20");
        Log.i("el url",url);
        StringRequest var1 = new StringRequest(Request.Method.GET,url,this,this);
        request.add(var1);
    }
}
