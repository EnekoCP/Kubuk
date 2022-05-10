package com.example.kubuk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kubuk.login.LoginActivity;
import com.example.kubuk.login.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    Button accederBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accederBoton = findViewById(R.id.accederButton);

        accederBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceder();
            }
        });

    }

    public void acceder(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}