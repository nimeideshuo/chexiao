package com.sunwuyou.swymcx.view;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import com.sunwuyou.swymcx.utils.TextUtils;

/**
 * Created by admin
 * 2018/7/17.
 * content
 */

public class AutoTextView extends AppCompatAutoCompleteTextView {
    private String beforeTextChange;
    private OnTextChangeListener changeListener;
    private Handler handler;
    private boolean isItemClick = false;
    private boolean isReplace = true;
    private TextWatcher watcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            beforeTextChange = s.toString();
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if ((AutoTextView.this.isItemClick) && (AutoTextView.this.isReplace)) {
                AutoTextView.this.isItemClick = false;
                return;
            }
            if (changeListener != null) {
                changeListener.onChanged(AutoTextView.this, s.toString());
            }
        }
    };

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTextChangedListener(this.watcher);
        this.handler = new Handler();
    }

    public void setOnTextChangeListener(OnTextChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public void setReplace(boolean paramBoolean) {
        this.isReplace = paramBoolean;
    }

    public void setText(String text) {
        super.setText(text);
        if (!TextUtils.isEmptyS(text))
            setSelection(text.length());
    }

    protected void replaceText(CharSequence s) {
        this.isItemClick = true;
        if (this.isReplace) {
            super.replaceText(s);
            return;
        }
        Editable localEditable = getText();
        Selection.setSelection(localEditable, localEditable.length());
    }

    private void show() {
        super.showDropDown();
    }

    public String getBeforeTextChange() {
        return this.beforeTextChange;
    }

    public void showDropDown() {
        this.handler.postDelayed(new Runnable() {
            public void run() {
                AutoTextView.this.show();
            }
        }, 100L);
    }

    public abstract interface OnTextChangeListener {
        public abstract void onChanged(View v, String str);
    }
}
