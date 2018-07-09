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
import android.widget.EditText;
import android.widget.Toast;

import me.drakeet.support.toast.BadTokenListener;
import me.drakeet.support.toast.ToastCompat;

/**
 * 作者： YaoChen
 * 日期： 2017/8/12 10:29
 * 描述： 基础类的封装
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "ceshi";
    //音乐是否播放完毕
    private boolean isPlayMusicOk = false;
    //音乐播放完毕回调
    MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            isPlayMusicOk = false;
            VibratorUtil.Vibrate(BaseActivity.this, 100);   //震动100ms          }
        }

    };
    private EditText editText;
    //    private Activity activity;
    private AlertDialog showService;
    //activity  是否可见
    private boolean isVisible;
    //服务器Ip
    private String[] serverIpAndName = {"apis.no1im.com    线上服务器", "api.no1im.com    线上服务器", "192.168.60.137:9000    王金飞", "192.168.60.202:9010    测试服务器", "192.168.60.203:9010    测试组专用", "192.168.60.58:9010    潘阳", "192.168.60.65:9    孟朋朋", "192.168.60.116:9010    姚文涛", "192.168.60.62:9010    杨童", "192.168.60.88:9010    邹强", "192.168.60.60:9010    朱涛", "192.168.60.83:9010    曹飞", "192.168.60.39:9010    高东"};
    private String[] serverIp = {"apis.no1im.com", "api.no1im.com", "192.168.60.137:9000", "192.168.60.202:9010", "192.168.60.203:9010", "192.168.60.58:9010", "192.168.60.65:9:9010", "192.168.60.116:9010", "192.168.60.62:9010", "192.168.60.88:9010", "192.168.60.60:9010", "192.168.60.83:9010", "192.168.60.39:9010"};
    SensorEventListener eventListener = new SensorEventListener() {


        @Override
        public void onSensorChanged(SensorEvent event) {
            int sensorType = event.sensor.getType();
            //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
            float[] values = event.values;

            float x = values[0];
            float y = values[1];
            float z = values[2];
//            Log.i(TAG, "x:" + x + "y:" + y + "z:" + z);
            if (sensorType == Sensor.TYPE_ACCELEROMETER) {
                int value = 40;//摇一摇阀值,不同手机能达到的最大值不同,如某品牌手机只能达到20
                if (x >= value || x <= -value || y >= value || y <= -value || z >= value || z <= -value) {
                    if (!isPlayMusicOk) {
                        isPlayMusicOk = true;
                        //播放动画，更新界面，并进行对应的业务操作
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplication(), R.raw.wxyaoyiyao);
                        mediaPlayer.setOnCompletionListener(completionListener);
                        mediaPlayer.start();
                        if ( BaseActivity.this.isDestroyed() || BaseActivity.this.isFinishing()) {
                            return;
                        }
                        if (showService != null && showService.isShowing()) {
                            showService.dismiss();
                        }
                        if (!isVisible) {
                            return;
                        }
                        Log.i(TAG, "检测到摇动");

                        showService = new AlertDialog.Builder(BaseActivity.this)
                                .setItems(serverIpAndName, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        //0 线上服务器地址  后面是测试地址
                                        String ip = which == 0 ? "http://" + serverIp[which] : "http://" + serverIp[which];
                                        //保存服务器地址
                                        SPUtils.put(getApplication(), "serviceIp", ip);
                                        BaseUrl.setUrl(ip);
                                        toast("更换服务器地址成功!请退出应用后重新登录!");
                                    }
                                })
                                .setPositiveButton("没有你的地址?点击此处输入!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        showDialogInputServiceIp();
                                    }
                                })
                                .show();

                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    private SensorManager sensorManager;

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
        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //去掉摇一摇
        sensorManager.registerListener(eventListener, sensor, 1);
//        activity = this;
        isVisible = true;
    }

    /**
     * 显示输入的dialog
     */
    private void showDialogInputServiceIp() {
        editText = new EditText(BaseActivity.this);
        editText.setText(serverIp[3]);
        new AlertDialog.Builder(BaseActivity.this)
                .setTitle("请输入服务器地址!")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ip = editText.getText().toString();
                        if (ip.isEmpty()) {
                            toast("你还没有输入服务器IP地址!");
                            return;
                        }
                        //判断是否是正确的服务器ip
                        int length = ip.length() - ip.replace(".", "").length();
                        if (length != 3) {
                            toast("请输入正确的服务器IP地址");
                            return;
                        }
                        SPUtils.put(getApplication(), "serviceIp", "http://" + ip);
                        BaseUrl.setUrl("http://" + ip);
                        toast("更换服务器地址成功!请退出应用后重新登录!");

                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
        sensorManager.unregisterListener(eventListener);
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
