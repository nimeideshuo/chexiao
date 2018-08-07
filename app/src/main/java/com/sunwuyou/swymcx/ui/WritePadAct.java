package com.sunwuyou.swymcx.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.FieldSaleImageDAO;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSaleImage;
import com.sunwuyou.swymcx.utils.FileUtils;
import com.sunwuyou.swymcx.utils.MLog;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.Utils;

/**
 * Created by admin
 * 2018/7/25.
 * content
 */

public class WritePadAct extends BaseHeadActivity implements View.OnClickListener {

    static final int BACKGROUND_COLOR = -1;
    static final int BRUSH_COLOR = -16777216;
    int mColorIndex;
    private PaintView mView;
    @SuppressLint("HandlerLeak") private Handler resultHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ("fail".equals(msg.obj)) {
                PDH.showFail("保存失败，请重试");
            } else {
                PDH.showSuccess("保存成功");
            }

        }
    };
    private Button btnCancel;
    private Button btnClear;
    private Button btnConfirm;
    private FieldSale fieldSale;
    private FileUtils fileUtils;
    private boolean flag;
    private int height;
    private ImageView imgSignature;
    private FieldSaleImage signItem;
    private int width;

    @Override
    public int getLayoutID() {
        return R.layout.write_pad;
    }

    @Override
    public void initView() {
        this.flag = false;
        DisplayMetrics v1 = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(v1);
        width = v1.widthPixels;
        height = v1.heightPixels - 55;
        mView = new PaintView(this);
        LinearLayout tablet_view = findViewById(R.id.tablet_view);
        tablet_view.addView(this.mView);
        this.fieldSale = new FieldSaleDAO().getFieldsale(this.getIntent().getLongExtra("fieldsaleid", -1));
        this.mView.requestFocus();
        this.imgSignature = this.findViewById(R.id.imgSignature);
        this.btnConfirm = this.findViewById(R.id.btnConfirm);
        this.btnClear = this.findViewById(R.id.btnClear);
        this.btnCancel = this.findViewById(R.id.btnCancel);
        this.btnConfirm.setOnClickListener(this);
        this.btnClear.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
        this.fileUtils = new FileUtils();
        this.loadData();
    }

    @Override
    public void initData() {

    }

    public String getPicName() {
        return String.valueOf(Utils.getTimeStamp()) + "(" + this.fieldSale.getCustomername() + ")";
    }

    private FieldSaleImage getPictureItem(String arg4) {
        FieldSaleImage v0 = new FieldSaleImage();
        v0.setImagepath(arg4);
        v0.setIssignature(true);
        v0.setRemark("签名");
        v0.setFieldsaleid(this.fieldSale.getId());
        return v0;
    }

    private void loadData() {
        this.signItem = new FieldSaleImageDAO().querySignaturePic(this.fieldSale.getId());
        if (this.signItem != null) {
            Bitmap v0 = BitmapFactory.decodeFile(String.valueOf(new FileUtils().getPicDir()) + "/" + this.signItem.getImagepath());
            this.imgSignature.setVisibility(View.VISIBLE);
            this.imgSignature.setImageBitmap(v0);
            this.flag = false;
        } else {
            this.mView.clear();
            this.flag = true;
        }
    }

    public void setActionBarText() {
        setTitle("签名");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:
                this.finish();
                Bitmap v0 = this.mView.getCachebBitmap();
                String v1 = String.valueOf(this.getPicName()) + ".jpg";
                this.fileUtils.savePicture(v0, v1);
                savePicture(this.getPictureItem(v1));
                break;
            case R.id.btnClear:
                this.imgSignature.setVisibility(View.GONE);
                this.mView.clear();
                this.flag = true;
                break;
            case R.id.btnCancel:
                this.finish();
                break;
        }
    }

    private void savePicture(final FieldSaleImage item) {
        PDH.show(this, "正在处理...", new PDH.ProgressCallBack() {
            public void action() {
                if (WritePadAct.this.signItem == null) {
                    MLog.d("add");
                    new FieldSaleImageDAO().addJobImage(item);
                    resultHandler.sendMessage(resultHandler.obtainMessage(0, "success"));
                } else {
                    resultHandler.sendMessage(resultHandler.obtainMessage(1, "success"));
                }
            }
        });
    }

    class PaintView extends View {
        private Canvas cacheCanvas;
        private Bitmap cachebBitmap;
        private float cur_x;
        private float cur_y;
        private Paint paint;
        private Path path;

        public PaintView(Context context) {
            super(context);
            this.init();
        }

        public void clear() {
            int v2 = -1;
            if (this.cacheCanvas != null) {
                this.paint.setColor(v2);
                this.cacheCanvas.drawPaint(this.paint);
                this.paint.setColor(-16777216);
                this.cacheCanvas.drawColor(v2);
                this.invalidate();
            }
        }

        public Bitmap getCachebBitmap() {
            return this.cachebBitmap;
        }

        private void init() {
            this.paint = new Paint();
            this.paint.setAntiAlias(true);
            this.paint.setStrokeWidth(4f);
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setColor(-16777216);
            this.path = new Path();
            this.cachebBitmap = Bitmap.createBitmap(WritePadAct.this.width, WritePadAct.this.height - 120, Bitmap.Config.ARGB_8888);
            this.cacheCanvas = new Canvas(this.cachebBitmap);
            this.cacheCanvas.drawColor(-1);
        }

        protected void onDraw(Canvas arg4) {
            if (flag) {
                arg4.drawBitmap(this.cachebBitmap, 0f, 0f, null);
                arg4.drawPath(this.path, this.paint);
            }
        }

        protected void onSizeChanged(int arg8, int arg9, int arg10, int arg11) {
            if (WritePadAct.this.flag) {
                int v1 = this.cachebBitmap != null ? this.cachebBitmap.getWidth() : 0;
                int v0 = this.cachebBitmap != null ? this.cachebBitmap.getHeight() : 0;
                if (v1 >= arg8 && v0 >= arg9) {
                    return;
                }

                if (v1 < arg8) {
                    v1 = arg8;
                }

                if (v0 < arg9) {
                    v0 = arg9;
                }

                Bitmap v2 = Bitmap.createBitmap(v1, v0, Bitmap.Config.ARGB_8888);
                Canvas v3 = new Canvas();
                v3.setBitmap(v2);
                if (this.cachebBitmap != null) {
                    v3.drawBitmap(this.cachebBitmap, 0f, 0f, null);
                }

                this.cachebBitmap = v2;
                this.cacheCanvas = v3;
            }
        }

        public boolean onTouchEvent(MotionEvent arg6) {
            boolean v2;
            if (!WritePadAct.this.flag) {
                v2 = false;
            } else {
                float v0 = arg6.getX();
                float v1 = arg6.getY();
                switch (arg6.getAction()) {
                    case 0: {
                        this.cur_x = v0;
                        this.cur_y = v1;
                        this.path.moveTo(this.cur_x, this.cur_y);
                        break;
                    }
                    case 1: {
                        this.cacheCanvas.drawPath(this.path, this.paint);
                        this.path.reset();
                        break;
                    }
                    case 2: {
                        this.path.quadTo(this.cur_x, this.cur_y, v0, v1);
                        this.cur_x = v0;
                        this.cur_y = v1;
                        break;
                    }
                }

                this.invalidate();
                v2 = true;
            }
            return v2;
        }
    }

}
