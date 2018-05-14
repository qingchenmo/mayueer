package com.jlkf.ego.widget.swipefinish.baseactivity;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jlkf.ego.crash.ExitAppUtils;
import com.jlkf.ego.widget.swipefinish.widget.SwipeFinishLayout;


/**
 * 右滑退出
 *
 * @author Administrator
 */
public abstract class BaseSwipeFinishFragmentActivity extends AppCompatActivity implements SwipeFinishLayout.SwipeToCloseLayoutAction {

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
    protected void onResume() {
        super.onResume();
        ExitAppUtils.getInstance().setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    	ExitAppUtils.getInstance().delActivity(this);
        if (mSwipeToCloseLayout != null) {
            mSwipeToCloseLayout.removeAllViews();
            mSwipeToCloseLayout.setSwipeToCloseLayoutAction(null);
            mSwipeToCloseLayout = null;
        }
    }

//    /**
//     * 关闭执行的动画
//     */
//    @Override
//    public final void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.push_none, R.anim.push_up_out);
//    }
//
//    /**
//     * 打开activity执行的方法
//     */
//    @Override
//    public void startActivity(Intent intent) {
//        super.startActivity(intent);
//        overridePendingTransition(R.anim.push_up_in, R.anim.push_none);
//    }

}
