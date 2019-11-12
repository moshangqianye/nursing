package com.jqsoft.livebody_verify_lib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * @author yedong
 * @date 2019/5/27
 * 图片添加水印工具类
 */
public class WaterMarkBitmapUtil {

    /**
     *  图片添加文字水印
     * @param context 上下文
     * @param src  资源图片
     * @param text  水印文字
     * @return  返回bitmap
     */
    public static Bitmap createWaterMaskBitmap(Context context, Bitmap src, String text) {
        if (src == null) return null;
        int width = src.getWidth();  // 图片宽度
        int height = src.getHeight();  // 图片高度
        Bitmap newB = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newB);
        canvas.drawBitmap(src, 0, 0, null);
        canvas.save();
        canvas.rotate(-30);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(45, 255, 255, 255));
        paint.setTextSize(sp2px(context, 13));
        paint.setDither(true);
        float textWidth = paint.measureText(text);
        int index = 0;
        for (int positionY = height / 10; positionY <= height; positionY += height / 10 + 80) {
            float fromX = -width + (index++ % 5) * textWidth;
            for (float positionX = fromX; positionX < width; positionX += textWidth * 1.5) {
                int spacing = 0;//间距
                for (int i = 0; i < 2; i++) {
                    canvas.drawText(text, positionX + 20, positionY + spacing, paint);
                    spacing = spacing + 50;
                }
            }
        }
        canvas.restore();
        return newB;
    }

    /**
     * 尺寸转化
     * @param context  上下文
     * @param spValue sp
     * @return 像素
     */
    private static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
