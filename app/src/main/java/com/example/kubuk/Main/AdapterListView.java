package com.example.kubuk.Main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.kubuk.R;

import java.util.List;

public class AdapterListView extends BaseAdapter {
   private Context ctx = null;
   private List listViewItemDtoList = null;

   public AdapterListView(Context var1, List var2) {
      this.ctx = var1;
      this.listViewItemDtoList = var2;
   }

   public int getCount() {
      int var1 = 0;
      List var2 = this.listViewItemDtoList;
      if (var2 != null) {
         var1 = var2.size();
      }

      return var1;
   }

   public Object getItem(int var1) {
      Object var2 = null;
      List var3 = this.listViewItemDtoList;
      if (var3 != null) {
         var2 = var3.get(var1);
      }

      return var2;
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      ListViewItemViewHolder var6;
      if (var2 != null) {
         var6 = (ListViewItemViewHolder)var2.getTag();
      } else {
         var2 = View.inflate(this.ctx, R.layout.activity_list_item_receta, (ViewGroup)null);
         RatingBar var4 = (RatingBar)var2.findViewById(R.id.rating);
         TextView var5 = (TextView)var2.findViewById(R.id.titulo);
         var6 = new ListViewItemViewHolder(var2);
         var6.setItemRate(var4);
         var6.setItemTextView(var5);
         var2.setTag(var6);
      }

      RecetasComunidad var7 = (RecetasComunidad)this.listViewItemDtoList.get(var1);
      var6.getItemTextView().setText(var7.getItemText());
      var6.getItemRate().setNumStars(2);
      return var2;
   }
}