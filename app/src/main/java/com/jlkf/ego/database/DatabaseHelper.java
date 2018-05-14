package com.jlkf.ego.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Gravity;
import android.widget.Toast;

import com.jlkf.ego.utils.AppLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 数据库工具类
 * DatabaseHelper 助手类
 * @author  邓超桂
 * @date 创建时间：2017年7月10日 下午2:08:03
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private Context _context;
    public static final int VERSION = 1;

    public static final String DB_NAME = "ego.db";

    public static final String TABLE_GOODS_HISTORY="history_goods";

    public static final String COL_GOODS_NAME="name";
    public static final String COL_TIME="time";

    private SQLiteDatabase mDefaultWritableDatabase = null;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        _context = context;
    }
    public DatabaseHelper(Context context, String name) {
        this(context, name, null, VERSION);
        db = this.getReadableDatabase();
    }
    @Override
    public SQLiteDatabase getWritableDatabase() {
        final SQLiteDatabase db;
        if(mDefaultWritableDatabase != null){
            db = mDefaultWritableDatabase;
        } else {
            db = super.getWritableDatabase();
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.mDefaultWritableDatabase = db;
        //创建搜索商品历史表
        String sql1 = "CREATE TABLE if not exists "+TABLE_GOODS_HISTORY+"(_id integer primary key AUTOINCREMENT,"+COL_GOODS_NAME+" varchar(100),"+COL_TIME+" varchar(100))";

        db.execSQL(sql1);

        AppLog.Loge("dcg", "创建搜索商品历史表");
    }
    /**
     * 插入一条搜索商品历史记录
     */
    public long insertGoodsHistoryData(String goodsName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String time = Calendar.getInstance().getTimeInMillis()+"";
        //重复的不在插入
        Cursor cs=queryAllGoodsHistory();
        List<String> mList=new ArrayList<String>();
        //等于result.查询sqlite中的字段信息
        while(cs.moveToNext()){
            int id=cs.getInt(0);
            String domainName=cs.getString(1);
            mList.add(domainName);
        }
        for (int i = 0; i < mList.size(); i++) {
            if(mList.get(i).equals(goodsName)){
                deleteGoodsHistory(goodsName);//删除旧的，使用最新的
                break;
            }
        }
        long row=0;
        ContentValues cv = new ContentValues();
        cv.put(COL_GOODS_NAME, goodsName);
        cv.put(COL_TIME, time);
        row = db.insert(TABLE_GOODS_HISTORY, null, cv);
        return row;
    }
    /**
     * 查询所有 搜索商品历史记录
     */
    public Cursor queryAllGoodsHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from "+TABLE_GOODS_HISTORY, new String[]{});
        return cursor;
    }
    /**
     * 删除记录 搜索商品历史记录
     */
    public void deleteGoodsHistory(String goodsName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where =COL_GOODS_NAME+" = ?";
        String[] whereValue = { goodsName };
        db.delete(TABLE_GOODS_HISTORY, where, whereValue);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        this.mDefaultWritableDatabase = db;
        System.out.println("db upgrade");
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.mDefaultWritableDatabase = db;
    }
    // 数据库的查询函数
    public Cursor rawQuery(String sql) {
        return db.rawQuery(sql, null);
    }
    public boolean execSQL(String sql) {
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            Toast toast = Toast.makeText(_context,"android.database.sqlite.SQLiteException",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        return true;
    }
    public boolean execSQL(String sql, boolean Throw) {
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            if (Throw)
                throw e;
            return false;
        }
        return true;
    }
    // 封装系统的执行sql语句的函数
    public boolean execSQL(String sql, Object[] object) {
        try {
            db.execSQL(sql, object);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(_context, e.getMessage(),
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        return true;
    }
    public void beginTransaction() {
        db.beginTransaction();
    }

    public void setTransactionSuccessful() {
        db.setTransactionSuccessful();
    }

    public void endTransaction() {
        db.endTransaction();
    }

    public void close() {
        db.close();
    }
}
