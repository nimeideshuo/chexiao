package com.sunwuyou.swymcx.http;

/**
 * @author Administrator
 * @content 基类
 * @date 2017/11/10
 */

public class BaseBean {
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * code :
     * data : 接口返回数据
     * message : 接口信息
     */

    private int state;
    private String msg;

}
