package com.jlkf.ego.newpage.weidget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jlkf.ego.R;

/**
 * Created by zcs on 2018/5/15.
 */

public class IntegerBar extends View {
    private float mPercentage = 0;//百分比
    private Paint mPaint;

    public IntegerBar(Context context) {
        this(context, null);
    }

    public IntegerBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IntegerBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Matrix matrix = new Matrix();
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.shape_integer_bg), 0, 0, mPaint);
//        canvas.drawBitmap();
    }
}
