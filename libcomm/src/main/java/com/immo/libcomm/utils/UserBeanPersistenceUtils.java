package com.immo.libcomm.utils;

import com.immo.libcomm.R;
import com.immo.libcomm.http.LoginBean;

import android.content.Context;
import android.widget.Toast;


/**
 * 作者： YaoChen
 * 日期： 2017/7/26 13:37
 * 描述： 登录数据的处理
 */
public class UserBeanPersistenceUtils {
    public final static String LOGIN_DATA="login_yimai.txt";
    private static LoginBean.ObjBean userBean;

    public static void write(LoginBean.ObjBean userData) {
        if (userData != null) {
            userBean = userData;
        }
        DataFilePersistenceUtils.write(LOGIN_DATA, userData);
    }
    public static String getUserId(Context context){
        LoginBean.ObjBean objBean = UserBeanPersistenceUtils.readGoLoad(context, false);
        String userId = "";
        if (objBean != null&&objBean.getUserId()!=null) {
            userId = objBean.getUserId();
        }
        return userId;
    }

    /**
     * 读取用户信息，失败跳到登录页面
     *
     * @param context
     * @return
     */
    public static LoginBean.ObjBean readGoLoad(Context context,boolean jump) {
        if (userBean == null) {

            Object obj = DataFilePersistenceUtils.readObj(LOGIN_DATA);
            if (obj == null) {
                if (jump==true){
                    Toast.makeText(context, context.getString(R.string.please_complete_logo),Toast.LENGTH_SHORT).show();
                }
                return null;
            }
            if (!(obj instanceof LoginBean.ObjBean)) {
                if (jump==true){
                    Toast.makeText(context,context.getString(R.string.please_complete_logo),Toast.LENGTH_SHORT).show();
                }
                return null;
            }
            userBean = (LoginBean.ObjBean) obj;
        }
        return userBean;

    }

    public static void del() {
        DataFilePersistenceUtils.delFile(LOGIN_DATA);
    }

}
