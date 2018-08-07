package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.GoodsUnit;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/7/27.
 * content
 */

public class GoodsUnitDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper = new DBOpenHelper();
    private Cursor cursor;

    // 获取商品的件数
    public String getBigNum(String goodsid, String unitid, double num) {
        if (!(TextUtils.isEmpty(goodsid) && !TextUtils.isEmpty(unitid))) {
            String v2 = "";
            GoodsUnit v4 = this.getBigUnit(goodsid);
            GoodsUnit v3 = this.getBasicUnit(goodsid);
            if (v4 == null || v3 == null) {
                return "";
            }
            double v0 = num;
            if (!TextUtils.isEmpty(unitid)) {
                v0 *= this.getGoodsUnitRatio(goodsid, unitid);
            }
            int v6 = ((int) (v0 / v4.getRatio()));
            if (v6 != 0) {
                v2 = String.valueOf(v2) + v6 + v4.getUnitname();
                v0 -= (((double) v6)) * v4.getRatio();
            }
            if (v0 == 0) {
                return v2;
            }
            GoodsUnit v5 = this.getMidUnit(goodsid);
            if (v5 != null) {
                v6 = ((int) (v0 / v5.getRatio()));
                if (v6 != 0) {
                    v2 = String.valueOf(v2) + v6 + v5.getUnitname();
                    v0 -= (((double) v6)) * v5.getRatio();
                }
            }
            v0 = Utils.normalize(v0, 2);
            return v2 + (v0 % 1 == 0 ? (int) v0 + "" : v0 + "") + v3.getUnitname();
        }
        return "";
    }

    public GoodsUnit getMidUnit(String goodsid) {
        this.db = this.helper.getReadableDatabase();
        String[] arrays = {goodsid};
        GoodsUnit goodsunit = null;
        try {
            String sql = "select goodsid,unitid,unitname,isbasic,isshow,ratio from sz_goodsunit where goodsid=? and ratio > 0 and ratio < 1 limit 1 offset 0";
            cursor = this.db.rawQuery(sql, arrays);
            while (cursor.moveToNext()) {
                goodsunit = new GoodsUnit();
                goodsunit.setGoodsid(cursor.getString(0));
                goodsunit.setUnitid(cursor.getString(1));
                goodsunit.setUnitname(cursor.getString(2));
                goodsunit.setIsbasic(cursor.getInt(3) == 1 ? true : false);
                goodsunit.setIsshow(cursor.getInt(4) == 1 ? true : false);
                goodsunit.setRatio(cursor.getDouble(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return goodsunit;
    }

    //  获取商品 规格 1*多少
    public double getGoodsUnitRatio(String goodsid, String unitid) {
        this.db = this.helper.getReadableDatabase();
        try {
            String sql = "select a.ratio from sz_goodsunit a where goodsid=? and unitid=?";
            cursor = this.db.rawQuery(sql, new String[]{goodsid, unitid});
            if (cursor.moveToNext()) {
                return cursor.getDouble(0);
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return 1.0D;
    }

    // 基本单位
    public GoodsUnit getBasicUnit(String goodsid) {
        this.db = this.helper.getReadableDatabase();
        GoodsUnit goodsunit = null;
        try {
            String sql = "select goodsid,unitid,unitname,isbasic,isshow,ratio from sz_goodsunit where isbasic=1 and goodsid = ?";
            cursor = this.db.rawQuery(sql, new String[]{goodsid});
            while (cursor.moveToNext()) {
                goodsunit = new GoodsUnit();
                goodsunit.setGoodsid(cursor.getString(0));
                goodsunit.setUnitid(cursor.getString(1));
                goodsunit.setUnitname(cursor.getString(2));
                goodsunit.setIsbasic(cursor.getInt(3) == 1 ? true : false);
                goodsunit.setIsshow(cursor.getInt(4) == 1 ? true : false);
                goodsunit.setRatio(cursor.getDouble(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return goodsunit;
    }

    // 获取大的单位id
    public GoodsUnit getBigUnit(String goodsid) {
        this.db = this.helper.getReadableDatabase();
        String[] arrays = {goodsid};
        GoodsUnit goodsunit = null;

        try {
            String sql = "select goodsid,unitid,unitname,isbasic,isshow,ratio from sz_goodsunit where isshow=1 and goodsid = ?";
            cursor = this.db.rawQuery(sql, arrays);
            while (cursor.moveToNext()) {
                goodsunit = new GoodsUnit();
                goodsunit.setGoodsid(cursor.getString(0));
                goodsunit.setUnitid(cursor.getString(1));
                goodsunit.setUnitname(cursor.getString(2));
                goodsunit.setIsbasic(cursor.getInt(3) == 1 ? true : false);
                goodsunit.setIsshow(cursor.getInt(4) == 1 ? true : false);
                goodsunit.setRatio(cursor.getDouble(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }

        return goodsunit;
    }

    public List<GoodsUnit> queryGoodsUnits(String goodsid) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select goodsid,unitid,unitname,ratio,isbasic,isshow from sz_goodsunit where goodsid=? order by ratio";
        ArrayList<GoodsUnit> unitArrayList = new ArrayList<GoodsUnit>();
        try {
            cursor = this.db.rawQuery(sql, new String[]{goodsid});
            while (cursor.moveToNext()) {
                GoodsUnit v2 = new GoodsUnit();
                v2.setGoodsid(cursor.getString(0));
                v2.setUnitid(cursor.getString(1));
                v2.setUnitname(cursor.getString(2));
                v2.setRatio(cursor.getDouble(3));
                v2.setIsbasic(cursor.getInt(4) == 1);
                v2.setIsshow(cursor.getInt(5) == 1);
                unitArrayList.add(v2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return unitArrayList;
    }

    public boolean isBasicUnit(String arg9, String arg10) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select isbasic from sz_goodsunit where goodsid=? and unitid=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{arg9, arg10});
            if (cursor.moveToNext()) {
                return cursor.getInt(0) == 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return true;
    }

    public GoodsUnit getGoodsUnit(String goodsid, String unitid) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select goodsid,unitid,unitname,ratio,isbasic,isshow from sz_goodsunit where goodsid=? and unitid=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{goodsid, unitid});
            if (cursor.moveToNext()) {
                GoodsUnit v3 = new GoodsUnit();
                v3.setGoodsid(cursor.getString(0));
                v3.setUnitid(cursor.getString(1));
                v3.setUnitname(cursor.getString(2));
                v3.setRatio(cursor.getDouble(3));
                v3.setIsbasic(cursor.getInt(4) == 1);
                v3.setIsshow(cursor.getInt(5) == 1);
                return v3;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }

        return null;
    }

    public GoodsUnit queryBaseUnit(String arg10) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select goodsid,unitid,unitname,ratio,isbasic,isshow from sz_goodsunit where goodsid=? and isbasic=\'1\'";
        try {
            cursor = this.db.rawQuery(sql, new String[]{arg10});
            if (cursor.moveToNext()) {
                GoodsUnit unit = new GoodsUnit();
                unit.setGoodsid(cursor.getString(0));
                unit.setUnitid(cursor.getString(1));
                unit.setUnitname(cursor.getString(2));
                unit.setRatio(cursor.getDouble(3));
                unit.setIsbasic(cursor.getInt(4) == 1);
                unit.setIsshow(cursor.getInt(5) == 1);
                return unit;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return null;
    }

    public GoodsUnit queryBigUnit(String arg10) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select goodsid,unitid,unitname,ratio,isbasic,isshow from sz_goodsunit where goodsid=? and isshow=\'1\'";
        try {
            cursor = this.db.rawQuery(sql, new String[]{arg10});
            if (cursor.moveToNext()) {
                GoodsUnit goodsUnit = new GoodsUnit();
                goodsUnit.setGoodsid(cursor.getString(0));
                goodsUnit.setUnitid(cursor.getString(1));
                goodsUnit.setUnitname(cursor.getString(2));
                goodsUnit.setRatio(cursor.getDouble(3));
                goodsUnit.setIsbasic(cursor.getInt(4) == 1);
                goodsUnit.setIsshow(cursor.getInt(5) == 1);
                return goodsUnit;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }

        return null;
    }

}
