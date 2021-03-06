package com.example.kubuk.ListaCompra;

import static android.view.MenuItem.SHOW_AS_ACTION_IF_ROOM;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.Main.RecetasComunidad;
import com.example.kubuk.R;
import com.example.kubuk.myRecipes.MyRecipes;
import com.example.kubuk.users.LoginActivity;
import com.example.kubuk.users.ModifUserActivity;
import com.example.kubuk.users.aboutUsActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class EnseñarListaCompra extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {
    List<Elemento> itemList;
    AdapterListViewListCom listViewDataAdapter;
    ListView listViewWithCheckbox;
    String email;
    ArrayList rclista = new ArrayList();
    RequestQueue request;
    EditText elem = null;
    AdapterListViewListCom var13;

    private static final int MNU_OPCDEL = 6;


    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(R.layout.activity_lista_compra);
        elem = findViewById(R.id.id_edit_text);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setSubtitleTextColor(0);

        Bundle extras = getIntent().getExtras();
        email = extras.getString("usuario");

        request = Volley.newRequestQueue(this.getApplicationContext());
        getElements();

        Button anadir = findViewById(R.id.anadir_ingr);
        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AñadirListaCompra alc = new AñadirListaCompra(email, elem.getText().toString(), request);
                alc.anadirElement();

                listViewWithCheckbox = (ListView) findViewById(R.id.listvcompra);
                Elemento ne = new Elemento(false, elem.getText().toString());
                rclista.add(ne);
                itemList = enseñar(rclista);
                var13 = new AdapterListViewListCom(getApplicationContext(), itemList);
                listViewDataAdapter = var13;
                var13.notifyDataSetChanged();
                listViewWithCheckbox.setAdapter(listViewDataAdapter);
                //elem.setText("INSERTA AQUÍ EL ELEMENTO QUE QUIERAS AÑADIR");
                elem.setText("");
            }
        });


}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.add(Menu.NONE, MNU_OPCDEL, Menu.NONE, "Eliminar")
                .setIcon(R.drawable.papelera2).setShowAsAction(SHOW_AS_ACTION_IF_ROOM);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem var1) {
        switch (var1.getItemId()) {
            case R.id.recetascomunidad:
                //Toast.makeText(this, "deberia entrar en la comunidad", Toast.LENGTH_LONG).show();
                Intent i = new Intent(EnseñarListaCompra.this, RecetasComunidad.class);
                i.putExtra("usuario", email);
                startActivity(i);
                return true;
            case R.id.misrecetas:
                //Toast.makeText(this, "debería entrar en la lista de mis recetas", Toast.LENGTH_LONG).show();
                Intent imy = new Intent(EnseñarListaCompra.this, MyRecipes.class);
                imy.putExtra("usuario", email);
                startActivity(imy);
                return true;
            case R.id.listacompra:
                //Toast.makeText(this, "deberia entrar en la lista de la compra", Toast.LENGTH_LONG).show();
                return true;
            case R.id.modifuser:
                //Toast.makeText(this, "deberia entrar en modificar user", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EnseñarListaCompra.this, ModifUserActivity.class);
                intent.putExtra("usuario",email);
                startActivity(intent);
                return true;
            case R.id.aboutus:
                //Toast.makeText(this, "deberia entrar en about us", Toast.LENGTH_LONG).show();
                Intent aboutus = new Intent(EnseñarListaCompra.this, aboutUsActivity.class);
                aboutus.putExtra("usuario",email);
                startActivity(aboutus);
                return true;
            case R.id.logout:
                //Toast.makeText(this, "deberia entrar en login", Toast.LENGTH_LONG).show();
                Intent logout = new Intent(EnseñarListaCompra.this, LoginActivity.class);
                startActivity(logout);
                return true;
            default:
                return super.onOptionsItemSelected(var1);
        }
    }


    private void display(String response) {
        Log.d("Respuesta", response.trim());
        String var3 = response.trim();
        if (!var3.equals("false")) {

            label31:
            {
                JSONException var10000 = null;
                label37:
                {
                    JSONArray var14;
                    boolean var10001;
                    try {

                        var14 = new JSONArray(response.toString());


                    } catch (JSONException var10) {
                        var10000 = var10;
                        var10001 = false;
                        break label37;
                    }

                    int var2 = 0;

                    while (true) {
                        try {
                            if (var2 >= var14.length()) {
                                break label31;
                            }

                            String elemento = var14.getJSONObject(var2).getString("elemento");
                            String marcado = var14.getJSONObject(var2).getString("marcado");
                            Elemento e = new Elemento(Boolean.parseBoolean(marcado), elemento);
                            e.setEmail(email);
                            rclista.add(e);
                        } catch (JSONException var9) {
                            var10000 = var9;
                            break;
                        }
                        ++var2;
                    }
                }
                JSONException var11 = var10000;
                var11.printStackTrace();
            }
            listViewWithCheckbox = (ListView) findViewById(R.id.listvcompra);
            itemList = enseñar(rclista);
            Log.i("ha hecho bienel display", itemList.toString());
            AdapterListViewListCom var13 = new AdapterListViewListCom(getApplicationContext(), itemList);
            listViewDataAdapter = var13;
            var13.notifyDataSetChanged();
            listViewWithCheckbox.setAdapter(listViewDataAdapter);

        }

    }

    private List<Elemento> enseñar(ArrayList var1) {
        ArrayList<Elemento> var3 = new ArrayList();

        for (int var2 = 0; var2 < var1.size(); ++var2) {
            Log.i("elemkop", " " + var1.size());
            Elemento var4 = (Elemento) var1.get(var2);
            var4.setItemText(var4.getItemText());
            var4.setChecked(var4.isChecked());
            var3.add(var4);
        }

        return var3;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Log.i("LA RESPUESTA", response);
        if (!Boolean.parseBoolean(response)) {
            display(response);
        }

    }

    private void getElements() {
        String url = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/conseguirlistacompra.php?usuario=" + email;
        url = url.replace(" ", "%20");
        StringRequest var1 = new StringRequest(Request.Method.GET, url, this, this);
        request.add(var1);
    }

}
