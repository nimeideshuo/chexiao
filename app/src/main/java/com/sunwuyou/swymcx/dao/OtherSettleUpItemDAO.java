package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.OtherSettleUpItem;
import com.sunwuyou.swymcx.request.ReqDocAddOtherSettleUpItem;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on
 * 2018/8/1.
 * content
 */
public class OtherSettleUpItemDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public OtherSettleUpItemDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public List<OtherSettleUpItem> getItems(long othersettleupid) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select serialid,othersettleupid,accountid,accountname,amount from cw_othersettleupitem where othersettleupid=?", new String[]{String.valueOf(othersettleupid)});
            ArrayList<OtherSettleUpItem> upItemArrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                OtherSettleUpItem upItem = new OtherSettleUpItem();
                upItem.setSerialid(cursor.getLong(0));
                upItem.setOthersettleupid(cursor.getLong(1));
                upItem.setAccountid(cursor.getString(2));
                upItem.setAccountname(cursor.getString(3));
                upItem.setAmount(cursor.getDouble(4));
                upItemArrayList.add(upItem);
            }
            return upItemArrayList;
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

    public double getOtherSettleupAmount(long othersettleupid) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select total(amount) from cw_othersettleupitem where othersettleupid=?", new String[]{String.valueOf(othersettleupid)});
            if (cursor.moveToNext()) {
                return Utils.getSubtotal(cursor.getDouble(0));
            }
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
        return 0;
    }

    public boolean delete(long arg8) {
        boolean v0 = true;
        this.db = this.helper.getWritableDatabase();
        if (this.db.delete("cw_othersettleupitem", "serialid=?", new String[]{String.valueOf(arg8)}) != 1) {
            v0 = false;
        }

        if (this.db != null) {
            this.db.close();
        }

        return v0;
    }

    public boolean update(long arg9, OtherSettleUpItem arg11) {
        boolean v0 = true;
        this.db = this.helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("othersettleupid", arg11.getOthersettleupid());
        contentValues.put("accountid", arg11.getAccountid());
        contentValues.put("accountname", arg11.getAccountname());
        contentValues.put("amount", arg11.getAmount());
        if (this.db.update("cw_othersettleupitem", contentValues, "serialid=?", new String[]{String.valueOf(arg9)}) != 1) {
            v0 = false;
        }
        if (this.db != null) {
            this.db.close();
        }
        return v0;
    }

    public long save(OtherSettleUpItem arg7) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v2 = new ContentValues();
        v2.put("othersettleupid", arg7.getOthersettleupid());
        v2.put("accountid", arg7.getAccountid());
        v2.put("accountname", arg7.getAccountname());
        v2.put("amount", arg7.getAmount());
        long v0 = this.db.insert("cw_othersettleupitem", null, v2);
        if (this.db != null) {
            this.db.close();
        }
        return v0;
    }

    public List<ReqDocAddOtherSettleUpItem> getOtherSettleUpForUpload(long arg10) {
        this.db = this.helper.getWritableDatabase();
        try {
            cursor = this.db.rawQuery("select accountid, amount from cw_othersettleupitem where othersettleupid = ? ", new String[]{String.valueOf(arg10)});
            ArrayList<ReqDocAddOtherSettleUpItem> vsettles = new ArrayList<>();
            while (cursor.moveToNext()) {
                ReqDocAddOtherSettleUpItem upItem = new ReqDocAddOtherSettleUpItem();
                upItem.setAccountId(cursor.getString(0));
                upItem.setAmount(cursor.getDouble(1));
                vsettles.add(upItem);
            }
            return vsettles;
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
