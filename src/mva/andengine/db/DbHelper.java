package mva.andengine.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: vasiliy
 * Date: 25.05.13
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class DbHelper extends SQLiteOpenHelper {
    private final static int VERSION = 5;
    public final static String DB_NAME = "tanks_db.db";
    public final static String TABLE_NAME = "tanks_table";
    public static final String _ID = "_id";
    public final static String RECORD = "record";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  "+TABLE_NAME+" (" + _ID + " integer primary key autoincrement, "+RECORD+" integer); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
    }
}
