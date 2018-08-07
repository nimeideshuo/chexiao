package com.sunwuyou.swymcx.ui.field;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.FieldSaleImageDAO;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSaleImage;
import com.sunwuyou.swymcx.utils.BitmapUtils;
import com.sunwuyou.swymcx.utils.FileUtils;
import com.sunwuyou.swymcx.utils.MLog;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by admin
 * 2018/8/3.
 * content
 */

public class CameraActivity extends BaseHeadActivity {

    private FieldSale fieldSale;
    private FrameLayout frameLayout;
    private int type;
    private Button btnTakePicture;
    private FileUtils fileUtils;
    private Picture mPicture;
    private CameraPreview cameraPreview;

    @Override
    public int getLayoutID() {
        return R.layout.act_field_camera;
    }

    @Override
    public void initView() {
        fieldSale = new FieldSaleDAO().getFieldsale(this.getIntent().getLongExtra("fieldsaleid", -1));
        if (this.getIntent().getBooleanExtra("frompicture", false)) {
            this.getWindow().getAttributes().windowAnimations = 2131492896;
        }
        frameLayout = this.findViewById(R.id.camera_preview);
        btnTakePicture = this.findViewById(R.id.btnTakePicture);
        this.btnTakePicture.setOnClickListener(this.openCameraListener);
        type = this.getIntent().getIntExtra("type", 0);
    }

