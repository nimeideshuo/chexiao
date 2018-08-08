package com.sunwuyou.swymcx.bmob.service;

import android.provider.Settings;

import com.sunwuyou.swymcx.app.MyApplication;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.bmob.molder.BUser;
import com.sunwuyou.swymcx.model.AccountSetEntity;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.utils.MLog;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by admin
 * 2018/8/8.
 * content
 */

public class UserDao {

    private BmobCallBackListener listener;

    public void upPhone() {
        AccountSetEntity entity = SystemState.getAccountSet();
        User user = SystemState.getUser();
        if (entity == null || user == null) {
            return;
        }
        BUser bUser = new BUser();
        bUser.setDatabase(entity.getDatabase());
        bUser.setUserName(user.getName());
        bUser.deviceid = MyApplication.getInstance().getAndroidId();
        bUser.model = android.os.Build.MODEL;
        // 提交的时间
        bUser.registerDate = Utils.getDataTime();
        bUser.accountset = entity.getDatabase();
        bUser.code = MyApplication.getInstance().getUniqueCode();
        bUser.versionname = MyApplication.getInstance().getVersionName();
        bUser.userid = user.getId();
        bUser.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e == null) {
                    listener.onSuccess(null);
                } else {
                    listener.onError(s, e);
                }
            }
        });
    }

    public void querUser(final BmobCallBackListener listener) {
        BmobQuery<BUser> bmobQuery = new BmobQuery<BUser>();
        String deviceid = MyApplication.getInstance().getAndroidId();
        bmobQuery.addWhereEqualTo("deviceid", deviceid);
        bmobQuery.findObjects(new FindListener<BUser>() {
            @Override
            public void done(List<BUser> list, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e == null) {
                    BUser bUser = list.get(0);
                    MLog.d("查询到注册用户 " + bUser.getUserName() +  "  id>>"+bUser.getUserid()+"  code>>" + bUser.getCode());
                    MLog.d(bUser.toString());
                    listener.onSuccess(bUser);
                } else {
                    listener.onError(null, e);
                }
            }
        });
    }

    public void setListener(BmobCallBackListener listener) {
        this.listener = listener;
    }

    public interface BmobCallBackListener<T> {
        void onSuccess(T object);

        void onError(String s, BmobException e);
    }

}
