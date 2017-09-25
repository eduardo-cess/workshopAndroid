package com.workshop.cotic.feedback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

/**
 * Created by armando on 25/09/17.
 */

public class FeedBackHelper extends Activity{

    public void showListPalestras(final Context ContextoAtual, final int layout, final Cursor cursor,
                                  String[] nomeCampos, int[] idViews, ListView listarPalestra, final Context contextoAtual) {
        SimpleCursorAdapter adapterPalestras = new SimpleCursorAdapter(
                ContextoAtual,
                layout,
                cursor,
                nomeCampos,
                idViews,
                0
        );


        listarPalestra.setAdapter(adapterPalestras);

        listarPalestra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                Intent intent = new Intent(contextoAtual.getApplicationContext() , VisualizarPalestra.class);
                intent.putExtra("id", codigo);
                startActivity(intent);
            }
        });
    }
}
