package com.sunwuyou.swymcx.app;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class ActivityManager {
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    public static ActivityManager getScreenManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public Activity currentActivity() {
        Activity localActivity = null;
        if (!activityStack.empty()) {
            localActivity = (Activity) activityStack.lastElement();
        }
        return localActivity;
    }

    public void popActivity(Activity paramActivity) {
        if (paramActivity != null) {
            paramActivity.finish();
            activityStack.remove(paramActivity);
        }
    }

    public void popAllActivityExceptOne(Class<?> paramClass) {
        Activity ac = currentActivity();
        if (ac.getClass().equals(paramClass)) {
            return;
        }
        popActivity(ac);
    }

    public void pushActivity(Activity paramActivity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(paramActivity);
    }
}
