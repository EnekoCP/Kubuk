package com.example.kubuk.ListaCompra;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kubuk.R;

import java.util.List;

public class AdapterListViewListCom extends BaseAdapter {
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

    @Override
    public View getView(int itemIndex, View convertView, ViewGroup viewGroup) {

        CompraLViewHolder viewHolder = null;

        if(convertView!=null)
        {
            viewHolder = (CompraLViewHolder) convertView.getTag();
        }else
        {
            convertView = View.inflate(ctx, R.layout.lista_compra_item, null);

            CheckBox listItemCheckbox = (CheckBox) convertView.findViewById(R.id.checkbox);

            TextView listItemText = (TextView) convertView.findViewById(R.id.titulo);

            viewHolder = new CompraLViewHolder(convertView);

            viewHolder.setItemCheckbox(listItemCheckbox);

            viewHolder.setItemTextView(listItemText);

            convertView.setTag(viewHolder);
        }

       Elemento listViewItemDto = listViewItemDtoList.get(itemIndex);
        if(listViewItemDto.isChecked().equals("true")){
            viewHolder.getItemCheckbox().setChecked(Boolean.parseBoolean("true"));
        }
        else{
            viewHolder.getItemCheckbox().setChecked(Boolean.parseBoolean("false"));
        }

       viewHolder.getItemTextView().setText(listViewItemDto.getItemText());


        return convertView;
    }
}
