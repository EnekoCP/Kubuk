package com.example.kubuk.myRecipes;

import static com.example.kubuk.myRecipes.Utilidades.TABLA_RECETA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kubuk.R;

import java.util.ArrayList;
import java.util.Date;

public class MyRecipes extends AppCompatActivity {

    RecyclerView recipeRecycler;
    RecipeOverviewAdapter recipeOverview;
    ArrayList<Recipe> listaRecetas;
    SQLiteOpenHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        construirRecycler();
        recipeRecycler.setAdapter(recipeOverview);
    }

    private void consultarListaRecetas() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Recipe recipe = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_RECETA, null);

        while (cursor.moveToNext()) {
            recipe = new Recipe(cursor.getLong(0));
            recipe.setTitulo(cursor.getString(1));
            recipe.setTexto(cursor.getString(2));

            listaRecetas.add(recipe);
        }
    }

    private void construirRecycler() {
        recipeRecycler = findViewById(R.id.recycler);
        recipeRecycler.setLayoutManager(new LinearLayoutManager(this));

        listaRecetas = new ArrayList<Recipe>();

        //Boogey para pruebas
        Recipe recipe1 = new Recipe(new Date().getTime());
        recipe1.setTitulo("Receta Prueba 1");
        recipe1.setTexto("qwertyuiopasdfghjklñzxcvbnm");
        listaRecetas.add(recipe1);
        Recipe recipe2 = new Recipe(new Date().getTime());
        recipe2.setTitulo("Tortilla");
        recipe2.setTexto("mnbvcxzñlkjhgfdsapoiuytrewqmnbvcxzñlkjhgfdsapoiuytrewq");
        listaRecetas.add(recipe2);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_recetas", null, 1);
        consultarListaRecetas();


        recipeOverview = new RecipeOverviewAdapter(listaRecetas, this);
        recipeOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        listaRecetas.get(recipeRecycler.getChildAdapterPosition(view)).getTitulo(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View view) {
        /*
        Intent miIntent = new Intent(MainActivity.this, IntroducirDatosActivity.class);
        startActivity(miIntent);
        finish();
        */
        Toast.makeText(this.getApplicationContext(), "Abrir pantalla editar vacía", Toast.LENGTH_LONG).show();

    }


}