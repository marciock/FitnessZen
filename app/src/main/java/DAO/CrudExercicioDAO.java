package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.SimpleCursorAdapter;

import POJO.ExercicioVO;

public class CrudExercicioDAO {


    private String nomeTabela="exercicio";
    private SQLiteDatabase database;
    private ConexaoDAO db;
    private SimpleCursorAdapter adapterCursor;

    public CrudExercicioDAO(Context context){

        ConexaoDAO db=new ConexaoDAO(context);


    }

    public void open() throws SQLiteException{
        database=db.getWritableDatabase();
    }
    public void close(){
        if (database!=null){
            database.close();
        }
    }
    public void inserir(ExercicioVO tbValue){
        //
        ContentValues value=new ContentValues();

        value.put("id_grupo",tbValue.getId_grupo());
        value.put("exercicio",tbValue.getExercicio());


        //

        database.insert("exercicio", null, value);


        close();

    }

    public void atualizar(ExercicioVO tbValue,long rowID){
        //
        ContentValues value=new ContentValues();
        value.put("id_grupo",tbValue.getId_grupo());
        value.put("exercicio",tbValue.getExercicio());

        //

        database.update("exercicio", value, "id_grupo="+rowID, null);


        close();

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

    public Cursor fieldCursor(String field){

        String selectQuery="SELECT  * FROM "+nomeTabela+" WHERE exercicio LIKE %"+field+"%";
        SQLiteDatabase myDb=db.getWritableDatabase();
        Cursor cursor=myDb.rawQuery(selectQuery, null);

        return cursor;
    }


    public boolean deleteConfirm(long rowId)
    {
        open();
        return database.delete(nomeTabela, "id_exercicio=" + rowId, null) > 0;
    }

}
