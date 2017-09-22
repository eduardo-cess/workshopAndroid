package com.workshop.cotic.feedback;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class VisualizarPalestra extends AppCompatActivity {

    Bundle extras;
    TextView textViewTitulo;
    TextView textViewTipo;
    DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_palestra);

        helper = new DataBaseHelper(this);
        extras = getIntent().getExtras();
        int id = (Integer) extras.getInt("id");
        String[] campos = {"titulo", "tipo"};
        String table = "palestra";
        Cursor cursor = helper.carregaDadoById(id , campos, table);

        String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
        String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
//
        textViewTitulo = (TextView)findViewById(R.id.visualizarTitulo);
        textViewTipo = (TextView)findViewById(R.id.visualizarTipo);
        textViewTitulo.setText("Título: "+titulo);
        textViewTipo.setText("Tipo: "+tipo);

    }
}
