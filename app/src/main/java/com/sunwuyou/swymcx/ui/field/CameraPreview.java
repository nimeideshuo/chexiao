package com.sunwuyou.swymcx.ui.field;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.hardware.Camera;
import com.sunwuyou.swymcx.utils.MLog;

import java.io.IOException;

/**
 * Created by admin
 * 2018/8/3.
 * content
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private String TAG;
    private Camera mCamera;
    private final SurfaceHolder mHolder;

    public CameraPreview(Context context, Camera mCamera) {
        super(context);
        this.mCamera = mCamera;
        mHolder = this.getHolder();
        this.mHolder.addCallback(this);
        this.TAG = "TAG";
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            this.mCamera.setPreviewDisplay(holder);
            this.mCamera.startPreview();
        }
        catch(IOException v0) {
            MLog.d("Error setting camera preview: " + v0.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (this.mHolder.getSurface() != null) {
            try {
                this.mCamera.stopPreview();
            } catch (Exception v1) {
            }

            try {
                this.mCamera.setPreviewDisplay(this.mHolder);
                this.mCamera.startPreview();
            } catch (Exception v0) {
                MLog.d(this.TAG, "Error starting camera preview: " + v0.getMessage());
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
