package com.example.kubuk.myRecipes;

import java.io.Serializable;

public class Recipe implements Serializable {

    private String titulo;
    private String ingredientes;
    private String preparacion;

    public Recipe(){
        this.titulo = "";
        this.ingredientes = "";
        this.preparacion = "";
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getPreparacion() { return preparacion; }

    public String getResumen(){
        String res;
        if (preparacion != null) {
            if (preparacion.length() < 40) {
                res = preparacion.substring(0, preparacion.length());
            }
            else {
                res = preparacion.substring(0, 40) + "...";
            }
        }
        else {
            res = "";
        }
        return res;
    }

    public void setTitulo(String pTitulo) {
        this.titulo = pTitulo;
    }

    public void setIngredientes(String pIngredientes) {
        this.ingredientes = pIngredientes;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }
}
