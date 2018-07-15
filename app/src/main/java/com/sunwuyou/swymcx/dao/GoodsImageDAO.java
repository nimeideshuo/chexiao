package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.GoodsImage;
import com.sunwuyou.swymcx.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class GoodsImageDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper = new DBOpenHelper();

    // 查询 sz_goodsimage 数据
    public List<GoodsImage> getNoImage() {
        this.db = this.helper.getWritableDatabase();
        Cursor cursor = this.db.rawQuery("select * from sz_goodsimage where isgot = 0", null);
        ArrayList<GoodsImage> localArrayList = new ArrayList<GoodsImage>();
        try {
            while (cursor.moveToNext()) {
                GoodsImage localGoodsImage = new GoodsImage();
                localGoodsImage.setSerialid(cursor.getLong(cursor.getColumnIndex("serialid")));
                localGoodsImage.setGoodsid(cursor.getString(cursor.getColumnIndex("goodsid")));
                localGoodsImage.setImagepath(cursor.getString(cursor.getColumnIndex("imagepath")));
                if (cursor.getInt(cursor.getColumnIndex("isgot")) != 0) {
                    localGoodsImage.setIsgot(true);
                } else {
                    localGoodsImage.setIsgot(false);
                }
                localArrayList.add(localGoodsImage);
            }
            return localArrayList;
        } catch (Exception localException) {
            localException.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return null;
    }


    // 保存 Image
    public void saveImage(long serialid, String strImage, String filename) {
//        if (strImage != null && strImage.length() > 0) {
//            new BitmapUtils().savePicture(strImage, filename);
//            this.db = this.helper.getWritableDatabase();
//            this.db.execSQL("update sz_goodsimage set isgot = 1 where serialid = ?",
//                    new String[] { String.valueOf(serialid) });
//            if (this.db != null) {
//                this.db.close();
//            }
//        }
    }

}
