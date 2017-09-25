package com.workshop.cotic.feedback;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.ArrayMap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    //Cria a tabela Palestra e a Tabela Avaliação
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS palestra (_id INTEGER PRIMARY KEY AUTOINCREMENT, titulo VARCHAR, tipo INT(1))");
        db.execSQL("CREATE TABLE IF NOT EXISTS avaliacao " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "avaliacao INT(1)," +
                "sugestao VARCHAR," +
                "palestra_id INT (3) NOT NULL, " +
                "FOREIGN KEY(palestra_id) REFERENCES palestra (_id))");
    }

    //Insere uma nova palestra
    public void inserirPalestra(String titulo, int tipo){
        ContentValues values = new ContentValues();
        values.put("titulo", titulo);
        values.put("tipo", tipo);
        getWritableDatabase().insert("palestra", null, values);
    }

    // Insere uma nova Avaliação
    public void inserirAvaliacao (int avaliacao, String sugestao, int palestraId ){
        ContentValues values = new ContentValues();
        values.put("avaliacao", avaliacao);
        values.put("sugestao", sugestao);
        values.put("palestra_id",palestraId);
        getWritableDatabase().insert("avaliacao", null, values);

    }
    // Pega TODOS os registros de uma tabela
    public Cursor carregaDados(String[] campos, String table){
        Cursor cursor;
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.query(table, campos, null, null, null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    // Apaga um registro de uma tabela conforme a condição where
    public void deleteRegistro(String nomeTabela, String condicaoWhere){
        getWritableDatabase().delete(nomeTabela, condicaoWhere, null);
    }

    //Carrega dados por id
    public Cursor carregaDadoById(int id, String[] campos, String table){
        Cursor cursor;
        String where = "_id =" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.query(table,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    // Carrega avaliação de uma palestra
    public Cursor carregaAvaliacaoByIdPalestra(int idPalestra, String[] campos){
        Cursor cursor;
        String where = "palestra_id =" + idPalestra;
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.query("avaliacao", campos, where, null, null, null, null, null);
        cursor.moveToFirst();
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST palestra");
        db.execSQL("DROP TABLE IF EXIST avaliacao");
        onCreate(db);
    }

}

