package com.example.kubuk.ListaCompra;

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
import com.example.kubuk.Main.MenuMain;
import com.example.kubuk.R;
import com.example.kubuk.users.ModifUserActivity;

public class EliminarListaFragment extends DialogFragment {


    String email = "";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);


        Bundle data = getArguments();
        email = data.getString("email");


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Aviso importante");
        builder.setMessage("Al seleccionar un producto de la lista de la compra, éste se elimina de la lista. Por su salud mental, tenga cuidado.");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
                Intent ilis = new Intent(getActivity(), EnseñarListaCompra.class);
                ilis.putExtra("usuario",email);
                startActivity(ilis);
            }
        });

        return builder.create();
    }

}