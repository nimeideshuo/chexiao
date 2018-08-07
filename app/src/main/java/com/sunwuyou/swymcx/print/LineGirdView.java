package com.sunwuyou.swymcx.print;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public class LineGirdView extends View {

    public static int CELL_HEIGHT;
    public static int CELL_WIDTH;
    public static int COLOUMNS;
    public static int ROWS;
    public static int WIDTH;
    private Paint paint;

    static {
        LineGirdView.CELL_HEIGHT = 50;
        LineGirdView.WIDTH = 0;
        LineGirdView.COLOUMNS = 32;
        LineGirdView.ROWS = 13;
        LineGirdView.CELL_WIDTH = 0;
    }

    public LineGirdView(Context arg3, AttributeSet arg4) {
        super(arg3, arg4);
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(1f);
        this.paint.setColor(-3355444);
    }
    public static int getCellWidth() {
        return LineGirdView.CELL_WIDTH;
    }

    protected void onDraw(Canvas arg11) {
        this.paint.setColor(-3355444);
        arg11.drawText("页眉", 20f, 20f, this.paint);
        arg11.drawText("页脚", 20f, ((float)(LineGirdView.CELL_HEIGHT * 6 + 20)), this.paint);
        LineGirdView.WIDTH = this.getWidth();
        int v8 = LineGirdView.CELL_HEIGHT * LineGirdView.ROWS;
        int v9;
        for(v9 = 0; v9 <= LineGirdView.ROWS; ++v9) {
            arg11.drawLine(0f, ((float)(LineGirdView.CELL_HEIGHT * v9)), ((float)LineGirdView.WIDTH), ((float)(LineGirdView.CELL_HEIGHT * v9)), this.paint);
        }

        LineGirdView.CELL_WIDTH = LineGirdView.WIDTH / LineGirdView.COLOUMNS;
        for(v9 = 0; v9 <= LineGirdView.COLOUMNS + 1; v9 += 2) {
            arg11.drawLine(((float)(LineGirdView.CELL_WIDTH * v9)), 0f, ((float)(LineGirdView.CELL_WIDTH * v9)), ((float)v8), this.paint);
        }

        this.paint.setColor(-16777216);
        arg11.drawLine(0f, ((float)(LineGirdView.CELL_HEIGHT * 6)), ((float)LineGirdView.WIDTH), ((float)(LineGirdView.CELL_HEIGHT * 6)), this.paint);
    }
}
