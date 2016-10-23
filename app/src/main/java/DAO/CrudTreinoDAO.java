package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import POJO.TreinoVO;

public class CrudTreinoDAO {

    private String nomeTabela="treino";
    private SQLiteDatabase database;
    private ConexaoDAO db;

    public CrudTreinoDAO(Context context){

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
    public void inserir(TreinoVO tbValue){
        //
        ContentValues value=new ContentValues();

        //value.put("id_treino",tbValue.getId_treino());

        value.put("id_exercicio",tbValue.getId_exercicio());
        value.put("id_grupo", tbValue.getId_grupo());
        value.put("ordem",tbValue.getOrdem());
        value.put("serie",tbValue.getSerie());
        value.put("repeticao",tbValue.getRepeticao());
        value.put("carga",tbValue.getCarga());


        database.insert("treino", null, value);


        //close();

    }

    public void atualizar(TreinoVO tbValue,long rowID,long idExercicio){
        //
        ContentValues value=new ContentValues();

        //value.put("id_treino",tbValue.getId_treino());

        value.put("id_exercicio",tbValue.getId_exercicio());
        value.put("id_grupo", tbValue.getId_grupo());
        value.put("ordem",tbValue.getOrdem());
        value.put("serie",tbValue.getSerie());
        value.put("repeticao",tbValue.getRepeticao());
        value.put("carga",tbValue.getCarga());


        database.update("treino", value, "id_grupo="+rowID+" AND id_exercicio="+idExercicio, null);


        //close();

    }

    public Cursor innerJoin(String idGrupo){
        //+" AND b.id_grupo="+idGrupo+";"
        //
        String selectQuery="SELECT a.id_exercicio, a.exercicio, b.id_exercicio, b.id_grupo,b.ordem,b.serie,b.repeticao,b.carga FROM exercicio AS a INNER JOIN treino AS b WHERE b.id_exercicio=a.id_exercicio  AND b.id_grupo="+idGrupo;
        SQLiteDatabase myDb=db.getWritableDatabase();
        Cursor cursor=myDb.rawQuery(selectQuery, null);

        return cursor;


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

    public Cursor idAndCursor(String id,String idCursor){

        String selectQuery="SELECT  * FROM "+nomeTabela+" WHERE id_grupo="+idCursor;
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
        return database.delete(nomeTabela, "id_grupo=" + rowId, null) > 0;
    }

}
