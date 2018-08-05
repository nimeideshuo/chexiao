package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.PayType;
import com.sunwuyou.swymcx.model.SettleUp;
import com.sunwuyou.swymcx.model.SettleUpPayType;
import com.sunwuyou.swymcx.model.SettleupThin;
import com.sunwuyou.swymcx.utils.MLog;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on
 * 2018/7/31.
 * content
 */
public class SettleUpDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public SettleUpDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public long AddSettleUp(SettleUp arg11) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v6 = new ContentValues();
        v6.put("builderid", arg11.getBuilderId());
        v6.put("buildername", arg11.getBuilderName());
        v6.put("departmentid", arg11.getDepartmentId());
        v6.put("departmentname", arg11.getDepartmentName());
        v6.put("objectid", arg11.getObjectId());
        v6.put("objectname", arg11.getObjectName());
        v6.put("objectoperator", arg11.getObjectOperator());
        v6.put("remark", arg11.getRemark());
        v6.put("type", arg11.getType());
        v6.put("preference", arg11.getPreference());
        String v8 = "issubmit";
        v6.put(v8, arg11.getIsSubmit() ? 1 : 0);
        v6.put("buildtime", arg11.getBuildTime());
        long v2 = this.db.insert("cw_settleup", null, v6);
        if (this.db != null) {
            this.db.close();
        }
        SettleUpPayTypeDAO v0 = new SettleUpPayTypeDAO();
        if (v2 != -1) {
            List<PayType> typeList = new PayTypeDAO().getPaytypes(!TextUtils.isEmptyS(arg11.getObjectId()));
            for (int i = 0; i < typeList.size(); i++) {
                SettleUpPayType v5 = new SettleUpPayType();
                v5.setAmount(0);
                v5.setPaytypeid(typeList.get(i).getId());
                v5.setPaytypename(typeList.get(i).getName());
                v5.setSettleupid(v2);
                v0.save(v5);
            }
        }
        return v2;
    }

    public SettleUp getSettleUp(long arg11) {
        this.db = this.helper.getReadableDatabase();
        try {
            String sql = "select id,objectid,objectname,departmentid,departmentname,objectoperator,preference,issubmit,remark,builderid,buildername,buildtime,type from cw_settleup where id=?";
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg11)});
            if (cursor.moveToNext()) {
                SettleUp settleUp = new SettleUp();
                settleUp.setId(cursor.getLong(0));
                settleUp.set0bjectId(cursor.getString(1));
                settleUp.setObjectName(cursor.getString(2));
                settleUp.setDepartmentId(cursor.getString(3));
                settleUp.setDepartmentName(cursor.getString(4));
                settleUp.setObjectOperator(cursor.getString(5));
                settleUp.setPreference(cursor.getDouble(6));
                settleUp.setIsSubmit(cursor.getInt(7) == 1);
                settleUp.setRemark(cursor.getString(8));
                settleUp.setBuilderId(cursor.getString(9));
                settleUp.setBuilderName(cursor.getString(10));
                settleUp.setBuildTime(cursor.getString(11));
                settleUp.setType(cursor.getString(12));
                return settleUp;
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

    public boolean update(long arg9, String arg11, String arg12) {
        boolean v0 = true;
        this.db = this.helper.getWritableDatabase();
        ContentValues v1 = new ContentValues();
        v1.put(arg11, arg12);
        if (this.db.update("cw_settleup", v1, "id=?", new String[]{String.valueOf(arg9)}) != 1) {
            v0 = false;
        }
        if (this.db != null) {
            this.db.close();
        }
        return v0;
    }

    public List<SettleupThin> getSettleUps() {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select c.id,c.objectid,c.objectname,c.preference,c.issubmit,c.type from cw_settleup c order by c.id desc ", null);
            ArrayList<SettleupThin> arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                SettleupThin settleupThin = new SettleupThin();
                settleupThin.setId(cursor.getLong(0));
                settleupThin.setObjectid(cursor.getString(1));
                settleupThin.setObjectname(cursor.getString(2));
                settleupThin.setPreference(cursor.getDouble(3));
                settleupThin.setIssubmit(cursor.getInt(4) == 1);
                settleupThin.setType(cursor.getString(5));
                double v3 = cursor.getString(5).equals("63") ? new SettleUpItemDAO().getSettleupAmount(cursor.getLong(0)) : new OtherSettleUpItemDAO().getOtherSettleupAmount(cursor.getLong(0));
                settleupThin.setReceivableamount(v3 - settleupThin.getPreference());
                settleupThin.setReceivedamount(new SettleUpPayTypeDAO().getSettleupPaysAmount(cursor.getLong(0)));
                arrayList.add(settleupThin);
            }
            return arrayList;
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

    public boolean deleteSettleUp(long arg10) {
        boolean v0 = true;
        SettleUp v1 = this.getSettleUp(arg10);
        this.db = this.helper.getWritableDatabase();
        this.db.delete("cw_settleuppaytype", "settleupid=?", new String[]{new StringBuilder(String.valueOf(arg10)).toString()});
        if (v1.getType().equals("63")) {
            this.db.delete("cw_settleupitem", "settleupid=?", new String[]{new StringBuilder(String.valueOf(arg10)).toString()});
        } else {
            this.db.delete("cw_othersettleupitem", "othersettleupid=?", new String[]{new StringBuilder(String.valueOf(arg10)).toString()});
        }

        if (this.db.delete("cw_settleup", "id=?", new String[]{String.valueOf(arg10)}) != 1) {
            v0 = false;
        }

        if (this.db != null) {
            this.db.close();
        }
        return v0;
    }

    public boolean isEmpty(long arg9, boolean arg11) {
        this.db = this.helper.getWritableDatabase();
        String v3 = arg11 ? "select 1 from cw_othersettleupitem where othersettleupid=?" : "select 1 from cw_settleupitem where settleupid=?";
        try {
            cursor = this.db.rawQuery(v3, new String[]{String.valueOf(arg9)});
            if (!cursor.moveToNext()) {
                return true;
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
        return false;
    }

    public boolean isSettleup(long arg9, boolean arg11) {
        double v0;
        double v2 = 0;
        if (arg11) {
            v0 = new OtherSettleUpItemDAO().getOtherSettleupAmount(arg9);
        } else {
            v0 = new SettleUpItemDAO().getSettleupAmount(arg9);
            v2 = this.getPreference(arg9);
        }
        return Utils.equals(v0 - v2, new SettleUpPayTypeDAO().getSettleupPaysAmount(arg9));
    }

    public double getPreference(long arg10) {
        this.db = this.helper.getWritableDatabase();
        try {
            String sql = "select preference from cw_settleup where id=?";
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg10)});
            if (cursor.moveToNext()) {
                return cursor.getDouble(0);
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
        return 0;
    }

    public boolean submit(long arg3) {
        return this.update(arg3, "issubmit", String.valueOf(1));
    }


}
