package com.sunwuyou.swymcx.utils;

import android.content.Context;
import android.util.TypedValue;

import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.dao.PricesystemDAO;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class Utils {
    public static int DEFAULT_OutDocUNIT;
    public static int DEFAULT_TransferDocUNIT;
    public static int int_subtotal_change;
    public static boolean isAutoChangeGoodsDiscountAfterDoc;
    public static String strCancelWarehouse;
    public static int DEFAULT_PRICESYSTEM;
    public static String CUSTOMER_CHECK_SELECT;
    public static int DEFAULT_UNIT;
    public static String GOODS_CHECK_SELECT;
    public static boolean IS_ALLOWEDMODIFY_PRINTED;
    public static int NUMBER_DEC = 2;
    public static int PRICE_DEC_NUM = 2;
    public static int RECEIVE_DEC_NUM;
    public static int SUBTOTAL_DEC_NUM = 2;
    public static int TRANSFER_DEFAULT_UNIT;
    public static boolean USE_CUSTOMER_PRICE;
    public static String companyName;
    public static int intGenerateBatch;
    public static boolean isDownloadCustomerByVisitLine;
    public static boolean isTuiHuanHuoSamePrice;
    public static boolean isUploadLocation;
    public static boolean isUseCurrentPrice;
    public static String pricesystemid;
    public static String strBatchPrefix;
    public static String strBatchSuffix;
    static AccountPreference ap = new AccountPreference();
    private static List<HashMap<String, String>> listbiz;

    static {
        RECEIVE_DEC_NUM = 2;
        DEFAULT_UNIT = 0;
        TRANSFER_DEFAULT_UNIT = 0;
        USE_CUSTOMER_PRICE = false;
        isUseCurrentPrice = false;
        isTuiHuanHuoSamePrice = false;
        IS_ALLOWEDMODIFY_PRINTED = true;
        isUploadLocation = false;
        isDownloadCustomerByVisitLine = false;
        companyName = "";
        GOODS_CHECK_SELECT = "id,pinyin,name,barcode";
        CUSTOMER_CHECK_SELECT = "pinyin";
        pricesystemid = "";
        init();
    }

    // 删除末尾的 0 加上 .00
    public static String cutLastZero(String paramString) {
        if (paramString.contains(".")) {
            String[] split = paramString.split("\\.");
            return split[0];
        }
        return null;
    }

    public static String cleanZero(double douStr) {
        return douStr % 1 == 0 ? cutLastZero("" + douStr) : "" + douStr;
    }

    public static int dp2px(Context paramContext, int paramInt) {
        return (int) TypedValue.applyDimension(1, paramInt, paramContext.getResources().getDisplayMetrics());
    }

    public static boolean equals(double paramDouble1, double paramDouble2) {
        return (normalizeDouble(paramDouble1 - paramDouble2) != 0.0D);
    }

    public static boolean equalsZero(double paramDouble) {
        return (normalizeDouble(paramDouble) != 0.0D);
    }

    public static String formatDate(long paramLong) {
        return formatDate(paramLong, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(long data, String dateformat) {
        Date localDate = new Date(data);
        SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
        String str = sdf.format(localDate);
        return str;
    }

    public static String formatDate(String dataTime, String dateFormat) {
        try {
            Date localDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse(dataTime);
            String localString = new SimpleDateFormat(dateFormat, Locale.CHINA).format(localDate);
            return localString;
        } catch (Exception localException) {
        }
        return "";
    }

    public static String formatCalendarDataTime(Date data, String dateFormat) {
        String format = "";
        try {

            SimpleDateFormat dateFormatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            format = dateFormatTime.format(data);
        } catch (Exception e) {
        }
        return format;

    }

    private static String formatDouble(double value, int paramInt) {
        return formatDouble(value, new String[]{"0", "0.0", "0.00", "0.000", "0.0000"}[paramInt]);
    }

    private static String formatDouble(double value, String paramString) {
        return new DecimalFormat(paramString).format(value);
    }

    public static String formatDouble(String paramString, int paramInt) {
        return formatDouble(getDouble(paramString).doubleValue(), paramInt);
    }

    public static String generateBatch(long paramLong) {
        if (paramLong > 0L)
            switch (intGenerateBatch) {
                default:
                    return null;
                case 1:
                    return strBatchPrefix + new SimpleDateFormat("yyyyMMdd").format(Long.valueOf(paramLong))
                            + strBatchSuffix;
                case 2:
            }
        return strBatchPrefix + new SimpleDateFormat("yyMMdd").format(Long.valueOf(paramLong)) + strBatchSuffix;
    }

    public static long getCurrentTime(boolean paramBoolean) {
        return new Date().getTime();
    }

    public static Double getDouble(String douStr) {
        if (douStr == null) {
            return Double.valueOf(0.0D);
        }
        if (douStr.length() == 0) {
            return Double.valueOf(0.0D);
        }
        try {
            return Double.parseDouble(douStr);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }

    }


    public static Integer getInteger(String paramString) {
        if (paramString == null)
            return Integer.valueOf(0);
        try {
            Integer localInteger = Integer.valueOf(Integer.parseInt(paramString));
            return localInteger;
        } catch (NumberFormatException localNumberFormatException) {
        }
        return Integer.valueOf(0);
    }

    public static String getNumber(double paramDouble) {
        return formatDouble(paramDouble, NUMBER_DEC);
    }

    public static String getNumber(String paramString) {
        return formatDouble(paramString, NUMBER_DEC);
    }

    public static String getPrice(double paramDouble) {
        return formatDouble(paramDouble, PRICE_DEC_NUM);
    }

    public static String getRecvableMoney(double arg5) {
        int v0 = Utils.RECEIVE_DEC_NUM;
        if (v0 == 0) {
            double v1 = arg5 % 10;
            int v3 = v1 >= 5 ? 10 : 0;
            arg5 += (((double) v3)) - v1;
            v0 = 0;
        }
        if (v0 >= 1) {
            --v0;
        }
        return Utils.formatDouble(arg5, v0);
    }

    public static String getServiceAddress(String paramString1, String paramString2) {
        return paramString1 + "/" + paramString2;
    }


    public static String getSubtotalMoney(double value) {
        return formatDouble(value, SUBTOTAL_DEC_NUM);
    }

    /**
     * 获取当前系统的时间
     *
     * @return
     */
    public static String getDataTime() {
        return getTimeStamp("yyyy-MM-dd HH:mm:ss");
    }

    public static String getData() {
        return getTimeStamp("yyyy-MM-dd");
    }

    public static String getTimeStamp() {
        return getTimeStamp("yyyyMMddHHmmssSSS");
    }

    public static String getTimeStamp(String paramString) {
        return formatDate(new Date().getTime(), paramString);
    }

    public static boolean greaterEqualsThan(double paramDouble1, double paramDouble2) {
        return (normalizeDouble(paramDouble1) < normalizeDouble(paramDouble2));
    }

    public static boolean greaterThan(double paramDouble1, double paramDouble2) {
        return (normalizeDouble(paramDouble1) <= normalizeDouble(paramDouble2));
    }

    public static void init() {
        AccountPreference v0 = new AccountPreference();
        Utils.listbiz = v0.getBizInfoMap();
        int v1;
        for (v1 = 0; v1 < Utils.listbiz.size(); ++v1) {
            if ("intPricePrecision".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.PRICE_DEC_NUM = Integer.parseInt(Utils.listbiz.get(v1).get("valueint"));
            } else if ("intSubtotalPrecision".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.SUBTOTAL_DEC_NUM = Integer.parseInt(Utils.listbiz.get(v1).get("valueint"));
            } else if ("intReceivablePrecision".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.RECEIVE_DEC_NUM = Integer.parseInt(Utils.listbiz.get(v1).get("valueint"));
            } else if ("intOutDocUnit".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.DEFAULT_UNIT = Integer.parseInt(Utils.listbiz.get(v1).get("valueint"));
            } else if ("intTransferDocUnit".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.TRANSFER_DEFAULT_UNIT = Integer.parseInt(Utils.listbiz.get(v1).get("valueint"));
            } else if ("isUseCustomerPrice".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.USE_CUSTOMER_PRICE = Boolean.parseBoolean(Utils.listbiz.get(v1).get("valuebool"));
            } else if ("strPriceSystemCheXiao".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.pricesystemid = Utils.listbiz.get(v1).get("valuestring");
                if (TextUtils.isEmptyS(Utils.pricesystemid)) {
                    Utils.pricesystemid = new PricesystemDAO().queryAvailablePricesystem();
                }
            } else if ("isUseCurrentPrice".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.isUseCurrentPrice = Boolean.parseBoolean(Utils.listbiz.get(v1).get("valuebool"));
            } else if ("isTuiHuanHuoSamePrice".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.isTuiHuanHuoSamePrice = Boolean.parseBoolean(Utils.listbiz.get(v1).get("valuebool"));
            } else if ("companyname".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.companyName = Utils.listbiz.get(v1).get("valuestring");
            } else if ("isCheXiaoAllowModifyPrinted".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.IS_ALLOWEDMODIFY_PRINTED = "true".equals(Utils.listbiz.get(v1).get("valuebool"));
            } else if ("isUploadLocation".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.isUploadLocation = Boolean.parseBoolean(Utils.listbiz.get(v1).get("valuebool"));
            } else if ("isDownloadCustomerByVisitLine".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.isDownloadCustomerByVisitLine = Boolean.parseBoolean(Utils.listbiz.get(v1).get("valuebool"));
            } else if ("intGenerateBatch".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.intGenerateBatch = Integer.parseInt(Utils.listbiz.get(v1).get("valueint"));
            } else if ("strBatchPrefix".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.strBatchPrefix = Utils.listbiz.get(v1).get("valuestring");
                if (TextUtils.isEmptyS(Utils.strBatchPrefix)) {
                    Utils.strBatchPrefix = "";
                }
            } else if ("strBatchSuffix".equals(Utils.listbiz.get(v1).get("bpid"))) {
                Utils.strBatchSuffix = Utils.listbiz.get(v1).get("valuestring");
                if (TextUtils.isEmptyS(Utils.strBatchSuffix)) {
                    Utils.strBatchSuffix = "";
                }
            }
        }
        String v2 = TextUtils.isEmptyS(v0.getValue("goods_check_select")) ? "id,pinyin,name,barcode" : v0.getValue("goods_check_select");
        Utils.GOODS_CHECK_SELECT = v2;
        v2 = TextUtils.isEmptyS(v0.getValue("customer_check_select")) ? "id,pinyin,name" : v0.getValue("customer_check_select");
        Utils.CUSTOMER_CHECK_SELECT = v2;
    }

    public static double normalize(double paramDouble, int y) {
        int i = (int) Math.pow(10.0D, y);
        return (Double.parseDouble(Math.round(paramDouble * i) + "") / i);
    }

    public static String removeZero(String paramString) {

        if (paramString.contains(".")) {
            String[] split = paramString.split("\\.");
            return split[0];
        }
        return null;
    }

    public static double normalizeDouble(double paramDouble) {
        return (Math.round(paramDouble * 10000.0D) / 10000.0D);
    }

    public static double normalizePrice(double paramDouble) {
        int i = (int) Math.pow(10.0D, -1 + RECEIVE_DEC_NUM);
        long round = Math.round(paramDouble * i);
        return Double.parseDouble(round + "") / i;
    }

    public static double normalizeReceivable(double paramDouble) {
        int i = (int) Math.pow(10.0D, -1 + RECEIVE_DEC_NUM);
        long round = Math.round(paramDouble * i);
        return Double.parseDouble(round + "") / i;
    }

    public static double normalizeSubtotal(double paramDouble) {
        int i = (int) Math.pow(10.0D, SUBTOTAL_DEC_NUM);
        long round = Math.round(paramDouble * i);
        return Double.parseDouble(round + "") / i;
    }

    public static long parseDate(String paramString) {
        return parseDate(paramString, "yyyy-MM-dd HH:mm:ss");
    }

    public static long parseDate(String paramString1, String paramString2) {
        try {
            long l = new SimpleDateFormat(paramString2).parse(paramString1).getTime();
            return l;
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return 0L;
    }

    /**
     * 是否开启商品合并
     *
     * @return
     */
    public static boolean isCombination() {
        return Boolean.parseBoolean(ap.getValue("iscombinationItem", "false"));
    }

    public static int px2dip(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().density);
    }

    public static Long getStartTime() {
        Calendar v0 = Calendar.getInstance();
        v0.set(Calendar.HOUR, 0);
        v0.set(Calendar.MINUTE, 0);
        v0.set(Calendar.SECOND, 0);
        v0.set(Calendar.MILLISECOND, 0);
        return v0.getTime().getTime();
    }

    public static double getRecvable(double arg5) {
        int v0 = ((int) Math.pow(10, ((double) (Utils.RECEIVE_DEC_NUM - 1))));
        return (((double) Math.round((((double) v0)) * arg5))) / (((double) v0));
    }

    public static double getSubtotal(double arg5) {
        int v0 = ((int) Math.pow(10, ((double) Utils.SUBTOTAL_DEC_NUM)));
        return (((double) Math.round((((double) v0)) * arg5))) / (((double) v0));
    }

    public static boolean validNAMAndShow(String arg10, int arg11, double[] arg12) {
        boolean v5 = false;
        try {
            double v3 = Double.parseDouble(arg10);
            for (double anArg12 : arg12) {
                if (v3 == anArg12) {
                    PDH.showError("不能为" + v3);
                    return false;
                }
            }
            if (v3 > 100000000) {
                PDH.showError("输入的值过大");
                return false;
            }

            if (v3 < -100000000) {
                PDH.showError("输入的值过小");
                return false;
            }
            if (arg10.split("\\.").length > 1 && arg10.split("\\.")[1].length() > arg11) {
                PDH.showError("小数位过多,只能输入" + arg11 + "位小数");
                return false;
            }
        } catch (NumberFormatException v0) {
            PDH.showError("输入不正确");
        }
        return true;
    }
}
