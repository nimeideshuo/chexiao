package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.model.CustomerThin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/7/19.
 * content
 */

public class CustomerDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper = new DBOpenHelper();
    private Cursor cursor;

    public List<Customer> queryAllCustomer() {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select id, name, pinyin, orderno, contact, contactmoblie, telephone, regionid, customertypeid, visitlineid, depositbank,  bankingaccount, promotionid, address, pricesystemid, longitude, latitude, remark, isnew, isfinish, exhibitionterm, lastexhibition,  isusecustomerprice from cu_customer where isavailable = '1' order by pinyin", null);
            ArrayList<Customer> arrayList = new ArrayList<Customer>();
            while (cursor.moveToNext()) {
                Customer customer = new Customer();
                customer.setId(cursor.getString(0));
                customer.setName(cursor.getString(1));
                customer.setPinyin(cursor.getString(2));
                customer.setOrderNo(cursor.getInt(3));
                customer.setContact(cursor.getString(4));
                customer.setContactMoblie(cursor.getString(5));
                customer.setTelephone(cursor.getString(6));
                customer.setRegionId(cursor.getString(7));
                customer.setCustomerTypeId(cursor.getString(8));
                customer.setVisitLineId(cursor.getString(9));
                customer.setDepositBank(cursor.getString(10));
                customer.setBankingAccount(cursor.getString(11));
                customer.setPromotionId(cursor.getString(12));
                customer.setAddress(cursor.getString(13));
                customer.setPriceSystemId(cursor.getString(14));
                customer.setLongitude(cursor.getDouble(15));
                customer.setLatitude(cursor.getDouble(16));
                customer.setRemark(cursor.getString(17));
                customer.setIsNew(cursor.getInt(18) == 1);
                customer.setIsFinish(cursor.getInt(19) == 1);
                customer.setExhibitionTerm(cursor.getInt(20));
                customer.setLastExhibition(cursor.getLong(21));
                customer.setIsusecustomerprice(cursor.getInt(22) == 1);
                arrayList.add(customer);
            }
            return arrayList;
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

    public Customer getNextVisitCustomer() {
        this.db = this.helper.getReadableDatabase();
        try {
            String sql = "select id, name, pinyin, orderno, contact, contactmoblie, telephone, regionid, customertypeid, visitlineid, depositbank,  bankingaccount, promotionid, address, pricesystemid, longitude, latitude, remark, isnew, isfinish, exhibitionterm, lastexhibition,  isusecustomerprice from cu_customer where orderno=(select min(orderno) from cu_customer where isfinish='0')";
            cursor = this.db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                Customer customer = new Customer();
                customer.setId(cursor.getString(0));
                customer.setName(cursor.getString(1));
                customer.setPinyin(cursor.getString(2));
                customer.setOrderNo(cursor.getInt(3));
                customer.setContact(cursor.getString(4));
                customer.setContactMoblie(cursor.getString(5));
                customer.setTelephone(cursor.getString(6));
                customer.setRegionId(cursor.getString(7));
                customer.setCustomerTypeId(cursor.getString(8));
                customer.setVisitLineId(cursor.getString(9));
                customer.setDepositBank(cursor.getString(10));
                customer.setBankingAccount(cursor.getString(11));
                customer.setPromotionId(cursor.getString(12));
                customer.setAddress(cursor.getString(13));
                customer.setPriceSystemId(cursor.getString(14));
                customer.setLongitude(cursor.getDouble(15));
                customer.setLatitude(cursor.getDouble(16));
                customer.setRemark(cursor.getString(17));
                customer.setIsNew(cursor.getInt(18) == 1);
                customer.setIsFinish(cursor.getInt(19) == 1);
                customer.setExhibitionTerm(cursor.getInt(20));
                customer.setLastExhibition(cursor.getLong(21));
                customer.setIsusecustomerprice(cursor.getInt(22) == 1);
                return customer;
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

    public boolean isExists(String arg8) {
        this.db = this.helper.getWritableDatabase();
        Cursor cursor = null;
        try {
            String sql = "select 1 from cu_customer where id=? and isnew='0'";
            cursor = this.db.rawQuery(sql, new String[]{arg8});
            if (cursor.moveToNext()) {
                return true;
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
        return false;
    }

    public void getPromotionID(List<String> arg7, String arg8) {
        this.db = this.helper.getWritableDatabase();
        try {
            cursor = this.db.rawQuery("select promotionid from cu_customer where isnew = 0 and promotionid is not null and id in (" + arg8 + ")", null);
            while (cursor.moveToNext()) {
                String v2 = cursor.getString(0);
                if (arg7.contains(v2)) {
                    arg7.add(v2);
                }
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
    }

    public List<CustomerThin> getCustomerThin(boolean arg11) {
        this.db = this.helper.getWritableDatabase();
        ArrayList<CustomerThin> v3 = new ArrayList<CustomerThin>();
        try {
            String sql = "";
            if (arg11) {
                sql = "select id, name, contactmoblie, telephone, address, promotionid, orderno, isnew, isfinish, exhibitionterm, lastexhibition, isusecustomerprice from cu_customer" + " where isnew = 0 and isavailable = '1'";
            } else {
                sql = "select id, name, contactmoblie, telephone, address, promotionid, orderno, isnew, isfinish, exhibitionterm, lastexhibition, isusecustomerprice from cu_customer where isavailable = '1'";
            }
            cursor = this.db.rawQuery(sql + " order by orderno", null);
            while (cursor.moveToNext()) {
                CustomerThin customerThin = new CustomerThin();
                customerThin.setId(cursor.getString(0));
                customerThin.setName(cursor.getString(1));
                customerThin.setContactMoblie(cursor.getString(2));
                customerThin.setTelephone(cursor.getString(3));
                customerThin.setAddress(cursor.getString(4));
                customerThin.setPromotionId(cursor.getString(5));
                customerThin.setOrderNo(cursor.getInt(6));
                customerThin.setIsNew(cursor.getInt(7) == 1);
                customerThin.setIsfinish(cursor.getInt(8) == 1);
                customerThin.setExhibitionTerm(cursor.getInt(9));
                customerThin.setLastExhibition(cursor.getLong(10));
                customerThin.setIsusecustomerprice(cursor.getInt(11) == 1);
                v3.add(customerThin);
            }
            return v3;
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
        return v3;
    }

    public int getMaxOrderNo() {
        this.db = this.helper.getReadableDatabase();
        try {
            Cursor v0 = this.db.rawQuery("select max(orderno) from cu_customer", null);
            if (v0.moveToNext()) {
                return v0.getInt(0);
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

    public Customer getCustomer(String id, boolean isnew) {
        this.db = this.helper.getReadableDatabase();
        try {
            String sql = "select id, name, pinyin, orderno, contact, contactmoblie, telephone, regionid, customertypeid, visitlineid, depositbank,  bankingaccount, promotionid, address, pricesystemid, longitude, latitude, remark, isnew, isfinish, exhibitionterm, lastexhibition,  isusecustomerprice from cu_customer where id = ? and isnew = ?";
            Cursor cursor = this.db.rawQuery(sql, new String[]{id, isnew ? "1" : "0"});
            if (cursor.moveToNext()) {
                Customer customer = new Customer();
                customer.setId(cursor.getString(0));
                customer.setName(cursor.getString(1));
                customer.setPinyin(cursor.getString(2));
                customer.setOrderNo(cursor.getInt(3));
                customer.setContact(cursor.getString(4));
                customer.setContactMoblie(cursor.getString(5));
                customer.setTelephone(cursor.getString(6));
                customer.setRegionId(cursor.getString(7));
                customer.setCustomerTypeId(cursor.getString(8));
                customer.setVisitLineId(cursor.getString(9));
                customer.setDepositBank(cursor.getString(10));
                customer.setBankingAccount(cursor.getString(11));
                customer.setPromotionId(cursor.getString(12));
                customer.setAddress(cursor.getString(13));
                customer.setPriceSystemId(cursor.getString(14));
                customer.setLongitude(cursor.getDouble(15));
                customer.setLatitude(cursor.getDouble(16));
                customer.setRemark(cursor.getString(17));
                customer.setIsNew(cursor.getInt(18) == 1);
                customer.setIsFinish(cursor.getInt(19) == 1);
                customer.setExhibitionTerm(cursor.getInt(20));
                customer.setLastExhibition(cursor.getLong(21));
                customer.setIsusecustomerprice(cursor.getInt(22) == 1);
                return customer;
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


    public boolean updateCustomerValue(String arg9, String arg10, String arg11) {
        boolean v1 = true;
        this.db = this.helper.getWritableDatabase();
        ContentValues v0 = new ContentValues();
        v0.put(arg10, arg11);
        if (this.db.update("cu_customer", v0, "id=?", new String[]{String.valueOf(arg9)}) != 1) {
            v1 = false;
        }
        return v1;
    }

    public void addCustomer(Customer arg9) {
        this.db = this.helper.getWritableDatabase();
        String v0 = "insert into cu_customer (id, name, pinyin, orderno, contact,  contactmoblie, telephone, regionid, customertypeid, visitlineid, depositbank,  bankingaccount, promotionid, address, pricesystemid, longitude,  latitude, remark, isnew, isfinish, isavailable, isusecustomerprice)  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1,?)";
        SQLiteDatabase v4 = this.db;
        Object[] v5 = new Object[21];
        v5[0] = arg9.getId();
        v5[1] = arg9.getName();
        v5[2] = arg9.getPinyin();
        v5[3] = arg9.getOrderNo();
        v5[4] = arg9.getContact();
        v5[5] = arg9.getContactMoblie();
        v5[6] = arg9.getTelephone();
        v5[7] = arg9.getRegionId();
        v5[8] = arg9.getCustomerTypeId();
        v5[9] = arg9.getVisitLineId();
        v5[10] = arg9.getDepositBank();
        v5[11] = arg9.getBankingAccount();
        v5[12] = arg9.getPromotionId();
        v5[13] = arg9.getAddress();
        v5[14] = arg9.getPriceSystemId();
        v5[15] = arg9.getLongitude();
        v5[16] = arg9.getLatitude();
        v5[17] = arg9.getRemark();
        v5[18] = arg9.getIsNew() ? 1 : 0;
        v5[19] = arg9.getIsFinish() ? 1 : 0;
        if (!arg9.getIsusecustomerprice()) {
            v5[20] = 0;
        }
        v4.execSQL(v0, v5);
        if (this.db != null) {
            this.db.close();
        }
    }

    public void updateCustomer(String arg8, Customer arg9) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v0 = new ContentValues();
        v0.put("id", arg9.getId());
        v0.put("name", arg9.getName());
        v0.put("pinyin", arg9.getPinyin());
        v0.put("orderno", arg9.getOrderNo());
        v0.put("contact", arg9.getContact());
        v0.put("contactmoblie", arg9.getContactMoblie());
        v0.put("telephone", arg9.getTelephone());
        v0.put("regionid", arg9.getRegionId());
        v0.put("customertypeid", arg9.getCustomerTypeId());
        v0.put("visitlineid", arg9.getVisitLineId());
        v0.put("depositbank", arg9.getDepositBank());
        v0.put("bankingaccount", arg9.getBankingAccount());
        v0.put("promotionid", arg9.getPromotionId());
        v0.put("address", arg9.getAddress());
        v0.put("pricesystemid", arg9.getPriceSystemId());
        v0.put("longitude", arg9.getLongitude());
        v0.put("latitude", arg9.getLatitude());
        v0.put("remark", arg9.getRemark());
        v0.put("isnew", true);
        v0.put("isfinish", arg9.getIsFinish());
        SQLiteDatabase v2 = this.db;
        String v3 = "cu_customer";
        String v4 = "id=? and isnew=?";
        String[] v5 = new String[2];
        v5[0] = arg8;
        String v1 = arg9.getIsNew() ? "1" : "0";
        v5[1] = v1;
        v2.update(v3, v0, v4, v5);
        if (this.db != null) {
            this.db.close();
        }
    }

    public void updateCustomerDocs(String arg12, String arg13) {
        Customer v0 = this.getCustomer(arg13, true);
        this.db = this.helper.getWritableDatabase();
        this.db.execSQL("update kf_fieldsale set customerid=?,customername=? where customerid=? and isnewcustomer=?", new String[]{v0.getId(), v0.getName(), arg12, "1"});
        this.db.execSQL("update cw_settleup set objectid=?,objectname=? where objectid=? and isnewobject=?", new String[]{v0.getId(), v0.getName(), arg12, "1"});
    }

    public boolean isCustomerHasDoc(String arg15) {
        Cursor v11;
        Cursor v10;
        this.db = this.helper.getReadableDatabase();
        boolean v9 = false;
        v10 = this.db.query("kf_fieldsale", null, "customerid=?", new String[]{arg15}, null, null, null);
        v11 = this.db.query("cw_settleup", null, "objectid=?", new String[]{arg15}, null, null, null);
        if (v10.getCount() > 0) {
            return true;
        } else if (v11.getCount() > 0) {
            return true;
        }
        if (db != null) {
            db.close();
        }

        return false;

    }

    public boolean delete(List arg15) {
        int v0;
        Cursor v1;
        String v6;
        boolean v5 = false;
        int v4 = 0;
        while (v4 < arg15.size()) {



        }

        return true;

    }

}
