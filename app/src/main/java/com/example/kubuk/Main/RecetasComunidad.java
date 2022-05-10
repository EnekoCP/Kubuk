package com.example.kubuk.Main;

public class RecetasComunidad {
   private String[] ingredientes;
   private int itemRate = 0;
   private String itemText = "";
   private String preparacion;
   private int puntuacion;
   private String titulo;
   private String usuario;

   public RecetasComunidad(String var1, String[] var2, String var3, String var4) {
      this.titulo = var1;
      this.ingredientes = var2;
      this.preparacion = var3;
      this.usuario = var4;
   }

   public String[] getIngredientes() {
      return this.ingredientes;
   }

   public int getItemRate() {
      return this.itemRate;
   }

   public String getItemText() {
      return this.itemText;
   }

   public String getPreparacion() {
      return this.preparacion;
   }

   public int getPuntuacion() {
      return this.puntuacion;
   }

   public String getTitulo() {
      return this.titulo;
   }

   public String getUsuario() {
      return this.usuario;
   }

   public void setIngredientes(String[] var1) {
      this.ingredientes = var1;
   }

   public void setItemRate(int var1) {
      this.itemRate = var1;
   }

   public void setItemText(String var1) {
      this.itemText = var1;
   }

   public void setPreparacion(String var1) {
      this.preparacion = var1;
   }

   public void setPuntuacion(int var1) {
      this.puntuacion = var1;
   }

   public void setTitulo(String var1) {
      this.titulo = var1;
   }

   public void setUsuario(String var1) {
      this.usuario = var1;
   }
}