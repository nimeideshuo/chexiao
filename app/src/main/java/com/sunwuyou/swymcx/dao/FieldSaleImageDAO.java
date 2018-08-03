package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.FieldSaleImage;
import com.sunwuyou.swymcx.request.ReqVstAddVisitCustomerJobImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on
 * 2018/8/2.
 * content
 */
public class FieldSaleImageDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public FieldSaleImageDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public List<ReqVstAddVisitCustomerJobImage> getFieldSaleImageForUpload(long arg13) {
        this.db = this.helper.getWritableDatabase();
        try {
            cursor = this.db.rawQuery("select issignature, imagepath, remark from kf_fieldsaleimage where fieldsaleid = ? ", new String[]{String.valueOf(arg13)});
            ArrayList<ReqVstAddVisitCustomerJobImage> v3 = new ArrayList<>();
            while (cursor.moveToNext()) {
                ReqVstAddVisitCustomerJobImage v2 = new ReqVstAddVisitCustomerJobImage();
                v2.setIsSignature(cursor.getLong(0) == 1);
                v2.setImagePath(cursor.getString(1));
                v2.setRemark(cursor.getString(2));
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

    public FieldSaleImage querySignaturePic(long arg12) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select serialid, fieldsaleid, issignature, imagepath, remark from kf_fieldsaleimage where fieldsaleid=? and issignature=\'1\'", new String[]{String.valueOf(arg12)});
            if (cursor.moveToNext()) {
                FieldSaleImage saleImage = new FieldSaleImage();
                saleImage.setSerialid(cursor.getLong(0));
                saleImage.setFieldsaleid(cursor.getLong(1));
                saleImage.setIssignature(cursor.getInt(2) == 1);
                saleImage.setImagepath(cursor.getString(3));
                saleImage.setRemark(cursor.getString(4));
                return saleImage;
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

    public void addJobImage(FieldSaleImage arg8) {
        int v1 = 0;
        this.db = this.helper.getWritableDatabase();
        String v0 = "insert into kf_fieldsaleimage (fieldsaleid, issignature, imagepath, remark) values (?,?,?,?)";
        SQLiteDatabase v3 = this.db;
        Object[] v4 = new Object[4];
        v4[0] = arg8.getFieldsaleid();
        if (arg8.isIssignature()) {
            v1 = 1;
        }
        v4[1] = v1;
        v4[2] = arg8.getImagepath();
        v4[3] = arg8.getRemark();
        v3.execSQL(v0, v4);
        if (this.db != null) {
            this.db.close();
        }
    }

    public boolean delete(long arg8) {
        boolean v0 = true;
        this.db = this.helper.getWritableDatabase();
        if (this.db.delete("kf_fieldsaleimage", "serialid=?", new String[]{String.valueOf(arg8)}) != 1) {
            v0 = false;
        }

        if (this.db != null) {
            this.db.close();
        }

        return v0;
    }

    public List<FieldSaleImage> queryJobImage(long arg11) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select serialid, fieldsaleid, issignature, imagepath, remark from kf_fieldsaleimage where fieldsaleid=? and issignature=\'0\'", new String[]{String.valueOf(arg11)});
            ArrayList<FieldSaleImage> arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                FieldSaleImage saleImage = new FieldSaleImage();
                saleImage.setSerialid(cursor.getLong(0));
                saleImage.setFieldsaleid(cursor.getLong(1));
                saleImage.setIssignature(cursor.getInt(2) == 1);
                saleImage.setImagepath(cursor.getString(3));
                saleImage.setRemark(cursor.getString(4));
                arrayList.add(saleImage);
            }
            return arrayList;
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
