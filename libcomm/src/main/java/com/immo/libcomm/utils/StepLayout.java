package com.immo.libcomm.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.immo.libcomm.R;


/**
 * Created by xin on 2017/8/2.
 * 横向的一步步提示
 */

public class StepLayout extends View {
    private CharSequence[] lists;
    private int colorDeon;
    private int colorUnDone;
    private int sizeText;
    private int currentStep;
    private Paint paintTv;
    private int sizeFlowText;
    private Paint paintFlowTv;
    private float flowTextPad;

    public StepLayout(Context context) {
        this(context, null);
    }

    public StepLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
        flowTextPad = context.getResources().getDisplayMetrics().density * 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        final int textLength = lists.length;
        float sizeFlowCircle = width / (textLength * 10);

        float textHeight = paintTv.getFontMetrics().bottom - paintTv.getFontMetrics().top;
        float height = getPaddingTop() + getPaddingBottom() + textHeight + sizeFlowCircle * 2 + flowTextPad;
        int heightSpec = MeasureSpec.makeMeasureSpec((int) height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }


    private void initView(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StepLayout);
        lists = ta.getTextArray(R.styleable.StepLayout_data);
        colorDeon = ta.getColor(R.styleable.StepLayout_colorDone, Color.BLACK);
        colorUnDone = ta.getColor(R.styleable.StepLayout_colorUndone, Color.parseColor("#dadada"));

        sizeText = (int) ta.getDimension(R.styleable.StepLayout_textSize, 14);
        sizeText = px2sp(getContext(), sizeText);

        sizeFlowText = (int) ta.getDimension(R.styleable.StepLayout_flowTextSize, 14);
        sizeFlowText = px2sp(getContext(), sizeFlowText);
        currentStep = ta.getInt(R.styleable.StepLayout_currentStep, 1);
        ta.recycle();

        paintTv = new Paint();
        paintTv.setTextSize(sizeText);
        paintTv.setColor(colorDeon);
        paintTv.setTextAlign(Paint.Align.CENTER);

        paintFlowTv = new Paint();
        paintFlowTv.setTextSize(sizeFlowText);
        paintFlowTv.setColor(Color.WHITE);
        paintFlowTv.setTextAlign(Paint.Align.CENTER);
    }

    public static int px2sp(Context context, float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                pxValue, context.getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isEmptyData()) {
            return;
        }
        final int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        final int textLength = lists.length;
        final int unitWidth = width / textLength;
        Paint paintFlow = new Paint();
        float sizeFlowCircle = width / (textLength * 10); /*半径*/

        /*文字的基线位置*/
        float textTop = getPaddingTop() + sizeFlowCircle * 2 + flowTextPad;
        Paint.FontMetrics fontMetricsText = paintTv.getFontMetrics();
        float textBottom = getMeasuredHeight() - getPaddingBottom();
        final float textY = (textBottom + textTop - fontMetricsText.bottom - fontMetricsText.top) / 2;

        for (int i = 0, size = textLength; i < size; i++) {
            if (i < currentStep) {
                paintFlow.setColor(colorDeon);
                paintTv.setColor(colorDeon);
            } else {
                paintFlow.setColor(colorUnDone);
                paintTv.setColor(colorUnDone);
            }
            float unitLeft = getPaddingLeft() + i * unitWidth;
            float unitRight = unitLeft + unitWidth;
            float unitTop = getPaddingTop();
            float unitBottom = unitTop + sizeFlowCircle * 2;
            float cx = (unitLeft + unitRight) / 2;
            float cy = (unitTop + unitBottom) / 2;
             /*绘制上图案*/

            canvas.drawRect(unitLeft, unitTop + sizeFlowCircle - 2, unitRight, unitTop + sizeFlowCircle + 2, paintFlow);
            canvas.drawCircle(cx, cy, sizeFlowCircle, paintFlow);
            Paint.FontMetrics fontMetrics = paintFlowTv.getFontMetrics();
            String text = "" + (i + 1);
            float baseline = (unitBottom + unitTop - fontMetrics.bottom - fontMetrics.top) / 2;
            canvas.drawText(text, cx, baseline, paintFlowTv);
             /*绘制文字*/
            canvas.drawText(lists[i].toString(), unitLeft + unitWidth / 2, textY, paintTv);
        }

    }

    private boolean isEmptyData() {
        return lists == null || lists.length == 0;
    }

    /**
     * @param currentStep 从1开始
     */
    public void setCurrentStep(int currentStep) {
        if (isEmptyData() && currentStep > lists.length - 1) {
            return;
        }
        this.currentStep = currentStep;
        invalidate();
    }

    public void setLists(CharSequence[] lists) {
        if (isEmptyData()) {
            return;
        }
        this.lists = lists;
        invalidate();
    }
}
