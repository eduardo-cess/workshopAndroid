package com.workshop.cotic.feedback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class VisualizarPalestra extends AppCompatActivity implements View.OnClickListener {

    Bundle extras;
    TextView textViewTitulo;
    TextView textViewTipo;
    DataBaseHelper helper;
    private AlertDialog.Builder dialog;
    private Button mAvaliarButton;
    private ImageButton mApagarPalestra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_palestra);

        textViewTitulo = (TextView)findViewById(R.id.visualizarTitulo);
        textViewTipo = (TextView)findViewById(R.id.visualizarTipo);
        mAvaliarButton = (Button) findViewById(R.id.visualizar_avaliar_btn);
        mApagarPalestra = (ImageButton) findViewById(R.id.visualizar_delete_btn);

        mAvaliarButton.setOnClickListener(this);
        mApagarPalestra.setOnClickListener(this);


        helper = new DataBaseHelper(this);
        int id = getIdExtra();
        String[] campos = {"titulo", "tipo"};
        String table = "palestra";
        Cursor cursor = helper.carregaDadoById(id , campos, table);

        String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
        String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
//
        textViewTitulo.setText("Título: "+titulo);
        textViewTipo.setText("Tipo: "+tipo);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.visualizar_avaliar_btn){
            Intent avaliacaoIntent = new Intent(VisualizarPalestra.this, Avaliacao.class);
            avaliacaoIntent.putExtra("idPalestra", getIdExtra());
            startActivity(avaliacaoIntent);
        }
        if(view.getId() == R.id.visualizar_delete_btn){
            createDialog();
        }

    }

    private void createDialog() {
        dialog = new AlertDialog.Builder(VisualizarPalestra.this);
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
                helper.deleteRegistro("palestra", "_id = " + getIdExtra());
                startActivity(new Intent(VisualizarPalestra.this, MainActivity.class));
                Toast.makeText(VisualizarPalestra.this, "Palestra apagada", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
    }

    public int getIdExtra() {
        extras = getIntent().getExtras();
        int idPalestra = (Integer) extras.getInt("id");
        return idPalestra;
    }
}
