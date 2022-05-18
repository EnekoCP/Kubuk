package com.example.kubuk.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class miDB extends SQLiteOpenHelper {
   private static final String DB_NAME = "kubuk.sqlite";
   private static final int DB_VERSION = 1;
   private static final String ID_COL = "id";
   private static final String INGRED_COL = "ingredientes";
   private static final String PREP_COL = "preparacion";
   private static final String PUNT_COL = "puntuacion";
   private static final String RECETAC_TABLE = "recetaComunidad";
   private static final String TITULO_COL = "titulo";
   private static final String USUNOM_COL = "usuario";

   public miDB(Context var1) {
      super(var1, "kubuk.sqlite", (SQLiteDatabase.CursorFactory)null, 1);
   }

   @SuppressLint("Range")
   public ArrayList displayAll(String var1) {
      SQLiteDatabase var4 = this.getReadableDatabase();
      ArrayList var3 = new ArrayList();
      Cursor var6 = var4.query("recetaComunidad", (String[])null, (String)null, (String[])null, (String)null, (String)null, (String)null);

      label30: {
         boolean var10001;
         try {
            if (!var6.moveToFirst()) {
               return var3;
            }
         } catch (Exception var11) {
            var10001 = false;
            break label30;
         }

         while(true) {
            try {
               @SuppressLint("Range") String var7 = var6.getString(var6.getColumnIndex("titulo"));
               @SuppressLint("Range") String var12 = var6.getString(var6.getColumnIndex("ingredientes"));
               @SuppressLint("Range") String var8 = var6.getString(var6.getColumnIndex("preparacion"));
               var6.getString(var6.getColumnIndex("puntuacion"));
               RecetasComunidad var5 = new RecetasComunidad(var7, var12, var8, var1);
               var3.add(var5);
            } catch (Exception var10) {
               var10001 = false;
               break;
            }

            boolean var2;
            try {
               var2 = var6.moveToNext();
            } catch (Exception var9) {
               var10001 = false;
               break;
            }

            if (!var2) {
               return var3;
            }
         }
      }

      Log.i("error", "error");
      return var3;
   }

   public void onCreate(SQLiteDatabase var1) {
      var1.execSQL("CREATE TABLE recetaComunidad (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT,ingredientes TEXT,preparacion TEXT,puntuacion TEXT,usuario TEXT)");
   }

   public void onUpgrade(SQLiteDatabase var1, int var2, int var3) {
   }
}