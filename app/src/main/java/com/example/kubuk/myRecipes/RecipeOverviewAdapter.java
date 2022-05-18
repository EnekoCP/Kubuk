package com.example.kubuk.myRecipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.AddEditRecetas.EditRecetaActivity;
import com.example.kubuk.Main.RecetasComunidad;
import com.example.kubuk.R;
import com.example.kubuk.User;

import org.json.JSONObject;

import java.util.List;

public class RecipeOverviewAdapter extends RecyclerView.Adapter<RecipeOverviewAdapter.ViewHolder>
    implements View.OnClickListener, Response.Listener<JSONObject> ,Response.ErrorListener{

    private List<Recipe> recipeList;
    private Context context;
    private View.OnClickListener listener;
    private RequestQueue queue = Volley.newRequestQueue(context);

    public RecipeOverviewAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mi_receta_card, parent, false);
        view.setOnClickListener(this);
        Button btnEditar = view.findViewById(R.id.btn_editar);
        btnEditar.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe miReceta = recipeList.get(position);
        holder.txtTitulo.setText(recipeList.get(position).getTitulo());
        holder.txtResumen.setText(recipeList.get(position).getResumen());
        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent miIntent = new Intent(context, EditRecetaActivity.class);
                miIntent.putExtra("titulo", miReceta.getTitulo());

                context.startActivity(miIntent);
                ((Activity) context).finish();


                Toast.makeText(context, "Abrir pantalla editar con datos de " + miReceta.getTitulo(), Toast.LENGTH_LONG).show();
            }
        });
        holder.btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // miNota.rotateColor();
                //registrarNota(miNota);
                //Intent miIntent = new Intent(context, MainActivity.class);
                //miIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                ((Activity) context).recreate();
                //context.startActivity(miIntent);


                Toast.makeText(context, "Receta de " + miReceta.getTitulo() + " publicada", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setOnClickListener(View.OnClickListener pListener) {
        this.listener = pListener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    private void publicar(Recipe receta){
        // Instantiate the RequestQueue.
        String url ="http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/ecalvo023/WEB/add1.php?name="+receta.getTitulo()+"&descripcion="+receta.getPreparacion()+
                "&ingredientes="+receta.getIngredientes()+"&user="+ User.getUsuario();

        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,this,this);

        queue.add(jsonRequest);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFoto;
        private TextView txtTitulo;
        private TextView txtResumen;
        private Button btnEditar;
        private Button btnPublicar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoto = itemView.findViewById(R.id.img_foto);
            txtTitulo = itemView.findViewById(R.id.txt_titulo);
            txtResumen = itemView.findViewById(R.id.txt_resumen);
            btnEditar = itemView.findViewById(R.id.btn_editar);
            btnPublicar = itemView.findViewById(R.id.btn_publicar);
        }
    }
}
