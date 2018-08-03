package com.sunwuyou.swymcx.model;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class Unit {
    private String id;
    private String name;

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
