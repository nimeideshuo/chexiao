package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

/**
 * Created by admin
 * 2018/7/19.
 * content
 */

public class FieldSaleDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public FieldSaleDAO() {
        super();
        this.helper = new DBOpenHelper();
    }


    public long addFieldsale(FieldSale arg10) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v5 = new ContentValues();
        v5.put("showid", arg10.getShowid());
        v5.put("customerid", arg10.getCustomerid());
        String v7 = "isnewcustomer";
        String v6 = arg10.isIsnewcustomer() ? "1" : "0";
        v5.put(v7, v6);
        v5.put("builderid", arg10.getBuilderid());
        v5.put("buildername", arg10.getBuildername());
        v5.put("customername", arg10.getCustomername());
        v5.put("departmentid", arg10.getDepartmentid());
        v5.put("departmentname", arg10.getDepartmentname());
        v5.put("pricesystemid", this.queryPricesystemid(arg10.getCustomerid(), arg10.isIsnewcustomer()));
        v5.put("promotionid", arg10.getPromotionid());
        v5.put("remark", arg10.getRemark());
        v5.put("warehouseid", arg10.getWarehouseid());
        v5.put("warehousename", arg10.getWarehousename());
        v5.put("preference", Double.valueOf(arg10.getPreference()));
        v5.put("status", Integer.valueOf(arg10.getStatus()));
        v5.put("buildtime", arg10.getBuildtime());
        v5.put("longitude", Double.valueOf(arg10.getLongitude()));
        v5.put("latitude", Double.valueOf(arg10.getLatitude()));
        v5.put("address", arg10.getAddress());
        long v2 = this.db.insert("kf_fieldsale", null, v5);
        List v4 = new PayTypeDAO().getPaytypes(TextUtils.isEmptyS(arg10.getCustomerid()));
        int v1;
        for(v1 = 0; v1 < v4.size(); ++v1) {
            FieldSalePayType v0 = new FieldSalePayType();
            v0.setFieldsaleid(v2);
            v0.setPaytypeid(v4.get(v1).getId());
            v0.setPaytypename(v4.get(v1).getName());
            v0.setAmount(0);
            new FieldSalePayTypeDAO().save(v0);
        }

        if(this.db != null) {
            this.db.close();
        }

        return v2;
    }

    private String queryPricesystemid(String arg4, boolean arg5) {
        String v1;
        if(!TextUtils.isEmptyS(arg4)) {
            Customer v0 = new CustomerDAO().getCustomer(arg4, arg5);
            if(v0 == null) {
                goto label_16;
            }
            else if(TextUtils.isEmptyS(v0.getPriceSystemId())) {
                goto label_16;
            }
            else if(new PricesystemDAO().isPricesystemidExists(v0.getPriceSystemId())) {
                v1 = v0.getPriceSystemId();
            }
            else {
                goto label_16;
            }
        }
        else {
            label_16:
            if(!TextUtils.isEmptyS(Utils.pricesystemid) && (new PricesystemDAO().isPricesystemidExists(Utils.pricesystemid))) {
                return Utils.pricesystemid;
            }

            v1 = new PricesystemDAO().queryAvailablePricesystem();
        }

        return v1;
    }



    public String makeShowId() {
        try {
            long v9 = Utils.getStartTime().longValue();
            this.db = this.helper.getReadableDatabase();
            String sql = "select showid from kf_fieldsale where buildtime between ? and ? order by id desc limit 1 offset 0";
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(v9), String.valueOf(v9 + 86400000)});
            if (cursor.moveToNext()) {
                return cursor.getString(0);
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
        return "";
    }

}
