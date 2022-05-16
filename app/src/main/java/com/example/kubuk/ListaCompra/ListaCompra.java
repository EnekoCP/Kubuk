package com.example.kubuk.ListaCompra;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.toolbox.Volley;
import com.example.kubuk.Main.MenuMain;
import com.example.kubuk.Main.RecetasComunidad;
import com.example.kubuk.R;
import com.example.kubuk.myRecipes.MyRecipes;

public class ListaCompra extends AppCompatActivity {
    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(R.layout.activity_lista_compra);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        myToolbar.setSubtitleTextColor(0);

        //request = Volley.newRequestQueue(this.getApplicationContext());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem var1) {
        switch (var1.getItemId()) {
            case R.id.recetascomunidad:
                //Toast.makeText(this, "deberia entrar en la comunidad", Toast.LENGTH_LONG).show();
                Intent i= new Intent(ListaCompra.this, RecetasComunidad.class);
                startActivity(i);
                return true;
            case R.id.misrecetas:
                //Toast.makeText(this, "debería entrar en la lista de mis recetas", Toast.LENGTH_LONG).show();
                Intent imy= new Intent(ListaCompra.this, MyRecipes.class);
                startActivity(imy);
                return true;
            case R.id.listacompra:
                //Toast.makeText(this, "deberia entrar en la lista de la compra", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(var1);
        }
    }

}
