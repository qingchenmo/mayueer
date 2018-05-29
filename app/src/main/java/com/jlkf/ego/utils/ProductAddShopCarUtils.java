package com.jlkf.ego.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jlkf.ego.R;
import com.jlkf.ego.adapter.ProductQuickAdapter;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.MyBaseBean;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.bean.ShopCarGoodsBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;

import static com.jlkf.ego.utils.ShardeUtil.getUser;

/**
 * @autor zcs
 * @time 2017/8/30
 * @describe
 */

public class ProductAddShopCarUtils {
    private static ProductAddShopCarUtils mUtils;

    private ProductAddShopCarUtils() {

    }

    public static ProductAddShopCarUtils getInstance() {
        if (mUtils == null) {
            mUtils = new ProductAddShopCarUtils();
        }
        return mUtils;
    }

    public void EditShopCar(boolean add, final ProductListBean.DataBean info, final TextView etSelectNum,
                            Activity activity, final OnAddShopCarListener listener, final ImageView view, final boolean edit, final String str) {
        if (!edit) {
            if (add) {
                if (info.getSelectNum() >= Integer.parseInt(info.getOnhand())) {
                    ToastUtil.show("库存不足");
                    return;
                }
                //判断是否要通过包增加
                if (info.isLargePackage()) {
                    info.setSelectNum(info.getSelectNum() + Integer.parseInt(info.getUUX()));
//                    info.setLargePackage(false);
                } else if (info.isSmallPackage()) {
                    info.setSelectNum(info.getSelectNum() + Integer.parseInt(info.getUUB()));
//                    info.setSmallPackage(false);
                } else {
                    info.setSelectNum(info.getSelectNum() + 1);
                }
                if (info.getSelectNum() > Integer.parseInt(info.getOnhand())) {
                    ToastUtil.show("库存不足");
                    info.setSelectNum(Integer.parseInt(info.getOnhand()));
                    etSelectNum.setText(String.valueOf(info.getSelectNum()));
                } else {
                    etSelectNum.setText(String.valueOf(info.getSelectNum()));
                }
            } else {
                if (info.getSelectNum() == 0) {
//                    ToastUtil.show("已选商品数量为0");
                    return;
                }
                if (info.isLargePackage()) {
                    info.setSelectNum(info.getSelectNum() - Integer.parseInt(info.getUUX()));
                } else if (info.isSmallPackage()) {
                    info.setSelectNum(info.getSelectNum() - Integer.parseInt(info.getUUB()));
                } else {
                    info.setSelectNum(info.getSelectNum() - 1);
                }
                if (info.getSelectNum() < 0) info.setSelectNum(0);
                etSelectNum.setText(String.valueOf(info.getSelectNum()));
            }
        }
        JSONObject object = new JSONObject();
        object.put("name", info.getItemname());
        object.put("price", info.getPrice());
        object.put("logo", info.getPicturname());
        object.put("itemcode", info.getItemcode());
        object.put("quantity", edit ? str : (info.getSelectNum() + ""));
        object.put("codebars", info.getCodebars());
        object.put("brandname", info.getpName());
        object.put("brandId", info.getUPp());
        object.put("uId", getUser().getUId() + "");
        HttpUtil.getInstacne(activity).post2(Urls.shopInsert, MyBaseBean.class, object.toString(), new HttpUtil.OnCallBack<MyBaseBean>() {
            @Override
            public void success(MyBaseBean data) {
//                ToastUtil.show(data.getMsg());
                if (edit) info.setSelectNum(Integer.parseInt(str));
                if (edit) etSelectNum.setText(info.getSelectNum() + "");
                ShopCarGoodsBean bean = new ShopCarGoodsBean();
                bean.setNum(info.getSelectNum());
                bean.setGoodsCode(info.getItemcode());
                statisShopNum(bean);
                if (listener != null)
                    listener.addShopCarListener(view);
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtil.show(msg);
            }
        });
    }

    /**
     * 商品列表页增加购物车
     *
     * @param add         true为增加商品，false为减少商品
     * @param info        商品的信息
     * @param etSelectNum 要显示商品的输入框
     * @param activity
     */
    public void EditShopCar(boolean add, final ProductListBean.DataBean info, TextView etSelectNum,
                            Activity activity, final OnAddShopCarListener listener, final ImageView view) {
        EditShopCar(add, info, etSelectNum, activity, listener, view, false, "0");
    }

    /**
     * 收藏或取消收藏
     *
     * @param activity
     * @param box
     * @param info
     */
    public void connectionProduct(Activity activity, final CheckBox box, ProductListBean.DataBean info) {
        connectionProduct(activity, box, info, null);
    }

