package com.sunwuyou.swymcx.bmob.molder;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin
 * 2018/8/8.
 * content
 */

public class BUser extends BmobObject {
    public String accountset;
    public String code;
    public String database;
    public String deviceid;
    public String memory;
    public String message;
    public String registerDate;
    public String model;

    public Integer sleep;
    public Integer state;// 0 默认 1, 警告，2 停止,3 退出
    public String userName;

    public String userid;
    public String versionname;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getSleep() {
        return sleep;
    }

    public void setSleep(Integer sleep) {
        this.sleep = sleep;
    }

    public Number getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getAccountset() {
        return accountset;
    }

    public void setAccountset(String accountset) {
        this.accountset = accountset;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BUser{" +
                "accountset='" + accountset + '\'' +
                ", code='" + code + '\'' +
                ", database='" + database + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", memory='" + memory + '\'' +
                ", message='" + message + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", model='" + model + '\'' +
                ", sleep=" + sleep +
                ", state=" + state +
                ", userName='" + userName + '\'' +
                ", userid='" + userid + '\'' +
                ", versionname='" + versionname + '\'' +
                '}';
    }
}
