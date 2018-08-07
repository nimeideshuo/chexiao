package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/11.
 * content
 */

public class TerminalEntity {
    private String Identifier;
    private boolean IsPC;
    private String LoginName;
    private String Mac;
    private String Model;
    private String Owner;
    private String RegistrationCode;
    private String VersionKey;

    public TerminalEntity() {
    }

    public TerminalEntity(String Identifier, String VersionKey) {
        this.Identifier = Identifier;
        this.IsPC = false;
        this.VersionKey = VersionKey;
    }

    public String getIdentifier() {
        return Identifier == null ? "" : Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    public boolean isPC() {
        return IsPC;
    }

    public void setPC(boolean PC) {
        IsPC = PC;
    }

    public String getLoginName() {
        return LoginName == null ? "" : LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getMac() {
        return Mac == null ? "" : Mac;
    }

    public void setMac(String mac) {
        Mac = mac;
    }

    public String getModel() {
        return Model == null ? "" : Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getOwner() {
        return Owner == null ? "" : Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getRegistrationCode() {
        return RegistrationCode == null ? "" : RegistrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        RegistrationCode = registrationCode;
    }

    public String getVersionKey() {
        return VersionKey == null ? "" : VersionKey;
    }

    public void setVersionKey(String versionKey) {
        VersionKey = versionKey;
    }

}
