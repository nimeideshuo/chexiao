package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.model.Goods;
import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.UpdateUtils;
import com.sunwuyou.swymcx.utils.Utils;

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
    private Cursor cursor;

    public GoodsDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public int getCount() {
        this.db = this.helper.getReadableDatabase();
        cursor = this.db.rawQuery("select count(*) from sz_goods", null);
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
        cursor = this.db.rawQuery("select count(*) from sz_goods where initnumber is not null and initnumber != 0", null);
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
        cursor = this.db.rawQuery(sql, null);
        ArrayList<GoodsThin> arrayList = new ArrayList<GoodsThin>();
        try {
            while (cursor.moveToNext()) {
                GoodsThin goodsThin = new GoodsThin();
                goodsThin.setId(cursor.getString(0));
                goodsThin.setName(cursor.getString(1));
                goodsThin.setPinyin(cursor.getString(2));
                goodsThin.setBarcode(cursor.getString(3));
                goodsThin.setSpecification(cursor.getString(4));
                goodsThin.setIsusebatch(cursor.getInt(5) == 1);
                goodsThin.setStocknumber(cursor.getDouble(6));
                goodsThin.setBigstocknumber(cursor.getString(7));
                goodsThin.setInitnumber(cursor.getDouble(8));
                goodsThin.setBiginitnumber(cursor.getString(9));
                arrayList.add(goodsThin);
            }
            return arrayList;
        } catch (Exception localException) {
            localException.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
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

    public GoodsThin getGoodsThin(String arg10) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select id,name,pinyin,barcode,specification,isusebatch,stocknumber,bigstocknumber,initnumber,biginitnumber  from sz_goods where id=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{arg10});
            if (cursor.moveToNext()) {
                GoodsThin goodsThin = new GoodsThin();
                goodsThin.setId(cursor.getString(0));
                goodsThin.setName(cursor.getString(1));
                goodsThin.setPinyin(cursor.getString(2));
                goodsThin.setBarcode(cursor.getString(3));
                goodsThin.setSpecification(cursor.getString(4));
                goodsThin.setIsusebatch(cursor.getInt(5) == 1);
                goodsThin.setStocknumber(cursor.getDouble(6));
                goodsThin.setBigstocknumber(cursor.getString(7));
                goodsThin.setInitnumber(cursor.getDouble(8));
                goodsThin.setBiginitnumber(cursor.getString(9));
                return goodsThin;
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

        return null;
    }

    // 从 SQL 中 取 数据 匹配
    public ArrayList<GoodsThin> queryGoods(String text, long fieldsaleid) {
        AccountPreference v0 = new AccountPreference();
        String v8 = "select g.id,g.name,g.pinyin,g.barcode,g.specification,g.isusebatch,stocknumber,bigstocknumber,initnumber,biginitnumber from sz_goods g where g.isavailable = \'1\' and g.id not in (select f.goodsid from kf_fieldsaleitem f where f.fieldsaleid=?) and (";
        if ("1".equals(v0.getValue("goods_result_select", "0"))) {
            v8 = String.valueOf(v8) + " g.stocknumber>0.0 and (";
        }
        String[] v5 = Utils.GOODS_CHECK_SELECT.split(",");
        int v4;
        for (v4 = 0; v4 < v5.length; ++v4) {
            v8 = String.valueOf(v8) + "g." + v5[v4] + " like ? ";
            if (v4 != v5.length - 1) {
                v8 = String.valueOf(v8) + " or ";
            }
        }
        if ("1".equals(v0.getValue("goods_result_select", "0"))) {
            v8 = String.valueOf(v8) + ")";
        }
        v8 = String.valueOf(v8) + ") order by g.id asc";
        String[] v6 = new String[v5.length + 1];
        v6[0] = String.valueOf(fieldsaleid);
        for (v4 = 1; v4 < v6.length; ++v4) {
            v6[v4] = "%" + text + "%";
        }
        ArrayList<GoodsThin> v7 = new ArrayList<>();
        try {
            this.db = this.helper.getReadableDatabase();
            Cursor v1 = this.db.rawQuery(v8, v6);
            while (v1.moveToNext()) {
                GoodsThin v3 = new GoodsThin();
                v3.setId(v1.getString(0));
                v3.setName(v1.getString(1));
                v3.setPinyin(v1.getString(2));
                v3.setBarcode(v1.getString(3));
                v3.setSpecification(v1.getString(4));
                v3.setIsusebatch(v1.getInt(5) == 1);
                v3.setStocknumber(v1.getDouble(6));
                v3.setBigstocknumber(v1.getString(7));
                v3.setInitnumber(v1.getDouble(8));
                v3.setBiginitnumber(v1.getString(9));
                v7.add(v3);
            }
            return v7;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (this.db != null)
                this.db.close();
        }
        return v7;
    }

    public String queryGoodsBigStockNumber(String arg8) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select bigstocknumber from sz_goods where id=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{arg8});
            if (cursor.moveToNext()) {
                return TextUtils.isEmptyS(cursor.getString(0)) ? "无库存" : cursor.getString(0);
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

        return "";
    }


    public List<GoodsThin> queryGoodsThin(boolean arg11) {
        this.db = this.helper.getReadableDatabase();
        try {
            String v4 = arg11 ? "select id,name,pinyin,barcode,specification,isusebatch,stocknumber,bigstocknumber,initnumber,biginitnumber from sz_goods where isavailable = \'1\' and (initnumber is not null and cast(initnumber as decimal(10, 2)) <> 0) or (stocknumber is not null and cast(stocknumber as decimal(10, 2)) <> 0) order by lower(pinyin)" : "select id,name,pinyin,barcode,specification,isusebatch,stocknumber,bigstocknumber,initnumber,biginitnumber from sz_goods where isavailable = \'1\' order by lower(pinyin)";
            Cursor v0 = this.db.rawQuery(v4, null);
            ArrayList<GoodsThin> v3 = new ArrayList<>();
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
                v3.add(v2);
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

    public int queryGoodsIndexByPinyin(String arg8) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select count(*) from sz_goods where lower(substr(pinyin, 1, 1)) < lower(substr(?, 1, 1))";
        Cursor v1 = this.db.rawQuery(sql, new String[]{arg8});
        if (v1.moveToNext()) {
            return v1.getInt(0);
        }
        v1.close();
        db.close();
        return 0;
    }

    public Goods getGoods(String arg10) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select id,name,pinyin,barcode,salecue,specification,model,isusebatch,goodsclassid,goodsclassname,stocknumber,bigstocknumber,initnumber,biginitnumber,getstocktime from sz_goods where id=?", new String[]{arg10});
            if (cursor.moveToNext()) {
                Goods v3 = new Goods();
                v3.setId(cursor.getString(0));
                v3.setName(cursor.getString(1));
                v3.setPinyin(cursor.getString(2));
                v3.setBarcode(cursor.getString(3));
                v3.setSalecue(cursor.getString(4));
                v3.setSpecification(cursor.getString(5));
                v3.setModel(cursor.getString(6));
                v3.setIsusebatch(cursor.getInt(7) == 1);
                v3.setGoodsclassid(cursor.getString(8));
                v3.setGoodsclassname(cursor.getString(9));
                v3.setStocknumber(cursor.getString(10));
                v3.setBigstocknumber(cursor.getString(11));
                v3.setInitnumber(cursor.getString(12));
                v3.setBiginitnumber(cursor.getString(13));
                v3.setGetstocktime(cursor.getString(14));
                return v3;
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
        return null;
    }


}
