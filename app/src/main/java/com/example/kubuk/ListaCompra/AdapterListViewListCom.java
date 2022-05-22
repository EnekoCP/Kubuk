package com.example.kubuk.ListaCompra;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.example.kubuk.R;
import com.example.kubuk.users.ConfirmarEliminarFragment;
import com.example.kubuk.users.eliminarDialogFragment;

import java.util.List;

public class AdapterListViewListCom extends BaseAdapter  {
    private List<Elemento> listViewItemDtoList = null;
    private Context ctx = null;

    public AdapterListViewListCom(Context ctx, List<Elemento> listViewItemDtoList) {
        this.ctx = ctx;
        this.listViewItemDtoList = listViewItemDtoList;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(listViewItemDtoList!=null)
        {
            ret = listViewItemDtoList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int itemIndex) {
        Object ret = null;
        if(listViewItemDtoList!=null) {
            ret = listViewItemDtoList.get(itemIndex);
        }
        return ret;
    }

    @Override
    public long getItemId(int itemIndex) {
        return itemIndex;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int itemIndex, View convertView, ViewGroup viewGroup) {

        CompraLViewHolder viewHolder = null;

        if(convertView!=null)
        {
            viewHolder = (CompraLViewHolder) convertView.getTag();
        }else
        {
            convertView = View.inflate(ctx, R.layout.lista_compra_item, null);
            CheckBox checkBox1 = (CheckBox) convertView.findViewById(R.id.checkbox);
            boolean checked = PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean("checkbox", false);
            checkBox1.setChecked(checked);
            checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //cuando clickas en el checkbox
                    Log.i("ha tocado","el elemento");
                    EliminarElementoListaC elim= new EliminarElementoListaC(listViewItemDtoList.get(itemIndex).getEmail(),listViewItemDtoList.get(itemIndex).getItemText(),ctx);
                    elim.eliminarElem();
                    ActualizarCheckBox acb= new ActualizarCheckBox(listViewItemDtoList.get(itemIndex).getEmail(),listViewItemDtoList.get(itemIndex).isChecked(),ctx,listViewItemDtoList.get(itemIndex).getItemText());
                    acb.actualizarMarcado();
                }
            });

            CheckBox listItemCheckbox = (CheckBox) convertView.findViewById(R.id.checkbox);

            TextView listItemText = (TextView) convertView.findViewById(R.id.titulo);

            viewHolder = new CompraLViewHolder(convertView);

            viewHolder.setItemCheckbox(listItemCheckbox);

            viewHolder.setItemTextView(listItemText);

            convertView.setTag(viewHolder);
        }

        Elemento listViewItemDto = listViewItemDtoList.get(itemIndex);


        viewHolder.getItemCheckbox().setChecked(listViewItemDto.isChecked());
        viewHolder.getItemTextView().setText(listViewItemDto.getItemText());

        return convertView;
    }


}