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
    Bitmap imageBitmap,imageBitmap2,imageBitmap3;
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


        //SET EL TITULO
        TextView tituTxt= findViewById(R.id.titulo);
        tituTxt.setText(receta[0]);

        //SET LOS INGREDIENTES
        TextView ingredTxt = findViewById(R.id.ingredientes);
        if(receta[1].contains(",")){
            String[] ingreds= receta[1].split(",");
            int pos=0;
            String ingredBullet = " ";
            while(pos<ingreds.length){
                //String ingredBullet= "&#8226; uning<br/> &#8226; dosing<br/> &#8226; tresing<br/>";
                ingredBullet= ingredBullet+"&#8226; "+ingreds[pos]+"<br/>";
                pos++;
            }
            ingredTxt.setText(Html.fromHtml(ingredBullet));
        }
        else{
            ingredTxt.setText(receta[1]);
        }


        //SET LA PREPARACIÓN
        TextView prepTxt= findViewById(R.id.preparacion);
        prepTxt.setText(receta[2]);


    }
    private void setImages(String im1,String im2,String im3){
        //Log.i("la imagen1",im1);
        byte[] bytes= Base64.decode(im1,Base64.URL_SAFE);
        imageBitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        //Log.i("el bitmap",imageBitmap.toString());

        ImageView img1= findViewById(R.id.imageView1);
        img1.setImageBitmap(imageBitmap);

        bytes= Base64.decode(im1,Base64.URL_SAFE);
        imageBitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        ImageView img2= findViewById(R.id.imageView2);
        img2.setImageBitmap(imageBitmap);

        bytes= Base64.decode(im1,Base64.URL_SAFE);
        imageBitmap3= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        ImageView img3= findViewById(R.id.imageView3);
        img3.setImageBitmap(imageBitmap);
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
                int i=1;//SOLO PARA HACER PRUEBAS, PORQUE LA SEGUNDA RECETA NO TIENE IMAGENES, DESPUES CAMBIARLO A 0
                while(i<json.length()){
                    i1 = json.getJSONObject(i).getString("imagen1");
                    i2 = json.getJSONObject(i).getString("imagen2");
                    i3 = json.getJSONObject(i).getString("imagen3");
                    Log.i("i1",i1);
                    i++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setImages(i1,i2,i3);
    }
}
