package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.PromotionGoods;

/**
 * Created by admin
 * 2018/7/29.
 * content
 */

public class PromotionGoodsDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public PromotionGoodsDAO() {
        super();
        this.helper = new DBOpenHelper();
    }


    public PromotionGoods getPromotiongoods(String arg10, String arg11) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select p.promotionid,p.goodsid,p.unitid,p.type,p.giftgoodsid,g.name as giftgoodsname,p.giftunitid,p.num,p.giftnum,p.initnum,p.leftnum,p.price,p.summary from sz_promotiongoods p left join sz_goods g on g.id=p.giftgoodsid where p.goodsid=? and p.promotionid=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{arg11, arg10});
            if (cursor.moveToNext()) {
                PromotionGoods goods = new PromotionGoods();
                goods.setPromotionid(cursor.getString(0));
                goods.setGoodsid(cursor.getString(1));
                goods.setUnitid(cursor.getString(2));
                goods.setType(cursor.getInt(3));
                goods.setGiftgoodsid(cursor.getString(4));
                goods.setGiftgoodsname(cursor.getString(5));
                goods.setGiftunitid(cursor.getString(6));
                goods.setNum(cursor.getDouble(7));
                goods.setGiftnum(cursor.getDouble(8));
                goods.setInitnum(cursor.getDouble(9));
                goods.setLeftnum(cursor.getDouble(10));
                goods.setPrice(cursor.getDouble(11));
                goods.setSummary(cursor.getString(12));
                return goods;
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
