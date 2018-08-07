package com.sunwuyou.swymcx.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
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
    public Bitmap rotate(Bitmap arg9, int arg10) {
        float v3 = 2f;
        if(arg10 != 0 && arg9 != null) {
            Matrix v5 = new Matrix();
            v5.setRotate(((float)arg10), (((float)arg9.getWidth())) / v3, (((float)arg9.getHeight())) / v3);
            try {
                Bitmap v7 = Bitmap.createBitmap(arg9, 0, 0, arg9.getWidth(), arg9.getHeight(), v5, true);
                if(arg9 == v7) {
                    return arg9;
                }

                arg9.recycle();
                arg9 = v7;
            }
            catch(OutOfMemoryError v0) {
            }
        }

        return arg9;
    }
    public Bitmap reverseBitmap(Bitmap arg10) {
        float[] v8 = new float[]{-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};
        Matrix v5 = new Matrix();
        v5.setValues(v8);
        Bitmap v7 = Bitmap.createBitmap(arg10, 0, 0, arg10.getWidth(), arg10.getHeight(), v5, true);
        arg10.recycle();
        return v7;
    }
}
