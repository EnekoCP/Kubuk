package com.example.kubuk.myRecipes;

import java.io.Serializable;

public class Recipe implements Serializable {

    private final long fechaCreacion;
    private String titulo;
    private String texto;

    public Recipe(long pFechaCreacion){
        this.fechaCreacion = pFechaCreacion;
        this.titulo = "";
        this.texto = "";
    }

    public long getFechaCreacion() {
        return fechaCreacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }

    public String getResumen(){
        String res;
        if (texto != null) {
            if (texto.length() < 40) {
                res = texto.substring(0, texto.length());
            }
            else {
                res = texto.substring(0, 40) + "...";
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

    public void setTexto(String pTexto) {
        this.texto = pTexto;
    }

}
