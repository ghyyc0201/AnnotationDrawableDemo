package com.example.yuanyc.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.yuanyc.annotationdrawabledemo.R;
import com.example.yuanyc.utils.LayoutUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yuanyc on 2016/2/29.
 */
public class MyView extends View {
    private static final int WAHT = 0;
    /**
     * 用来表示gps展示图片的编号
     */
    private int gpsAnimationId = 0;
    /**
     * 对应图片的id资源
     */
    private int[] gpsAnimationIds = {R.mipmap.navi_big_gps_state_1, R.mipmap.navi_big_gps_state_2, R.mipmap.navi_big_gps_state_3, R.mipmap.navi_big_gps_state_4};
    /**
     * 用于放图片drawable的数组集合
     */
    private Drawable[] gpsAnimationDrawable;
    /**
     * 丢星文字
     */
    private String gpsTestStr = null;
    /**
     * resources
     */
    private Resources resources = null;
    /**
     * 构建画笔对象
     */
    private TextPaint textPaint = null;
    /**
     * gps动画位置
     */
    private Rect gpsAnimationBounds = null;
    /**
     * gps文字绘制范围
     */
    private Rect gpsTextRect = null;
    /**
     * 文字尺寸
     */
    private int gpsTestSize = 0;
    /**
     * 文字与图片的边距
     */
    private int gpsTestMargin = 0;
    /**
     * 图片左边距
     */
    private int gpsTestLeftMargin = 0;
    /**
     * 动态图片的矩形区域
     */
    private Rect gpsRect = null;
    private int backColor = 0;
    private NaviTitleDrawable naviTitleDrawable = null;
    /**
     * 定时器
     */
    private Timer gpsAnimationTimer = null;
    private TimerTask gpsAnimationTask = new TimerTask() {
        @Override
        public void run() {
            Message message = Message.obtain();
            message.what = WAHT;
            handler.sendMessage(message);
            //资源id加1
            gpsAnimationId++;
            //简单算法，取模之后，保证每次gpsAnimationId持续从0.1.2.3四个值中循环
            gpsAnimationId %= gpsAnimationIds.length;
        }
    };
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WAHT:
                    //关键方法，此方法一旦电泳就会重绘，也就是会执行onDraw方法
                    invalidate();
                    break;
            }
        }
    };

    /**
     * 获取naviTitleDrawable
     * @return
     */
    public NaviTitleDrawable getNaviTitleDrawable() {
        return naviTitleDrawable;
    }

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        resources = context.getResources();
        gpsTestStr = resources.getString(R.string.str_default);
        gpsAnimationDrawable = new Drawable[gpsAnimationIds.length];
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        gpsAnimationTimer = new Timer();
        gpsAnimationBounds = new Rect();
        gpsTextRect = new Rect();
        gpsRect = new Rect(0, 0, getPxByDimens(R.dimen.IS6), getPxByDimens(R.dimen.IS6));
        backColor = resources.getColor(R.color.colorPrimary);
        naviTitleDrawable = new NaviTitleDrawable();
        for (int i = 0; i < gpsAnimationDrawable.length; i++) {
            gpsAnimationDrawable[i] = resources.getDrawable(gpsAnimationIds[i]);
        }
        gpsAnimationTimer.schedule(gpsAnimationTask, 0, 500);
    }

    /**
     * 获取定义在 dimens 的尺寸
     *
     * @param id
     * @return 像素值
     */
    private int getPxByDimens(int id) {
        return resources.getDimensionPixelSize(id);
    }


    public class NaviTitleDrawable extends Drawable {

        public void draw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setColor(backColor);
            canvas.drawRect(getMyBounds(), paint);
            drawGPS(canvas);
        }

        /**
         * 画图片和文字
         * @param canvas
         */
        private void drawGPS(Canvas canvas) {
            gpsTestSize = getPxByDimens(R.dimen.F5);
            gpsTestMargin = getPxByDimens(R.dimen.M1);
            gpsTestLeftMargin = getPxByDimens(R.dimen.OM6);
            gpsAnimationBounds.set(gpsRect);

            textPaint.setTextSize(gpsTestSize);
            textPaint.getTextBounds(gpsTestStr, 0, gpsTestStr.length(), gpsTextRect);
            //利用工具类居中显示
            LayoutUtils.getCenter(getMyBounds(), gpsAnimationBounds, LayoutUtils.CENTER_VERTICAL);
            //当设置CENTER_VERTICAL垂直居中的时候，设置图片距离左边的Margin值，设置文字距离图片的Margin值
            gpsAnimationBounds.offset(gpsTestLeftMargin, 0);
            gpsTextRect.offset(gpsAnimationBounds.right + gpsTestMargin, 0);
            System.out.println("gpsAnimationId:"+gpsAnimationId);
            System.out.println("gpsAnimationDrawable[gpsAnimationId]:"+gpsAnimationDrawable[gpsAnimationId]);
            gpsAnimationDrawable[gpsAnimationId].setBounds(gpsAnimationBounds);
            //绘制图片和文字
            gpsAnimationDrawable[gpsAnimationId].draw(canvas);
            canvas.drawText(gpsTestStr, gpsTextRect.left, gpsTextRect.bottom, textPaint);
        }

        @Override
        public void setAlpha(int alpha) {

        }

        @Override
        public void setColorFilter(ColorFilter cf) {

        }

        @Override
        public int getOpacity() {
            return 0;
        }
    }

    private Rect getMyBounds() {
        return naviTitleDrawable.getBounds();
    }
}
