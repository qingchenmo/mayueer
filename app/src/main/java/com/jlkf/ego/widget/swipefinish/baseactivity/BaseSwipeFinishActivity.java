package com.jlkf.ego.widget.swipefinish.baseactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.jlkf.ego.R;
import com.jlkf.ego.crash.ExitAppUtils;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.widget.swipefinish.widget.SwipeFinishLayout;


/**
 * 右滑退出
 *
 * @author Administrator
 */
public abstract class BaseSwipeFinishActivity extends AppCompatActivity implements SwipeFinishLayout.SwipeToCloseLayoutAction {

    private SwipeFinishLayout mSwipeToCloseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExitAppUtils.getInstance().addActivity(this);
    }

    /**
     * 此方法要在setContentView之后执行，用户添加右滑退出的布局。
     */
    protected void addSwipeFinishLayout() {
        mSwipeToCloseLayout = new SwipeFinishLayout(this);
        mSwipeToCloseLayout.attachToActivity(this);
        mSwipeToCloseLayout.setSwipeToCloseLayoutAction(this);
    }

    /**
     * 是否可滑动退出
     *
     * @param enableGesture
     */
    public void setEnableGesture(boolean enableGesture) {
        if (mSwipeToCloseLayout != null) {
            mSwipeToCloseLayout.setEnableGesture(enableGesture);
        }
    }

    /**
     * 全屏时滑动的处理
     *
     * @param fullScreen
     */
    public void setActivityFullScreen(boolean fullScreen) {
        if (mSwipeToCloseLayout != null) {
            mSwipeToCloseLayout.setActivityFullScreen(fullScreen);
        }
    }

    /**
     * 向右滑动是否可关闭activity
     */
    @Override
    public boolean onScrollToClose() {
        return true;
    }

    /**
     * 是否点击在可左右滑动的views里面
     */
    @Override
    public boolean inChild(Rect rect, Point point) {
        return false;
    }

    @Override
    public void onCloseAction() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSwipeToCloseLayout != null) {
            mSwipeToCloseLayout.removeAllViews();
            mSwipeToCloseLayout.setSwipeToCloseLayoutAction(null);
            mSwipeToCloseLayout = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ExitAppUtils.getInstance().setCurrentActivity(this);
    }

    /**
     * 关闭执行的动画
     */
    @Override
    public final void finish() {
        hideInput(this);
        ExitAppUtils.getInstance().delActivity(this);
        super.finish();
        overridePendingTransition(R.anim.push_none, R.anim.push_up_out);
    }

    /**
     * 打开activity执行的方法
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_none);
    }


    /**
     * 隐藏键盘
     *
     * @param mContext
     */
    public static void hideInput(Activity mContext) {
        try {
            ((InputMethodManager) mContext
                    .getSystemService(mContext.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(mContext.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (RuntimeException re) {
            AppLog.Logi("dcg", "re==" + re);
        }
    }
}
