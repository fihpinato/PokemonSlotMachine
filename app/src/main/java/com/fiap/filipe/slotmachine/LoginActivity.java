package com.fiap.filipe.slotmachine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private ImageView ivChar;
    private EditText etNome;
    private Spinner spFichas;

    private boolean checked;
    private int escolha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ivChar = (ImageView) findViewById(R.id.ivChar);

        etNome = (EditText) findViewById(R.id.etNome);

        spFichas = (Spinner) findViewById(R.id.spFichas);
    }

    public void changeImage(View v){
        checked = ((RadioButton) v).isChecked();

        switch (v.getId()){
            case R.id.rbAsh:
                if(checked)
                    ivChar.setBackgroundResource(R.drawable.ash);
                escolha = 1;
                break;
            case R.id.rbMay:
                if(checked)
                    ivChar.setBackgroundResource(R.drawable.may);
                    escolha = 2;
                break;
        }
    }

    public void jogar(View v) {
        if(etNome.getText().toString().equals("")) {
            Toast.makeText(this, "Coloque seu nome!", Toast.LENGTH_SHORT).show();
        } else if (!checked){
            Toast.makeText(this, "Selecione seu personagem!", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(this, MaquinaActivity.class);
            i.putExtra("NOME", etNome.getText().toString());
            i.putExtra("FICHAS",spFichas.getSelectedItem().toString());
            i.putExtra("ESCOLHA",Integer.toString(escolha));
            startActivity(i);
        }
    }
}
