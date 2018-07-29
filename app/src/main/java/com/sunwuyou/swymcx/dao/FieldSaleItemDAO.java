package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.loc.d;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.model.FieldSaleItem;
import com.sunwuyou.swymcx.model.FieldSaleItemSource;
import com.sunwuyou.swymcx.model.FieldSaleItemThin;
import com.sunwuyou.swymcx.model.FieldSaleItemTotal;
import com.sunwuyou.swymcx.model.FieldSaleSum;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/7/25.
 * content
 */

public class FieldSaleItemDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public FieldSaleItemDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public double getDocSalePrice(long arg10) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select sum(salenum * saleprice) from kf_fieldsaleitem where fieldsaleid=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg10)});
            if (cursor.moveToNext()) {
                return Utils.getRecvable(cursor.getDouble(0));
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

    public double getDocCancelPrice(long arg10) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select sum(num * price) from kf_fieldsaleitembatch where fieldsaleid=? and isout=\'0\'";
        try {
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg10)});
            if (cursor.moveToNext()) {
                return Utils.getRecvable(cursor.getDouble(0));
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

    public List<FieldSaleItemSource> getFieldSaleItems(long arg11) {
        this.db = this.helper.getReadableDatabase();
        try {
            String sql = "select item.serialid, item.fieldsaleid,item.goodsid, g.name goodsname, g.barcode, g.specification, g.isusebatch, item.saleunitid, us.name saleunitname, item.salenum, item.saleprice,item.giveunitid, ug.name giveunitname, item.givenum,item.cancelbasenum,item.giftgoodsid,item.giftgoodsname,item.giftunitid, item.giftunitname,item.giftnum,item.promotiontype,item.ispromotion, item.warehouseid,item.saleremark,item.giftremark,item.cancelremark,item.giveremark,item.isexhibition from kf_fieldsaleitem as item left outer join sz_goods as g on item.goodsid = g.id left outer join sz_unit as us on item.saleunitid = us.id left outer join sz_unit as ug on item.giveunitid = ug.id where item.fieldsaleid = ? ";
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg11)});
            ArrayList<FieldSaleItemSource> v3 = new ArrayList<FieldSaleItemSource>();
            while (cursor.moveToNext()) {
                FieldSaleItemSource itemSource = new FieldSaleItemSource();
                itemSource.setSerialid(cursor.getLong(0));
                itemSource.setFieldsaleid(cursor.getLong(1));
                itemSource.setGoodsid(cursor.getString(2));
                itemSource.setGoodsname(cursor.getString(3));
                itemSource.setBarcode(cursor.getString(4));
                itemSource.setSpecification(cursor.getString(5));
                itemSource.setIsusebatch(cursor.getInt(6) == 1);
                itemSource.setSaleunitid(cursor.getString(7));
                itemSource.setSaleunitname(cursor.getString(8));
                itemSource.setSalenum(cursor.getDouble(9));
                itemSource.setSaleprice(cursor.getDouble(10));
                itemSource.setGiveunitid(cursor.getString(11));
                itemSource.setGiveunitname(cursor.getString(12));
                itemSource.setGivenum(cursor.getDouble(13));
                itemSource.setCancelbasenum(cursor.getDouble(14));
                itemSource.setGiftgoodsid(cursor.getString(15));
                itemSource.setGiftgoodsname(cursor.getString(16));
                itemSource.setGiftunitid(cursor.getString(17));
                itemSource.setGiftunitname(cursor.getString(18));
                itemSource.setGiftnum(cursor.getDouble(19));
                itemSource.setPromotiontype(cursor.getInt(20));
                itemSource.setIspromotion(cursor.getInt(21) == 1);
                itemSource.setWarehouseid(cursor.getString(22));
                itemSource.setSaleremark(cursor.getString(23));
                itemSource.setGiftremark(cursor.getString(24));
                itemSource.setCancelremark(cursor.getString(25));
                itemSource.setGiveremark(cursor.getString(26));
                itemSource.setIsexhibition(cursor.getInt(27) == 1);
                v3.add(itemSource);
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
        return new ArrayList<>();
    }

    public int getCount(long arg9) {
        this.db = this.helper.getWritableDatabase();
        String sql = "select count(*) from kf_fieldsaleitem where fieldsaleid = ? ";
        try {
            Cursor cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg9)});
            if (cursor.moveToNext()) {
                return cursor.getInt(0);
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

    public FieldSaleItemSource getFieldSaleItem(long arg11) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select item.serialid, item.fieldsaleid,item.goodsid, g.name as goodsname, g.barcode, g.specification,g.isusebatch,item.saleunitid, us.name as saleunitname, item.salenum, item.saleprice,item.giveunitid, ug.name as giveunitname, item.givenum,item.cancelbasenum,item.giftgoodsid,item.giftgoodsname,item.giftunitid, item.giftunitname,item.giftnum,item.promotiontype,item.ispromotion, item.warehouseid,item.saleremark,item.giftremark,item.cancelremark,item.giveremark,item.isexhibition from kf_fieldsaleitem as item left outer join sz_goods as g on item.goodsid = g.id left outer join sz_unit as us on item.saleunitid = us.id left outer join sz_unit as ug on item.giveunitid = ug.id where item.serialid = ? ";
        try {
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg11)});
            if (cursor.moveToNext()) {
                FieldSaleItemSource itemSource = new FieldSaleItemSource();
                itemSource.setSerialid(cursor.getLong(0));
                itemSource.setFieldsaleid(cursor.getLong(1));
                itemSource.setGoodsid(cursor.getString(2));
                itemSource.setGoodsname(cursor.getString(3));
                itemSource.setBarcode(cursor.getString(4));
                itemSource.setSpecification(cursor.getString(5));
                itemSource.setIsusebatch(cursor.getInt(6) == 1);
                itemSource.setSaleunitid(cursor.getString(7));
                itemSource.setSaleunitname(cursor.getString(8));
                itemSource.setSalenum(cursor.getDouble(9));
                itemSource.setSaleprice(cursor.getDouble(10));
                itemSource.setGiveunitid(cursor.getString(11));
                itemSource.setGiveunitname(cursor.getString(12));
                itemSource.setGivenum(cursor.getDouble(13));
                itemSource.setCancelbasenum(cursor.getDouble(14));
                itemSource.setGiftgoodsid(cursor.getString(15));
                itemSource.setGiftgoodsname(cursor.getString(16));
                itemSource.setGiftunitid(cursor.getString(17));
                itemSource.setGiftunitname(cursor.getString(18));
                itemSource.setGiftnum(cursor.getDouble(19));
                itemSource.setPromotiontype(cursor.getInt(20));
                itemSource.setIspromotion(cursor.getInt(21) == 1);
                itemSource.setWarehouseid(cursor.getString(22));
                itemSource.setSaleremark(cursor.getString(23));
                itemSource.setGiftremark(cursor.getString(24));
                itemSource.setCancelremark(cursor.getString(25));
                itemSource.setGiveremark(cursor.getString(26));
                itemSource.setIsexhibition(cursor.getInt(27) == 1);
                return itemSource;
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
        return null;
    }

    public boolean delete(long arg8) {
        new FieldSaleItemBatchDAO().deleteAll(arg8);
        this.db = this.helper.getWritableDatabase();
        try {
            String v2 = "delete from kf_fieldsaleitem where serialid=?";
            this.db.execSQL(v2, new String[]{String.valueOf(arg8)});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    public long saveFieldSaleItem(FieldSaleItem arg7) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v2 = new ContentValues();
        v2.put("fieldsaleid", Long.valueOf(arg7.getFieldsaleid()));
        v2.put("goodsid", arg7.getGoodsid());
        v2.put("warehouseid", arg7.getWarehouseid());
        v2.put("saleunitid", arg7.getSaleunitid());
        v2.put("salenum", Double.valueOf(arg7.getSalenum()));
        v2.put("saleprice", Double.valueOf(arg7.getSaleprice()));
        v2.put("cancelbasenum", Double.valueOf(arg7.getCancelbasenum()));
        v2.put("giveunitid", arg7.getGiveunitid());
        v2.put("givenum", Double.valueOf(arg7.getGivenum()));
        String v4 = "ispromotion";
        int v3 = arg7.isIspromotion() ? 1 : 0;
        v2.put(v4, Integer.valueOf(v3));
        v2.put("promotiontype", Integer.valueOf(arg7.getPromotiontype()));
        v2.put("giftgoodsid", arg7.getGiftgoodsid());
        v2.put("giftgoodsname", arg7.getGiftgoodsname());
        v2.put("giftunitid", arg7.getGiftunitid());
        v2.put("giftunitname", arg7.getGiftunitname());
        v2.put("giftnum", Double.valueOf(arg7.getGiftnum()));
        v4 = "isexhibition";
        v2.put(v4, arg7.isIsexhibition() ? "1" : "0");
        v2.put("saleremark", arg7.getSaleremark());
        v2.put("giftremark", arg7.getGiftremark());
        v2.put("cancelremark", arg7.getCancelremark());
        v2.put("giveremark", arg7.getGiveremark());
        long v0 = this.db.insert("kf_fieldsaleitem", null, v2);
        if (this.db != null) {
            this.db.close();
        }
        return v0;
    }

    public List<FieldSaleItemThin> queryDocItems(long arg13) {
        this.db = this.helper.getReadableDatabase();
        String v5 = "select item.fieldsaleid, item.serialid, item.goodsid, g.name goodsname, g.barcode, g.specification, \t\t(item.salenum*item.saleprice - (select total(fb.num*fb.price) from kf_fieldsaleitembatch fb where fb.fieldsaleid=item.fieldsaleid and fb.goodsid=item.goodsid and isout=\'0\')) as subtotl, \t\titem.salenum*gus.ratio as salebasenum, item.givenum*gug.ratio as givebasenum, item.cancelbasenum, \t\titem.ispromotion, item.giftgoodsid, gg.name, item.giftnum*gugift.ratio from kf_fieldsaleitem as item left outer join sz_goods as g on item.goodsid = g.id \t\tleft outer join sz_goodsunit as gus on item.goodsid = gus.goodsid and item.saleunitid = gus.unitid \t\tleft outer join sz_goodsunit as gug on item.goodsid = gug.goodsid and item.giveunitid = gug.unitid \t\tleft outer join sz_goods as gg on item.giftgoodsid = gg.id \t\tleft outer join sz_goodsunit as gugift on item.giftgoodsid = gugift.goodsid and item.giftunitid = gugift.unitid where item.fieldsaleid = ? ";
        String v3 = new AccountPreference().getValue("item_order", "0");
        int v6 = 0;
        if (!TextUtils.isEmpty(v3)) {
            v6 = Integer.parseInt(v3);
        }
        switch (v6) {
            case 1: {
                v5 = String.valueOf(v5) + " order by item.goodsid ";
                break;
            }
            case 2: {
                v5 = String.valueOf(v5) + " order by g.name ";
                break;
            }
            case 3: {
                v5 = String.valueOf(v5) + " order by g.barcode ";
                break;
            }
            case 4: {
                v5 = String.valueOf(v5) + " order by g.goodsclassname ";
                break;
            }
        }
        cursor = this.db.rawQuery(v5, new String[]{String.valueOf(arg13)});
        ArrayList<FieldSaleItemThin> thinArrayList = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                FieldSaleItemThin itemThin = new FieldSaleItemThin();
                itemThin.setFieldsaleid(cursor.getLong(0));
                itemThin.setSerialid(cursor.getLong(1));
                itemThin.setGoodsid(cursor.getString(2));
                itemThin.setGoodsname(cursor.getString(3));
                itemThin.setBarcode(cursor.getString(4));
                itemThin.setSpecification(cursor.getString(5));
                itemThin.setSubtotal(cursor.getDouble(6));
                itemThin.setSalebasenum(cursor.getDouble(7));
                itemThin.setGivebasenum(cursor.getDouble(8));
                itemThin.setCancelbasenum(cursor.getDouble(9));
                itemThin.setIspromotion(cursor.getInt(10) == 1);
                itemThin.setGiftgoodsid(cursor.getString(11));
                itemThin.setGiftgoodsname(cursor.getString(12));
                itemThin.setGiftbasenum(cursor.getDouble(13));
                thinArrayList.add(itemThin);
            }
            return thinArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (this.cursor != null) {
                this.cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return thinArrayList;
    }

    public FieldSaleSum getDocSum(long arg13) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select total(salenum * saleprice) as saleamount, total(salenum) as salenum, total(givenum) as givenum, total(giftnum) promotionnum, ispromotion from kf_fieldsaleitem where fieldsaleid=? ";
        Cursor cursor1 = null;
        try {
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg13)});
            String sql1 = "select total(num * price) as cancelamount, total(num) as cancelnum from kf_fieldsaleitembatch where fieldsaleid=? and isout=\'0\' ";
            cursor1 = this.db.rawQuery(sql1, new String[]{String.valueOf(arg13)});
            if ((cursor.moveToNext()) && (cursor1.moveToNext())) {
                FieldSaleSum saleSum = new FieldSaleSum();
                saleSum.setSaleamount(cursor.getDouble(0));
                saleSum.setSalenum(cursor.getDouble(1));
                saleSum.setGivenum(cursor.getDouble(2));
                saleSum.setPromotionnum(cursor.getDouble(3));
                saleSum.setIsPromotion(cursor.getInt(4) == 1);
                saleSum.setCancelamount(cursor1.getDouble(0));
                saleSum.setCancelnum(cursor1.getDouble(1));
                return saleSum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (this.cursor != null) {
                this.cursor.close();
            }
            if (cursor1 != null) {
                cursor1.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    public boolean updateFieldSaleItem(FieldSaleItem arg11) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v1 = new ContentValues();
        v1.put("fieldsaleid", arg11.getFieldsaleid());
        v1.put("goodsid", arg11.getGoodsid());
        v1.put("warehouseid", arg11.getWarehouseid());
        v1.put("saleunitid", arg11.getSaleunitid());
        v1.put("salenum", arg11.getSalenum());
        v1.put("saleprice", arg11.getSaleprice());
        v1.put("giveunitid", arg11.getGiveunitid());
        v1.put("givenum", arg11.getGivenum());
        String v5 = "isexhibition";
        String v2 = arg11.isIsexhibition() ? "1" : "0";
        v1.put(v5, v2);
        v1.put("cancelbasenum", arg11.getCancelbasenum());
        v5 = "ispromotion";
        int v2_1 = arg11.isIspromotion() ? 1 : 0;
        v1.put(v5, v2_1);
        v1.put("promotiontype", arg11.getPromotiontype());
        v1.put("giftgoodsid", arg11.getGiftgoodsid());
        v1.put("giftgoodsname", arg11.getGiftgoodsname());
        v1.put("giftunitid", arg11.getGiftunitid());
        v1.put("giftunitname", arg11.getGiftunitname());
        v1.put("giftnum", arg11.getGiftnum());
        v1.put("saleremark", arg11.getSaleremark());
        v1.put("giftremark", arg11.getGiftremark());
        v1.put("cancelremark", arg11.getCancelremark());
        v1.put("giveremark", arg11.getGiveremark());
        boolean v0 = this.db.update("kf_fieldsaleitem", v1, "serialid=?", new String[]{String.valueOf(arg11.getSerialid())}) == 1;
        if (this.db != null) {
            this.db.close();
        }
        return v0;
    }

    public List queryFieldItemTotal(long arg19, boolean arg21) {
//        boolean v13_1;
//        FieldSaleItemTotal v8;
//        FieldSaleItemBatchDAO v5;
//        Cursor v2;
//        ArrayList v10 = new ArrayList();
//        this.db = this.helper.getReadableDatabase();
//        Cursor v3 = null;
//        String sql = "select item.goodsid,g.name as goodsname,g.barcode,g.isusebatch, (item.salenum * gs.ratio + item.givenum * gg.ratio) as outbasicnum, item.cancelbasenum as inbasicnum from kf_fieldsaleitem item left join sz_goods g on g.id = item.goodsid left join sz_goodsunit gs on gs.goodsid = item.goodsid and gs.unitid = item.saleunitid left join sz_goodsunit gg on gg.goodsid = item.goodsid and gg.unitid = item.giveunitid where item.fieldsaleid=? and (item.salenum > 0 or item.givenum > 0 or item.cancelbasenum > 0)";
//        v2 = this.db.rawQuery(sql, new String[]{String.valueOf(arg19)});
//        v5 = new FieldSaleItemBatchDAO();
//        while (v2.moveToNext()){
//
//
//
//
//
        return null;
    }

}
