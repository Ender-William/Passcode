package com.example.pwdmanager.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pwdmanager.uitls.DBUitls;

public class itemsDao<DbHelper> {
    private SQLiteDatabase myDb;
    private DBUitls dbUitls;

    public itemsDao(Context context) {
        dbUitls = new DBUitls(context,"passcode.db",null,1);
    }

    public Cursor allQuery(){
        myDb = dbUitls.getReadableDatabase();
        return myDb.rawQuery("select * from passcode",null);
    }

    public  int getRecordsNumber(){
        myDb = dbUitls.getReadableDatabase();
        Cursor cursor= myDb.rawQuery("select * from passcode",null);
        return cursor.getCount();
    }

    public void insertInfo(String appname,String usrname,String passwd){
        myDb = dbUitls.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("appname", appname);
        values.put("username", usrname);
        values.put("passwd", passwd);
        long rowid=myDb.insert(dbUitls.TB_NAME, null, values);
        if(rowid==-1)
            Log.i("myDbDemo", "数据插入失败！");
        else
            Log.i("myDbDemo", "数据插入成功！"+rowid);
    }

    public void deleteInfo(String Id){
        String where = "id=" + Id;
        int i = myDb.delete(dbUitls.TB_NAME, where, null);
        if (i > 0)
            Log.i("myDbDemo", "数据删除成功！");
        else
            Log.i("myDbDemo", "数据未删除！");
    }

    public void updateInfo(String id,String appname,String usrname,String passwd){
        ContentValues values = new ContentValues();
        values.put("appname", appname);
        values.put("username", usrname);
        values.put("passwd", passwd);
        String where="id="+id;
        int i=myDb.update(dbUitls.TB_NAME, values, where, null);
        if(i>0)
            Log.i("myDbDemo","数据更新成功！");
        else
            Log.i("myDbDemo","数据未更新！");
    }

}
