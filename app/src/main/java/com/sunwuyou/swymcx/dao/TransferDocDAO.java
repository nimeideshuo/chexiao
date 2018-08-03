package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.TransferDoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on
 * 2018/7/30.
 * content
 */
public class TransferDocDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public TransferDocDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public List<TransferDoc> queryAllTransferDoc() {
        this.db = this.helper.getReadableDatabase();
        ArrayList<TransferDoc> list = new ArrayList<>();
        try {
            cursor = this.db.rawQuery("select id,showid,departmentid,departmentname,inwarehouseid,inwarehousename, isposted,isupload,remark,builderid,buildername,buildtime from kf_transferdoc order by id desc", null);
            while (cursor.moveToNext()) {
                TransferDoc transferDoc = new TransferDoc();
                transferDoc.setId(cursor.getLong(0));
                transferDoc.setShowid(cursor.getString(1));
                transferDoc.setDepartmentid(cursor.getString(2));
                transferDoc.setDepartmentname(cursor.getString(3));
                transferDoc.setInwarehouseid(cursor.getString(4));
                transferDoc.setInwarehousename(cursor.getString(5));
                transferDoc.setIsposted(cursor.getInt(6) == 1);
                transferDoc.setIsupload(cursor.getInt(7) == 1);
                transferDoc.setRemark(cursor.getString(8));
                transferDoc.setBuilderid(cursor.getString(9));
                transferDoc.setBuildername(cursor.getString(10));
                transferDoc.setBuildtime(cursor.getString(11));
                list.add(transferDoc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return list;
    }

    public boolean isEmpty(long transferdocid) {
        this.db = this.helper.getWritableDatabase();
        String sql = "select 1 from kf_transferitem where transferdocid=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(transferdocid)});
            if (cursor.moveToNext()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return false;
    }

    public boolean isExistsNoBatch(long arg9) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select 1 from kf_transferitem as item, sz_goods as g  where item.goodsid = g.id and item.transferdocid=? and g.isusebatch = \'true\' and (item.batch is null or item.batch = \'\')", new String[]{String.valueOf(arg9)});
            if (cursor.moveToNext()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return false;

    }

    public boolean submit(long arg3) {
        return this.updateDocValue(arg3, "isupload", String.valueOf(1));
    }

    public boolean updateDocValue(long arg9, String arg11, String arg12) {
        boolean v0 = true;
        this.db = this.helper.getWritableDatabase();
        ContentValues v1 = new ContentValues();
        v1.put(arg11, arg12);
        if (this.db.update("kf_transferDoc", v1, "id=?", new String[]{String.valueOf(arg9)}) != 1) {
            v0 = false;
        }
        if (this.db != null) {
            this.db.close();
        }
        return v0;
    }
    public boolean deletetransferDoc(long arg8) {
        boolean v0 = true;
        this.db = this.helper.getWritableDatabase();
        if(this.db.delete("kf_transferitem", "transferdocid=?", new String[]{String.valueOf(arg8)}) == -1 || this.db.delete("kf_transferdoc", "id=?", new String[]{String.valueOf(arg8)}) != 1) {
            v0 = false;
        }

        if(this.db != null) {
            this.db.close();
        }

        return v0;
    }

}
