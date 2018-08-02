package com.sunwuyou.swymcx.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

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

    public String bitmaptoString(Bitmap arg6) {
        ByteArrayOutputStream v0 = new ByteArrayOutputStream();
        arg6.compress(Bitmap.CompressFormat.PNG, 100, v0);
        return Base64.encodeToString(v0.toByteArray(), 0);
    }
}
