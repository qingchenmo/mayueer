package com.jlkf.ego.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.jlkf.ego.R;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class FavoritesDailog extends Dialog {


    public FavoritesDailog(@NonNull Context context) {
        this(context, R.style.shopDialog);
    }

    public FavoritesDailog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_favorites);

        getWindow().setBackgroundDrawable(new BitmapDrawable());
    }
}
