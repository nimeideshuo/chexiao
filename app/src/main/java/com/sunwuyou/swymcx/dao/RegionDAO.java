package com.sunwuyou.swymcx.dao;

import android.database.Cursor;

import com.sunwuyou.swymcx.model.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupiao on
 * 2018/8/1.
 * content
 */
public class RegionDAO extends BaseDao {

    private Cursor cursor;

    public List<Region> getAllRegions() {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select id,name,pinyin from sz_region where isavailable = \'1\'", null);
            ArrayList<Region> arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Region v2 = new Region();
                v2.setId(cursor.getString(0));
                v2.setName(cursor.getString(1));
                v2.setPinyin(cursor.getString(2));
                arrayList.add(v2);
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
        return null;
    }

}
