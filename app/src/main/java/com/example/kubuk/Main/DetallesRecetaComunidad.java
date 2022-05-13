package com.example.kubuk.Main;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;
import com.example.kubuk.AddEditRecetas.EditRecetaActivity;
import com.example.kubuk.R;

import org.w3c.dom.Text;

public class DetallesRecetaComunidad extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_receta_comunidad);

        TextView ingredTxt = findViewById(R.id.ingredientes);
        String ingredBullet= "&#8226; uning<br/> &#8226; dosing<br/> &#8226; tresing<br/>";

        ingredTxt.setText(Html.fromHtml(ingredBullet));
    }


}
