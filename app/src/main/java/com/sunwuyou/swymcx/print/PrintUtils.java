package com.sunwuyou.swymcx.print;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by admin
 * 2018/9/15.
 * content
 */

public class PrintUtils {
    /**
     * PrintUtils.selectCommand(PrintUtils.RESET);
     PrintUtils.selectCommand(PrintUtils.LINE_SPACING_DEFAULT);
     PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
     PrintUtils.printText("美食餐厅\n\n");
     PrintUtils.selectCommand(PrintUtils.DOUBLE_HEIGHT_WIDTH);
     PrintUtils.printText("桌号：1号桌\n\n");
     PrintUtils.selectCommand(PrintUtils.NORMAL);
     PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
     PrintUtils.printText(PrintUtils.printTwoData("订单编号", "201507161515\n"));
     PrintUtils.printText(PrintUtils.printTwoData("点菜时间", "2016-02-16 10:46\n"));
     PrintUtils.printText(PrintUtils.printTwoData("上菜时间", "2016-02-16 11:46\n"));
     PrintUtils.printText(PrintUtils.printTwoData("人数：2人", "收银员：张三\n"));

     PrintUtils.printText("--------------------------------\n");
     PrintUtils.selectCommand(PrintUtils.BOLD);
     PrintUtils.printText(PrintUtils.printThreeData("项目", "数量", "金额\n"));
     PrintUtils.printText("--------------------------------\n");
     PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
     PrintUtils.printText(PrintUtils.printThreeData("面", "1", "0.00\n"));
     PrintUtils.printText(PrintUtils.printThreeData("米饭", "1", "6.00\n"));
     PrintUtils.printText(PrintUtils.printThreeData("铁板烧", "1", "26.00\n"));
     PrintUtils.printText(PrintUtils.printThreeData("一个测试", "1", "226.00\n"));
     PrintUtils.printText(PrintUtils.printThreeData("牛肉面啊啊", "1", "2226.00\n"));
     PrintUtils.printText(PrintUtils.printThreeData("牛肉面啊啊啊牛肉面啊啊啊", "888", "98886.00\n"));

     PrintUtils.printText("--------------------------------\n");
     PrintUtils.printText(PrintUtils.printTwoData("合计", "53.50\n"));
     PrintUtils.printText(PrintUtils.printTwoData("抹零", "3.50\n"));
     PrintUtils.printText("--------------------------------\n");
     PrintUtils.printText(PrintUtils.printTwoData("应收", "50.00\n"));
     PrintUtils.printText("--------------------------------\n");

     PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
     PrintUtils.printText("备注：不要辣、不要香菜");
     PrintUtils.printText("\n\n\n\n\n");

     作者：胡萝卜小兔
     链接：https://www.jianshu.com/p/ec0cdcd62595
     來源：简书
     简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
     */
    /**
     * 复位打印机
     */
    public static final byte[] RESET = {0x1b, 0x40};

    /**
     * 左对齐
     */
    public static final byte[] ALIGN_LEFT = {0x1b, 0x61, 0x00};

    /**
     * 中间对齐
     */
    public static final byte[] ALIGN_CENTER = {0x1b, 0x61, 0x01};

    /**
     * 右对齐
     */
    public static final byte[] ALIGN_RIGHT = {0x1b, 0x61, 0x02};

    /**
     * 选择加粗模式
     */
    public static final byte[] BOLD = {0x1b, 0x45, 0x01};

    /**
     * 取消加粗模式
     */
    public static final byte[] BOLD_CANCEL = {0x1b, 0x45, 0x00};

    /**
     * 宽高加倍
     */
    public static final byte[] DOUBLE_HEIGHT_WIDTH = {0x1d, 0x21, 0x11};

    /**
     * 宽加倍
     */
    public static final byte[] DOUBLE_WIDTH = {0x1d, 0x21, 0x10};

    /**
     * 高加倍
     */
    public static final byte[] DOUBLE_HEIGHT = {0x1d, 0x21, 0x01};

    /**
     * 字体不放大
     */
    public static final byte[] NORMAL = {0x1d, 0x21, 0x00};

    /**
     * 设置默认行间距
     */
    public static final byte[] LINE_SPACING_DEFAULT = {0x1b, 0x32};


    /**
     * 打印纸一行最大的字节
     */
    private static final int LINE_BYTE_SIZE = 32;

    /**
     * 打印三列时，中间一列的中心线距离打印纸左侧的距离
     */
    private static final int LEFT_LENGTH = 16;

    /**
     * 打印三列时，中间一列的中心线距离打印纸右侧的距离
     */
    private static final int RIGHT_LENGTH = 16;

    /**
     * 打印三列时，第一列汉字最多显示几个文字
     */
    private static final int LEFT_TEXT_MAX_LENGTH = 5;
    private static OutputStream out;

    /**
     * 获取数据长度
     *
     * @ param msg
     * @ return
     */
    private static int getBytesLength(String msg) {
        return msg.getBytes(Charset.forName("GB2312")).length;
    }
    public static void setOutputStream(OutputStream o){
       out = o;
    }

    /**
     * 设置打印格式
     *
     * @ param command 格式指令
     */
    public static void selectCommand(byte[] command) {
        try {
            out.write(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 打印文字
     *
     * @param text 要打印的文字
     */
    public static void printText(String text) {
        try {
            byte[] data = text.getBytes("gbk");
            out.write(data, 0, data.length);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印三列
     *
     * @ param leftText   左侧文字
     * @ param middleText 中间文字
     * @ param rightText  右侧文字
     * @ return
     */
    public static String printThreeData(String leftText, String middleText, String rightText) {
        StringBuilder sb = new StringBuilder();
        // 左边最多显示 LEFT_TEXT_MAX_LENGTH 个汉字 + 两个点
        if (leftText.length() > LEFT_TEXT_MAX_LENGTH) {
            leftText = leftText.substring(0, LEFT_TEXT_MAX_LENGTH) + "..";
        }
        int leftTextLength = getBytesLength(leftText);
        int middleTextLength = getBytesLength(middleText);
        int rightTextLength = getBytesLength(rightText);

        sb.append(leftText);
        // 计算左侧文字和中间文字的空格长度
        int marginBetweenLeftAndMiddle = LEFT_LENGTH - leftTextLength - middleTextLength / 2;

        for (int i = 0; i < marginBetweenLeftAndMiddle; i++) {
            sb.append(" ");
        }
        sb.append(middleText);

        // 计算右侧文字和中间文字的空格长度
        int marginBetweenMiddleAndRight = RIGHT_LENGTH - middleTextLength / 2 - rightTextLength;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }

        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
        sb.delete(sb.length() - 1, sb.length()).append(rightText);
        return sb.toString();
    }

    /**
     * 打印两列
     *
     * @ param leftText  左侧文字
     * @ param rightText 右侧文字
     * @ return
     */
    public static String printTwoData(String leftText, String rightText) {
        StringBuilder sb = new StringBuilder();
        int leftTextLength = getBytesLength(leftText);
        int rightTextLength = getBytesLength(rightText);
        sb.append(leftText);

        // 计算两侧文字中间的空格
        int marginBetweenMiddleAndRight = LINE_BYTE_SIZE - leftTextLength - rightTextLength;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }
        sb.append(rightText);
        return sb.toString();
    }

}
