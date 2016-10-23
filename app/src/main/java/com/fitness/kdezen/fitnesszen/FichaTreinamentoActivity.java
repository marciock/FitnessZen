package com.fitness.kdezen.fitnesszen;

import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.annotation.StyleableRes;
import android.support.v4.media.RatingCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import DAO.CrudExercicioDAO;
import DAO.CrudRepertorioDAO;
import DAO.CrudTreinoDAO;
import POJO.ExercicioVO;
import POJO.RepertorioVO;
import POJO.TreinoVO;

public class FichaTreinamentoActivity extends AppCompatActivity {

    private String grupo;
    private String acao;

    private MediaPlayer toqueBem=new MediaPlayer();
    private CrudRepertorioDAO dbRepertorio=new CrudRepertorioDAO(this);
    private RepertorioVO voRepertorio;
    private ArrayList<String> listaMusicas= new ArrayList<String>();
    private int track=0;
    private String variavel=null;
    private String varID=null;

//	private ArrayList<TextView> listaOrdem=new ArrayList<TextView>();
//	private ArrayList<TextView> listaExercicio=new ArrayList<TextView>();
//	private ArrayList<TextView> listaSerie=new ArrayList<TextView>();
//	private ArrayList<TextView> listaRepeticao=new ArrayList<TextView>();
//	private ArrayList<TextView> listaCarga=new ArrayList<TextView>();

    private TextView ordem;
    private TextView exercicios;
    private TextView serie;
    private TextView o_x;
    private TextView repeticao;
    private TextView carga;
    private TableRow linha;
    private TableLayout tabela;
    private LinearLayout encaixa;

    private Cursor cExercicios=null;
    private CrudExercicioDAO dbExec=new CrudExercicioDAO(this);
    private CrudTreinoDAO dbTreino=new CrudTreinoDAO(this);


