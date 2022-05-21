package com.example.kubuk.Main;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.ListaCompra.EnseñarListaCompra;
import com.example.kubuk.R;
import com.example.kubuk.users.LoginActivity;
import com.example.kubuk.myRecipes.MyRecipes;
import com.example.kubuk.users.ModifUserActivity;
import com.example.kubuk.users.aboutUsActivity;

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
   String email;
   ImageView img1,img2,img3;

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(R.layout.activity_recetas_comunidad);

      Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(myToolbar);
      getSupportActionBar().setDisplayShowTitleEnabled(false);
      myToolbar.setSubtitleTextColor(0);

      NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
      NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
              .setSmallIcon(R.drawable.logo_pequeno)
              .setContentTitle("¡Bienvenido!")
              .setContentText("¿Qué te apetece cocinar hoy?")
              .setVibrate(new long[]{0, 1000, 500, 1000})
              .setPriority(NotificationCompat.PRIORITY_DEFAULT);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         NotificationChannel canal = new NotificationChannel("3", "Bienvenida",
                 NotificationManager.IMPORTANCE_DEFAULT);
         manager.createNotificationChannel(canal);
      }
      manager.notify(1, builder.build());

      Bundle extra= getIntent().getExtras();
      email=extra.getString("usuario");
      Log.i("ha entrado","en la clase mainmenu con usuario"+email);

      request = Volley.newRequestQueue(this.getApplicationContext());
      cargarWebService();
   }
   private void cargarWebService() {
      String url="http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/conseguirRecetas.php?email="+email;
      url.replace(" ", "%20");
      Log.i("el url",url);
      StringRequest var1 = new StringRequest(Request.Method.GET, url,this,this);

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
            return true;
         case R.id.misrecetas:
            //Toast.makeText(this, "debería entrar en la lista de mis recetas", Toast.LENGTH_LONG).show();
            Intent i= new Intent(MenuMain.this, MyRecipes.class);
            i.putExtra("usuario",email);
            startActivity(i);
            return true;
         case R.id.listacompra:
            //Toast.makeText(this, "deberia entrar en la lista de la compra", Toast.LENGTH_LONG).show();
            Intent ilis = new Intent(MenuMain.this, EnseñarListaCompra.class);
            ilis.putExtra("usuario",email);
            startActivity(ilis);
            return true;
         case R.id.modifuser:
            //Toast.makeText(this, "deberia entrar en modificar user", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MenuMain.this, ModifUserActivity.class);
            intent.putExtra("usuario",email);
            startActivity(intent);
            return true;
         case R.id.aboutus:
            //Toast.makeText(this, "deberia entrar en about us", Toast.LENGTH_LONG).show();
            Intent aboutus = new Intent(MenuMain.this, aboutUsActivity.class);
            aboutus.putExtra("usuario",email);
            startActivity(aboutus);
            return true;
         case R.id.logout:
            //Toast.makeText(this, "deberia entrar en login", Toast.LENGTH_LONG).show();
            Intent logout = new Intent(MenuMain.this, LoginActivity.class);
            startActivity(logout);
            return true;
         default:
            return super.onOptionsItemSelected(var1);
      }
   }

   public void onErrorResponse(VolleyError var1) {
      var1.printStackTrace();
   }

   @SuppressLint("ResourceType")
   @Override
   public void onResponse(String response) {
      Log.d("Respuesta", response.trim());
      String var3 = response.trim();
      if (!var3.equals("false")) {

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
                     String prep = var14.getJSONObject(var2).getString("preparacion");
                     String var4 = var14.getJSONObject(var2).getString("puntuacion");
                     String var7 = var14.getJSONObject(var2).getString("usuario");
                     StringBuilder var8 = new StringBuilder();
                     Log.i("receta", var8.append(var6).append(var5).append(response).append(var7).toString());
                     RecetasComunidad var15 = new RecetasComunidad(var6, var5, prep, var7);
                     var15.setPuntuacion(Float.parseFloat(var4));
                     this.rclista.add(var15);
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

         listViewWithCheckbox = (ListView)findViewById(R.id.listview);
         itemList = display(rclista);
         Log.i("ha hecho bienel display", itemList.toString());
         AdapterListView var13 = new AdapterListView(getApplicationContext(),itemList);
         listViewDataAdapter = var13;
         var13.notifyDataSetChanged();
         listViewWithCheckbox.setAdapter(listViewDataAdapter);
         listViewWithCheckbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intdet= new Intent(MenuMain.this,DetallesRecetaComunidad.class);
               RecetasComunidad re=itemList.get(i);
               String[] receta= {re.getTitulo(), re.getIngredientes(),re.getPreparacion(),re.getUsuario()};
               intdet.putExtra("receta", receta);
               startActivity(intdet);
            }
         });
      }
   }





}
