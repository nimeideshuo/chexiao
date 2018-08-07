package com.immo.libcomm.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * @author Administrator
 * @content 自定义圆形imageview
 * @date 2017/11/15
 */

@SuppressLint("AppCompatCustomView")
public class CircleTextView extends TextView {
    private Paint mBgPaint = new Paint();
    PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
    public CircleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setAntiAlias(true);
    }
    public CircleTextView(Context context) {
        super(context);
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setAntiAlias(true);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int max = Math.max(measuredWidth, measuredHeight);
        setMeasuredDimension(max, max);
    }
    @Override
    public void setBackgroundColor(int color) {
        mBgPaint.setColor(color);
    }
    /**
     * 设置通知个数显示
     * @param text
     */
    public void setNotifiText(int text){
        setText(text+"");
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.setDrawFilter(pfd);//给Canvas加上抗锯齿标志
        canvas.drawCircle(getWidth()/2, getHeight()/2, Math.max(getWidth(), getHeight())/2, mBgPaint);
        super.draw(canvas);
    }
}
