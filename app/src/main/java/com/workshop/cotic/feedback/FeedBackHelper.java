package com.workshop.cotic.feedback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by armando on 25/09/17.
 */


public class FeedBackHelper extends Activity{
    private DataBaseHelper helper;

    public void showListPalestras(final Context contextoAtual, int[] idViews, ListView listarPalestra, final Class activityDestino) {

        final Cursor cursor = getCamposTabelaPalestra(contextoAtual);
        String[] nomeCampos =  {"_id","titulo"};
        SimpleCursorAdapter adapterPalestras = new SimpleCursorAdapter(
                contextoAtual,
                R.layout.content_main,
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
                Intent intent = new Intent(contextoAtual , activityDestino);
                codigo = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                intent.putExtra("id", codigo);
                contextoAtual.startActivity(intent);
            }
        });
    }


    public void createDialogExcluirPalestra(AlertDialog.Builder dialog, final Context contextoAtual, final int idPalestra){
            helper = getContextHelper(contextoAtual);
            dialog = new AlertDialog.Builder(contextoAtual);
            dialog.setTitle("Deletar Palestra");
            dialog.setMessage("Você tem certeza que deseja deletar palestra ?");

            dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    helper.deleteRegistro("palestra", "_id = " + idPalestra);
                    contextoAtual.startActivity(new Intent(contextoAtual, MainActivity.class));
                    Toast.makeText(contextoAtual, "Palestra apagada", Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alert = dialog.create();
            alert.show();
        }

    public Cursor getCamposTabelaPalestra(Context context){
        helper = getContextHelper(context);
        String[] campos =  {"_id","titulo", "tipo"};
        String table = "palestra";
        final Cursor cursor = helper.carregaDados(campos, table);
        return cursor;

    }

    public DataBaseHelper getContextHelper(Context context){
        helper = new DataBaseHelper(context);
        return helper;
    }

}
