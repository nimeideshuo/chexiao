package com.immo.libcomm.base;

import com.immo.libcomm.R;
import com.immo.libcomm.http.BaseUrl;
import com.immo.libcomm.utils.SPUtils;
import com.immo.libcomm.utils.VibratorUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import me.drakeet.support.toast.BadTokenListener;
import me.drakeet.support.toast.ToastCompat;

/**
 * 作者：
 * 日期： 2017/8/12 10:29
 * 描述： 基础类的封装
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * toast的提示的封装
     *
     * @param s 提示内容
     */
    public void toast(String s) {

        ToastCompat.makeText(this, s, Toast.LENGTH_SHORT)
                .setBadTokenListener(new BadTokenListener() {
                    @Override
                    public void onBadTokenCaught(@NonNull Toast toast) {

                    }
                }).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        initView();
        initData();
    }
    public abstract View getLayoutID();
    public abstract void initView();
    public abstract void initData();


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
