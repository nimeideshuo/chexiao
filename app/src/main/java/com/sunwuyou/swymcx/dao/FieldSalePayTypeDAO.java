package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.FieldSalePayType;
import com.sunwuyou.swymcx.request.ReqDocUpdatePayType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/7/24.
 * content
 */

public class FieldSalePayTypeDAO {

    private SQLiteDatabase db;
    private DBOpenHelper helper = new DBOpenHelper();
    private Cursor cursor;


    public long save(FieldSalePayType arg6) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v1 = new ContentValues();
        v1.put("fieldsaleid", arg6.getFieldsaleid());
        v1.put("paytypeid", arg6.getPaytypeid());
        v1.put("paytypename", arg6.getPaytypename());
        v1.put("amount", arg6.getAmount());
        try {
            return db.insert("kf_fieldsalepaytype", null, v1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

    public boolean isHasPay(long arg9) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select 1 from kf_fieldsalepaytype where fieldsaleid=? and amount!=0";
        try {
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg9)});
            if (cursor.moveToNext()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }


    public double getPayTypeAmount(long arg10) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select sum(amount) from kf_fieldsalepaytype where fieldsaleid=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg10)});
            if (cursor.moveToNext()) {
                return cursor.getDouble(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

    public List<FieldSalePayType> queryPayTypes(long fieldsaleid) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select serialid,fieldsaleid,paytypeid,paytypename,amount from kf_fieldsalepaytype where fieldsaleid=?";
        ArrayList<FieldSalePayType> payTypes = new ArrayList<FieldSalePayType>();
        try {
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(fieldsaleid)});
            while (cursor.moveToNext()) {
                FieldSalePayType payType = new FieldSalePayType();
                payType.setSerialid(cursor.getLong(0));
                payType.setFieldsaleid(cursor.getLong(1));
                payType.setPaytypeid(cursor.getString(2));
                payType.setPaytypename(cursor.getString(3));
                payType.setAmount(cursor.getDouble(4));
                payTypes.add(payType);
            }
            return payTypes;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return new ArrayList<>();
    }

    public boolean update(FieldSalePayType arg13) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v1 = new ContentValues();
        v1.put("fieldsaleid", arg13.getFieldsaleid());
        v1.put("paytypeid", arg13.getPaytypeid());
        v1.put("paytypename", arg13.getPaytypename());
        v1.put("amount", arg13.getAmount());
        return this.db.update("kf_fieldsalepaytype", v1, "serialid=?", new String[]{String.valueOf(arg13.getSerialid())}) == 1;
    }

    public List<ReqDocUpdatePayType> getPayTypeForUpload(long arg10) {
        this.db = this.helper.getWritableDatabase();
        try {
            cursor = this.db.rawQuery("select paytypeid, amount from kf_fieldsalepaytype where fieldsaleid = ? and amount != 0 order by paytypeid asc", new String[]{String.valueOf(arg10)});
            ArrayList<ReqDocUpdatePayType> typeArrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                ReqDocUpdatePayType v2 = new ReqDocUpdatePayType();
                v2.setPayTypeId(cursor.getString(0));
                v2.setAmount(cursor.getDouble(1));
                typeArrayList.add(v2);
            }
            return typeArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return new ArrayList();
    }

}
