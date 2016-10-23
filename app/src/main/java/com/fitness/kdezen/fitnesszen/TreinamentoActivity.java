package com.fitness.kdezen.fitnesszen;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import DAO.CrudExercicioDAO;
import DAO.CrudTreinoDAO;
import POJO.ExercicioVO;
import POJO.TreinoVO;

public class TreinamentoActivity extends AppCompatActivity {

    private EditText ordem;
    private TextView exercicios;
    private EditText serie;
    private TextView o_x;
    private EditText repeticao;
    private EditText carga;
    private TableRow linha;
    private TableLayout tabela;
    private LinearLayout encaixa;
    private LinearLayout titulo;
    private Cursor cExercicios=null;
    private CrudExercicioDAO dbExec=new CrudExercicioDAO(this);
    private CrudTreinoDAO dbTreino=new CrudTreinoDAO(this);
    private String variavel=null;
    private String varID=null;
    private int altura;
    private int largura;
    private ArrayList<EditText> listaOrdem=new ArrayList<EditText>();
    private ArrayList<TextView> listaExercicio=new ArrayList<TextView>();
    private ArrayList<EditText> listaSerie=new ArrayList<EditText>();
    private ArrayList<EditText> listaRepeticao=new ArrayList<EditText>();
    private ArrayList<EditText> listaCarga=new ArrayList<EditText>();
    private int conta=0;
    private ArrayList<Integer> id_exercicio=new ArrayList<Integer>();
    private String grupo;
    private String acao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treinamento);

        tabela=(TableLayout) findViewById(R.id.tabExercicios);

        pegaGrupo();
        acoes();
        mudaTitulo(grupo);

        dataFound();
        //userInset();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.treinamento, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem items){

        if(items.getItemId()==R.id.menu_cancelar){
            sair();
        }
        if(items.getItemId()==R.id.menu_salvar){
            saveData();
        }
        return super.onOptionsItemSelected(items);
    }

    public void sair(){
        finish();


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

        conta=cExercicios.getCount();


        // mensagens(this,"teste","registros= "+conta,"OK");

        if(cExercicios.moveToFirst()){
            //int conta=cExercicios.getCount();

            int i=0;



            do{

                consulta.setId_exercicio(Integer.parseInt(cExercicios.getString(0)));
                consulta.setId_grupo(Integer.parseInt(cExercicios.getString(1)));
                consulta.setExercicio(cExercicios.getString(2));

                ordem=new EditText(this);
                exercicios=new TextView(this);
                serie=new EditText(this);

                o_x=new TextView(this);

                repeticao=new EditText(this);
                carga=new EditText(this);

                encaixa=new LinearLayout(this);
                //tabela=new TableLayout(this);
                //encaixa.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
                encaixa.setGravity(Gravity.CENTER);
                linha=new TableRow(this);
             //   exercicios.setTextAppearance(this, android.R.attr.textAppearanceMedium);
              //  o_x.setTextAppearance(this, android.R.attr.textAppearanceMedium);

                exercicios.setTextSize(12);
                exercicios.setText(consulta.getExercicio().toString());
                int myExercicio=(int) consulta.getId_exercicio();
                id_exercicio.add(myExercicio);
                //repeticao[i].setTextAppearance(this, android.R.attr.textAppearanceMedium);
                //carga[i].setTextAppearance(this, android.R.attr.textAppearanceMedium);
//
//				//exercicios[i]
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

                listaOrdem.add(ordem);
                listaExercicio.add(exercicios);
                listaSerie.add(serie);
                listaRepeticao.add(repeticao);
                listaCarga.add(carga);

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

            conta=cExercicios.getCount();


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

                ordem=new EditText(this);
                exercicios=new TextView(this);
                serie=new EditText(this);

                o_x=new TextView(this);

                repeticao=new EditText(this);
                carga=new EditText(this);

                ordem.setText(treinoConsulta.getOrdem().toString());
                serie.setText(treinoConsulta.getSerie().toString());
                repeticao.setText(treinoConsulta.getRepeticao().toString());
                carga.setText(treinoConsulta.getCarga().toString());


                encaixa=new LinearLayout(this);

                encaixa.setGravity(Gravity.CENTER);
                linha=new TableRow(this);
               // exercicios.setTextAppearance(this, android.R.attr.textAppearanceMedium);
              //  o_x.setTextAppearance(this, android.R.attr.textAppearanceMedium);

                exercicios.setTextSize(12);
                exercicios.setText(treinoConsulta.getExercicio().toString());
                int myExercicio=(int) treinoConsulta.getId_exercicio();
                id_exercicio.add(myExercicio);

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

                listaOrdem.add(ordem);
                listaExercicio.add(exercicios);
                listaSerie.add(serie);
                listaRepeticao.add(repeticao);
                listaCarga.add(carga);

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



    public void saveData(){

        TreinoVO treino=new TreinoVO();

        //Cursor idCursor=dbTreino.innerJoin(usuario, variavel);
        Cursor idCursor=dbTreino.idCursor(variavel);

        int jatem=idCursor.getCount();


        int myconta=0;

        //livrando dos campos em branco
        for(int i=0;i<conta;i++){

            if(listaOrdem.get(i).getText().toString()!=null && listaSerie.get(i).getText().toString()!=null && listaRepeticao.get(i).getText().toString()!=null && listaCarga.get(i).getText().toString()!=null){

                myconta++;

                if(jatem>0){


                    treino.setId_exercicio(Integer.parseInt(id_exercicio.get(i).toString()));
                    treino.setId_grupo(Integer.parseInt(variavel));
                    treino.setOrdem(listaOrdem.get(i).getText().toString());
                    treino.setSerie(listaSerie.get(i).getText().toString());
                    treino.setRepeticao(listaRepeticao.get(i).getText().toString());
                    treino.setCarga(listaCarga.get(i).getText().toString());
                    int idExercicio=Integer.parseInt(id_exercicio.get(i).toString());
                    int idGrupo=Integer.parseInt(variavel);
                    dbTreino.open();
                    dbTreino.atualizar(treino,idGrupo,idExercicio);
                    dbTreino.close();

                }else{

                    treino.setId_exercicio(Integer.parseInt(id_exercicio.get(i).toString()));
                    treino.setId_grupo(Integer.parseInt(variavel));
                    treino.setOrdem(listaOrdem.get(i).getText().toString());
                    treino.setSerie(listaSerie.get(i).getText().toString());
                    treino.setRepeticao(listaRepeticao.get(i).getText().toString());
                    treino.setCarga(listaCarga.get(i).getText().toString());

                    dbTreino.open();
                    dbTreino.inserir(treino);
                    dbTreino.close();

                }

            }


        }

        mensagens(this,"Salvando Dados","Salvo com sucesso.","OK");

    }
    public void mensagens(TreinamentoActivity context, String title, String message, String textP){

        AlertDialog.Builder builder=new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);



        builder.setPositiveButton(textP,new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;

                //runActivity(com.example.fitnessdroid.PersonalizarActivity.class);
            }
        });

        builder.show();

    }
}
