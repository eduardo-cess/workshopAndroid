package com.workshop.cotic.feedback;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ListView mListPalestra;
    private List<String> palestras;
    private DataBaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListPalestra = (ListView) findViewById(R.id.mainListPalestra);
        showListPalestras();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CadastrarPalestra.class));
            }
        });
    }

    private void showListPalestras() {
        palestras = new ArrayList<String>();
        helper = new DataBaseHelper(this);
        String[] campos =  {"_id","titulo", "tipo"};
        String[] nomeCampos =  {"_id","titulo"};
        String table = "palestra";
        final Cursor cursor = helper.carregaDados(campos, table);
        int[] idViews = {R.id.contentMain_idPalestra, R.id.contentMain_titulo};

        SimpleCursorAdapter adapterPalestras = new SimpleCursorAdapter(
                this,
                R.layout.content_main ,
                cursor,
                nomeCampos,
                idViews,
                0
        );

//        final SQLiteDatabase database = openOrCreateDatabase("app", MODE_PRIVATE, null);
//        final Cursor cursor = database.rawQuery("SELECT _id, titulo, tipo FROM palestra", null);
//        cursor.moveToFirst();
//        while(cursor.moveToNext()){
//            String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
//            Log.i("id", "id: "+cursor.getString(cursor.getColumnIndex("id")));
//            Log.i("titulo", "titulo: "+cursor.getString(cursor.getColumnIndex("titulo")));
//            Log.i("tipo", "tipo: "+cursor.getString(cursor.getColumnIndex("tipo")));
//
//            palestras.add(titulo);
//        }
//
//        ArrayAdapter<String> adapterPalestras = new ArrayAdapter<String>(
//                MainActivity.this,
//                android.R.layout.simple_list_item_1, palestras);

        mListPalestra.setAdapter(adapterPalestras);

        mListPalestra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                Intent intent = new Intent(MainActivity.this, VisualizarPalestra.class);
                intent.putExtra("id", codigo);
                startActivity(intent);
                //finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
