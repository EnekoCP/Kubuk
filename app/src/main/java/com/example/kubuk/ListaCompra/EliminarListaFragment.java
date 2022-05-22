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
import com.example.kubuk.R;
import com.example.kubuk.users.ModifUserActivity;

public class EliminarListaFragment extends DialogFragment {

    int index;

    com.example.kubuk.ListaCompra.EliminarListaFragment.ListenerdelDialogoElim miListener;

    public interface ListenerdelDialogoElim {
        void alpulsarSiElim();
        void alpulsarNoElim();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        miListener = (com.example.kubuk.ListaCompra.EliminarListaFragment.ListenerdelDialogoElim) getActivity();

        Bundle data = getArguments();
        index = data.getInt("index");


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("¿Eliminar producto?");
        builder.setMessage("¿De verdad quieres quitar el producto de la lista?");

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
                miListener.alpulsarSiElim();

            }
        });

        builder.setNegativeButton("Volver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
                miListener.alpulsarNoElim();
            }
        });


        return builder.create();
    }

}