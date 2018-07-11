package com.sunwuyou.swycx.model;

import java.io.Serializable;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class PayType implements Serializable {
    public String id;
    public String name;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
