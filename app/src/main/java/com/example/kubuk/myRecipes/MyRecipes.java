package com.example.kubuk.myRecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.AddEditRecetas.AddRecetaActivity;
import com.example.kubuk.ListaCompra.EnseñarListaCompra;
import com.example.kubuk.Main.MenuMain;
import com.example.kubuk.R;
import com.example.kubuk.User;
import com.example.kubuk.users.LoginActivity;
import com.example.kubuk.users.ModifUserActivity;
import com.example.kubuk.users.aboutUsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class MyRecipes extends AppCompatActivity implements Response.Listener<JSONObject> ,Response.ErrorListener{

    RecyclerView recipeRecycler;
    RecipeOverviewAdapter recipeOverview;
    ArrayList<Recipe> listaRecetas;
    private RequestQueue queue;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recipes);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setSubtitleTextColor(0);

        Bundle extras= getIntent().getExtras();
        email= extras.getString("usuario");

        queue = Volley.newRequestQueue(getApplicationContext());

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
                i.putExtra("usuario",email);
                startActivity(i);
                return true;
            case R.id.misrecetas:
                //nada porque es este
                return true;
            case R.id.listacompra:
                //Toast.makeText(this, "deberia entrar en la lista de la compra", Toast.LENGTH_LONG).show();
                Intent ilis = new Intent(MyRecipes.this, EnseñarListaCompra.class);
                ilis.putExtra("usuario",email);
                startActivity(ilis);
                return true;
            case R.id.modifuser:
                //Toast.makeText(this, "deberia entrar en modificar user", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MyRecipes.this, ModifUserActivity.class);
                intent.putExtra("usuario",email);
                startActivity(intent);
                return true;
            case R.id.aboutus:
                //Toast.makeText(this, "deberia entrar en about us", Toast.LENGTH_LONG).show();
                Intent aboutus = new Intent(MyRecipes.this, aboutUsActivity.class);
                aboutus.putExtra("usuario",email);
                startActivity(aboutus);
                return true;
            case R.id.logout:
                //Toast.makeText(this, "deberia entrar en login", Toast.LENGTH_LONG).show();
                Intent logout = new Intent(MyRecipes.this, LoginActivity.class);
                startActivity(logout);
                return true;
            default:
                return super.onOptionsItemSelected(var1);
        }
    }



    private void construirRecycler() {
        listaRecetas = new ArrayList<Recipe>();
        getDatos(this.listaRecetas);

        recipeRecycler = findViewById(R.id.recycler);
        recipeRecycler.setLayoutManager(new LinearLayoutManager(this));
        System.out.println("Usuario: " + User.getUsuario());


        recipeOverview = new RecipeOverviewAdapter(listaRecetas, getApplicationContext());
        recipeOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        listaRecetas.get(recipeRecycler.getChildAdapterPosition(view)).getTitulo(),
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getDatos(ArrayList<Recipe> lRecetas) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/conseguirMisRecetas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray recetas = null;
                        Recipe recetaActual = null;
                        try {
                            recetas = new JSONArray(response);
                            for (int i = 0; i < recetas.length(); i++) {
                                JSONObject receta = recetas.getJSONObject(i);

                                recetaActual = new Recipe();
                                recetaActual.setTitulo(receta.getString("titulo"));
                                recetaActual.setIngredientes(receta.getString("ingredientes"));
                                recetaActual.setPreparacion(receta.getString("preparacion"));
                                lRecetas.add(recetaActual);
                            }

                            recipeOverview.notifyDataSetChanged();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), getString(R.string.errorServidor), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("user", User.getUsuario());
                System.out.println("Usuario: " + User.getUsuario());

                return parametros;
            }
        };

        queue.add(stringRequest);

    }

    public void onClick(View view) {
        Intent miIntent = new Intent(MyRecipes.this, AddRecetaActivity.class);
        startActivity(miIntent);
        //finish();
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
