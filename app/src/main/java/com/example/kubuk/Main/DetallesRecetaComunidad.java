package com.example.kubuk.Main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class DetallesRecetaComunidad extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {
    Bitmap imageBitmap;
    String email;
    RequestQueue request;
    String i1,i2,i3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_receta_comunidad);
        Bundle extras = getIntent().getExtras();
        String receta[]=extras.getStringArray("receta"); //el orden es: titulo(0), ingredientes(1), preparacion(2), email(3).
        Log.i("los extras, titulo", receta[0]);
        email=receta[3];
        request = Volley.newRequestQueue(this.getApplicationContext());
        cargarWebService();


        TextView tituTxt= findViewById(R.id.titulo);
        tituTxt.setText(receta[0]);

        TextView ingredTxt = findViewById(R.id.ingredientes);
        String ingredBullet= "&#8226; "+receta[1]+"<br/> &#8226; dosing<br/> &#8226; tresing<br/>";
        ingredTxt.setText(Html.fromHtml(ingredBullet));

        TextView prepTxt= findViewById(R.id.preparacion);
        prepTxt.setText(receta[2]);







    }
    private void setImages(){
        Log.i("la imagen1",i1);
        byte[] bytes= Base64.decode(i1,Base64.URL_SAFE);
        imageBitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        ImageView img1= findViewById(R.id.imageView1);
        img1.setImageBitmap(imageBitmap);
    }
    private void cargarWebService() {
        String url="http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/conseguirImgRecetas.php?email="+email;
        url.replace(" ", "%20");
        StringRequest var1 = new StringRequest(Request.Method.GET, url,this,this);

        request.add(var1);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Log.i("la respuesta",response);
        if(!response.equals("false")){
            JSONArray json = null;
            try {
                json=new JSONArray(response);
                int i=0;
                while(i<json.length()){
                    i1 = json.getJSONObject(i).getString("imagen1");
                    i2 = json.getJSONObject(i).getString("imagen2");
                    i3 = json.getJSONObject(i).getString("imagen3");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setImages();
    }
}
