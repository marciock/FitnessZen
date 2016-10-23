package com.fitness.kdezen.fitnesszen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import DAO.ConexaoDAO;

public class MainActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConexaoDAO db = new ConexaoDAO(this);

        try {
            db.createDataBase();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        // tempo em que a tela fica

        Handler handler=new Handler();
        handler.postDelayed(this, 3000);
        //
    }



    public void run() {
        // TODO Auto-generated method stub
        startActivity(new Intent(this,com.fitness.kdezen.fitnesszen.LogoEntradaActivity.class));

        finish();

    }
}