    public void connectionProduct(final Activity activity, final CheckBox box, final ProductListBean.DataBean info, final ProductQuickAdapter adapter) {
        JSONObject object = new JSONObject();
        object.put("uId", MyApplication.getmUserBean().getUId() + "");
        object.put("itemCode", info.getItemcode());
        object.put("type", box.isChecked() ? "1" : "2");
        HttpUtil.getInstacne(activity).post2(Urls.coller, MyBaseBean.class, object.toString(), new HttpUtil.OnCallBack<MyBaseBean>() {
            @Override
            public void success(MyBaseBean data) {
                if (adapter != null)
                    adapter.notifyDataSetChanged();
                info.setIsColler(box.isChecked() ? "0" : "1");
                if (data.getMsg().equals("成功取消收藏")) {
                    ToastUti.show(activity.getResources().getString(R.string.cgqxsc));
                } else if (data.getMsg().equals("已收藏")) {
                    ToastUti.show(activity.getResources().getString(R.string.ysc));
                }
            }

            @Override
            public void onError(String msg, int code) {
                box.setChecked(!box.isChecked());
            }
        });
    }


    public void showAnimation(Context context, ImageView goodsImage, final FrameLayout showCarView, final ViewGroup layout) {
        // 贝塞尔曲线中间过程点坐标
        final float[] mCurrentPosition = new float[2];
        // 路径测量
        final PathMeasure mPathMeasure;

        // 创造出执行动画的主题goodsImg（这个图片就是执行动画的图片,从开始位置出发,经过一个抛物线（贝塞尔曲线）,移动到购物车里）
        final ImageView goods = new ImageView(context);
        goods.setImageDrawable(goodsImage.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        layout.addView(goods, params);

        // 得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        layout.getLocationInWindow(parentLocation);

        // 得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        goodsImage.getLocationInWindow(startLoc);

        // 得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        showCarView.getLocationInWindow(endLoc);

        // 开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + goodsImage.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + goodsImage.getHeight() / 2;

        // 商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLoc[0] - parentLocation[0] + showCarView.getWidth() / 5;
        float toY = endLoc[1] - parentLocation[1];

        // 开始绘制贝塞尔曲线
        Path path = new Path();
        // 移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        // 使用二阶贝塞尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        // mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);

        // 属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(500);

        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                // mCurrentPosition此时就是中间距离点的坐标值
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });

        // 开始执行动画
        valueAnimator.start();

        // 动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 购物车商品数量加1
                // 把执行动画的商品图片从父布局中移除
                layout.removeView(goods);
                ScaleAnimation anim = new ScaleAnimation(1f, 1.5f, 1f, 1.5f, 1f, 1f);
                anim.setDuration(200);
                showCarView.setAnimation(anim);
                anim.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    public interface OnAddShopCarListener {
        void addShopCarListener(ImageView productImgView);
    }

    /**
     * 设置购物车数量提醒
     *
     * @param tv
     */
    public void setShopCarNum(TextView tv) {

        int size = MyApplication.mAppApplication.mGoodsBeen.size();
        int num = 0;
        for (int i = 0; i < size; i++) {
            num = num + MyApplication.mAppApplication.mGoodsBeen.get(i).getNum();
        }
        tv.setText(num + "");
        tv.setVisibility(num == 0 ? View.GONE : View.VISIBLE);
        Log.e("tag", num + "");
    }

    /**
     * 编辑购物车中的商品信息
     *
     * @param bean
     */
    public void statisShopNum(ShopCarGoodsBean bean) {
        int size = MyApplication.mAppApplication.mGoodsBeen.size();
        int position = -1;
        boolean isNeedAdd = true;
        for (int i = 0; i < size; i++) {
            ShopCarGoodsBean goodsBean = MyApplication.mAppApplication.mGoodsBeen.get(i);
            if (bean.getGoodsCode().equals(goodsBean.getGoodsCode())) {
                isNeedAdd = false;
                goodsBean.setNum(bean.getNum());
                if (goodsBean.getNum() == 0) {
                    position = i;
                }
            }

        }
        if (isNeedAdd) {
            MyApplication.mAppApplication.mGoodsBeen.add(bean);
        }
        if (position != -1) {
            MyApplication.mAppApplication.mGoodsBeen.remove(position);
        }
    }

    private Ringtone r;
    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    /**
     * 系统提示音
     *
     * @param context
     */
    public void startAlarm(Context context) {
        if (notification == null) return;
        if (r == null)
            r = RingtoneManager.getRingtone(context, notification);
        if (r.isPlaying()) r.stop();
        r.play();
    }

    /**
     * 获取商品在购物车中的数量
     *
     * @param itemCode
     * @return
     */
    public int getGoodsNumInShopCar(String itemCode) {
        int size = MyApplication.mAppApplication.mGoodsBeen.size();
        for (int i = 0; i < size; i++) {
            ShopCarGoodsBean bean = MyApplication.mAppApplication.mGoodsBeen.get(i);
            if (bean.getGoodsCode().equals(itemCode)) {
                return bean.getNum();
            }
        }
        return 0;
    }
}
