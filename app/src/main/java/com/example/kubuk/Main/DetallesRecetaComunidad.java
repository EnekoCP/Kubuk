package com.example.kubuk.Main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.R;
import com.example.kubuk.User;

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
    String receta[];
    String titulo;
    String accion="receta";
    Uri imagenUri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_receta_comunidad);
        Bundle extras = getIntent().getExtras();
        receta=extras.getStringArray("receta"); //el orden es: titulo(0), ingredientes(1), preparacion(2), email(3).
        Log.i("los extras, titulo", receta[0]);
        email=receta[3];
        titulo=receta[0];
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
            ingredTxt.setMovementMethod(new ScrollingMovementMethod());
        }
        else{
            ingredTxt.setText(receta[1]);
        }


        //SET LA PREPARACI??N
        TextView prepTxt= findViewById(R.id.preparacion);
        if(receta[2].contains("/")){
            String[] prep= receta[2].split("/");
            int pos=0;
            String prepBullet = " ";
            while(pos<prep.length){
                //String ingredBullet= "&#8226; uning<br/> &#8226; dosing<br/> &#8226; tresing<br/>";
                prepBullet= prepBullet+"&#8226; "+prep[pos]+"<br/>";
                pos++;
            }
            prepTxt.setText(Html.fromHtml(prepBullet));
            prepTxt.setMovementMethod(new ScrollingMovementMethod());
        }
        else{
            prepTxt.setText(receta[2]);
        }
        //prepTxt.setText(receta[2]);


        //SET RATING BAR
        RatingBar valorar=findViewById(R.id.valorar);
        getValoracion();

        valorar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                    valorar.setRating(rating);
                    ratingBar.setRating(rating);

                    setValoracion(Math.round(rating));
                    sendMessage(Math.round(rating));

            }
        });
        valorar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!User.getUsuario().equals(email)){
                    if (event.getAction() == MotionEvent.ACTION_UP) {


                        float touchPositionX = event.getX();
                        float width = valorar.getWidth();
                        float starsf = (touchPositionX / width) * 5.0f;
                        int stars = (int)starsf + 1;
                        valorar.setRating(stars);

                        setValoracion(Math.round(stars));
                        accion="sendmssg";

                        sendMessage(Math.round(stars));

                    }
                }
                return true;
            }
        });


    }
    private void setImages(String im1,String im2,String im3){

        ImageView img1 = findViewById(R.id.imageView1);
        ImageView img2 = findViewById(R.id.imageView2);
        ImageView img3= findViewById(R.id.imageView3);
        imagenUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.logo_pequeno);

        if(im1!=null ) {
            Log.i("la imagen1", im1);
            byte[] bytes = Base64.decode(im1, Base64.URL_SAFE);
            imageBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            //Log.i("el bitmap",imageBitmap.toString());

            img1.setImageBitmap(imageBitmap);

        } else{
            //En caso de que la receta no tuviese imagen 1
            img1.setImageURI(imagenUri);
        }

        if(im2!=null) {
            Log.i("la imagen2", im2);
            byte[] bytes2 = Base64.decode(im2, Base64.URL_SAFE);
            imageBitmap2 = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);

            img2.setImageBitmap(imageBitmap);

        }
        else{
            //En caso de que la receta no tuviese imagen 2
            img2.setImageURI(imagenUri);
        }

        if(im3!=null){
            Log.i("la imagen3", im3);
            byte[] bytes3= Base64.decode(im3,Base64.URL_SAFE);
            imageBitmap3= BitmapFactory.decodeByteArray(bytes3,0,bytes3.length);
            img3.setImageBitmap(imageBitmap);
        }
        else{
            //En caso de que la receta no tuviese imagen 3
            img3.setImageURI(imagenUri);
        }


    }
    private void cargarWebService() {
        accion="receta";
        String url="http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/conseguirImgRecetas.php?email="+email+"&titulo="+receta[0];
        url.replace(" ", "%20");
        StringRequest var1 = new StringRequest(Request.Method.GET, url,this,this);

        request.add(var1);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorServidor), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(String response) {
        Log.i("la respuesta",response);
        if(!response.equals("false")){
            JSONArray json = null;
            try {
                json=new JSONArray(response);
                int i=0;//SOLO PARA HACER PRUEBAS, PORQUE LA SEGUNDA RECETA NO TIENE IMAGENES, DESPUES CAMBIARLO A 0
                while(i<json.length()){
                    if(true) {
                        i1 = json.getJSONObject(i).getString("imagen1");
                        i2 = json.getJSONObject(i).getString("imagen2");
                        i3 = json.getJSONObject(i).getString("imagen3");
                        Log.i("i1", i1);
                        String result=json.getJSONObject(i).getString("resultado");
                        try{
                            setPuntos(result);
                        }catch (NumberFormatException e){
                        }
                    }
                    i++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        setImages(i1, i2, i3);


        if(!response.equals("false")){
            JSONArray json = null;
            try {
                json=new JSONArray(response);
                int i=0;//SOLO PARA HACER PRUEBAS, PORQUE LA SEGUNDA RECETA NO TIENE IMAGENES, DESPUES CAMBIARLO A 0
                while(i<json.length()){
                        String result=json.getJSONObject(i).getString("resultado");
                        try{
                            setPuntos(result);
                        }catch (NumberFormatException e){
                        }
                    i++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }



    private void getValoracion(){
        accion="get";
        if(User.getUsuario().equals(email)){
            RatingBar ratingBar=findViewById(R.id.valorar);
            ratingBar.setIsIndicator(true);
        }
        String url="http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/puntuacion.php?funcion=get&autor="+email+"&receta="+titulo+"&email="+User.getUsuario();
        url.replace(" ", "%20");
        StringRequest var1 = new StringRequest(Request.Method.GET, url,this,this);

        request.add(var1);

    }

    private void setValoracion(int valoracion){
        accion="set";
        String url="http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/puntuacion.php?funcion=set&autor="+email+"&receta="+titulo+"&email="+User.getUsuario()+"&puntuacion="+valoracion;
        url.replace(" ", "%20");
        StringRequest var1 = new StringRequest(Request.Method.GET, url,this,this);

        request.add(var1);

    }

    private void setPuntos(String puntos){
        RatingBar ratingBar=findViewById(R.id.valorar);
        ratingBar.setRating(Float.parseFloat(puntos));


    }

    private void sendMessage(int puntos){
        if(accion=="sendmssg"){
            ServicioFirebase firebase=new ServicioFirebase();
            String url = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/notificacionValoracion.php?user="
                    +User.getUsuario() + "&fromToken=" +firebase.getToken()+"&funcion=send&autor="+email+"&receta="+titulo+"&puntuacion="+puntos;

            url = url.replace(" ", "%20");
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this,this);

            request.add(stringRequest);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(this,MenuMain.class);
        intent.putExtra("usuario",User.getUsuario());
        intent.putExtra("login","false");
        finish();
        startActivity(intent);
    }


}
