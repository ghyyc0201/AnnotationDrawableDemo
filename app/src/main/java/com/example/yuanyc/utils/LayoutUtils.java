package com.example.yuanyc.utils;

import android.graphics.Rect;

/**
 * Created by yuanyc on 2016/2/24.
 */
public class LayoutUtils {
    public static final int CENTER = 0;
    public static final int CENTER_VERTICAL = 2;
    public static final int CENTER_HORIZONTAL = 1;

    /**
     * 给定父矩形和子矩形的宽高，自动将子矩形修改为居中区域
     * 都是一些坐标点的运算
     *
     * @param centerType 1:水平居中 2:垂直居中 0:全部居中
     */
    public static Rect getCenter(Rect outer, Rect inner, int centerType) {
        int w = inner.width();
        int h = inner.height();
        switch (centerType) {
            case CENTER:
                inner.top = (outer.height() - h) / 2 + outer.top;
                inner.bottom = inner.top + h;
            case CENTER_HORIZONTAL:
                inner.left = (outer.width() - w) / 2 + outer.left;
                inner.right = inner.left + w;
                break;
            case CENTER_VERTICAL:
                inner.top = (outer.height() - h) / 2 + outer.top;
                inner.bottom = inner.top + h;
                break;
        }
        return inner;
    }
}
