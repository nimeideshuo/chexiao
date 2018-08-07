package com.sunwuyou.swymcx.print;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.utils.TextUtils;

import java.util.HashMap;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public class ETextView extends android.support.v7.widget.AppCompatTextView {
    public static final int LOC_CENTER = -2;
    public static final int LOC_LEFT = -1;
    public static final int LOC_RIGHT = -3;
    private static int DH = 0;

    static {
        ETextView.DH = LineGirdView.CELL_HEIGHT;
    }

    Handler handler;
    private boolean isSelect;
    private int location;
    private int mPreviousx;
    private int mPreviousy;

    public ETextView(Context context) {
        super(context);
    }

    public ETextView(Context arg4, AttributeSet arg5) {
        super(arg4);
        int v2 = -2;
        this.mPreviousx = 0;
        this.mPreviousy = 0;
        this.isSelect = false;
        this.location = 0;
        this.handler = new Handler();
        ViewGroup.LayoutParams v0 = this.getLayoutParams();
        if (v0 == null) {
            v0 = new ViewGroup.LayoutParams(v2, v2);
        } else {
            v0.width = v2;
            v0.height = v2;
        }
        this.setBackgroundResource(R.drawable.btn_1_bg_nor);
        this.setLayoutParams(v0);
    }

    public HashMap<String,String> getParam() {
        ViewGroup.LayoutParams v0 = this.getLayoutParams();
        HashMap<String, String> v1 = new HashMap<>();
        v1.put("text", this.getText().toString());
        v1.put("garity", this.location == 0 ? "" : String.valueOf(this.location));
        v1.put("marginleft", this.location == 0 ? String.valueOf(((FrameLayout.LayoutParams) v0).leftMargin / LineGirdView.CELL_WIDTH) : "");
        v1.put("margintop", String.valueOf(((FrameLayout.LayoutParams) v0).topMargin));
        return v1;
    }

    public int getX(int arg5) {
        int v0 = LineGirdView.CELL_WIDTH;
        int v1 = arg5 % v0;
        int v2 = v1 >= v0 / 2 ? arg5 + v0 - v1 : arg5 - v1;
        if (v2 < 0) {
            v2 = 0;
        }
        return v2;
    }

    private int getY(int arg5) {
        int v0 = arg5 % ETextView.DH;
        int v1 = v0 >= ETextView.DH / 2 ? ETextView.DH + arg5 - v0 - this.getHeight() : arg5 - v0 - this.getHeight();
        if (v1 < 0) {
            v1 = ETextView.DH - this.getHeight();
        }
        return v1;
    }

    public void init(HashMap<String, String> arg10) {
        int v8 = -2;
        FrameLayout.LayoutParams v3 = (FrameLayout.LayoutParams) this.getLayoutParams();
        this.setText(arg10.get("text"));
        v3.width = v8;
        v3.topMargin = Integer.parseInt(arg10.get("margintop"));
        String v1 = arg10.get("garity");
        String v4 = arg10.get("marginleft");
        if (TextUtils.isEmpty(v1)) {
            try {
                v3.leftMargin = Integer.parseInt(v4) * LineGirdView.CELL_WIDTH;
            } catch (NumberFormatException v0) {
                v3.leftMargin = 0;
            }
        }
        int v2 = Integer.parseInt(v1);
        this.location = v2;
        if (v2 == -1) {
            v3.gravity = 3;
        }

        if (v2 == v8) {
            v3.gravity = 1;
        }

        if (v2 == -3) {
            v3.gravity = 5;
        }
        this.setLayoutParams(v3);

    }
    public void location(int arg5) {
        int v3 = -2;
        this.location = arg5;
        FrameLayout.LayoutParams v0 = new FrameLayout.LayoutParams(this.getWidth(), this.getHeight());
        v0.width = v3;
        v0.topMargin = this.getTop();
        if(arg5 == -1) {
            v0.gravity = 3;
            v0.leftMargin = 0;
        }
        else if(arg5 == v3) {
            v0.gravity = 1;
        }
        else if(arg5 == -3) {
            v0.gravity = 5;
        }

        this.setLayoutParams(v0);
    }
    public boolean onTouchEvent(MotionEvent arg10) {
        int v0 = arg10.getAction();
        int v1 = ((int)arg10.getX());
        int v2 = ((int)arg10.getY());
        switch(v0) {
            case 0: {
                this.mPreviousx = v1;
                this.mPreviousy = v2;
                this.location = 0;
                if(this.isSelect) {
                    return true;
                }

                this.performClick();
                break;
            }
            case 2: {
                int v3 = v1 - this.mPreviousx;
                int v4 = v2 - this.mPreviousy;
                this.setLocation(this.getLeft() + v3, this.getTop() + v4);
                this.mPreviousx = v1 - v3;
                this.mPreviousy = v2 - v4;
                break;
            }
        }

        return true;
    }

    public void select(boolean arg2) {
        this.isSelect = arg2;
        int v0 = arg2 ? 2130837512 : 2130837511;
        this.setBackgroundResource(v0);
    }

    public void setLocation(int arg4, int arg5) {
        FrameLayout.LayoutParams v0 = new FrameLayout.LayoutParams(this.getWidth(), this.getHeight());
        v0.leftMargin = this.getX(arg4);
        v0.topMargin = this.getY(arg5);
        v0.width = -2;
        this.setLayoutParams(v0);
    }



}
