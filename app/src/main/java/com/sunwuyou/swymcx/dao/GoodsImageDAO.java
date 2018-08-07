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
    private Cursor cursor;

    // 查询 sz_goodsimage 数据
    public List<GoodsImage> getNoImage() {
        this.db = this.helper.getWritableDatabase();
        Cursor cursor = this.db.rawQuery("select * from sz_goodsimage where isgot = 0", null);
        ArrayList<GoodsImage> localArrayList = new ArrayList<GoodsImage>();
        try {
            while (cursor.moveToNext()) {
                GoodsImage localGoodsImage = new GoodsImage();
                localGoodsImage.setSerialid(cursor.getLong(cursor.getColumnIndex("serialid")));
                localGoodsImage.setGoodsId(cursor.getString(cursor.getColumnIndex("goodsid")));
                localGoodsImage.setImagePath(cursor.getString(cursor.getColumnIndex("imagepath")));
                if (cursor.getInt(cursor.getColumnIndex("isgot")) != 0) {
                    localGoodsImage.setIsGot(true);
                } else {
                    localGoodsImage.setIsGot(false);
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

    public List<GoodsImage> get(String arg9) {
        this.db = this.helper.getWritableDatabase();
        try {
            cursor = this.db.rawQuery("select serialid, goodsid, imagepath from sz_goodsimage where isgot = 1 and goodsid = ?", new String[]{arg9});
            ArrayList<GoodsImage> v3 = new ArrayList<>();
            while (cursor.moveToNext()) {
                GoodsImage v2 = new GoodsImage();
                v2.setSerialid(cursor.getLong(0));
                v2.setGoodsId(cursor.getString(1));
                v2.setImagePath(cursor.getString(2));
                v2.setIsGot(true);
                v3.add(v2);
            }
            return v3;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return new ArrayList();
    }


}
