package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.TransferItem;
import com.sunwuyou.swymcx.model.TransferItemSource;
import com.sunwuyou.swymcx.request.ReqDocAddTransferItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on
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
            String sql = "select count(*) from kf_transferitem where transferdocid = ? ";
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg9)});
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
        try {
            ArrayList<ReqDocAddTransferItem> items = new ArrayList<>();
            String sql = "select goodsid, unitid, num, batch, productiondate, warehouseid, remark from kf_transferitem where transferdocid = ? limit ? offset ? ";
            Cursor cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg12), String.valueOf(arg14), String.valueOf(arg15)});
            while (cursor.moveToNext()) {
                ReqDocAddTransferItem item = new ReqDocAddTransferItem();
                item.setGoodsid(cursor.getString(0));
                item.setUnitid(cursor.getString(1));
                item.setNum(cursor.getDouble(2));
                item.setBatch(cursor.getString(3));
                item.setProductiondate(cursor.getString(4));
                item.setWarehouseId(cursor.getString(5));
                item.setRemark(cursor.getString(6));
                items.add(item);
            }
            return items;
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

    public boolean delete(long serialid) {
        this.db = this.helper.getWritableDatabase();
        try {
            String v2 = "delete from kf_transferitem where serialid=?";
            this.db.execSQL(v2, new String[]{String.valueOf(serialid)});
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public long saveTransferItem(TransferItem item) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v2 = new ContentValues();
        v2.put("transferdocid", item.getTransferdocid());
        v2.put("goodsid", item.getGoodsid());
        v2.put("unitid", item.getUnitid());
        v2.put("num", item.getNum());
        v2.put("batch", item.getBatch());
        v2.put("productiondate", item.getProductiondate());
        v2.put("warehouseid", item.getWarehouseid());
        v2.put("remark", item.getRemark());
        long v0 = this.db.insert("kf_transferitem", null, v2);
        if (this.db != null) {
            this.db.close();
        }
        return v0;
    }

    public List<TransferItemSource> getTransferItems(long arg11) {
        this.db = this.helper.getReadableDatabase();
        try {
            String sql = "select item.serialid, item.transferdocid, item.goodsid, g.name goodsname, g.barcode, g.specification, g.isusebatch, item.unitid, u.name unitname, item.num, item.batch, item.productiondate, item.warehouseid, wh.name warehousename, item.remark from kf_transferitem as item left outer join sz_goods as g on item.goodsid = g.id left outer join sz_unit as u on item.unitid = u.id left outer join sz_warehouse as wh on item.warehouseid = wh.id where item.transferdocid = ? ";
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg11)});
            ArrayList<TransferItemSource> sourceArrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                TransferItemSource source = new TransferItemSource();
                source.setSerialid(cursor.getLong(0));
                source.setTransferdocid(cursor.getLong(1));
                source.setGoodsid(cursor.getString(2));
                source.setGoodsname(cursor.getString(3));
                source.setBarcode(cursor.getString(4));
                source.setSpecification(cursor.getString(5));
                source.setIsusebatch(cursor.getInt(6) == 1);
                source.setUnitid(cursor.getString(7));
                source.setUnitname(cursor.getString(8));
                source.setNum(cursor.getDouble(9));
                source.setBatch(cursor.getString(10));
                source.setProductiondate(cursor.getString(11));
                source.setWarehouseid(cursor.getString(12));
                source.setWarehousename(cursor.getString(13));
                source.setRemark(cursor.getString(14));
                sourceArrayList.add(source);
            }
            return sourceArrayList;
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


    public boolean updateTransferItem(TransferItem arg10) {
        boolean v0 = true;
        this.db = this.helper.getWritableDatabase();
        ContentValues v1 = new ContentValues();
        v1.put("transferdocid", arg10.getTransferdocid());
        v1.put("goodsid", arg10.getGoodsid());
        v1.put("unitid", arg10.getUnitid());
        v1.put("num", arg10.getNum());
        v1.put("batch", arg10.getBatch());
        v1.put("productiondate", arg10.getProductiondate());
        v1.put("warehouseid", arg10.getWarehouseid());
        v1.put("remark", arg10.getRemark());
        if (this.db.update("kf_transferitem", v1, "serialid=?", new String[]{String.valueOf(arg10.getSerialid())}) != 1) {
            v0 = false;
        }
        if (this.db != null) {
            this.db.close();
        }
        return v0;
    }

    public TransferItemSource getTransferItem(long itemid) {
        this.db = this.helper.getReadableDatabase();

        try {
            String sql = "select item.serialid, item.transferdocid,item.goodsid, g.name goodsname, g.barcode, g.specification, g.isusebatch, item.unitid, u.name unitname, item.num, item.batch, item.productiondate, item.warehouseid, wh.name warehousename, item.remark from kf_transferitem as item left outer join sz_goods as g on item.goodsid = g.id left outer join sz_unit as u on item.unitid = u.id left outer join sz_warehouse as wh on item.warehouseid = wh.id where item.serialid = ? ";
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(itemid)});
            if (cursor.moveToNext()) {
                TransferItemSource source = new TransferItemSource();
                source.setSerialid(cursor.getLong(0));
                source.setTransferdocid(cursor.getLong(1));
                source.setGoodsid(cursor.getString(2));
                source.setGoodsname(cursor.getString(3));
                source.setBarcode(cursor.getString(4));
                source.setSpecification(cursor.getString(5));
                source.setIsusebatch(cursor.getInt(6) == 1);
                source.setUnitid(cursor.getString(7));
                source.setUnitname(cursor.getString(8));
                source.setNum(cursor.getDouble(9));
                source.setBatch(cursor.getString(10));
                source.setProductiondate(cursor.getString(11));
                source.setWarehouseid(cursor.getString(12));
                source.setWarehousename(cursor.getString(13));
                source.setRemark(cursor.getString(14));
                return source;
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
