package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import POJO.RepertorioVO;

public class CrudRepertorioDAO {

    private String nomeTabela="repertorio";
    private SQLiteDatabase database;
    private ConexaoDAO db;
    public CrudRepertorioDAO (Context context){

        db=new ConexaoDAO(context);


    }


    public void open() throws SQLiteException{
        database=db.getWritableDatabase();
    }
    public void close(){
        if (database!=null){
            database.close();
        }
    }
    public void inserir(RepertorioVO tbValue){
        //
        ContentValues value=new ContentValues();

        //value.put("id_repertorio",tbValue.getId_repertorio());
        //value.put("id_repertorio",tbValue.getId_repertorio());
        value.put("id_grupo",tbValue.getId_grupo());
        value.put("musica",tbValue.getMusica());
        value.put("caminho", tbValue.getCaminho());

        database.insert("repertorio", null, value);


        //close();

    }

    public void atualizar(RepertorioVO tbValue,long rowID){
        //
        ContentValues value=new ContentValues();

        //value.put("id_repertorio",tbValue.getId_repertorio());
        //value.put("id_repertorio",tbValue.getId_repertorio());
        value.put("id_grupo",tbValue.getId_grupo());
        value.put("musica",tbValue.getMusica());
        value.put("caminho", tbValue.getCaminho());

        database.update("repertorio", value, "id_repertorio="+rowID, null);


        //close();

    }


    public Cursor tbCursor(){


        String selectQuery="SELECT  * FROM "+nomeTabela;
        SQLiteDatabase myDb=db.getWritableDatabase();
        Cursor cursor=myDb.rawQuery(selectQuery, null);

        return cursor;

    }



    public Cursor idCursor(String id){

        String selectQuery="SELECT  * FROM "+nomeTabela+" WHERE id_grupo="+id;
        SQLiteDatabase myDb=db.getWritableDatabase();
        Cursor cursor=myDb.rawQuery(selectQuery, null);

        return cursor;
    }


    public Cursor porID(String por,String id){

        String selectQuery="SELECT  * FROM "+nomeTabela+" WHERE "+por+id;
        SQLiteDatabase myDb=db.getWritableDatabase();
        Cursor cursor=myDb.rawQuery(selectQuery, null);

        return cursor;
    }

    public boolean deleteConfirm(long rowId)
    {
        open();
        return database.delete(nomeTabela, "id_repertorio=" + rowId, null) > 0;
    }

}
