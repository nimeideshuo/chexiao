package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.amap.api.location.AMapLocation;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSalePayType;
import com.sunwuyou.swymcx.model.PayType;
import com.sunwuyou.swymcx.request.ReqDocAddCheXiao;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.UpdateUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        v5.put("isnewcustomer", arg10.isIsnewcustomer() ? "1" : "0");
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
        v5.put("preference", arg10.getPreference());
        v5.put("status", arg10.getStatus());
        v5.put("buildtime", arg10.getBuildtime());
        v5.put("longitude", arg10.getLongitude());
        v5.put("latitude", arg10.getLatitude());
        v5.put("address", arg10.getAddress());
        long v2 = this.db.insert("kf_fieldsale", null, v5);
        List<PayType> v4 = new PayTypeDAO().getPaytypes(TextUtils.isEmptyS(arg10.getCustomerid()));
        for (int i = 0; i < v4.size(); i++) {
            FieldSalePayType v0 = new FieldSalePayType();
            v0.setFieldsaleid(v2);
            v0.setPaytypeid(v4.get(i).getId());
            v0.setPaytypename(v4.get(i).getName());
            v0.setAmount(0);
            new FieldSalePayTypeDAO().save(v0);
        }
        if (this.db != null) {
            this.db.close();
        }
        return v2;
    }

    private String queryPricesystemid(String paramString, boolean paramBoolean) {
        if (!TextUtils.isEmptyS(paramString)) {
            Customer customer = new CustomerDAO().getCustomer(paramString, paramBoolean);
            if ((customer != null) && (!TextUtils.isEmptyS(customer.getPriceSystemId())) && (new PricesystemDAO().isPricesystemidExists(customer.getPriceSystemId()))) {
                return customer.getPriceSystemId();
            }
        }
        if ((!TextUtils.isEmptyS(Utils.pricesystemid)) && (new PricesystemDAO().isPricesystemidExists(Utils.pricesystemid))) {
            return Utils.pricesystemid;
        }
        return new PricesystemDAO().queryAvailablePricesystem();
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

    public ReqDocAddCheXiao getFieldSaleForUpload(long arg11) {
        this.db = this.helper.getWritableDatabase();
        String sql = "select customerid, departmentid, warehouseid, preference, pricesystemid, promotionid, builderid, buildtime, remark, printnum, longitude, latitude, address from kf_fieldsale where id = ?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg11)});
            ReqDocAddCheXiao v3 = null;
            if (cursor.moveToNext()) {
                ReqDocAddCheXiao cheXiao = new ReqDocAddCheXiao();
                cheXiao.setIsCarsell(true);
                cheXiao.setCustomerId(cursor.getString(0));
                cheXiao.setDepartmentId(cursor.getString(1));
                cheXiao.setWarehouseId(cursor.getString(2));
                cheXiao.setPreference(cursor.getDouble(3));
                cheXiao.setPriceSystemId(cursor.getString(4));
                cheXiao.setPromotionId(cursor.getString(5));
                cheXiao.setBuilderId(cursor.getString(6));
                cheXiao.setBuildTime(cursor.getString(7));
                cheXiao.setMobile(null);
                cheXiao.setRemark(cursor.getString(8));
                cheXiao.setPrintNum(cursor.getInt(9));
                cheXiao.setLongitude(cursor.getDouble(10));
                cheXiao.setLatitude(cursor.getDouble(11));
                cheXiao.setAddress(cursor.getString(12));
                FieldSaleItemDAO v2 = new FieldSaleItemDAO();
                cheXiao.setWarehouseId(SystemState.getWarehouse().getId());
                cheXiao.setSaleAmount(Utils.getRecvable(v2.getDocSalePrice(arg11)));
                cheXiao.setCancelAmount(Utils.getRecvable(v2.getDocCancelPrice(arg11)));
                return cheXiao;

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

    public FieldSale getFieldsale(Long arg12) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select id, showid, customerid, customername, departmentid, departmentname, warehouseid, warehousename, builderid, buildername, buildtime, preference, pricesystemid, promotionid, remark, status, longitude, latitude, address, isnewcustomer,printnum from kf_fieldsale where id=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg12)});
            if (cursor.moveToNext()) {
                FieldSale sale = new FieldSale();
                sale.setId(cursor.getLong(0));
                sale.setShowid(cursor.getString(1));
                sale.setCustomerid(cursor.getString(2));
                sale.setCustomername(cursor.getString(3));
                sale.setDepartmentid(cursor.getString(4));
                sale.setDepartmentname(cursor.getString(5));
                sale.setWarehouseid(cursor.getString(6));
                sale.setWarehousename(cursor.getString(7));
                sale.setBuilderid(cursor.getString(8));
                sale.setBuildername(cursor.getString(9));
                sale.setBuildtime(cursor.getString(10));
                sale.setPreference(cursor.getDouble(11));
                sale.setPricesystemid(cursor.getString(12));
                sale.setPromotionid(cursor.getString(13));
                sale.setRemark(cursor.getString(14));
                sale.setStatus(cursor.getInt(15));
                sale.setLongitude(cursor.getDouble(16));
                sale.setLatitude(cursor.getDouble(17));
                sale.setAddress(cursor.getString(18));
                sale.setIsnewcustomer(cursor.getDouble(19) == 1);
                sale.setPrintnum(((double) cursor.getInt(20)));
                return sale;
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

    public void deleteFieldsale(long paramLong) {
        this.db = this.helper.getWritableDatabase();
        ArrayList<HashMap<String, String>> localArrayList = new ArrayList<HashMap<String, String>>();
        String str = " delete from kf_fieldsaleitem where fieldsaleid = " + paramLong;
        HashMap<String, String> localHashMap = new HashMap<String, String>();
        localHashMap.put("sql", str);
        localArrayList.add(localHashMap);
        str = " delete from kf_fieldsalepaytype where fieldsaleid = " + paramLong;
        localHashMap = new HashMap<String, String>();
        localHashMap.put("sql", str);
        localArrayList.add(localHashMap);
        str = " delete from kf_fieldsaleitembatch where fieldsaleid = " + paramLong;
        localHashMap = new HashMap<String, String>();
        localHashMap.put("sql", str);
        localArrayList.add(localHashMap);
        str = " delete from kf_fieldsaleimage where fieldsaleid = " + paramLong;
        localHashMap = new HashMap<String, String>();
        localHashMap.put("sql", str);
        localArrayList.add(localHashMap);
        str = " delete from kf_fieldsale where id = " + paramLong;
        localHashMap = new HashMap<String, String>();
        localHashMap.put("sql", str);
        localArrayList.add(localHashMap);
        new UpdateUtils().saveToLocalDB(localArrayList);
    }

    public boolean updateDocValue(long arg9, String arg11, String arg12) {
        boolean v0 = true;
        this.db = this.helper.getWritableDatabase();
        ContentValues v1 = new ContentValues();
        v1.put(arg11, arg12);
        if (this.db.update("kf_fieldsale", v1, "id=?", new String[]{String.valueOf(arg9)}) != 1) {
            v0 = false;
        }
        if (this.db != null) {
            this.db.close();
        }
        return v0;
    }

    public boolean updateLocation(Long arg11, AMapLocation arg12) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v1 = new ContentValues();
        v1.put("longitude", Double.valueOf(arg12.getLongitude()));
        v1.put("latitude", Double.valueOf(arg12.getLatitude()));
        v1.put("address", arg12.getAddress());
        try {
            if (this.db.update("kf_fieldsale", v1, "id=?", new String[]{"" + arg11}) != 1) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (db != null) {
            db.close();
        }
        return true;
    }

}
