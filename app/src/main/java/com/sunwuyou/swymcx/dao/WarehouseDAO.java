package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.Warehouse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/7/17.
 * content
 */

public class WarehouseDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public WarehouseDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public List<Warehouse> getAllWarehouses(boolean warehouses) {
        this.db = this.helper.getReadableDatabase();
        String sql = warehouses ? "select id, name from sz_warehouse where istruck = \'1\' and isavailable = \'1\'" : "select id, name from sz_warehouse where isavailable = \'1\'";
        cursor = this.db.rawQuery(sql, null);
        List<Warehouse> localArrayList = new ArrayList<Warehouse>();
        try {
            while (cursor.moveToNext()) {
                Warehouse warehouse = new Warehouse();
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                warehouse.setId(id);
                warehouse.setName(name);
                localArrayList.add(warehouse);
            }
            return localArrayList;
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
        return localArrayList;
    }

    public Warehouse getWarehouse(String id) {
        this.db = this.helper.getReadableDatabase();
        try {
            String sql = "select id, name from sz_warehouse where id=?";
            cursor = this.db.rawQuery(sql, new String[]{id});
            if (cursor.moveToNext()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setId(cursor.getString(0));
                warehouse.setName(cursor.getString(1));
                return warehouse;
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
