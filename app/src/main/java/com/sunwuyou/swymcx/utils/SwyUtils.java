package com.sunwuyou.swymcx.utils;

import com.sunwuyou.swymcx.request.ReqSynUpdateInfo;

import java.util.List;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class SwyUtils {
    public long getPagesFromUpdateInfo(List<ReqSynUpdateInfo> inforList, String rversion) {
        for (ReqSynUpdateInfo info : inforList) {
            if (info.getTablename().equals(rversion)) {
                return info.getPages();
            }
        }
        return 0;
    }
}
