package com.fiap.filipe.slotmachine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void jogar(View v){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void sobre(View v){
        Intent i = new Intent(this, SobreActivity.class);
        startActivity(i);
    }
}
