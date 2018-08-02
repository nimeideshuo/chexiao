package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.FieldSaleImageDAO;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSaleImage;
import com.sunwuyou.swymcx.utils.FileUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.view.MessageDialog;

import java.util.List;

/**
 * Created by admin
 * 2018/7/25.
 * content
 */

public class PicturesActivity extends BaseHeadActivity {

    private FileUtils fileUtils;

    @Override
    public int getLayoutID() {
        return R.layout.act_field_picture;
    }

    private ViewPager viewPager;
    private FieldSale fieldSale;
    private int currentIndex;

    @Override
    public void initView() {
        setTitleRight("拍照", null);
        setTitleRight1("删除");
        this.viewPager = this.findViewById(R.id.viewPager);
        this.fieldSale = new FieldSaleDAO().getFieldsale(this.getIntent().getLongExtra("fieldsaleid", -1));
        this.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int arg1) {
            }

            public void onPageScrolled(int arg1, float arg2, int arg3) {
            }

            public void onPageSelected(int arg3) {
                if (arg3 > currentIndex) {
                    ++currentIndex;
                } else if (arg3 < currentIndex) {
                    --currentIndex;
                }
            }
        });
        fileUtils = new FileUtils();
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        //拍照
        this.startActivity(new Intent().setClass(this, CameraActivity.class).putExtra("fieldsaleid", this.fieldSale.getId()));
    }

    @Override
    protected void onRightClick1() {
        super.onRightClick1();
        //删除
        if (this.currentIndex >= 0) {
            this.delete(this.currentIndex);
        }
    }

    List jobImageInfos;

    public void delete(final int index) {
        new MessageDialog(this).showDialog("提示", "确定删除吗？", null, null, new MessageDialog.CallBack() {
            @Override
            public void btnOk(View view) {
                PDH.show(PicturesActivity.this, "正在删除....", new PDH.ProgressCallBack() {
                    public void action() {
                        Object v0 = jobImageInfos.get(index);
                        if (!fileUtils.deletePic(((FieldSaleImage) v0).getImagepath()) || !new FieldSaleImageDAO().delete(((FieldSaleImage) v0).getSerialid())) {
                            deleteHandler.sendEmptyMessage(1);
                        } else {
                            jobImageInfos.remove(v0);
                            deleteHandler.sendEmptyMessage(0);
                        }
                    }
                });
            }

            @Override
            public void btnCancel(View view) {

            }
        }).showDialog();
    }

    @SuppressLint("HandlerLeak") private Handler deleteHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (PicturesActivity.this.currentIndex > PicturesActivity.this.jobImageInfos.size() - 1) {
                    PicturesActivity.this.currentIndex = PicturesActivity.this.jobImageInfos.size() - 1;
                }
                myPagerAdapter.notifyDataSetChanged();
            } else {
                PDH.showFail("删除失败");
            }

        }
    };

    @Override
    public void initData() {

    }

    public void setActionBarText() {
        setTitle("照片");
    }
}
