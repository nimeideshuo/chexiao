package com.sunwuyou.swymcx.request;

/**
 * Created by liupiao on
 * 2018/8/2.
 * content
 */
public class ReqVstAddVisitCustomerJobImage {
    private String imagefile;
    private String imagepath;
    private boolean issignature;
    private String remark;
    private long visitjobid;

    public ReqVstAddVisitCustomerJobImage() {
        super();
    }

    public ReqVstAddVisitCustomerJobImage(long arg1, boolean arg3, String arg4, String arg5, String arg6) {
        super();
        this.visitjobid = arg1;
        this.issignature = arg3;
        this.imagepath = arg4;
        this.remark = arg5;
        this.imagefile = arg6;
    }

    public String getImageFile() {
        return this.imagefile;
    }

    public String getImagePath() {
        return this.imagepath;
    }

    public boolean getIsSignature() {
        return this.issignature;
    }

    public String getRemark() {
        return this.remark;
    }

    public long getVisitJobId() {
        return this.visitjobid;
    }

    public void setImageFile(String arg1) {
        this.imagefile = arg1;
    }

    public void setImagePath(String arg1) {
        this.imagepath = arg1;
    }

    public void setIsSignature(boolean arg1) {
        this.issignature = arg1;
    }

    public void setRemark(String arg1) {
        this.remark = arg1;
    }

    public void setVisitJobId(long arg1) {
        this.visitjobid = arg1;
    }
}
