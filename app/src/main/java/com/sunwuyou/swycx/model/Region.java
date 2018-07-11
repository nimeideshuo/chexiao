package com.sunwuyou.swycx.model;

import java.io.Serializable;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class Region implements Serializable {
    private String id;
    private String name;
    private String pinyin;

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

    public String getPinyin() {
        return pinyin == null ? "" : pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

}
