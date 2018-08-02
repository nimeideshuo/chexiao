package com.sunwuyou.swymcx.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;

/**
 * Created by liupiao on
 * 2018/8/2.
 * content
 */
public class FileUtils {
    private String[] titles;

    public FileUtils() {
        super();
        this.titles = new String[]{"商品编号", "商品名称", "商品规格", "购买单价", "购买数量", "退货单价", "退货数量", "换货数量", "赠送数量", "小计"};
    }

    private String dealNumPrice(String arg2) {
        if (arg2 == null || ("0.0".equals(arg2)) || (arg2.startsWith("0.0"))) {
            arg2 = "--";
        }

        return arg2;
    }

    public boolean deletePic(String arg4) {
        File v0 = new File(String.valueOf(this.getPicDir()) + "/" + arg4);
        boolean v1 = v0.exists() ? v0.delete() : true;
        return v1;
    }

    public File findPicture(String arg4) {
        File v0 = new File(String.valueOf(this.getPicDir()) + "/" + arg4);
        if (!v0.exists()) {
            v0 = null;
        }

        return v0;
    }

    public String getPic(String arg4) {
        String v1;
        Bitmap v0 = this.getPictureBitmap(arg4);
        if (v0 == null) {
            v1 = "";
        } else {
            v1 = new BitmapUtils().bitmaptoString(v0);
            v0.recycle();
        }

        return v1;
    }

    public String getPicDir() {
        String v1;
        try {
            v1 = Environment.getExternalStorageDirectory().getAbsolutePath();
        } catch (Exception v0) {
            return null;
        }
        return String.valueOf(v1) + "/" + "swy/swycx" + "/visitpic";
    }

    public Bitmap getPictureBitmap(String arg3) {
        return BitmapFactory.decodeFile(String.valueOf(this.getPicDir()) + "/" + arg3);
    }
}
