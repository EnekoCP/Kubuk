package com.example.kubuk.Main;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class ListViewItemViewHolder extends RecyclerView.ViewHolder {
   private RatingBar itemRating;
   private TextView itemTextView;

   public ListViewItemViewHolder(View var1) {
      super(var1);
   }

   public RatingBar getItemRate() {
      return this.itemRating;
   }

   public TextView getItemTextView() {
      return this.itemTextView;
   }

   public void setItemRate(RatingBar var1) {
      this.itemRating = var1;
   }

   public void setItemTextView(TextView var1) {
      this.itemTextView = var1;
   }
}