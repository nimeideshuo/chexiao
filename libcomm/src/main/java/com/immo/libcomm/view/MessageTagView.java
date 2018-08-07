package com.immo.libcomm.view;

/**
 * @author Administrator
 * @content
 * @date 2017/11/25
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.immo.libcomm.R;

/**
 * Created by xin on 2017/8/1.
 * 是否有消息已读未读
 */

public class MessageTagView extends AppCompatImageView {
    private boolean hasMessage;

    public boolean isHasMessage() {
        return hasMessage;
    }

    public void setHasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }

    public MessageTagView(Context context) {
        this(context, null);
    }

    public MessageTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MessageTagView);
        hasMessage = ta.getBoolean(R.styleable.MessageTagView_hasMessage, false);
        ta.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (hasMessage) {
            drawTRCircle(canvas);
        }
    }

    /**
     * 在右上角绘制红色的圆心
     *
     * @param canvas
     */
    private void drawTRCircle(Canvas canvas) {
        int radius = (int) (5* getResources().getDisplayMetrics().density);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int drawableHeight = getDrawable().getIntrinsicHeight();
        int drawableWidth = getDrawable().getIntrinsicWidth();
        int dia = 2 * radius;
        int pading = 1;
        int cx = 0;
        int cy = 0;
        if (width >= drawableWidth + dia + pading) {
            cx = width / 2 + drawableWidth / 2 + pading;
        } else {
            cx = width - radius - pading;
        }
        if (height >= drawableHeight + dia + pading) {
            cy = height / 2 - drawableWidth / 2 ;
        } else {
            cy = radius + pading;
        }
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(cx-5, cy+5, radius, paint);

    }


}
