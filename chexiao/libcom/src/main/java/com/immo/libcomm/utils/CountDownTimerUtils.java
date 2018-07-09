package com.immo.libcomm.utils;

import com.coorchice.library.SuperTextView;
import com.immo.libcomm.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


/**
 * 作者： YaoChen
 * 日期： 2017/7/17 16:10
 * 描述： 倒计时
 */
public class CountDownTimerUtils extends CountDownTimer {
    private SuperTextView mTextView;
    private EditText mEditText;
    private Context context;
    private EditText phone;

    /**
     * @param textView          The TextView
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receiver
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerUtils(EditText mPhone, final Context context, SuperTextView textView, EditText editText, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
        this.mEditText=editText;
        this.context = context;
        this.phone=mPhone;

    }
    public static void setCodeColor(EditText phone, final SuperTextView getcode, final Context context){
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length()>0){
                    getcode.setTextColor(context.getResources().getColor(R.color.color_mainAuto));
                }else {
                    getcode.setTextColor(context.getResources().getColor(R.color.color_mainAuto01));
                }
            }
        });
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText(""+millisUntilFinished / 1000+"s");  //设置倒计时时间
        mTextView.setTextColor(context.getResources().getColor(R.color.colorBlack));

        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                onFinish();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTextView.setSolid(ContextCompat.getColor(context,R.color.colorWhite)); //设置按钮为灰色，这时是不能点击的
        mTextView.setTextColor(ContextCompat.getColor(context,R.color.defaultColor));

        /**
         * 超链接 URLSpan
         * 文字背景颜色 BackgroundColorSpan
         * 文字颜色 ForegroundColorSpan
         * 字体大小 AbsoluteSizeSpan
         * 粗体、斜体 StyleSpan
         * 删除线 StrikethroughSpan
         * 下划线 UnderlineSpan
         * 图片 ImageSpan
         * http://blog.csdn.net/ah200614435/article/details/7914459
         */
//        SpannableString spannableString = new SpannableString(mTextView.getText().toString());  //获取按钮上的文字
//        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
//        /**
//         * public void setSpan(Object what, int start, int end, int flags) {
//         * 主要是start跟end，start是起始位置,无论中英文，都算一个。
//         * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
//         *
//         */
//        if (millisUntilFinished > 10) {
//            spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
//        } else {
//            spannableString.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
//        }
//        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTextView.setText(context.getString(R.string.recapture));
        mTextView.setClickable(true);//重新获得点击
        mTextView.setSolid(ContextCompat.getColor(context,R.color.colorWhite));
        mTextView.setTextColor(ContextCompat.getColor(context,R.color.color_mainAuto));

        cancel();

     }
}
