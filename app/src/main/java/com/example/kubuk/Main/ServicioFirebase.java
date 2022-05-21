package com.example.kubuk.Main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.R;
import com.example.kubuk.User;
import com.example.kubuk.users.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Hashtable;
import java.util.Map;

/* Gestiona el servicio de mensajeria FCM */
public class ServicioFirebase extends FirebaseMessagingService {

    private String token;
    RequestQueue request;

    public ServicioFirebase() {


}
    /* Consigue el token del dispositivo */
    public String generarToken(){
        token= FirebaseInstanceId.getInstance().getToken();
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>(){
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task){

                if (task.isSuccessful()){
                    token = task.getResult().getToken();
                    Log.i("Token", "ondo "+token);
                    //saveToken();

                }
                else{
                    Log.i("Token", "error");
                }

            }
        });
        return  token;
    }

    /* Gestiona los mensajes recibidos */
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {

        }
        if (remoteMessage.getNotification() != null) {
            Log.i("fcm ","recived");
            //Toast.makeText(getApplicationContext(),"FCM message",Toast.LENGTH_SHORT).show();
            NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(this, "2");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel elCanal = new NotificationChannel("2", "mssg",
                        NotificationManager.IMPORTANCE_DEFAULT);
                elManager.createNotificationChannel(elCanal);
                elCanal.setDescription("Firebase messages");
                elCanal.enableLights(true);
                elCanal.setLightColor(Color.RED);
                elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                elCanal.enableVibration(true);
            }
            elBuilder.setSmallIcon(android.R.drawable.stat_sys_warning)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody().toString())
                    .setSubText(remoteMessage.getNotification().getBody())
                    .setVibrate(new long[]{0, 1000, 500, 1000})
                    .setAutoCancel(true);
            elManager.notify(1, elBuilder.build());

        }
    }


/*
public void sendmessage(){
    // The topic name can be optionally prefixed with "/topics/".
    String topic = "highScores";

// See documentation on defining a message payload.
    Message message = Message.builder()
            .putData("score", "850")
            .putData("time", "2:45")
            .setTopic(topic)
            .build();

// Send a message to the devices subscribed to the provided topic.
    String response = FirebaseMessaging.getInstance().send(message);
// Response is a message ID string.
    System.out.println("Successfully sent message: " + response);
}*/



    /* Actualiza token del dispositivo */
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //token=FirebaseInstanceId.getInstance().getToken();
        generarToken();
        //gorde new token in db
    }

    /** Método utilizado para guardar el token en la BBDD remota */
   /* public void saveToken(Context ctx) {
        String url = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/notificacionValoracion.php?email="
                +User.getUsuario() + "&fromToken=" +token+"&funcion=save";

        url = url.replace(" ", "%20");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this,this);

        Volley.newRequestQueue(ctx).add(stringRequest);



    }*/

    public String getToken(){
        return token;
    }

    private void sendMessage(){

    }



    public void saveToken(Context ctx) {
        String url = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/notificacionValoracion.php";

        url = url.replace(" ", "%20");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Respuesta", response.trim());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("fromToken", token);
                parametros.put("user", User.getUsuario());
                parametros.put("funcion","save");



                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }



}
