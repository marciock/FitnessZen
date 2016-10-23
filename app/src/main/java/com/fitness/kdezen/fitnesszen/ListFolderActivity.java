package com.fitness.kdezen.fitnesszen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import DAO.CrudRepertorioDAO;
import POJO.RepertorioVO;

public class ListFolderActivity extends AppCompatActivity {

    private String myPasta;
    private String tipo;
    private int caso;
    private String grupo;
    private CrudRepertorioDAO dbExec;
    private String individuo;
    private RepertorioVO consulta=null;
    private Handler progressBarHandler= new Handler();
    private ProgressDialog progressBar;
    private int barra;
    private int progressBarStatus;
    private ArrayList<String> myList=new ArrayList<String>();
    private ArrayList<String> myPath=new ArrayList<String>();
    private String myCaminho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_folder);

        this.setTitle("Musicas");
        dbExec=new CrudRepertorioDAO(this);

        requestView();
        levelUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_folder, menu);
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

        runActivityBack(com.fitness.kdezen.fitnesszen.RepertorioActivity.class);

    }
    public void saveData(){

        if(myList.size()==0){
            runActivityBack(com.fitness.kdezen.fitnesszen.RepertorioActivity.class);

        }else{
            insereSom(myPath, myList);


        }
    }

    public void levelUp(){

        LinearLayout levelUP=(LinearLayout) findViewById(R.id.LayoutLevelUp);
        TextView voltar=(TextView) findViewById(R.id.textVoltar);
        levelUP.setClickable(true);



        //voltar.setText(myPasta);
        voltar.setText(myPasta);

        voltar.setTextColor(Color.parseColor("#87F007"));

        levelUP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.parseColor("#87F007"));

                File file=new File(myPasta);

                String var=file.getParent().toString();


                if(var.equals("/")){
                    //AlertDialog.Builder msn=new AlertDialog.Builder(v.getContext());
                    //	msn.setTitle("teste");
                    //	msn.setMessage(var);
                    //	msn.show();

                    runActivity(com.fitness.kdezen.fitnesszen.ListFolderActivity.class,"diretorio","/mnt");

                }else{
                    runActivity(com.fitness.kdezen.fitnesszen.ListFolderActivity.class,"diretorio",var);
                }

            }
        });

    }



    public void runActivityBack(Class<?> class1){

        Intent intent=new Intent(this,class1);

        intent.putExtra("filtro", tipo);
        intent.putExtra("grupo",grupo);

        startActivity(intent);


        finish();
    }

    public void runActivitySound(Class<?> class1,String receptor,String var){

        Intent intent=new Intent(this,class1);
        intent.putExtra(receptor, var);
        //intent.putExtra("acao","listagem");
        startActivity(intent);

        finish();
    }

    public void runActivity(Class<?> class1,String receptor,String var){

        Intent intent=new Intent(this,class1);
        intent.putExtra(receptor, var);
        intent.putExtra("filtro", tipo);
        intent.putExtra("grupo",grupo);
        //	intent.putExtra("individuo", individuo);


        startActivity(intent);
        //setResult(RESULT_OK, intent);
        finish();
    }

    public void requestView(){
        Bundle extras=getIntent().getExtras();

        if(extras.getString("diretorio")!=null){

            myPasta=extras.getString("diretorio").toString();
        }

        if(extras.getString("filtro")!=null){

            tipo=extras.getString("filtro").toString();
        }

        if(extras.getString("grupo")!=null){
            grupo=extras.getString("grupo").toString();
        }

    }

    public void listaPasta(){

        //String lista=motivo;


        File dir=new File(myPasta);
        File afile=null;

        File[] fileList=dir.listFiles();

        LinearLayout layout=(LinearLayout) findViewById(R.id.listFolder);

        if(fileList !=null){
            for(int i=0;i<fileList.length;i++){
                barra=i;
                ImageView imgFolder=new ImageView(this);

                //myFolder.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));

                TextView nomePasta=new TextView(this);
                // CheckBox nomePasta=new CheckBox(this);
                String  caminho = null;


                // if(fileList[i].isDirectory()){
                if(fileList[i].isDirectory()){
                    imgFolder.setImageResource(R.drawable.arquivo);




                    afile=new File(fileList[i].toString());
                    nomePasta.setText(afile.getName().toString());

                    caminho=fileList[i].getAbsolutePath().toString();

                    final String var=caminho;
                    final  File fl=new File(fileList[i].toString());
                    nomePasta.setTextSize(20);
                    nomePasta.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    nomePasta.setTextColor(Color.parseColor("#87F007"));
                    nomePasta.setPadding(10, 4, 4, 10);

                    LinearLayout ll=new LinearLayout(this);
                    ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ll.setGravity(Gravity.CENTER_VERTICAL);
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    ll.setGravity(Gravity.LEFT);
                    ll.setPadding(4, 20, 2, 20);

                    ll.addView(imgFolder);
                    ll.addView(nomePasta);
                    ll.setClickable(true);
                    ll.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            v.setBackgroundColor(Color.parseColor("#aa87F007"));

                            progressBar = new ProgressDialog(v.getContext());
                            progressBar.setCancelable(true);
                            progressBar.setMessage("Listando ...");
                            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressBar.setProgress(0);
                            progressBar.setMax(100);
                            progressBar.show();


                            //limpa status da barra de progresso
                            progressBarStatus=0;

                            new Thread(new Runnable(){

                                public void run(){
                                    while(progressBarStatus<100)
                                    {
                                        progressBarStatus=barra;

                                        try{
                                            Thread.sleep(1000);
                                        }catch(InterruptedException e){
                                            e.printStackTrace();
                                        }
                                        progressBarHandler.post(new Runnable(){
                                            public void run(){
                                                progressBar.setProgress(progressBarStatus);
                                            }
                                        });

                                    }

                                    if(progressBarStatus>=100)
                                    {
                                        try{
                                            Thread.sleep(2000);
                                        }catch(InterruptedException e){
                                            e.printStackTrace();
                                        }
                                        progressBar.dismiss();

                                    }
                                }



                            }).start();

                            runActivity(com.fitness.kdezen.fitnesszen.ListFolderActivity.class,"diretorio",var );


                        }

                    });

                    layout.addView(ll);
                }//final do if();
                else{
                    // 	finish();
                    listaSom();
                }
            }

        }

    }

    public void insereSom(ArrayList<String> caminho,ArrayList<String> myNome){
        consulta=new RepertorioVO();

        int idgrupo=Integer.parseInt(grupo);
        for(int i=0;i<myNome.size();i++){
            consulta.setId_grupo(idgrupo);
            consulta.setMusica(myNome.get(i));
            consulta.setCaminho(caminho.get(i));
            dbExec.open();
            dbExec.inserir(consulta);
            dbExec.close();
        }

        runActivityBack(com.fitness.kdezen.fitnesszen.RepertorioActivity.class);

    }

    public void listaSom(){




        File dir=new File(myPasta);
        File afile=null;

        File[] fileList=dir.listFiles();

        LinearLayout layout=(LinearLayout) findViewById(R.id.listFolder);




        if(fileList !=null){





            for(int i=0;i<fileList.length;i++){






                ImageView imgFolder=new ImageView(this);

                //myFolder.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));

                final TextView nomePasta=new TextView(this);
                //   CheckBox nomePasta=new CheckBox(getApplicationContext());
                String  caminho = null;




                if (fileList[i].toString().endsWith(".mp3") || fileList[i].toString().endsWith(".mp4") || fileList[i].toString().endsWith(".wav")) {



                    imgFolder.setImageResource(R.drawable.soundfolder);
                    afile=new File(fileList[i].toString());



                    nomePasta.setText(afile.getName().toString());

                    caminho=fileList[i].getAbsolutePath().toString();

                    myCaminho=fileList[i].getAbsolutePath().toString();

                    //itemList.add(caminho);
                    final String var=caminho;
                    final String myNome=afile.getName().toString();
                    //final String myNome=caminho;
                    nomePasta.setTextSize(20);
                    nomePasta.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    nomePasta.setTextColor(Color.parseColor("#87F007"));
                    nomePasta.setPadding(10, 4, 4, 10);


                    LinearLayout ll=new LinearLayout(this);

                    ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ll.setGravity(Gravity.CENTER_VERTICAL);
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    ll.setGravity(Gravity.LEFT);
                    ll.setPadding(4, 20, 4, 20);

                    ll.addView(imgFolder);
                    ll.addView(nomePasta);
                    ll.setClickable(true);
                    ll.setOnClickListener(new View.OnClickListener() {


                        @Override
                        public void onClick(View v) {
                            v.setBackgroundColor(Color.parseColor("#aa87F007"));


                            //
                            //  insereSom(var,myNome);
                            // finish();http://www.youtube.com/


                            //  runActivitySound(com.kdezen.fitnesszen.RepertorioActivity.class,"grupo",grupo);

                            int ocorrencia= Collections.frequency(myList,nomePasta.getText().toString() );

                            if(ocorrencia==0)
                            {
                                myList.add(nomePasta.getText().toString());
                                myPath.add(myCaminho);
                            }else{

                                for(int i=0;i<myList.size();i++){

                                    if(myList.get(i).toString()==nomePasta.getText().toString()){

                                        myList.remove(i);
                                        myPath.remove(i);

                                    }

                                }
                                v.setBackgroundColor(Color.parseColor("#0087F007"));
                            }



                        }


                    });



                    layout.addView(ll);
                }//final do if();
            }



        }

    }
}
