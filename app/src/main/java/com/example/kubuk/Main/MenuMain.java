package com.example.kubuk.Main;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class MenuMain extends AppCompatActivity implements Response.Listener, Response.ErrorListener {
   List itemList;
   AdapterListView listViewDataAdapter;
   ListView listViewWithCheckbox;
   ArrayList rclista = new ArrayList();
   RequestQueue request;

   private void cargarWebService() {
      StringRequest var1 = new StringRequest(0, "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/conseguirRecetas.php".replace(" ", "%20"), this, this);
      this.request.add(var1);
   }

   private List display(ArrayList var1) {
      ArrayList var3 = new ArrayList();

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
      this.setContentView(R.i);
      this.request = Volley.newRequestQueue(this.getApplicationContext());
      this.cargarWebService();
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      this.getMenuInflater().inflate(2131492868, var1);
      return true;
   }

   public void onErrorResponse(VolleyError var1) {
      Log.i("el error", var1.toString());
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      switch (var1.getItemId()) {
         case 2131231029:
            Toast.makeText(this, "deberia entrar en la comunidad", 0).show();
            return true;
         case 2131231030:
            Toast.makeText(this, "debería entrar en la lista de mis recetas", 0).show();
            return true;
         case 2131231031:
            Toast.makeText(this, "deberia entrar en la lista de la compra", 0).show();
            return true;
         default:
            return super.onOptionsItemSelected(var1);
      }
   }

   @Override
   public void onResponse(String response){
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

         this.display(this.rclista);
         this.listViewWithCheckbox = (ListView)this.findViewById(2131231220);
         List var12 = this.display(this.rclista);
         this.itemList = var12;
         Log.i("ha hecho bienel display", var12.toString());
         AdapterListView var13 = new AdapterListView(this.getApplicationContext(), this.itemList);
         this.listViewDataAdapter = var13;
         var13.notifyDataSetChanged();
         this.listViewWithCheckbox.setAdapter(this.listViewDataAdapter);
      }

   }

   }