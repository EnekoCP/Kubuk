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

public class ConfirmarEliminarFragment extends DialogFragment  implements Response.Listener<String>, Response.ErrorListener {

    String email = "";
    RequestQueue request;
    Intent modif;

    ListenerdelDialogo miListener;

    public interface ListenerdelDialogo {
        void alpulsarSI(String respuesta);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        miListener = (ListenerdelDialogo) getActivity();

        Bundle data = getArguments();
        email = data.getString("usuario");

        modif = new Intent(getActivity(), ModifUserActivity.class);
        modif.putExtra("usuario", email);

        request = Volley.newRequestQueue(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.eliminarUserConfT));
        builder.setMessage(getString(R.string.eliminarUserMssg));

        builder.setPositiveButton(getString(R.string.continuar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
                cargarWebService();
            }
        });

        builder.setNegativeButton(getString(R.string.volver), new DialogInterface.OnClickListener() {
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
        String url = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/everhorst001/WEB/Kubuk/eliminarUsuarioKubuk.php?email=" + email;

        url = url.replace(" ", "%20");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this,this);
        request.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dismiss();
        miListener.alpulsarSI("Error");
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(String response) {
        Log.d("Respuesta", response.trim());
        String respuesta = response.trim();
        switch (respuesta){
            case "Eliminado_done":
                dismiss();
                miListener.alpulsarSI(respuesta);
                Log.i("DELETE", "Delete Ok");
                break;
            case "Eliminado_notdone":
                dismiss();
                miListener.alpulsarSI(respuesta);
                Log.i("DELETE", "Delete not done");
                break;
        }

    }
}