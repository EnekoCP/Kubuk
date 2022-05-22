package com.example.kubuk.AddEditRecetas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.R;
import com.example.kubuk.User;
import com.example.kubuk.users.LoginActivity;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class AddRecetaActivity extends AppCompatActivity {

    ImageButton guardar;
    EditText nombre,descripcion,ingredientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receta);

        guardar = findViewById(R.id.next);
        nombre = findViewById(R.id.nomReceta);
        descripcion = findViewById(R.id.descripcion);
        ingredientes = findViewById(R.id.ingredientes);

         inicio();

    }

    private void inicio(){
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarDatos()) {
                    Intent i = new Intent(AddRecetaActivity.this, AddRecetaActivity2.class);
                    i.putExtra("name", nombre.getText().toString());
                    i.putExtra("ingredientes", ingredientes.getText().toString());
                    i.putExtra("descripcion", descripcion.getText().toString());

                    startActivity(i);
                    finish();
                }
            }
        });
    }

    /** Método utilizado para validar los datos del formulario de add receta (primera parte) */
    private boolean validarDatos() {

        boolean valido = true;

        //Validamos el email
        String nombreReceta = nombre.getText().toString();
        String ingr = ingredientes.getText().toString();
        String descr = descripcion.getText().toString();

        if (nombreReceta.equals("") || ingr.equals("") || descr.equals("")) { //Si alguno de los campos está vacío
            Toast.makeText(getApplicationContext(), R.string.receta1camposvacios, Toast.LENGTH_SHORT).show();
            valido = false;
        }

        return valido;
    }



}
