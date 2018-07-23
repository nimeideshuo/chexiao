package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.UpdateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin
 * 2018/7/17.
 * content
 */

public class GoodsDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;

    public GoodsDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public int getCount() {
        this.db = this.helper.getReadableDatabase();
        Cursor cursor = this.db.rawQuery("select count(*) from sz_goods", null);
        try {
            if (cursor.moveToNext()) {
                return cursor.getInt(0);
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return 0;
    }

    public int getTruckGoodsCount() {
        this.db = this.helper.getWritableDatabase();
        Cursor cursor = this.db.rawQuery("select count(*) from sz_goods where initnumber is not null and initnumber != 0", null);
        try {
            if (cursor.moveToNext()) {
                return cursor.getInt(0);
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return 0;
    }

    /**
     * 查询 所有商品 薄
     *
     * @return
     */
    public List<GoodsThin> queryGoodsThin(String goodsid) {
        this.db = this.helper.getReadableDatabase();
        boolean boo = TextUtils.isEmptyS(goodsid);
        String sql = boo ? "select  id,name,pinyin,barcode,specification,isusebatch,stocknumber,bigstocknumber,initnumber,biginitnumber from sz_goods where  initnumber != 0 and initnumber is not null" : "select id,name,pinyin,barcode,specification,isusebatch,stocknumber,bigstocknumber,initnumber,biginitnumber  from sz_goods where id=" + goodsid;
        Cursor v0 = this.db.rawQuery(sql, null);
        ArrayList<GoodsThin> arrayList = new ArrayList<GoodsThin>();
        try {
            while (v0.moveToNext()) {
                GoodsThin v2 = new GoodsThin();
                v2.setId(v0.getString(0));
                v2.setName(v0.getString(1));
                v2.setPinyin(v0.getString(2));
                v2.setBarcode(v0.getString(3));
                v2.setSpecification(v0.getString(4));
                v2.setIsusebatch(v0.getInt(5) == 1);
                v2.setStocknumber(v0.getDouble(6));
                v2.setBigstocknumber(v0.getString(7));
                v2.setInitnumber(v0.getDouble(8));
                v2.setBiginitnumber(v0.getString(9));
                arrayList.add(v2);
            }
            return arrayList;
        } catch (Exception localException) {
            localException.printStackTrace();
        } finally {
            if (v0 != null)
                v0.close();
            if (this.db != null)
                this.db.close();
        }
        return new ArrayList<>();
    }

    public void clearStock() {
        ArrayList<HashMap<String, String>> localArrayList = new ArrayList<HashMap<String, String>>();
        HashMap map = new HashMap();
        map.put("sql", "update sz_goods set stocknumber=0,bigstocknumber=null,initnumber=0,biginitnumber=null");
        localArrayList.add(map);
        map = new HashMap();
        map.put("sql", "delete from kf_goodsbatch");
        localArrayList.add(map);
        new UpdateUtils().saveToLocalDB(localArrayList);
    }
}
