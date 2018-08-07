package com.sunwuyou.swymcx.http;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author Administrator
 * @content
 * @date 2017/12/2
 */

public class LoginBean extends BaseBean implements Parcelable {


    public static final Creator<LoginBean> CREATOR = new Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel source) {
            return new LoginBean(source);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };
    /**
     * obj : {"status":1,"times":0,"token":"eyJhbGciOiJIUzUxMiJ9.eyJpZGVudGl0eSI6IjIzNWViYjliMDQ3ZDQxMjJhNjNmYmZkZjkwN2Y1MWZiIiwiaXNzIjoiaW1tb3J0YWwgaW50ZXJuYXRpb25hbCBob2xkaW5ncyIsImlhdCI6MTUxMzk0NjMyMywiZXhwIjoxNTEzOTQ4OTE1LCJzdWIiOiJqZWVxaW5Ac2VydmVyIn0.bTLnqb83p4qD-KlXGfoNpruv-NSfIks1ptlK1RxpEvKkQBurLf5K7QGhbx7ZQvhqh_ohh1_Vue-c2LUhoay1Lg"}
     */

    private ObjBean obj;

    public LoginBean() {
    }

    protected LoginBean(Parcel in) {
        this.obj = in.readParcelable(ObjBean.class.getClassLoader());
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.obj, flags);
    }

    public static class ObjBean implements Parcelable, Serializable {
        public static final Creator<ObjBean> CREATOR = new Creator<ObjBean>() {
            @Override
            public ObjBean createFromParcel(Parcel source) {
                return new ObjBean(source);
            }

            @Override
            public ObjBean[] newArray(int size) {
                return new ObjBean[size];
            }
        };
        /**
         * status : 1
         * times : 0
         * token : eyJhbGciOiJIUzUxMiJ9.eyJpZGVudGl0eSI6IjIzNWViYjliMDQ3ZDQxMjJhNjNmYmZkZjkwN2Y1MWZiIiwiaXNzIjoiaW1tb3J0YWwgaW50ZXJuYXRpb25hbCBob2xkaW5ncyIsImlhdCI6MTUxMzk0NjMyMywiZXhwIjoxNTEzOTQ4OTE1LCJzdWIiOiJqZWVxaW5Ac2VydmVyIn0.bTLnqb83p4qD-KlXGfoNpruv-NSfIks1ptlK1RxpEvKkQBurLf5K7QGhbx7ZQvhqh_ohh1_Vue-c2LUhoay1Lg
         */
        private String userId;
        private int status;
        private int times;
        private String token;
        private String iMusername = "321313";
        private String iMpassword = "321313";
        private int isBindUsername;
        private String mobile;
        private String email;
        private int rightId;
//        "easemob":1,
//                "":"17354109946",
//                "easemobPwd":"111111",
        private int easemob;

        public static Creator<ObjBean> getCREATOR() {
            return CREATOR;
        }

        public int getEasemob() {
            return easemob;
        }

        public void setEasemob(int easemob) {
            this.easemob = easemob;
        }

        public String getEasemobName() {
            return easemobName == null ? "" : easemobName;
        }

        public void setEasemobName(String easemobName) {
            this.easemobName = easemobName;
        }

        public String getEasemobPwd() {
            return easemobPwd == null ? "" : easemobPwd;
        }

        public void setEasemobPwd(String easemobPwd) {
            this.easemobPwd = easemobPwd;
        }

        private String easemobName;
        private String easemobPwd;
        public ObjBean() {
        }

        protected ObjBean(Parcel in) {
            this.status = in.readInt();
            this.times = in.readInt();
            this.token = in.readString();
            this.iMusername = in.readString();
            this.iMpassword = in.readString();
            this.userId = in.readString();
            this.easemob=in.readInt();
            this.easemobName=in.readString();
            this.easemobPwd=in.readString();
        }

        public int getRightId() {
            return rightId;
        }

        public void setRightId(int rightId) {
            this.rightId = rightId;
        }

        public int getIsBindUsername() {
            return isBindUsername;
        }

        public void setIsBindUsername(int isBindUsername) {
            this.isBindUsername = isBindUsername;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getiMpassword() {
            return iMpassword;
        }

        public void setiMpassword(String iMpasswor) {
            this.iMpassword = iMpasswor;
        }

        public String getiMusername() {

            return iMusername;
        }

        public void setiMusername(String iMusername) {
            this.iMusername = iMusername;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.status);
            dest.writeInt(this.times);
            dest.writeString(this.token);
            dest.writeString(this.iMusername);
            dest.writeString(this.iMpassword);
            dest.writeString(this.userId);
            dest.writeInt(this.easemob);
            dest.writeString(this.easemobName);
            dest.writeString(this.easemobPwd);
        }
    }
}
