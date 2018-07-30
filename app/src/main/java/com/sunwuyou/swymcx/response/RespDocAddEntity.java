package com.sunwuyou.swymcx.response;

/**
 * Created by liupiao on
 * 2018/7/30.
 * content
 */
public class RespDocAddEntity {
    private long id;
    private String info;
    private boolean isexcuteposted;
    private boolean issubmitsuccess;
    public RespDocAddEntity() {
        super();
    }

    public RespDocAddEntity(long arg1, boolean arg3, String arg4) {
        super();
        this.id = arg1;
        this.isexcuteposted = arg3;
        this.info = arg4;
    }

     public long getId() {
        return this.id;
    }

     public String getInfo() {
        return this.info;
    }

     public boolean getIsExcutePosted() {
        return this.isexcuteposted;
    }

     public boolean getIsSubmitSuccess() {
        return this.issubmitsuccess;
    }

     public void setId(long arg1) {
        this.id = arg1;
    }

     public void setInfo(String arg1) {
        this.info = arg1;
    }

     public void setIsExcutePosted(boolean arg1) {
        this.isexcuteposted = arg1;
    }

     public void setIsSubmitSuccess(boolean arg1) {
        this.issubmitsuccess = arg1;
    }

}