    private View.OnClickListener openCameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Camera.ShutterCallback v2 = null;
            if (CameraActivity.this.btnTakePicture.isEnabled()) {
                CameraActivity.this.btnTakePicture.setEnabled(false);
                if (mCamera == null) {
                    CameraActivity v1 = CameraActivity.this;
                    int v2_1 = CameraActivity.this.type;
                    int v0 = CameraActivity.this.type == 0 ? backCameraid : frontCameraid;
                    v1.openCamera(v2_1, v0);
                } else {
                    mCamera.takePicture(v2, ((Camera.PictureCallback) v2), mPicture);
                }
            }
        }
    };
    private Camera mCamera;

    protected void onResume() {
        super.onResume();
        if (this.mCamera == null) {
            int v0 = Camera.getNumberOfCameras();
            Camera.CameraInfo v2 = new Camera.CameraInfo();
            int v1;
            for (v1 = 0; v1 < v0; ++v1) {
                Camera.getCameraInfo(v1, v2);
                if (v2.facing == 0) {
                    this.backCameraid = v1;
                } else if (v2.facing == 1) {
                    this.frontCameraid = v1;
                }
            }

            if (this.backCameraid != -1 && this.type == 0) {
                this.openCamera(0, this.backCameraid);
                return;
            }

            this.openCamera(1, this.frontCameraid);
        }
    }

    protected void onStop() {
        super.onStop();
        this.closeCamera();
    }

    private void closeCamera() {
        Camera.PreviewCallback v1 = null;
        if (this.mCamera != null) {
            this.mCamera.setPreviewCallback(v1);
            this.mCamera.stopPreview();
            this.mCamera.release();
            this.mCamera = ((Camera) v1);
            this.frameLayout.removeAllViews();
        }
    }

    private void openCamera(int arg3, int arg4) {
        this.mCamera = this.getCamera(arg4);
        if (this.mCamera != null) {
            this.type = arg3;
            cameraPreview = new CameraPreview(this, this.mCamera);
            this.frameLayout.addView(this.cameraPreview);
        } else if (arg3 == 0) {
            PDH.showFail("后置相机无法打开");
        } else {
            PDH.showFail("前置相机无法打开");
        }
    }

    private Camera getCamera(int arg4) {
        try {
            this.mCamera = Camera.open(arg4);
            this.mCamera.setDisplayOrientation(this.getCameraDisplayOrientation(arg4, this.mCamera));
        } catch (Exception v0) {
            v0.printStackTrace();
        }
        return this.mCamera;
    }

    private void savePicture(final Bitmap bmp) {
        new Thread() {
            public void run() {
                String v5 = getFileUtils().getPicDir();
                if (!TextUtils.isEmptyS(v5)) {
                    File v1 = new File(v5);
                    if (!v1.exists()) {
                        v1.mkdir();
                    }

                    String v6 = String.valueOf(getPicName()) + ".jpg";
                    File v4 = new File(v1, v6);
                    try {
                        v4.createNewFile();
                        FileOutputStream v2 = new FileOutputStream(v4);
                        bmp.compress(Bitmap.CompressFormat.JPEG, 100, v2);
                        v2.flush();
                        v2.close();
                        bmp.recycle();
                        System.gc();
                        FieldSaleImage v3 = new FieldSaleImage();
                        v3.setImagepath(v6);
                        v3.setIssignature(false);
                        v3.setRemark("");
                        v3.setFieldsaleid(fieldSale.getId());
                        new FieldSaleImageDAO().addJobImage(v3);
                    } catch (Exception v0) {
                        v0.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private int frontCameraid;
    private int backCameraid;

    @Override
    public void initData() {
        this.frontCameraid = -1;
        this.backCameraid = -1;
        this.type = 0;
        mPicture = new Picture(this);
        this.openCameraListener = new CameraListener(this);
    }

    class CameraListener implements View.OnClickListener {
        CameraListener(CameraActivity arg1) {
        }

        public void onClick(View arg4) {
            Camera.ShutterCallback v2 = null;
            if (CameraActivity.this.btnTakePicture.isEnabled()) {
                CameraActivity.this.btnTakePicture.setEnabled(false);
                if (CameraActivity.this.mCamera == null) {
                    CameraActivity v1 = CameraActivity.this;
                    int v2_1 = CameraActivity.this.type;
                    int v0 = CameraActivity.this.type == 0 ? CameraActivity.this.backCameraid : CameraActivity.this.frontCameraid;
                    v1.openCamera(v2_1, v0);
                } else {
                    mCamera.takePicture(v2, ((Camera.PictureCallback) v2), mPicture);
                }
            }
        }
    }

    class Picture implements Camera.PictureCallback {
        Picture(CameraActivity arg1) {
        }

        public void onPictureTaken(byte[] arg11, Camera arg12) {
            BitmapFactory.Options v3 = new BitmapFactory.Options();
            v3.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(arg11, 0, arg11.length, v3);
            v3.inSampleSize = (v3.outHeight / 600 + v3.outWidth / 800) / 2;
            v3.inJustDecodeBounds = false;
            Bitmap v0 = BitmapFactory.decodeByteArray(arg11, 0, arg11.length, v3);
            BitmapUtils v1 = new BitmapUtils();
            int v2 = type == 0 ? 90 : getCameraDisplayOrientation(frontCameraid, mCamera) - 180;
            v0 = v1.rotate(v0, v2);
            if (type == 1) {
                v0 = v1.reverseBitmap(v0);
            }
            savePicture(setWaterMark(v0));
            arg12.startPreview();
            btnTakePicture.setEnabled(true);
        }
    }

    private int getCameraDisplayOrientation(int arg7, Camera arg8) {
        Camera.CameraInfo v1 = new Camera.CameraInfo();
        Camera.getCameraInfo(arg7, v1);
        int v0 = 0;
        switch (this.getWindowManager().getDefaultDisplay().getRotation()) {
            case 0: {
                v0 = 0;
                break;
            }
            case 1: {
                v0 = 90;
                break;
            }
            case 2: {
                v0 = 180;
                break;
            }
            case 3: {
                v0 = 270;
                break;
            }
        }

        int v2 = v1.facing == 1 ? (360 - (v1.orientation + v0) % 360) % 360 : (v1.orientation - v0 + 360) % 360;
        return v2;
    }

    private FileUtils getFileUtils() {
        if (this.fileUtils == null) {
            fileUtils = new FileUtils();
        }

        return this.fileUtils;
    }

    public String getPicName() {
        return String.valueOf(Utils.getTimeStamp()) + "(" + this.fieldSale.getCustomername() + ")";
    }

    private Bitmap setWaterMark(Bitmap arg10) {
        Canvas v0 = new Canvas(arg10);
        Paint v4 = new Paint(257);
        v4.setTextSize(24f);
        try {
            int v5 = arg10.getWidth();
            int v3 = arg10.getHeight();
            MLog.d("---------------------照片添加水印---------------------\n宽度：" + arg10.getWidth() + "， 长度：" + arg10.getHeight());
            v4.setColor(-1996488705);
            v0.drawRect(new Rect(0, v3 - 65, v5, v3), v4);
            v4.setColor(-16777216);
            v0.drawText(this.fieldSale.getCustomername(), 20f, ((float) (v3 - 40)), v4);
            v0.drawText(Utils.getTimeStamp("yyyy/MM/dd HH:mm"), 20f, ((float) (v3 - 10)), v4);
        } catch (Exception v2) {
            v2.printStackTrace();
        }

        return arg10;
    }

    public void setActionBarText() {
        setTitle("拍照");
    }
}
