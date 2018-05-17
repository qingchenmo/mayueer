package com.jlkf.ego.newpage.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zcs on 2018/4/3.
 *
 * @describe: 文本输入Utils
 */

public class TextInputUtils {
    /**
     * 为TextView填充文字,避免空指针异常
     *
     * @param view         要填充的控件
     * @param charSequence 填充的内容
     * @return 填充结果：内容为空返回false,否则返回true
     */
    public static boolean setText(TextView view, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        } else {
            view.setText(charSequence);
            return true;
        }
    }

    /**
     * 获取多种样式的文本
     *
     * @param sequence 文本内容
     * @param what     字符串风格
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @param flags    Spanned.SPAN_INCLUSIVE_EXCLUSIVE :从起始下标到终了下标，包括起始下标
     * @param flags    Spanned.SPAN_INCLUSIVE_INCLUSIVE :从起始下标到终了下标，同时包括起始下标和终了下标
     * @param flags    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE :从起始下标到终了下标，但都不包括起始下标和终了下标
     * @param flags    Spanned.SPAN_EXCLUSIVE_INCLUSIVE :从起始下标到终了下标，包括终了下标
     * @return SpannableString
     */
    public static SpannableString getMoreFormatText(CharSequence sequence, Object what,
                                                    int start, int end, int flags) {
        SpannableString spannableString = new SpannableString(sequence);
        spannableString.setSpan(what, start, end, flags);
        return spannableString;
    }

    /**
     * 获取多种样式的文本
     *
     * @param sequence 文本内容
     * @param what     字符串风格
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @return SpannableString
     */
    public static SpannableString getMoreFormatText(CharSequence sequence, Object what,
                                                    int start, int end) {
        return getMoreFormatText(sequence, what, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }


    /**
     * 为文本中部分字体设置前景色
     *
     * @param sequence 文本内容
     * @param color    要显示颜色
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @param flags    Spanned.SPAN_INCLUSIVE_EXCLUSIVE :从起始下标到终了下标，包括起始下标
     * @param flags    Spanned.SPAN_INCLUSIVE_INCLUSIVE :从起始下标到终了下标，同时包括起始下标和终了下标
     * @param flags    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE :从起始下标到终了下标，但都不包括起始下标和终了下标
     * @param flags    Spanned.SPAN_EXCLUSIVE_INCLUSIVE :从起始下标到终了下标，包括终了下标
     * @return SpannableString
     */
    public static SpannableString getMoreColorText(CharSequence sequence, @ColorInt int color,
                                                   int start, int end, int flags) {
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        return getMoreFormatText(sequence, colorSpan, start, end, flags);
    }

    /**
     * 为文本中部分字体设置前景色
     *
     * @param sequence 文本内容
     * @param color    要显示颜色
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @return SpannableString
     */
    public static SpannableString getMoreColorText(CharSequence sequence, @ColorInt int color,
                                                   int start, int end) {
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        return getMoreFormatText(sequence, colorSpan, start, end);
    }

    /**
     * 为文本中部分字体设置前景色
     *
     * @param sequence 文本内容
     * @param color    要显示颜色
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @return SpannableString
     */
    public static SpannableString getMoreBgColorText(CharSequence sequence, @ColorInt int color,
                                                     int start, int end) {
        BackgroundColorSpan colorSpan = new BackgroundColorSpan(color);
        return getMoreFormatText(sequence, colorSpan, start, end);
    }

    /**
     * 为文本设置不同大小字体
     *
     * @param sequence 文本内容
     * @param size     字体大小
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @return SpannableString
     */
    public static SpannableString getMoreSizeText(CharSequence sequence, float size,
                                                  int start, int end) {
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.2f);
        return getMoreFormatText(sequence, sizeSpan, start, end);
    }

    /**
     * 为文本设置删除线
     *
     * @param sequence 文本内容
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @return SpannableString
     */
    public static SpannableString getDeleteLineText(CharSequence sequence, int start, int end) {
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        return getMoreFormatText(sequence, strikethroughSpan, start, end);
    }

    /**
     * 为文本设置下划线
     *
     * @param sequence 文本内容
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @return SpannableString
     */
    public static SpannableString getUnderLineText(CharSequence sequence, int start, int end) {
        UnderlineSpan underlineSpan = new UnderlineSpan();
        return getMoreFormatText(sequence, underlineSpan, start, end);
    }

    /**
     * 为文本设置上标
     *
     * @param sequence 文本内容
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @return SpannableString
     */
    public static SpannableString getSuperScriptText(CharSequence sequence, int start, int end) {
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        return getMoreFormatText(sequence, superscriptSpan, start, end);
    }

    /**
     * 为文本设置下标
     *
     * @param sequence 文本内容
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @return SpannableString
     */
    public static SpannableString getSubScriptText(CharSequence sequence, int start, int end) {
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        return getMoreFormatText(sequence, subscriptSpan, start, end);
    }

    /**
     * 为文本设置粗体
     *
     * @param sequence 文本内容
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @return SpannableString
     */
    public static SpannableString getBoldText(CharSequence sequence, int start, int end) {
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        return getMoreFormatText(sequence, styleSpan, start, end);
    }

    /**
     * 为文本设置斜体
     *
     * @param sequence 文本内容
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @return SpannableString
     */
    public static SpannableString getItalicText(CharSequence sequence, int start, int end) {
        StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
        return getMoreFormatText(sequence, styleSpan, start, end);
    }

    /**
     * 为文本设置图片
     *
     * @param sequence 文本内容
     * @param drawable 图片
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @return SpannableString
     */
    public static SpannableString getItalicText(CharSequence sequence, Drawable drawable,
                                                int start, int end) {
        ImageSpan imageSpan = new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE){
            @Override
            public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
                try {
                    Drawable b = getDrawable();
                    canvas.save();
                    int transY = 0;
                    transY = ((bottom-top) - b.getBounds().bottom)/2+top;
                    canvas.translate(x, transY);
                    b.draw(canvas);
                    canvas.restore();
                } catch (Exception e) {
                }
            }
        };
        return getMoreFormatText(sequence, imageSpan, start, end);
    }

    /**
     * 为文本设置图片
     *
     * @param sequence 文本内容
     * @param drawable 图片
     * @param rect     图片大小
     * @param start    开始的字符下标
     * @param end      结束的字符下标
     * @return SpannableString
     */
    public static SpannableString getItalicText(CharSequence sequence, Drawable drawable, Rect rect,
                                                int start, int end) {
        drawable.setBounds(rect);
        ImageSpan imageSpan = new ImageSpan(drawable);
        return getMoreFormatText(sequence, imageSpan, start, end);
    }

    /**
     * 为文本设置可点击
     *
     * @param sequence 文本内容
     * @param start    开始的字符的下标
     * @param end      结束的字符下标
     * @param listener 点击回调
     * @return SpannableString
     */
    public static SpannableString getClickText(CharSequence sequence, int start, int end,
                                               final View.OnClickListener listener) {
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                listener.onClick(widget);
            }
        };
        return getMoreFormatText(sequence, clickableSpan, start, end);
    }

    /**
     * 为文本设置可点击并且没有下划线
     *
     * @param sequence 文本内容
     * @param start    开始的字符的下标
     * @param end      结束的字符下标
     * @param listener 点击回调
     * @return SpannableString
     */
    public static SpannableString getClickNoLineText(CharSequence sequence, int start, int end,
                                                     final View.OnClickListener listener) {
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                listener.onClick(widget);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        return getMoreFormatText(sequence, clickableSpan, start, end);
    }
}