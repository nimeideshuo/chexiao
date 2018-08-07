package com.sunwuyou.swymcx.utils;

import com.sunwuyou.swymcx.request.ReqSynUpdateInfo;

import java.util.Iterator;
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

    // 获取 list 中 需要更新 的 pages
    public int getSumPagesFromUpdateInfo(List<ReqSynUpdateInfo> paramList) {
        int i = 0;
        for (ReqSynUpdateInfo info : paramList) {
            if ((!info.getTablename().equals("rversion"))
                    && (!info.getTablename().equals("pagesize"))) {
                i = (int) (i + info.getPages());
            }
        }
        return i;
    }
}
