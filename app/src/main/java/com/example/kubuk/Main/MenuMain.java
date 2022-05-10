package com.example.kubuk.Main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.R;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class MenuMain extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {
   List<RecetasComunidad> itemList;
   AdapterListView listViewDataAdapter;
   ListView listViewWithCheckbox;
   ArrayList rclista = new ArrayList();
   RequestQueue request;

   private void cargarWebService() {
      StringRequest var1 = new StringRequest(Request.Method.GET, "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/conseguirRecetas.php".replace(" ", "%20"), this, this);
      request.add(var1);
   }

   private List<RecetasComunidad> display(ArrayList var1) {
      ArrayList<RecetasComunidad> var3 = new ArrayList();

      for(int var2 = 0; var2 < var1.size(); ++var2) {
         Log.i("recetakop", " " + var1.size());
         RecetasComunidad var4 = (RecetasComunidad)var1.get(var2);
         var4.setItemText(var4.getTitulo());
         var4.setItemRate(var4.getPuntuacion());
         var3.add(var4);
      }

      return var3;
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(R.layout.activity_recetas_comunidad);
      Log.i("ha entrado","en la clase mainmenu");
      request = Volley.newRequestQueue(this.getApplicationContext());
      cargarWebService();
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      this.getMenuInflater().inflate(R.menu.menu, var1);
      return true;
   }

   public void onErrorResponse(VolleyError var1) {
      Log.i("el error", var1.toString());
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      switch (var1.getItemId()) {
         case R.id.recetascomunidad:
            Toast.makeText(this, "deberia entrar en la comunidad", Toast.LENGTH_LONG).show();
            return true;
         case R.id.misrecetas:
            Toast.makeText(this, "debería entrar en la lista de mis recetas", Toast.LENGTH_LONG).show();
            return true;
         case R.id.listacompra:
            Toast.makeText(this, "deberia entrar en la lista de la compra", Toast.LENGTH_LONG).show();
            return true;
         default:
            return super.onOptionsItemSelected(var1);
      }
   }


   @SuppressLint("ResourceType")
   @Override
   public void onResponse(String response) {
      Log.d("Respuesta", response.trim());
      String var3 = response.trim();
      if (!var3.equals("false")) {
         Log.i("titulo", var3);

         label31: {
            JSONException var10000;
            label37: {
               JSONArray var14;
               boolean var10001;
               try {
                  var14 = new JSONArray(response);
               } catch (JSONException var10) {
                  var10000 = var10;
                  var10001 = false;
                  break label37;
               }

               int var2 = 0;

               while(true) {
                  try {
                     if (var2 >= var14.length()) {
                        break label31;
                     }

                     String var6 = var14.getJSONObject(var2).getString("titulo");
                     String var5 = var14.getJSONObject(var2).getString("ingredientes");
                     response = var14.getJSONObject(var2).getString("preparacion");
                     String var4 = var14.getJSONObject(var2).getString("puntuacion");
                     String var7 = var14.getJSONObject(var2).getString("usuario");
                     StringBuilder var8 = new StringBuilder();
                     Log.i("receta", var8.append(var6).append(var5).append(response).append(var7).toString());
                     RecetasComunidad var15 = new RecetasComunidad(var6, new String[]{var5}, response, var7);
                     var15.setPuntuacion(Integer.parseInt(var4));
                     this.rclista.add(var15);
                  } catch (JSONException var9) {
                     var10000 = var9;
                     var10001 = false;
                     break;
                  }

                  ++var2;
               }
            }

            JSONException var11 = var10000;
            var11.printStackTrace();
         }

         listViewWithCheckbox = (ListView)findViewById(R.id.listview);
         itemList = display(rclista);
         Log.i("ha hecho bienel display", itemList.toString());
         AdapterListView var13 = new AdapterListView(getApplicationContext(),itemList);
         listViewDataAdapter = var13;
         var13.notifyDataSetChanged();
         listViewWithCheckbox.setAdapter(listViewDataAdapter);
      }
   }
}
