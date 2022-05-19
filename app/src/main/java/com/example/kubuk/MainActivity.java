package com.example.kubuk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kubuk.users.LoginActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button accederBoton;
    String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState!= null) {
            language =savedInstanceState.getString("language");
        }

        setContentView(R.layout.activity_main);

        accederBoton = findViewById(R.id.accederButton);

        accederBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceder();
            }
        });

        TextView eu=(TextView) findViewById(R.id.euskera);
        TextView en=(TextView) findViewById(R.id.ingles);
        TextView es=(TextView) findViewById(R.id.español);

        //euskera button (textView) clicked, change language
        eu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info-paso","Euskera");
                language="eu";
                changeLanguage();
                finish();
                startActivity(getIntent());
            }
        });

        // english button (textview) clicked change language
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info-paso","Ingelesa");
                language="en";
                changeLanguage();
                finish();
                startActivity(getIntent());
                //onStart();
            }
        });

        //spanish button (textview) clicked change language
        es.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info-paso","Castellano");
                language="es";
                changeLanguage();
                finish();
                startActivity(getIntent());
                // onStart();
            }
        });
    }

    public void acceder(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //metodo laguntzaile to change language
    private void changeLanguage(){

        Locale nuevaloc = new Locale(language);
        Locale.setDefault(nuevaloc);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);

        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("language",language );
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        language = savedInstanceState.getString("language");
    }
}