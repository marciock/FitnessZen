package com.fitness.kdezen.fitnesszen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DAO.CrudRepertorioDAO;
import POJO.RepertorioVO;

public class RepertorioActivity extends AppCompatActivity {

    private ArrayAdapter<String> listAdapter;
    private String grupo;

    private String fileValue;
    private String variavel=null;
    private List<String> item=new ArrayList<String>();

    private Cursor cRepertorio=null;
    private CrudRepertorioDAO dbExec;
    private RepertorioVO myRepertorio;
    private String[] myPath;
    private int conta;
    private ArrayList<Integer> id_repertorio=new ArrayList<Integer>();
    private ListView lListSound;
    private LinearLayout btAddSound;
    private LinearLayout btDelSound;
    private LinearLayout btStopSound;
    private LinearLayout btPlaySound;
    private MediaPlayer toqueBem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repertorio);

        pegaGrupo();
        mudaTitulo(grupo);
        dbExec=new CrudRepertorioDAO(this);
        addSounds();
        //delSounds(); esta dando pau
//		playSound();
        ListSounds();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.repertorio, menu);
        return true;
    }


    public void tesste()
    {




    }

    public void pegaGrupo(){
        Bundle extras=getIntent().getExtras();

        if(extras!=null){

            grupo=extras.getString("grupo");
        }


    }
    public void mudaTitulo(String id){

        int i=Integer.parseInt(id);

        switch (i) {
            case 1:
                this.setTitle("Peitorais");
                break;
            case 2:
                this.setTitle("Dorsais");
                break;
            case 3:
                this.setTitle("Biceps");
                break;
            case 4:
                this.setTitle("Triceps");
                break;
            case 5:
                this.setTitle("Deltóides");
                break;
            case 6:
                this.setTitle("Abdomen");
                break;
            case 7:
                this.setTitle("M.I. Anteriores");
                break;
            case 8:
                this.setTitle("M.I. Posteriores");
                break;

            default:
                break;
        }

    }


    public void mensagens(Context context, String title, String message, String textP, String textN){

        AlertDialog.Builder builder=new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(textN,new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                //runActivity(com.example.fitnessdroid.FormManagerSoundActivity.class,"","");
            }
        });
        builder.setPositiveButton(textP,new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                finish();
                //runActivity(com.example.fitnessdroid.FormManagerSoundActivity.class,"","");
            }
        });
        builder.show();

    }

    public void addSounds(){

        btAddSound=(LinearLayout) findViewById(R.id.laddsound);

        btAddSound.setClickable(true);
        btAddSound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                runActivity(com.fitness.kdezen.fitnesszen.ListFolderActivity.class,grupo);


            }
        });


    }

    public void delSounds(){

        btDelSound=(LinearLayout) findViewById(R.id.ldelsound);

        final String music=selectChoices();

        if(music.isEmpty()){
            btDelSound.setClickable(false);
        }else{
            btDelSound.setClickable(true);
        }


        btDelSound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub



                Cursor pesquisa=dbExec.porID("musica LIKE","'%"+music+"%'");
                if(pesquisa.moveToFirst()){
                    int id=Integer.parseInt(pesquisa.getString(0));

                    dbExec.deleteConfirm(id);
                    //runActivity(com.example.fitnessdroid.FormManagerSoundActivity.class, "grupo", variavel);
                }
            }
        });

    }

    public void playSound()
    {
        btPlaySound=(LinearLayout) findViewById(R.id.lplaysound);

        btPlaySound.setClickable(true);
        btPlaySound.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                String music=selectChoices();
                Cursor pesquisa=dbExec.idCursor(id_repertorio.get(lListSound.getId()).toString());
                if(pesquisa.moveToFirst()){
                    String url=pesquisa.getString(3);
                    try {
                        toqueBem.setDataSource(url);
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();

                    }
                    try {
                        toqueBem.prepare();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    stopSound();
                    toqueBem.start();

                }

            }
        });



        String music=selectChoices();
        Cursor pesquisa=dbExec.porID("musica LIKE","'%"+music+"%'");
        if(pesquisa.moveToFirst()){
            int id=Integer.parseInt(pesquisa.getString(0));

            dbExec.deleteConfirm(id);
            //runActivity(com.example.fitnessdroid.FormManagerSoundActivity.class, "grupo", variavel);
        }


    }

    public void stopSound(){

        btStopSound=(LinearLayout) findViewById(R.id.lstopsound);

        btStopSound.setClickable(true);
        btStopSound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(toqueBem.isPlaying()){
                    toqueBem.stop();

                }
            }
        });

    }

    public void runActivity(Class<?> class1,String variavel){

        File sdcard= Environment.getExternalStorageDirectory();


        Intent intent=new Intent(this,class1);
        //intent.putExtra(receptor1, var1);
        intent.putExtra("diretorio","/mnt");
        intent.putExtra("grupo",variavel);
        startActivity(intent);
        finish();
        //sdcard.getAbsolutePath()


    }


    public void requestExtras(){
        //&& requestCode==REQUEST_CODE

        Bundle extras=getIntent().getExtras();

        //	if(extras.getString("arquivo")!=null){
        //		fileValue=extras.getString("arquivo");

        //	}"cod_repertorio=,

        if(extras.getString("grupo")!=null){
            variavel=extras.getString("grupo");

            mudaTitulo(variavel);

        }


    }

    public void ListSounds(){




        //LinearLayout layoutSound=(LinearLayout) findViewById(R.id.lListSound);

        lListSound=(ListView) findViewById(R.id.listSound);

        Bundle extras=getIntent().getExtras();
        String musculo=extras.getString("grupo");




        //começa o banco



        RepertorioVO consulta=new RepertorioVO();

        if(musculo!=null){
            //dbExec.open();
            cRepertorio=dbExec.idCursor(musculo);

            // conta=cRepertorio.getCount();



            //	msn.setMessage(cRepertorio.getString(1).toString());
            //msn.show();lListSound.getId();


            if(cRepertorio.moveToFirst()){
                //int conta=cRepertorios.getCount();

                int i=0;
                //ListView lListSound=(ListView) findViewById(R.id.listExercices);
                //List<String> item=new ArrayList<String>();

                do{

                    consulta.setId_repertorio(Integer.parseInt(cRepertorio.getString(0)));
                    consulta.setId_grupo(Integer.parseInt(cRepertorio.getString(1)));
                    consulta.setMusica(cRepertorio.getString(2));
                    consulta.setCaminho(cRepertorio.getString(3));


                    String varia=consulta.getMusica().toString();

                    //File myFile=new File(varia);

                    item.add(varia);lListSound.getId();

                    int myRepertorio=(int) consulta.getId_repertorio();
                    id_repertorio.add(myRepertorio);

                    i++;


                }while(cRepertorio.moveToNext());



            }

            listAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,item);
            lListSound.setChoiceMode(lListSound.CHOICE_MODE_SINGLE);
            lListSound.setAdapter(listAdapter);



        }
    }

    private String selectChoices(){
        int conta=lListSound.getCheckedItemCount();
        String var = null;



        for(int i=0;i<conta;i++){

            var=lListSound.getItemAtPosition(i).toString();



        }

        return var;


    }
}
