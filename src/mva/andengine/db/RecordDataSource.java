package mva.andengine.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: vasiliy
 * Date: 25.05.13
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
public class RecordDataSource {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public RecordDataSource(Context context){
        dbHelper = new DbHelper(context);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public int[] getRecords(int count){
        if(db == null) return null;

        String[] projection = { DbHelper.RECORD };
        Cursor cur = db.query(DbHelper.TABLE_NAME,projection,null,null,null,null,DbHelper.RECORD + " DESC","0,10"); // "0,"+count

        int[] recordArray = new int[cur.getCount()];
        int i = 0;
        cur.moveToFirst();
        while(!cur.isAfterLast()){
            recordArray[i++] = cur.getInt(0);
            cur.moveToNext();
        }
        return recordArray;
    }
    public void addRecord(int points){
        if(db == null || points < 1) return;

        ContentValues values = new ContentValues();
        values.put(DbHelper.RECORD,points);

        db.insert(DbHelper.TABLE_NAME,null,values);
    }

    public void close(){
        db.close();
    }
}
