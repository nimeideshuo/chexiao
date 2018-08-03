package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.SettleUpPayType;
import com.sunwuyou.swymcx.request.ReqDocUpdatePayType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on
 * 2018/7/31.
 * content
 */
public class SettleUpPayTypeDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public SettleUpPayTypeDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public long save(SettleUpPayType payType) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v2 = new ContentValues();
        v2.put("settleupid", payType.getSettleupid());
        v2.put("paytypeid", payType.getPaytypeid());
        v2.put("paytypename", payType.getPaytypename());
        v2.put("amount", payType.getAmount());
        v2.put("remark", payType.getRemark());
        long v0 = this.db.insert("cw_settleuppaytype", null, v2);
        if (this.db != null) {
            this.db.close();
        }
        return v0;
    }

    public double getSettleupPaysAmount(long arg11) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select total(amount) from cw_settleuppaytype where settleupid=?", new String[]{String.valueOf(arg11)});
            if (cursor.moveToNext()) {
                return cursor.getDouble(0);
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

    public List<SettleUpPayType> getPayTypes(long arg11) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select id,settleupid,paytypeid,paytypename,amount,remark from cw_settleuppaytype where settleupid=?", new String[]{new StringBuilder(String.valueOf(arg11)).toString()});
            ArrayList<SettleUpPayType> typeArrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                SettleUpPayType upPayType = new SettleUpPayType();
                upPayType.setId(cursor.getLong(0));
                upPayType.setSettleupid(cursor.getLong(1));
                upPayType.setPaytypeid(cursor.getString(2));
                upPayType.setPaytypename(cursor.getString(3));
                upPayType.setAmount(cursor.getDouble(4));
                upPayType.setRemark(cursor.getString(5));
                typeArrayList.add(upPayType);
            }
            return typeArrayList;
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

    public boolean update(Long arg9, SettleUpPayType arg10) {
        boolean v0 = true;
        this.db = this.helper.getWritableDatabase();
        ContentValues v1 = new ContentValues();
        v1.put("settleupid", Long.valueOf(arg10.getSettleupid()));
        v1.put("paytypeid", arg10.getPaytypeid());
        v1.put("paytypename", arg10.getPaytypename());
        v1.put("amount", Double.valueOf(arg10.getAmount()));
        v1.put("remark", arg10.getRemark());
        if (this.db.update("cw_settleuppaytype", v1, "id=?", new String[]{String.valueOf(arg9)}) != 1) {
            v0 = false;
        }
        if (this.db != null) {
            this.db.close();
        }
        return v0;
    }

    public List<ReqDocUpdatePayType> getPayTypeForUpload(long arg10) {
        this.db = this.helper.getWritableDatabase();
        try {
            cursor = this.db.rawQuery("select paytypeid, amount from cw_settleuppaytype where settleupid = ? ", new String[]{String.valueOf(arg10)});
            ArrayList<ReqDocUpdatePayType> typeArrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                ReqDocUpdatePayType payType = new ReqDocUpdatePayType();
                payType.setPayTypeId(cursor.getString(0));
                payType.setAmount(cursor.getDouble(1));
                typeArrayList.add(payType);
            }
            return typeArrayList;
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
