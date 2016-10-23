package com.fitness.kdezen.fitnesszen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class GrupoMuscularRepertorioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_muscular);

        abreActivity(R.id.layoutPeitorais, com.fitness.kdezen.fitnesszen.RepertorioActivity.class, "1");
        abreActivity(R.id.layoutDorsais,com.fitness.kdezen.fitnesszen.RepertorioActivity.class, "2");
        abreActivity(R.id.layoutBiceps, com.fitness.kdezen.fitnesszen.RepertorioActivity.class, "3");
        abreActivity(R.id.layoutTriceps, com.fitness.kdezen.fitnesszen.RepertorioActivity.class, "4");
        abreActivity(R.id.layoutDeltoides, com.fitness.kdezen.fitnesszen.RepertorioActivity.class, "5");
        abreActivity(R.id.layoutAbdomen, com.fitness.kdezen.fitnesszen.RepertorioActivity.class, "6");
        abreActivity(R.id.layoutAnteriores, com.fitness.kdezen.fitnesszen.RepertorioActivity.class, "7");
        abreActivity(R.id.layoutPosteriores, com.fitness.kdezen.fitnesszen.RepertorioActivity.class, "8");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.grupo_muscular, menu);
        return true;
    }

    public void runActitivty(Class<?> className,String grupo){

        Intent intent=new Intent(this,className);
        intent.putExtra("grupo", grupo);
        intent.putExtra("acao","listagem");
        this.startActivity(intent);

    }


    public void abreActivity(int id,Class<?> myClass,String idGrupo){

        LinearLayout abre=(LinearLayout) findViewById(id);

        final Class classe=myClass;
        final String myGrupo=idGrupo;
        abre.setClickable(true);
        abre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                runActitivty(classe,myGrupo);


            }
        });


    }
}
