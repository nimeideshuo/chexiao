package com.sunwuyou.swymcx.dao;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by admin on
 * 2018/8/1.
 * content
 */
public class BaseDao {
    protected SQLiteDatabase db;
    protected DBOpenHelper helper;

    public BaseDao() {
        super();
        this.helper = new DBOpenHelper();
    }

    public boolean deleteDataBase() {
        this.db = this.helper.getWritableDatabase();
        this.db.execSQL("delete from sz_unit");
        this.db.execSQL("delete from sz_department");
        this.db.execSQL("delete from sz_warehouse");
        this.db.execSQL("delete from sz_paytype");
        this.db.execSQL("delete from sz_account");
        this.db.execSQL("delete from sz_pricesystem");
        this.db.execSQL("delete from cu_customertype");
        this.db.execSQL("delete from sz_region");
        this.db.execSQL("delete from sz_visitline");
        this.db.execSQL("delete from sz_goods");
        this.db.execSQL("delete from kf_goodsbatch");
        this.db.execSQL("delete from sz_goodsunit");
        this.db.execSQL("delete from sz_goodsprice");
        this.db.execSQL("delete from sz_goodsimage");
        return true;
    }

    public void deleteLocalData() {
        this.db = this.helper.getWritableDatabase();
        this.db.execSQL("delete from cu_customer");
        this.db.execSQL("delete from cu_customerfieldsalegoods");
        this.db.execSQL("delete from sz_promotiongoods");
        this.db.execSQL("delete from sz_promotion");
        this.db.execSQL("delete from kf_serverdoc");
        this.db.execSQL("delete from cw_settleup");
        this.db.execSQL("delete from cw_settleupitem");
        this.db.execSQL("delete from cw_settleuppaytype");
        this.db.execSQL("delete from kf_fieldsaleimage");
        this.db.execSQL("delete from kf_fieldsalepaytype");
        this.db.execSQL("delete from kf_fieldsaleitembatch");
        this.db.execSQL("delete from kf_fieldsaleitem");
        this.db.execSQL("delete from kf_fieldsale");
        this.db.execSQL("delete from kf_transferitem");
        this.db.execSQL("delete from kf_transferdoc");
    }
    //    try {
    //
    //    }catch (Exception e){
    //        e.printStackTrace();
    //    } finally {
    //        if (cursor != null) {
    //            cursor.close();
    //        }
    //        if (this.db != null) {
    //            this.db.close();
    //        }
    //    }
}
