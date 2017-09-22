package com.workshop.cotic.feedback;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cess on 21/09/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE = "app";
    private static int VERSAO = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS palestra (_id INTEGER PRIMARY KEY AUTOINCREMENT, titulo VARCHAR, tipo INT(1))");
        db.execSQL("CREATE TABLE IF NOT EXISTS avaliacao (_id INTEGER PRIMARY KEY AUTOINCREMENT, avaliacao INT(1), sugestao VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXIST palestra");
//        db.execSQL("DROP TABLE IF EXIST avaliacao");
//        onCreate(db);
    }

    public void inserirPalestra(String titulo, int tipo){
        ContentValues values = new ContentValues();
        values.put("titulo", titulo);
        values.put("tipo", tipo);
        getWritableDatabase().insert("palestra", null, values);
    }

    public Cursor carregaDados(String[] campos, String table){
        Cursor cursor;
        //String[] campos =  {"_id","titulo", "tipo"};
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.query(table, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadoById(int id, String[] campos, String table){
        Cursor cursor;
        //String[] campos =  {"titulo", "tipo"};
        String where = "_id =" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.query(table,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void alteraRegistro(int id, String frase){
        ContentValues valores;
        String where;

        SQLiteDatabase db = this.getWritableDatabase();

        where = "_id=" + id;

        valores = new ContentValues();
        valores.put("frase", frase);

        db.update("frases",valores,where,null);
        db.close();
    }




}

