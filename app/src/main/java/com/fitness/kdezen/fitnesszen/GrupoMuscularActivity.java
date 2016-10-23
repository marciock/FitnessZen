package com.fitness.kdezen.fitnesszen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class GrupoMuscularActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_muscular);
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
        intent.putExtra("acao","edicao");
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
