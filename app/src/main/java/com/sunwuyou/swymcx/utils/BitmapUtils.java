package com.sunwuyou.swymcx.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class BitmapUtils {

    public BitmapUtils() {
        File localFile = new File(getPicDir());
        if (!localFile.exists())
            localFile.mkdirs();
    }

    public String getPicDir() {
        try {
            String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
            return sd + "/" + "swy/swyxs" + "/goodspic";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean savePicture(Bitmap bitmap, String name) {
        File localFile = new File(getPicDir(), name);
        try {
            localFile.createNewFile();
            FileOutputStream os = new FileOutputStream(localFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
            os.flush();
            os.close();
            bitmap.recycle();
            System.gc();
            return true;
        } catch (Exception localException) {
        }
        return false;
    }
}