    private ArrayList<Integer> id_exercicio=new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_treinamento);

        tabela=(TableLayout) findViewById(R.id.tabExercicios);
        pegaGrupo();
        acoes();
        mudaTitulo(grupo);

        dataFound();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ficha, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId()==R.id.menu_tras){
            irParaTras();

        }
        if(item.getItemId()==R.id.menu_tocar){
            tocar(track);
        }
        if(item.getItemId()==R.id.menu_pause){
            pausar();
        }
        if(item.getItemId()==R.id.menu_frente){
            irParFrente();
        }
        return super.onOptionsItemSelected(item);
    }

    public void pegaGrupo(){
        Bundle extras=getIntent().getExtras();

        if(extras!=null){

            grupo=extras.getString("grupo");
        }


    }


    public void abreRepertorio(){

        Bundle extras=getIntent().getExtras();



        voRepertorio=new RepertorioVO();

        Cursor cursor=dbRepertorio.idCursor(variavel);

        if(cursor.moveToFirst()){

            do{
                voRepertorio.setCaminho(cursor.getString(3));
                listaMusicas.add(voRepertorio.getCaminho().toString());

            }while(cursor.moveToNext());

        }
    }



    public void onBackPressed(){


        toqueBem.reset();

        finish();



    }


    public void irParaTras(){
        int total=listaMusicas.size();

        if(track==0){
            track=total;
        }else{

            track--;
        }
        toqueBem.reset();
        tocar(track);
    }
    public void irParFrente(){
        int total=listaMusicas.size();

        if(track>total){
            track=0;
        }else{

            track++;
        }
        toqueBem.reset();
        tocar(track);
    }
    public void tocar(int myTrack){
        abreRepertorio();
        //track=;

        try {
            toqueBem.setDataSource(listaMusicas.get(myTrack).toString());
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

        toqueBem.start();


    }
    public void pausar(){

        toqueBem.pause();
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

    public void acoes(){
        Bundle extras=getIntent().getExtras();

        if(extras.getString("acao")!=null){
            acao=extras.getString("acao");

        }


    }




    public void userInset(){

        Bundle extras=getIntent().getExtras();

        if(extras!=null){
            //varID=extras.getString("acao");
            variavel=extras.getString("grupo");

        }

        //mudaTitulo(variavel);


        ExercicioVO consulta=new ExercicioVO();

        cExercicios=dbExec.idCursor(variavel);

        // conta=cExercicios.getCount();


        // mensagens(this,"teste","registros= "+conta,"OK");

        if(cExercicios.moveToFirst()){
            //int conta=cExercicios.getCount();

            int i=0;



            do{

                consulta.setId_exercicio(Integer.parseInt(cExercicios.getString(0)));
                consulta.setId_grupo(Integer.parseInt(cExercicios.getString(1)));
                consulta.setExercicio(cExercicios.getString(2));

                ordem=new TextView(this);
                exercicios=new TextView(this);
                serie=new TextView(this);

                o_x=new TextView(this);

                repeticao=new TextView(this);
                carga=new TextView(this);

                encaixa=new LinearLayout(this);
                //tabela=new TableLayout(this);
                //encaixa.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
                encaixa.setGravity(Gravity.CENTER);
                linha=new TableRow(this);
             //   exercicios.setTextAppearance(this, android.R.attr.textAppearanceMedium);
             //   o_x.setTextAppearance(this, android.R.attr.textAppearanceMedium);

                exercicios.setTextSize(12);
                exercicios.setText(consulta.getExercicio().toString());
                int myExercicio=(int) consulta.getId_exercicio();
                o_x.setText("X");
                id_exercicio.add(myExercicio);
                //repeticao[i].setTextAppearance(this, android.R.attr.textAppearanceMedium);
                //carga[i].setTextAppearance(this, android.R.attr.textAppearanceMedium);
//
//			//exercicios[i]
                ordem.setEms(1);
                serie.setEms(2);
                repeticao.setEms(2);
                carga.setEms(2);
//
                ordem.setTextColor(Color.parseColor("#000000"));
                serie.setTextColor(Color.parseColor("#000000"));
                repeticao.setTextColor(Color.parseColor("#000000"));
                carga.setTextColor(Color.parseColor("#000000"));
//
//
                ordem.setHintTextColor(Color.parseColor("#585858"));
                serie.setHintTextColor(Color.parseColor("#585858"));
                repeticao.setHintTextColor(Color.parseColor("#585858"));
                carga.setHintTextColor(Color.parseColor("#585858"));
//
                serie.setInputType(InputType.TYPE_CLASS_NUMBER);
                repeticao.setInputType(InputType.TYPE_CLASS_NUMBER);
                carga.setInputType(InputType.TYPE_CLASS_NUMBER);
//
                exercicios.setId(i);
                ordem.setId(i);
                serie.setId(i);
                repeticao.setId(i);
                carga.setId(i);
//
//
                encaixa.addView(serie,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                encaixa.addView(o_x,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                encaixa.addView(repeticao,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

//			listaOrdem.add(ordem);
//			listaExercicio.add(exercicios);
//			listaSerie.add(serie);
//			listaRepeticao.add(repeticao);
//			listaCarga.add(carga);

                linha.addView(ordem,new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linha.addView(exercicios,new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linha.addView(encaixa,new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linha.addView(carga,new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                i++;



                tabela.addView(linha,new TableLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            }while(cExercicios.moveToNext());


        }


    }

    public void dataFound(){

        Bundle extras=getIntent().getExtras();

        if(extras!=null){
            //varID=extras.getString("listar");
            variavel=extras.getString("grupo");
            ExercicioVO consulta=new ExercicioVO();

            cExercicios=dbExec.idCursor(variavel);

            // conta=cExercicios.getCount();


        }
        //mudaTitulo(variavel);

        TreinoVO treinoConsulta=new TreinoVO();

        //Cursor cTreino=dbTreino.idCursor(variavel);
        Cursor cTreino=dbTreino.innerJoin(variavel);

        if(cTreino.moveToFirst()){


            int i=0;



            do{

                treinoConsulta.setId_exercicio(Integer.parseInt(cTreino.getString(0)));
                treinoConsulta.setId_grupo(Integer.parseInt(cTreino.getString(2)));
                treinoConsulta.setExercicio(cTreino.getString(1));
                treinoConsulta.setOrdem(cTreino.getString(4));
                treinoConsulta.setSerie(cTreino.getString(5));
                treinoConsulta.setRepeticao(cTreino.getString(6));
                treinoConsulta.setCarga(cTreino.getString(7));

                ordem=new TextView(this);
                exercicios=new TextView(this);
                serie=new TextView(this);

                o_x=new TextView(this);

                repeticao=new TextView(this);
                carga=new TextView(this);

                ordem.setText(treinoConsulta.getOrdem().toString());
                serie.setText(treinoConsulta.getSerie().toString());
                repeticao.setText(treinoConsulta.getRepeticao().toString());
                carga.setText(treinoConsulta.getCarga().toString());


                encaixa=new LinearLayout(this);

                encaixa.setGravity(Gravity.CENTER);
                linha=new TableRow(this);
            //    exercicios.setTextAppearance();
             //   o_x.setTextAppearance(this, android.R.attr.textAppearanceMedium);

                exercicios.setTextSize(12);
                exercicios.setText(treinoConsulta.getExercicio().toString());
                int myExercicio=(int) treinoConsulta.getId_exercicio();
                id_exercicio.add(myExercicio);

                o_x.setText("X");
                ordem.setEms(1);
                serie.setEms(2);
                repeticao.setEms(2);
                carga.setEms(2);



                ordem.setTextColor(Color.parseColor("#000000"));
                serie.setTextColor(Color.parseColor("#000000"));
                repeticao.setTextColor(Color.parseColor("#000000"));
                carga.setTextColor(Color.parseColor("#000000"));


                ordem.setHintTextColor(Color.parseColor("#585858"));
                serie.setHintTextColor(Color.parseColor("#585858"));
                repeticao.setHintTextColor(Color.parseColor("#585858"));
                carga.setHintTextColor(Color.parseColor("#585858"));

                serie.setInputType(InputType.TYPE_CLASS_NUMBER);
                repeticao.setInputType(InputType.TYPE_CLASS_NUMBER);
                carga.setInputType(InputType.TYPE_CLASS_NUMBER);

                exercicios.setId(i);
                ordem.setId(i);
                serie.setId(i);
                repeticao.setId(i);
                carga.setId(i);


                encaixa.addView(serie,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                encaixa.addView(o_x,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                encaixa.addView(repeticao,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//
//						listaOrdem.add(ordem);
//						listaExercicio.add(exercicios);
//						listaSerie.add(serie);
//						listaRepeticao.add(repeticao);
//						listaCarga.add(carga);

                linha.addView(ordem,new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linha.addView(exercicios,new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linha.addView(encaixa,new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linha.addView(carga,new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                i++;


                tabela.addView(linha,new TableLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//
            }while(cTreino.moveToNext());




        }else{
            userInset();
        }



    }
}
