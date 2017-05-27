package com.fiap.filipe.slotmachine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import info.hoang8f.widget.FButton;

public class MaquinaActivity extends AppCompatActivity {

    private ImageView ivSlot1, ivSlot2, ivSlot3;
    private Roda slot1, slot2, slot3;
    private TextView tvNome, tvFichas, tvMaquina, tvResultado;
    private FButton btJogar;

    private int numFichas;

    public static final Random RANDOM = new Random();

    public static long randomLong(long lower, long upper){
        return lower + (long)(RANDOM.nextDouble() * (upper - lower));
    }

    Context context = this;
    MediaPlayer mp;

    Timer T = new Timer();
    int segundo, minuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maquina);

        Intent i = getIntent();

        ivSlot1 = (ImageView) findViewById(R.id.ivSlot1);
        ivSlot2 = (ImageView) findViewById(R.id.ivSlot2);
        ivSlot3 = (ImageView) findViewById(R.id.ivSlot3);

        tvNome = (TextView) findViewById(R.id.tvNome);
        tvFichas = (TextView) findViewById(R.id.tvFichas);
        tvMaquina = (TextView) findViewById(R.id.tvMaquina);
        tvResultado = (TextView) findViewById(R.id.tvResultado);

        btJogar = (FButton) findViewById(R.id.btJogar);

        numFichas = Integer.parseInt(i.getStringExtra("FICHAS"));
        tvFichas.setText(getString(R.string.txt_fichas) + Integer.toString(numFichas));

        String nome = i.getStringExtra("NOME");
        int numEscolha = Integer.parseInt(i.getStringExtra("ESCOLHA"));
        tvNome.setText(nome);
        switch (numEscolha){
            case 1:
                tvNome.setTextColor(Color.BLUE);
                break;
            case 2:
                tvNome.setTextColor(Color.RED);
                break;
        }

        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        segundo++;
                        if(segundo > 59){
                            minuto++;
                            segundo = 0;
                        }
                        if(segundo < 10 && minuto < 10){
                            tvMaquina.setText("0" + minuto + ":" + "0" + segundo);
                        } else if(segundo >= 10 && minuto < 10){
                            tvMaquina.setText("0" + minuto + ":" + segundo);
                        } else {
                            tvMaquina.setText(minuto + ":" + segundo);
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    public void jogar(View v){
        if(numFichas > 0){
            rodarSlot1();
            rodarSlot2();
            rodarSlot3();

            btJogar.setEnabled(false);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    slot1.pararDeRodar();
                    slot2.pararDeRodar();
                    slot3.pararDeRodar();

                    exibeResultado();

                    btJogar.setEnabled(true);
                }
            }, 3000);
            numFichas--;
        } else {
            Toast.makeText(this, R.string.txt_aviso, Toast.LENGTH_SHORT).show();
        }
        tvFichas.setText(getString(R.string.txt_fichas) + Integer.toString(numFichas));
    }

    private void exibeResultado(){
        if(slot1.indiceAtual == slot2.indiceAtual && slot2.indiceAtual == slot3.indiceAtual){
            tvResultado.setTextColor(Color.GREEN);
            tvResultado.setText(R.string.txt_vitoria);
            numFichas++;
            try {
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                    mp = MediaPlayer.create(context, R.raw.coin1);
                } mp.start();
            } catch(Exception e) { e.printStackTrace(); }
        } else if (slot1.indiceAtual == slot2.indiceAtual || slot2.indiceAtual == slot3.indiceAtual || slot1.indiceAtual == slot3.indiceAtual){
            tvResultado.setTextColor(Color.BLUE);
            tvResultado.setText(R.string.txt_empate);
            try {
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                    mp = MediaPlayer.create(context, R.raw.coin10);
                } mp.start();
            } catch(Exception e) { e.printStackTrace(); }
        } else {
            tvResultado.setTextColor(Color.RED);
            tvResultado.setText(R.string.txt_derrota);
            try {
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                    mp = MediaPlayer.create(context, R.raw.error);
                } mp.start();
            } catch(Exception e) { e.printStackTrace(); }
        }
    }

    private void rodarSlot1(){
        slot1 = new Roda(new Roda.RodaListener(){
           @Override
            public void novaImagem(final int resourceID){
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       ivSlot1.setImageResource(resourceID);
                   }
               });
           }
        }, 200, randomLong(0,200));
        slot1.start();
    }

    private void rodarSlot2(){
        slot2 = new Roda(new Roda.RodaListener(){
            @Override
            public void novaImagem(final int resourceID){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivSlot2.setImageResource(resourceID);
                    }
                });
            }
        }, 200, randomLong(150,400));
        slot2.start();
    }

    private void rodarSlot3(){
        slot3 = new Roda(new Roda.RodaListener(){
            @Override
            public void novaImagem(final int resourceID){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivSlot3.setImageResource(resourceID);
                    }
                });
            }
        }, 200, randomLong(300,600));
        slot3.start();
    }

}
