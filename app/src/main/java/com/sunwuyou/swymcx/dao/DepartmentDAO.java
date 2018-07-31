package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/7/17.
 * content
 */

public class DepartmentDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public DepartmentDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public List<Department> getAllDepartment() {
        this.db = this.helper.getReadableDatabase();
        cursor = this.db.rawQuery(
                "select did, dname, warehouseid, warehousename from sz_department where isavailable='1'", null);
        List<Department> localArrayList = new ArrayList<Department>();
        try {
            while (cursor.moveToNext()) {
                Department localDepartment = new Department();
                localDepartment.setDid(cursor.getString(0));
                localDepartment.setDname(cursor.getString(1));
                localDepartment.setWarehouseid(cursor.getString(2));
                localDepartment.setWarehousename(cursor.getString(3));
                localArrayList.add(localDepartment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return localArrayList;
    }

    public boolean isExist() {
        this.db = this.helper.getReadableDatabase();
        try {
            Cursor v0 = this.db.rawQuery("select 1 from sz_department", null);
            if (v0.moveToNext()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (this.db != null)
                this.db.close();
        }
        return false;
    }
}
