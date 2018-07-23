package com.sunwuyou.swymcx.dao;

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
                customer.setOrderno(cursor.getInt(3));
                customer.setContact(cursor.getString(4));
                customer.setContactmoblie(cursor.getString(5));
                customer.setTelephone(cursor.getString(6));
                customer.setRegionid(cursor.getString(7));
                customer.setCustomertypeid(cursor.getString(8));
                customer.setVisitlineid(cursor.getString(9));
                customer.setDepositbank(cursor.getString(10));
                customer.setBankingaccount(cursor.getString(11));
                customer.setPromotionid(cursor.getString(12));
                customer.setAddress(cursor.getString(13));
                customer.setPricesystemid(cursor.getString(14));
                customer.setLongitude(cursor.getDouble(15));
                customer.setLatitude(cursor.getDouble(16));
                customer.setRemark(cursor.getString(17));
                customer.setIsnew(cursor.getInt(18) == 1);
                customer.setIsfinish(cursor.getInt(19) == 1);
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
                customer.setOrderno(cursor.getInt(3));
                customer.setContact(cursor.getString(4));
                customer.setContactmoblie(cursor.getString(5));
                customer.setTelephone(cursor.getString(6));
                customer.setRegionid(cursor.getString(7));
                customer.setCustomertypeid(cursor.getString(8));
                customer.setVisitlineid(cursor.getString(9));
                customer.setDepositbank(cursor.getString(10));
                customer.setBankingaccount(cursor.getString(11));
                customer.setPromotionid(cursor.getString(12));
                customer.setAddress(cursor.getString(13));
                customer.setPricesystemid(cursor.getString(14));
                customer.setLongitude(cursor.getDouble(15));
                customer.setLatitude(cursor.getDouble(16));
                customer.setRemark(cursor.getString(17));
                customer.setIsnew(cursor.getInt(18) == 1);
                customer.setIsfinish(cursor.getInt(19) == 1);
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

}
