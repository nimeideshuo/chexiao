package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.request.ReqDocAddTransferItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupiao on
 * 2018/7/30.
 * content
 */
public class TransferItemDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public TransferItemDAO() {
        super();
        this.helper = new DBOpenHelper();
    }


    public int getCount(long arg9) {
        this.db = this.helper.getWritableDatabase();
        try {
            cursor = this.db.rawQuery("select count(*) from kf_transferitem where transferdocid = ? ", new String[]{String.valueOf(arg9)});
            if (cursor.moveToNext()) {
                return cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return 0;
    }

    public List<ReqDocAddTransferItem> getTransferItemForUpload(long arg12, int arg14, int arg15) {
        this.db = this.helper.getReadableDatabase();
        ArrayList<ReqDocAddTransferItem> items = new ArrayList<>();
        try {
            Cursor cursor = this.db.rawQuery("select goodsid, unitid, num, batch, productiondate, warehouseid, remark from kf_transferitem where transferdocid = ? limit ? offset ? ", new String[]{String.valueOf(arg12), String.valueOf(arg14), String.valueOf(arg15)});
            while (cursor.moveToNext()) {
                ReqDocAddTransferItem v2 = new ReqDocAddTransferItem();
                v2.setGoodsid(cursor.getString(0));
                v2.setUnitid(cursor.getString(1));
                v2.setNum(cursor.getDouble(2));
                v2.setBatch(cursor.getString(3));
                v2.setProductiondate(cursor.getString(4));
                v2.setWarehouseId(cursor.getString(5));
                v2.setRemark(cursor.getString(6));
                items.add(v2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return items;
    }
}
