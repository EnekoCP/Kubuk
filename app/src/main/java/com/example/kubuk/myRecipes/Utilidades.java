package com.example.kubuk.myRecipes;

public class Utilidades {

    public static final String TABLA_RECETA = "receta";
    public static final String CAMPO_FECHA = "fecha";
    public static final String CAMPO_TITULO = "titulo";
    public static final String CAMPO_TEXTO = "texto";
    public static final String CAMPO_PUBLICADO = "publicado";

    public static final String CREAR_TABLA_RECETA = "CREATE TABLE " + TABLA_RECETA +
            "(" + CAMPO_FECHA + " LONG, " +
            CAMPO_TITULO + " TEXT, " +
            CAMPO_TEXTO + " TEXT, " +
            CAMPO_PUBLICADO + " TEXT)";
}
