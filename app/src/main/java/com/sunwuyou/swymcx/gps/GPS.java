package com.sunwuyou.swymcx.gps;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.sunwuyou.swymcx.utils.MLog;

/**
 * Created by admin
 * 2018/7/19.
 * content  定位工具类
 */

public class GPS implements AMapLocationListener {

    onLocationCallBack callBack;
    private AMapLocationClient mLocationClient = null;
    private String mAddress;
    private double mLongitude;
    private double mLatitude;
    private long mTagerTime;

    public GPS(Context context, onLocationCallBack callBack) {
        this.callBack = callBack;
        //声明AMapLocationClient类对象
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //声明AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取最近3s内精度最高的一次定位结果：
        mLocationOption.setOnceLocationLatest(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * //可在其中解析amapLocation获取相应内容。
     * amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
     * amapLocation.getLatitude();//获取纬度
     * amapLocation.getLongitude();//获取经度
     * amapLocation.getAccuracy();//获取精度信息
     * amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
     * amapLocation.getCountry();//国家信息
     * amapLocation.getProvince();//省信息
     * amapLocation.getCity();//城市信息
     * amapLocation.getDistrict();//城区信息
     * amapLocation.getStreet();//街道信息
     * amapLocation.getStreetNum();//街道门牌号信息
     * amapLocation.getCityCode();//城市编码
     * amapLocation.getAdCode();//地区编码
     * amapLocation.getAoiName();//获取当前定位点的AOI信息
     * amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
     * amapLocation.getFloor();//获取当前室内定位的楼层
     * amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
     * //获取定位时间
     * SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     * Date date = new Date(amapLocation.getTime());
     * df.format(date);
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode() == 0) {
            //地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
            mAddress = aMapLocation.getAddress();
            //获取经度
            mLongitude = aMapLocation.getLongitude();
            //获取纬度
            mLatitude = aMapLocation.getLatitude();
            //当前时间
            mTagerTime = aMapLocation.getTime();
            MLog.d("定位成功  获取经度:" + aMapLocation.getLongitude() + " 获取纬度:" + aMapLocation.getLatitude() + mAddress);
            callBack.onLocationChanged(aMapLocation);
        } else {
            //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
            MLog.d("location Error, ErrCode:"
                    + aMapLocation.getErrorCode() + ", errInfo:"
                    + aMapLocation.getErrorInfo());
        }
    }

    public void stop() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient = null;
        }
    }

    //地址
    public String getmAddress() {
        return mAddress == null ? "" : mAddress;
    }

    //当前时间
    public long getmTagerTime() {
        return mTagerTime;
    }

    //获取纬度
    public double getmLatitude() {
        return mLatitude;
    }

    //获取经度
    public double getmLongitude() {
        return mLongitude;
    }

    public void setCallBack(onLocationCallBack callBack) {
        this.callBack = callBack;
    }

    public interface onLocationCallBack {
        void onLocationChanged(AMapLocation aMapLocation);
    }
}
