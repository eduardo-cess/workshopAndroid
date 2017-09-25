package com.workshop.cotic.feedback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Avaliacao extends AppCompatActivity {

    private RadioGroup mAvaliacaoRadioGroup;
    private EditText mAvaliacaoText;
    private Button mEnviarAvaliacao;
    private DataBaseHelper helper;
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);

        helper =  new DataBaseHelper(this);
        mAvaliacaoText = (EditText) findViewById(R.id.avaliacao_text);
        mEnviarAvaliacao = (Button) findViewById(R.id.avaliacao_button_enviar);
        mAvaliacaoRadioGroup = (RadioGroup) findViewById(R.id.avaliacao_radioGroup);

//        extras = getIntent().getExtras();
//        int idPalestra = extras.getInt("idPalestra");
//        String[] campos = {"avaliacao", "sugestao", "palestra_id"};
//        helper.carregaAvaliacaoByIdPalestra(idPalestra, campos);


        mEnviarAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemChecked = mAvaliacaoRadioGroup.getCheckedRadioButtonId();
                String sugestao = mAvaliacaoText.getText().toString();
                if(itemChecked < 0 || sugestao.equals("")){
                    Toast.makeText(Avaliacao.this, "Por favor preencha os campos", Toast.LENGTH_SHORT).show();
                }
                else {
                    extras = getIntent().getExtras();
                    int idPalestra = extras.getInt("id");
                    helper.inserirAvaliacao(itemChecked, sugestao, idPalestra);
                    Toast.makeText(Avaliacao.this, "Avaliação cadastrada com Sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Avaliacao.this, MainActivity.class));
                }

            }
        });


    }
}
