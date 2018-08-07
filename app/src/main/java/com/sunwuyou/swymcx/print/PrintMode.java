package com.sunwuyou.swymcx.print;

import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.model.FieldSaleForPrint;
import com.sunwuyou.swymcx.model.FieldSaleItemForPrint;
import com.sunwuyou.swymcx.utils.SortMode;
import com.sunwuyou.swymcx.utils.SortUtils;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public abstract class PrintMode {
    public static final int ERROR = 1;
    public static  int SUCCESS;
    protected final String CHARSET;
    protected final String divider;
    protected final String line;
    protected final int swidth;
    protected final int width;
    protected List<FieldSaleItemForPrint> datainfo;
    protected FieldSaleForPrint docInfo;
    protected OutputStream out;
    protected List<HashMap<String, String>> pageinfo;
    protected String printermodel;
    byte[] l_line;
    byte[] r_line;
    private PrintCallback callback;
    private int index;

    public PrintMode(List<HashMap<String, String>> arg5) {
        super();
        this.CHARSET = "gbk";
        this.divider = "-------------------------------";
        this.index = 0;
        this.l_line = new byte[]{27, 45, 1};
        this.line = "                                ";
        this.swidth = 48;
        this.width = 32;
        byte[] v0 = new byte[3];
        v0[0] = 27;
        v0[1] = 45;
        this.r_line = v0;
        this.printermodel = new AccountPreference().getValue("printermodel_default", "epson");
        this.pageinfo = arg5;
    }

    public static PrintMode getPrintMode() {
        ModeHelper v1 = new ModeHelper();
        try {
            v1.parse();
        } catch (IOException v0) {
            v0.printStackTrace();
        }
        int v3 = v1.getBodytype();
        List<HashMap<String, String>> v2 = v1.getTextViews();
        if (v3 == 0) {
            return new PrintMode1(v2);
        }
        if (v3 == 1) {
            return new PrintMode2(v2);
        }
        return null;
    }

    private String getText(String arg7) {
        if (arg7.contains("{")) {
            String v0 = arg7.substring(arg7.indexOf("{") + 1, arg7.indexOf("}"));
            String v1 = "--";
            System.out.println("v0>>" + v0);
            if ("单据类型".equals(v0)) {
                v1 = this.docInfo.getDoctype();
            } else if ("部门".equals(v0)) {
                v1 = this.docInfo.getDepartmentname();
            } else if ("客户名称".equals(v0)) {
                v1 = this.docInfo.getCustomername();
            } else if ("单号".equals(v0)) {
                v1 = this.docInfo.getShowid();
            } else if ("开单人".equals(v0)) {
                v1 = this.docInfo.getBuildername();
            } else if ("时间".equals(v0)) {
                v1 = this.docInfo.getBuildtime();
            } else if ("合计".equals(v0)) {
                v1 = this.docInfo.getSumamount();
                if ("0".equals(new AccountPreference().getValue("clearlastzero", "0"))) {
                    v1 = Utils.cutLastZero(v1);
                }
            } else if ("应收".equals(v0)) {
                v1 = this.docInfo.getReceivable();
                if ("0".equals(new AccountPreference().getValue("clearlastzero", "0"))) {
                    v1 = Utils.cutLastZero(v1);
                }
            } else if ("已收".equals(v0)) {
                v1 = this.docInfo.getReceived();
                if ("0".equals(new AccountPreference().getValue("clearlastzero", "0"))) {
                    v1 = Utils.cutLastZero(v1);
                }
            } else if ("数量".equals(v0)) {
                v1 = this.docInfo.getNum();
                if ("0".equals(new AccountPreference().getValue("clearlastzero", "0"))) {
                    v1 = Utils.cutLastZero(v1);
                }
            } else if ("优惠".equals(v0)) {
                v1 = this.docInfo.getPreference();
                if ("0".equals(new AccountPreference().getValue("clearlastzero", "0"))) {
                    v1 = Utils.cutLastZero(v1);
                }
            } else if ("公司名称".equals(v0)) {
                v1 = Utils.companyName;
            } else if ("备注".equals(v0)) {
                v1 = this.docInfo.getRemark();
            }

            if (TextUtils.isEmptyS(v1)) {
                v1 = "--";
            }

            arg7 = arg7.replace("{" + v0 + "}", v1);
        }

        return arg7;
    }

    protected void line_space(byte arg4) throws IOException {
        this.out.write(new byte[]{27, 49, arg4});
    }

    protected void normalText() throws IOException {
        this.out.write(new byte[]{27, 56, 2});
    }

    protected void print(String arg3) throws IOException {
        this.out.write(arg3.getBytes("gbk"));
    }

    protected abstract boolean print() throws IOException;

    public void printDoc() {
        SortUtils.sort(this.pageinfo, "margintop", SortMode.ASC);
        try {
            this.print();
            this.doCallBack(0);
        } catch (IOException v0) {
            v0.printStackTrace();
            this.doCallBack(1);
        }
    }

    protected void printFoot() throws IOException {
        int v1;
        for (v1 = 6; this.index < this.pageinfo.size(); ++v1) {
            HashMap<String, String> v0 = this.pageinfo.get(this.index);
            if (Integer.parseInt(v0.get("margintop")) / 50 > v1) {
                --this.index;
                this.enter();
            } else {
                this.printInfo(((HashMap) v0));
            }

            ++this.index;
        }
    }


    protected void printHead() throws IOException {
        int v0 = 1;
        int v2;
        for (v2 = 0; v2 < 6; ++v2) {
            if (this.index >= this.pageinfo.size()) {
                return;
            }

            HashMap<String, String> v1 = this.pageinfo.get(this.index);
            Integer.parseInt((v1).get("margintop"));
            if (Integer.parseInt((v1).get("margintop")) / 50 > v2) {
                --this.index;
                if (v0 != 0) {
                    this.enter();
                }
            } else {
                v0 = 0;
                this.printInfo(((HashMap) v1));
            }

            ++this.index;
        }
    }

    public void printInfo(HashMap arg6) throws IOException {
        String v3 = this.getText(arg6.get("text").toString());
        String v2 = arg6.get("marginleft").toString();
        String v0 = arg6.get("garity").toString();
        if (!TextUtils.isEmpty(v0)) {
            int v1 = Integer.parseInt(v0);
            if (v1 == -2) {
                this.print_center(v3);
            } else if (v1 == -1) {
                this.print_left(v3);
            } else if (v1 == -3) {
                this.print_right(v3);
            }

            this.enter();
        } else {
            this.space(Integer.parseInt(v2));
            this.println(v3);
        }
    }

    protected void print_base(int arg2, int arg3, String arg4) throws IOException {
        this.space(arg3 - arg4.getBytes("gbk").length - arg2);
        this.print(arg4);
    }

    protected int print_center(String arg5) throws IOException {
        int v1;
        byte[] v0;
        if ("epson".equals(this.printermodel)) {
            v0 = new byte[]{27, 97, 1};
            this.out.write(v0);
            this.print(arg5);
            v1 = v0.length;
        } else {
            v0 = arg5.getBytes("gbk");
            this.space((32 - v0.length) / 2);
            this.print(arg5);
            v1 = v0.length;
        }

        return v1;
    }

    protected int print_left(String arg5) throws IOException {
        int v2;
        if ("epson".equals(this.printermodel)) {
            byte[] v1 = new byte[3];
            v1[0] = 27;
            v1[1] = 97;
            this.out.write(v1);
            this.print(arg5);
            v2 = v1.length;
        } else {
            byte[] v0 = arg5.getBytes("gbk");
            this.print(arg5);
            v2 = v0.length;
        }

        return v2;
    }

    protected void print_right(String arg3) throws IOException {
        if ("epson".equals(this.printermodel)) {
            this.out.write(new byte[]{27, 97, 2});
            this.print(arg3);
        } else {
            this.space(32 - arg3.getBytes("gbk").length);
            this.print(arg3);
        }
    }

    protected void println(String arg3) throws IOException {
        this.out.write(arg3.getBytes("gbk"));
        this.enter();
    }

    public void setCallback(PrintCallback arg1) {
        this.callback = arg1;
    }

    public void setDatainfo(List arg1) {
        this.datainfo = arg1;
    }

    public void setDocInfo(FieldSaleForPrint arg1) {
        this.docInfo = arg1;
    }

    public void setOutputStream(OutputStream arg1) {
        this.out = arg1;
    }

    protected void smallText() throws IOException {
        this.out.write(new byte[]{27, 56, 2});
    }


    private void doCallBack(int arg2) {
        if (this.callback != null) {
            this.callback.printOver(arg2);
        }
    }

    protected void enter() throws IOException {
        this.out.write(10);
    }

    protected void flush() throws IOException {
        this.enter();
        this.out.flush();
    }

    protected void space(int arg6) throws IOException {
        if ("epson".equals(this.printermodel)) {
            String v2 = "";
            int v0;
            for (v0 = 0; v0 < arg6; ++v0) {
                v2 = String.valueOf(String.valueOf(v2)) + " ";
            }

            this.out.write(v2.getBytes());
        } else {
            byte[] v1 = new byte[4];
            v1[0] = 27;
            v1[1] = 102;
            v1[3] = ((byte) arg6);
            this.out.write(v1);
        }
    }

    protected void tear_off() throws IOException {
        int v0;
        for (v0 = 0; v0 < 3; ++v0) {
            this.enter();
        }
    }

    protected void under_line(String arg3) throws IOException {
        this.out.write(this.l_line);
        this.print(arg3);
        this.out.write(this.r_line);

    }

    public interface PrintCallback {
        void printOver(int arg1);
    }
}
