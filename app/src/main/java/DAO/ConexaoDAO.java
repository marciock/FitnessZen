package DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConexaoDAO extends SQLiteOpenHelper{


    private static String card=Environment.getExternalStorageDirectory().toString();
    private static String dbPath=card+"/Android/data/";
    private static String dbName="fitnesszen.sqlite";
    private Context context;
    //private File dbFile;
    private SQLiteDatabase myDatabase;
    public ConexaoDAO(Context context) {
        super(context,dbPath+dbName, null,1);
        //"
        this.context=context;

        //dbFile=new File(dbPath,dbName);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    private boolean checkDataBase(){

        SQLiteDatabase db=null;
        try{

            String path=dbPath+dbName;

            db=SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            db.close();
        }catch(SQLiteException e){


        }
        return db !=null;

    }

    public void createDataBase() throws Exception{

        {

            boolean exists=checkDataBase();


            if(!exists){
                this.getReadableDatabase();

                try{
                    copyDataBase();
                }catch (Exception e) {
                    throw new Error("Não foi possível copiar o arquivo");
                }


            }


        }


    }



    private void copyDataBase() {
        try{

            String cpPath=dbPath+dbName;


            InputStream dbInputStream=context.getAssets().open(dbName);


            OutputStream dbStream=new FileOutputStream(cpPath);

            byte[] buffer=new byte[1024];

            int length;

            while((length=dbInputStream.read(buffer))>0){
                dbStream.write(buffer,0,length);
            }


            dbStream.flush();
            dbStream.close();
            dbInputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public SQLiteDatabase getDatabase() {

        try{
            //createDataBase();
            String path=dbPath+dbName;
            return	SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }catch (Exception e) {
            return getWritableDatabase();
        }






    }
}
