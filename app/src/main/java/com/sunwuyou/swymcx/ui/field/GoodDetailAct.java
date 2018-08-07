package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.GoodsDAO;
import com.sunwuyou.swymcx.dao.GoodsImageDAO;
import com.sunwuyou.swymcx.dao.GoodsPriceDAO;
import com.sunwuyou.swymcx.model.Goods;
import com.sunwuyou.swymcx.model.GoodsImage;
import com.sunwuyou.swymcx.ui.MAlertDialog;
import com.sunwuyou.swymcx.ui.SplashAct;
import com.sunwuyou.swymcx.utils.BitmapUtils;
import com.sunwuyou.swymcx.utils.ClickUtils;
import com.sunwuyou.swymcx.utils.NetUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;

import java.util.List;

/**
 * Created by admin
 * 2018/8/6.
 * content
 */
public class GoodDetailAct extends BaseHeadActivity {

    private Goods goods;
    private BitmapUtils bitmapUtils;
    private ImageView imageView;
    private TextView tvBarcode;
    private TextView tvGoodsID;
    private TextView tvModel;
    private TextView tvSpecificaion;
    private TextView tvStockNum;
    private TextView tvPrice;
    private TextView tvSaleCue;
    private TextView tvInitNum;
    private ViewPager viewPager;
    private List<GoodsImage> goodsImages;
    private MyPagerAdapter pagerAdapter;

    @Override
    public int getLayoutID() {
        return R.layout.dialog_stockiteminfo_details;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        goods = new GoodsDAO().getGoods(this.getIntent().getStringExtra("goodsid"));
        bitmapUtils = new BitmapUtils();
        this.imageView = findViewById(R.id.imageView);
        this.tvGoodsID = findViewById(R.id.tvGoodsID);
        this.tvBarcode = findViewById(R.id.tvBarcode);
        tvSpecificaion = findViewById(R.id.tvSpecification);
        this.tvModel = findViewById(R.id.tvModel);
        tvStockNum = findViewById(R.id.tvStockNum);
        this.tvPrice = this.findViewById(R.id.tvPrice);
        this.tvSaleCue = this.findViewById(R.id.tvSaleCue);
        this.tvInitNum = this.findViewById(R.id.tvInitNum);
        this.tvGoodsID.setText("商品编号：" + this.goods.getId());
        this.tvBarcode.setText("商品条码：" + TextUtils.out(this.goods.getBarcode()));
        this.tvSpecificaion.setText("规         格：" + TextUtils.out(this.goods.getSpecification()));
        this.tvModel.setText("型         号：" + TextUtils.out(this.goods.getModel()));
        this.tvInitNum.setText("装车数量：" + TextUtils.out(this.goods.getBiginitnumber()));
        this.tvStockNum.setText("剩余数量：" + TextUtils.out(this.goods.getBigstocknumber()));
        this.tvPrice.setText("销售单价：" + new GoodsPriceDAO().getDefaultPrice(this.goods.getId()));
        String v1 = this.goods.getSalecue();
        if (!TextUtils.isEmptyS(v1)) {
            this.tvSaleCue.setText("销售信息：" + v1);
        }
        viewPager = this.findViewById(R.id.viewPager);
        ViewGroup.LayoutParams v2 = this.viewPager.getLayoutParams();
        v2.height = SplashAct.height / 2;
        this.imageView.setLayoutParams(v2);
        goodsImages = new GoodsImageDAO().get(this.goods.getId());
        if (this.goodsImages == null || this.goodsImages.size() == 0) {
            this.imageView.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams v0 = this.imageView.getLayoutParams();
            v0.height = SplashAct.height / 2;
            this.imageView.setLayoutParams(v0);
        } else {
            this.imageView.setVisibility(View.GONE);
            pagerAdapter = new MyPagerAdapter(this, this.goodsImages);
            this.viewPager.setAdapter(this.pagerAdapter);
        }
    }


    @Override
    public void initData() {

    }

    public void setActionBarText() {
        setTitle(goods.getName());
    }

    class MyPagerAdapter extends PagerAdapter {
        public Context context;
        public List<GoodsImage> jobImageInfos;

        public MyPagerAdapter(Context context, List<GoodsImage> jobImageInfos) {
            super();
            this.context = context;
            this.jobImageInfos = jobImageInfos;
        }

        private void setViewData(View view, List<GoodsImage> paramList, int p_index) {
            if (p_index < 0) {
                finish();
            }
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            String v1 = String.valueOf(bitmapUtils.getPicDir()) + "/" + paramList.get(p_index).getImagePath();
            viewHolder.bmp = BitmapFactory.decodeFile(v1);
            if (viewHolder.bmp == null) {
                viewHolder.bmp = BitmapFactory.decodeResource(getResources(), R.drawable.swylogoimage);
            }
            viewHolder.imageView.setImageBitmap(viewHolder.bmp);
            viewHolder.tvPageNo.setText(String.valueOf(p_index + 1) + "/" + this.getCount());
        }

        private View constructView() {
            View localView = getLayoutInflater().inflate(R.layout.item_field_picture, null);
            ViewHolder localViewHolder = new ViewHolder();
            localViewHolder.tvPageNo = localView.findViewById(R.id.tvPageNo);
            localViewHolder.imageView = localView.findViewById(R.id.album_imgview);
            localView.setTag(localViewHolder);
            return localView;
        }

        @Override
        public int getCount() {
            return this.jobImageInfos.size();
        }

        public int getItemPosition(Object object) {
            return -2;
        }

        @Override
        public boolean isViewFromObject(View view, Object arg1) {
            return view == arg1;
        }

        @Override
        public Object instantiateItem(View container, int position) {
            View view = this.constructView();
            setViewData(view, this.jobImageInfos, position);
            ((ViewPager) container).addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        class ViewHolder {
            public Bitmap bmp;
            public ImageView imageView;
            public TextView tvPageNo;

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
