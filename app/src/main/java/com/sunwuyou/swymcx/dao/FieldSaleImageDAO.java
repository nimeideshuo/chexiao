package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.request.ReqVstAddVisitCustomerJobImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupiao on
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


}
