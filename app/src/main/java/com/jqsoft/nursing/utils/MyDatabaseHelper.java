package com.jqsoft.nursing.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by YLL on 2018-3-19.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "create table SRCLoginAreaBean (" +
            "id integer primary key autoincrement, " +
            "alias text, " +
            "areaCode text," +
            "areaPid text," +
            "areaLevel text, " +
            "areaName text)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        mContext = context;
    }

    /**
     * 数据库已经创建过了， 则不会执行到，如果不存在数据库则会执行
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK); // 执行这句才会创建表

        Toast.makeText(mContext, "create succeeded", Toast.LENGTH_SHORT).show();

    }

    /**
     * 创建数据库时不会执行，增大版本号升级时才会执行到
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 在这里面可以把旧的表 drop掉 从新创建新表，
        // 但如果数据比较重要更好的做法还是把旧表的数据迁移到新表上，比如升级qq聊天记录被删掉肯定招骂
        Toast.makeText(mContext, "onUpgrade oldVersion：" + oldVersion + " newVersion:" + newVersion, Toast.LENGTH_SHORT).show();
    }
}