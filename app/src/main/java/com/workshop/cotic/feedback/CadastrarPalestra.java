package com.workshop.cotic.feedback;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class CadastrarPalestra extends AppCompatActivity {

    private EditText titulo;
    private RadioGroup radioGroupTipoApresentacao;
    private Button enviar;
    DataBaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_palestra);

        //Database connection
        final SQLiteDatabase database = openOrCreateDatabase("app", MODE_PRIVATE, null);

        titulo = (EditText) findViewById(R.id.cadastro_titulo);
        radioGroupTipoApresentacao = (RadioGroup) findViewById(R.id.radioGroupTipoApresentacao);
        enviar = (Button)findViewById(R.id.cadastroPalestra_button_enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemSelectedId = radioGroupTipoApresentacao.getCheckedRadioButtonId();
                String tituloDigitado = titulo.getText().toString();

                if (tituloDigitado.equals("") || itemSelectedId < 0){
                    Toast.makeText(CadastrarPalestra.this, "Por favor preencha os campos", Toast.LENGTH_SHORT).show();
                }
                else {
                    inserePalestra(tituloDigitado, itemSelectedId, database);
                }

            }
        });

    }

    void inserePalestra(String titulo, int radioButtonTipoId, SQLiteDatabase database){
            switch (radioButtonTipoId){
            case (R.id.radioButton_palestra):
                radioButtonTipoId = 1;
                break;
            case (R.id.radioButton_minicurso):
                radioButtonTipoId = 2;
                break;
        }
        helper = new DataBaseHelper(this);
        helper.inserirPalestra(titulo,radioButtonTipoId);
        startActivity(new Intent(CadastrarPalestra.this, MainActivity.class));

    }
}
