package com.sunwuyou.swymcx.app;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.InputStream;

/**
 * Created by admin
 * 2018/7/16.
 * content
 */

public class SystemDBHelper extends SQLiteOpenHelper {
    public static final String T_MESSAGE = "message";
    public static final String T_USER = "user";
    private static final String DATABASE_NAME = "system.db";
    private static SystemDBHelper helper;

    public SystemDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        CreateTable();
    }

    public static SystemDBHelper conn() {
        if (helper == null) {
            helper = new SystemDBHelper(MyApplication.getInstance(), "system.db", null, 1);
        }
        return helper;
    }

    public void CreateTable() {
        try {
            InputStream v2 = MyApplication.getInstance().getAssets().open("system.sql");
            StringBuilder v5 = new StringBuilder();
            byte[] v0 = new byte[1024];
            while (true) {
                int v4 = v2.read(v0);
                if (v4 <= 0) {
                    break;
                }

                v5.append(new String(v0, 0, v4));
            }

            String[] v7 = v5.toString().split(";");
            int v3;
            for (v3 = 0; v3 < v7.length; ++v3) {
                this.getWritableDatabase().execSQL(v7[v3]);
            }

        } catch (Exception v1) {
            v1.printStackTrace();
        }
    }

    public boolean clearAllUser() {
        try {
            getWritableDatabase().execSQL("delete from user");
            return true;
        } catch (SQLException localSQLException) {
            localSQLException.printStackTrace();
        }
        return false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean deleteMessage(String paramString) {
        return getReadableDatabase().delete("message", "serialid=?", new String[]{paramString}) == 1;
    }

    public boolean deleteUser(String paramString) {
        return getReadableDatabase().delete("user", "id=?", new String[]{paramString}) == 1;
    }

    public boolean isExistUser(String paramString) {
        return getReadableDatabase().query("user", null, "id=?", new String[]{paramString}, null, null, null).moveToFirst();
    }


}
