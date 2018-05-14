package com.jlkf.ego.activity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.umanalytics.UMUtils;
import com.jlkf.ego.utils.DialogUtil;
import com.jlkf.ego.utils.ShardeUtil;

/**
 * 设置
 *
 * @author 邓超桂
 * @date 创建时间：2017年7月11日 下午3:40:13
 */
public class SettingActivity extends com.jlkf.ego.base.BaseActivity implements View.OnClickListener {

    //title
    private LinearLayout ll_update_password;
    private LinearLayout ll_select_language;
    private LinearLayout ll_clear_cache;
    private LinearLayout ll_update_vesion;
    private LinearLayout ll_about_ego;

    private Button btn_outLogin;
    private Dialog dialog;
/*

    @Override
    public void iniActivityAnimation() {
        //滑动关闭activity
        addSwipeFinishLayout();
        //设置滑动开启/关闭
        setEnableGesture(true);
    }
*/

    @Override
    protected int getlayoutid() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_setting);

        ll_update_password = (LinearLayout) findViewById(R.id.ll_update_password);
        ll_select_language = (LinearLayout) findViewById(R.id.ll_select_language);
        ll_clear_cache = (LinearLayout) findViewById(R.id.ll_clear_cache);
        ll_update_vesion = (LinearLayout) findViewById(R.id.ll_update_vesion);
        ll_about_ego = (LinearLayout) findViewById(R.id.ll_about_ego);
        btn_outLogin = (Button) findViewById(R.id.btn_outLogin);

        ll_update_password.setOnClickListener(this);
        ll_select_language.setOnClickListener(this);
        ll_clear_cache.setOnClickListener(this);
        ll_update_vesion.setOnClickListener(this);
        ll_about_ego.setOnClickListener(this);
        btn_outLogin.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        BaseActivity.finish(SettingActivity.this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_back://返回
                onBackPressed();
                break;
            case R.id.ll_update_password://修改密码
                intent = new Intent(this, SafetyCheckActivity.class);
                BaseActivity.startActivity(intent, SettingActivity.this);
                break;
            case R.id.ll_select_language://切换语言
                intent = new Intent(this, SelectAppLanguageActivity.class);
                BaseActivity.startActivity(intent, SettingActivity.this);
                break;
            case R.id.ll_clear_cache://清除缓存
                clearCache();
                break;
            case R.id.ll_update_vesion://版本更新
                DialogUtil.getTitleWithOneButton(this, getResources().getString(R.string.newversion), getResources().getString(R.string.haode), null).show();
//                DialogUtil.LoadingAppDia(this).show();
                break;
            case R.id.ll_about_ego://关于易购
                intent = new Intent(this, AboutUsActivity.class);
                BaseActivity.startActivity(intent, SettingActivity.this);
                break;
            case R.id.btn_outLogin://退出登录
                dialog = DialogUtil.getTitleWithTwoButtonDiaLog(this,
                        getResources().getString(R.string.out_des),
                        getResources().getString(R.string.ok), getResources().getString(R.string.no), this, this);
                dialog.show();
                break;
            case R.id.tv_cancel:
                dialog.dismiss();
                break;
            case R.id.tv_commit:
                getUser().setCookie(false);
                getUser().setUPassword("");
                ShardeUtil.putUser(getUser());
                Intent intent1 = new Intent(this, MainActivity.class);
                intent1.putExtra("tag", "setting");
                startActivity(intent1);
                dialog.dismiss();


                break;
        }
    }

    /**
     * 清理缓存
     */
    private void clearCache() {
        final View v = LayoutInflater.from(this).inflate(R.layout.dia_clean_chach, null);
        final Dialog loadingDialog = new AlertDialog.Builder(this, R.style.noBgDiaStyle).setView(v).create();
        final LinearLayout linCacher = (LinearLayout) v.findViewById(R.id.lin_clear);
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.iv_loading);
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.loading);
        animator.setTarget(spaceshipImage);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(true);// 用“返回键”取消
        loadingDialog.getWindow().setBackgroundDrawable(new BitmapDrawable());
        final TextView tv = (TextView) v.findViewById(R.id.tv_clear_success);
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setVisibility(View.VISIBLE);
                linCacher.setVisibility(View.GONE);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismiss();
                    }
                }, 1000);
            }
        }, 2000);
        loadingDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMUtils.onResumeUMActivity(this, "Setting_page");
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.onPauseUMActivity(this, "Setting_page");
    }
}
