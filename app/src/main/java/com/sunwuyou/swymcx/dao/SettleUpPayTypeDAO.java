package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.SettleUpPayType;

/**
 * Created by liupiao on
 * 2018/7/31.
 * content
 */
public class SettleUpPayTypeDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;

    public SettleUpPayTypeDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public long save(SettleUpPayType payType) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v2 = new ContentValues();
        v2.put("settleupid", payType.getSettleupid());
        v2.put("paytypeid", payType.getPaytypeid());
        v2.put("paytypename", payType.getPaytypename());
        v2.put("amount", payType.getAmount());
        v2.put("remark", payType.getRemark());
        long v0 = this.db.insert("cw_settleuppaytype", null, v2);
        if(this.db != null) {
            this.db.close();
        }
        return v0;
    }

}
