package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupiao on
 * 2018/8/1.
 * content
 */
public class AccountDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public AccountDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public List<Account> getAccounts(String arg9) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select aid,aname,parentaccountid from sz_account where parentaccountid=? and isavailable = \'1\'", new String[]{arg9});
            ArrayList<Account> v3 = new ArrayList<>();
            while (cursor.moveToNext()) {
                Account v0 = new Account();
                v0.setAid(cursor.getString(0));
                v0.setAname(cursor.getString(1));
                v0.setParentaccountid(cursor.getString(2));
                v3.add(v0);
            }
            return v3;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (this.db != null) {
                this.db.close();
            }
        }
        return new ArrayList<>();
    }


}
