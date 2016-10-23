package com.fitness.kdezen.fitnesszen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        abreActivity(R.id.layoutTreinamento,com.fitness.kdezen.fitnesszen.GrupoMuscularActivity.class);
        abreActivity(R.id.layoutficha,com.fitness.kdezen.fitnesszen.GrupoMuscularFichaActivity.class);
        abreActivity(R.id.layoutRepertorio,com.fitness.kdezen.fitnesszen.GrupoMuscularRepertorioActivity.class);
        abreActivity(R.id.layoutExercicios,com.fitness.kdezen.fitnesszen.GrupoMuscularExerciciosActivity.class);
    }

    public void runActitivty(Class<?> className){

        Intent intent=new Intent(this,className);

        this.startActivity(intent);

    }


    public void abreActivity(int id,Class<?> myClass){

        LinearLayout abre=(LinearLayout) findViewById(id);

        final Class classe=myClass;

        abre.setClickable(true);
        abre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                runActitivty(classe);


            }
        });


    }
}
