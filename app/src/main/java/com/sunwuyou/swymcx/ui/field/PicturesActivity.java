package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    List jobImageInfos;
    private FileUtils fileUtils;
    private MyPagerAdapter myPagerAdapter;
    private ViewPager viewPager;
    private FieldSale fieldSale;
    private int currentIndex;
    @SuppressLint("HandlerLeak")
    private Handler deleteHandler = new Handler() {
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
    public int getLayoutID() {
        return R.layout.act_field_picture;
    }

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

    @Override
    public void initData() {

    }

    protected void onResume() {
        super.onResume();
        this.jobImageInfos = new FieldSaleImageDAO().queryJobImage(this.fieldSale.getId());
        if (this.jobImageInfos == null || this.jobImageInfos.size() == 0) {
            PDH.showMessage("该任务没有照片");
            this.currentIndex = -1;
        } else {
            this.currentIndex = 0;
        }

        myPagerAdapter = new MyPagerAdapter(this, jobImageInfos);
        this.viewPager.setAdapter(myPagerAdapter);
    }

    public void setActionBarText() {
        setTitle("照片");
    }


    class MyPagerAdapter extends PagerAdapter {
        public Context context;
        public List<FieldSaleImage> jobImageInfos;

        public MyPagerAdapter(Context context, List<FieldSaleImage> jobImageInfos) {
            super();
            this.context = context;
            this.jobImageInfos = jobImageInfos;
        }

        private View constructView() {
            View view = PicturesActivity.this.getLayoutInflater().inflate(R.layout.item_field_picture, null);
            ViewHolder holder = new ViewHolder();
            holder.tvPageNo = view.findViewById(R.id.tvPageNo);
            holder.imageView = view.findViewById(R.id.album_imgview);
            view.setTag(holder);
            return view;
        }

        public void destroyItem(View paramView, int paramInt, Object paramObject) {
            ((ViewHolder) ((View) paramObject).getTag()).clear();
            ((ViewPager) paramView).removeView((View) paramObject);
        }

        public Object instantiateItem(View arg3, int arg4) {
            View v0 = this.constructView();
            setViewData(v0, this.jobImageInfos, arg4);
            ((ViewPager) arg3).addView(v0, 0);
            return v0;
        }


        @Override
        public int getCount() {
            return this.jobImageInfos.size();
        }

        public int getItemPosition(Object arg2) {
            return -2;
        }

        @SuppressLint("SetTextI18n")
        private void setViewData(View arg7, List<FieldSaleImage> arg8, int arg9) {
            if (arg9 < 0) {
                finish();
            }

            ViewHolder v0 = (ViewHolder) arg7.getTag();
            String v1 = String.valueOf(fileUtils.getPicDir()) + "/" + arg8.get(arg9).getImagepath();
//            v0.clear();
            v0.bmp = BitmapFactory.decodeFile(v1);
            if (v0.bmp == null) {
                v0.bmp = BitmapFactory.decodeResource(getResources(), R.drawable.swynopic);
            }

            v0.imageView.setImageBitmap(v0.bmp);
            v0.tvPageNo.setText(String.valueOf(arg9 + 1) + "/" + this.getCount());
        }

        @Override
        public boolean isViewFromObject(View arg2, Object arg3) {
            return arg2 == arg3;
        }

        class ViewHolder {
            public Bitmap bmp;
            public ImageView imageView;
            public TextView tvPageNo;

            private ViewHolder() {
                super();
            }

            public void clear() {
                if (this.bmp != null && !this.bmp.isRecycled()) {
                    this.bmp.recycle();
                    this.bmp = null;
                    System.gc();
                }
            }
        }
    }
}