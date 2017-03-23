package io.palaima.debugdrawer.app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Kale
 * @date 2015/12/17
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";

    private static final String TABLE_NAME = "test_table";

    static final String CATEGORY = "category";

    static final String LINE = "line";

    static final String EXTRA = "extra";

    private static final String CREATE_NEW_TABLE = "create table " + TABLE_NAME + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            CATEGORY + " TEXT," +
            LINE + " INTEGER," +
            EXTRA + " TEXT);";

    private SQLiteDatabase mDb;

    DataBaseHelper(Context context, String dbName) {
        super(context, dbName, null, 1);
        try {
            mDb = getReadableDatabase();
        } catch (Exception e) {
            mDb = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createNewTable() {
        if (!executeSql(CREATE_NEW_TABLE)) {
            Log.i(TAG, "建表成功");
        } else {
            Log.e(TAG, "建表失败");
        }
    }

    /**
     * 新增数据
     */
    public void insertData(String category, int line, String extra) {
        String sb = "'" + category + "'," + line + "," + "'" + extra + "'";
        String sql = "insert into " + TABLE_NAME + " (category,line,extra) " + "values(" + sb + ")";
        if (!executeSql(sql)) {
            Log.e(TAG, "插入失败");
        }
    }

    /**
     * 通过表名来删除一个表
     */
    public void deleteAllLog() {
        String sql = "delete from " + TABLE_NAME;
        if (executeSql(sql)) {
            Log.i(TAG, "删除成功");
        } else {
            Log.e(TAG, "删除失败");
        }
    }

    public Cursor queryAll() {
        return queryByColumn(new String[]{CATEGORY, LINE, EXTRA});
    }

    private Cursor queryByColumn(String[] column) {
        StringBuilder sb = new StringBuilder();
        for (String c : column) {
            sb.append(",").append(c);
        }
        String sql = "select _id" + sb.toString() + " from " + TABLE_NAME;
        return executeSql(sql, null);
    }

    /**
     * 查询数据，？是占位符，用于和string数组搭配使用
     */
    Cursor queryDataById(String id) {
        String sql = "select * from " + TABLE_NAME + " where _id=?";
        return executeSql(sql, new String[]{id});
    }

    /**
     * 建立表
     * SQLite内部只支持NULL,INTEGER,REAL(浮点),TEXT(文本),BLOB(大二进制)5种数据类型
     * 建立表成功了，返回true
     */
    private boolean executeSql(String sql) {
        try {
            if (mDb != null) {
                mDb.execSQL(sql);
                return true;
            }
            return false;
        } catch (SQLiteException e) {
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Unknown error");
            return false;
        }
    }

    private Cursor executeSql(String sql, String[] args) {
        try {
            if (mDb != null) {
                return mDb.rawQuery(sql, args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接
     */
    public void closeDB() {
        if (mDb != null) {
            close();
        }
    }

}
