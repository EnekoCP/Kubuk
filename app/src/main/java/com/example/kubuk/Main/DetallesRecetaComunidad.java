package com.example.kubuk.Main;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kubuk.R;

import org.w3c.dom.Text;

public class DetallesRecetaComunidad extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_receta_comunidad);
        Bundle extras = getIntent().getExtras();
        String receta[]=extras.getStringArray("receta"); //el orden es: titulo, ingredientes, preparacion.
        Log.i("los extras, titulo", receta[0]);

        TextView tituTxt= findViewById(R.id.titulo);
        tituTxt.setText(receta[0]);

        TextView ingredTxt = findViewById(R.id.ingredientes);
        String ingredBullet= "&#8226; "+receta[1]+"<br/> &#8226; dosing<br/> &#8226; tresing<br/>";
        ingredTxt.setText(Html.fromHtml(ingredBullet));

        TextView prepTxt= findViewById(R.id.preparacion);
        prepTxt.setText(receta[2]);


    }


}
