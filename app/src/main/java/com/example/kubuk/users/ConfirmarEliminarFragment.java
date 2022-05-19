package com.example.kubuk.users;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kubuk.R;
import com.example.kubuk.Main.MenuMain;

public class ConfirmarEliminarFragment extends DialogFragment  implements Response.Listener<String>, Response.ErrorListener {

    String email = "";
    RequestQueue request;
    Intent modif;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        Bundle data = getArguments();
        email = data.getString("usuario");

        modif = new Intent(getActivity(), ModifUserActivity.class);
        modif.putExtra("usuario", email);

        request = Volley.newRequestQueue(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Eliminar usuario: confirmación");
        builder.setMessage("¿Estás seguro de querer eliminar el usuario? Se eliminarán todos los datos.");

        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
                cargarWebService();
            }
        });

        builder.setNegativeButton("Volver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
                startActivity(modif);
            }
        });

        return builder.create();
    }

    /** Método utilizado para eliminar los datos en la BBDD remota */
    private void cargarWebService() {
        //TODO: Modificar url
        String url = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/eliminarUsuarioKubuk.php?email=" + email;

        url = url.replace(" ", "%20");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this,this);
        request.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), getString(R.string.errorServidor), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(String response) {
        Log.d("Respuesta", response.trim());
        String respuesta = response.trim();
        switch (respuesta){
            case "Eliminado_done":
                DialogFragment confirmarAlert = new eliminarDialogFragment();
                confirmarAlert.show(getFragmentManager(),"eliminar_dialog2");
                Log.i("DELETE", "Delete Ok");
                break;
            case "Eliminado_notdone":
                Toast.makeText(getContext(), "No se pudo eliminar los datos del usuario en este momento. Pruebe de nuevo más tarde", Toast.LENGTH_SHORT).show();
                Log.i("DELETE", "Delete not done");
                dismiss();
                startActivity(modif);
                break;
        }

    }
}