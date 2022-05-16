package com.example.kubuk.myRecipes;

import static com.example.kubuk.myRecipes.Utilidades.TABLA_RECETA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.kubuk.AddEditRecetas.EditRecetaActivity;
import com.example.kubuk.Main.MenuMain;
import com.example.kubuk.MainActivity;
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
        setContentView(R.layout.my_recipes);

        construirRecycler();
        recipeRecycler.setAdapter(recipeOverview);
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
                Intent i= new Intent(MyRecipes.this, MenuMain.class);
                startActivity(i);
                return true;
            case R.id.misrecetas:
                //Toast.makeText(this, "debería entrar en la lista de mis recetas", Toast.LENGTH_LONG).show();
                //Intent i= new Intent(MenuMain.this, MyRecipes.class);
                //startActivity(i);
                return true;
            case R.id.listacompra:
                //Toast.makeText(this, "deberia entrar en la lista de la compra", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(var1);
        }
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
        Intent miIntent = new Intent(MyRecipes.this, EditRecetaActivity.class);
        startActivity(miIntent);
        finish();


        Toast.makeText(this.getApplicationContext(), "Abrir pantalla editar vacía", Toast.LENGTH_LONG).show();

    }


}
