package com.jlkf.ego.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.newpage.activity.ValidationActivity;
import com.jlkf.ego.newpage.bean.EventBean;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.HttpUtils;
import com.jlkf.ego.utils.CompanyUtil;
import com.jlkf.ego.utils.DialogUtil;
import com.jlkf.ego.utils.ProductAddShopCarUtils;
import com.jlkf.ego.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jlkf.ego.R.id.lin_product_package_large;
import static com.jlkf.ego.R.id.lin_product_package_small;

/**
 * @author zcs
 *         商品详情页面
 */
public class ProductInfoActivity extends BaseActivity implements View.OnClickListener, ProductAddShopCarUtils.OnAddShopCarListener {
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_title_server)
    ImageView mIvTitleServer;
    @BindView(R.id.iv_title_connection)
    ImageView mIvTitleConnection;
    @BindView(R.id.iv_goods_img1)
    ImageView mIvGoodsImg1;
    @BindView(R.id.tv_goods_name)
    TextView mTvGoodsName;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_sale_num)
    TextView mTvSaleNum;
    @BindView(R.id.rb_connection)
    CheckBox mRbConnection;
    @BindView(R.id.tv_goods_code)
    TextView mTvGoodsCode;
    @BindView(R.id.tv_goods_detail)
    TextView mTvGoodsDetail;
    @BindView(R.id.lin_goods_img)
    LinearLayout mLinGoodsImg;
    @BindView(R.id.lin_size)
    LinearLayout mLinSize;
    @BindView(R.id.iv_product_package_large)
    ImageView mIvProductPackageLarge;
    @BindView(R.id.tv_product_num_large)
    TextView mTvProductNumLarge;
    @BindView(lin_product_package_large)
    LinearLayout mLinProductPackageLarge;
    @BindView(R.id.iv_product_package_small)
    ImageView mIvProductPackageSmall;
    @BindView(R.id.tv_product_num_small)
    TextView mTvProductNumSmall;
    @BindView(lin_product_package_small)
    LinearLayout mLinProductPackageSmall;
    @BindView(R.id.iv_product_num_sub)
    ImageView mIvProductNumSub;
    @BindView(R.id.et_product_select_num)
    TextView mEtProductSelectNum;
    @BindView(R.id.iv_product_num_add)
    ImageView mIvProductNumAdd;
    @BindView(R.id.lin_parent_size)
    LinearLayout mLinParentSize;
    @BindView(R.id.tv_car_num)
    TextView mTvCarNum;
    @BindView(R.id.fl_shop_car)
    FrameLayout mFlShopCar;
    @BindView(R.id.rl_partent)
    RelativeLayout mRlPartent;
    @BindView(R.id.lin_edit)
    LinearLayout mLinEdit;
    @BindView(R.id.tv_activity)
    TextView mTvActivity;
    @BindView(R.id.tv_event_1)
    TextView mTvEvent1;
    @BindView(R.id.lin_event)
    LinearLayout mLinEvent;
    @BindView(R.id.tv_event_end_time)
    TextView mTvEventEndTime;
    private ProductListBean.DataBean mBean;
    private EventBean mEventBean;

    @Override
    public void initView() {
        setContentView(R.layout.activity_product_info);
        ButterKnife.bind(this);
        mIvTitleConnection.setVisibility(View.GONE);
        mFlShopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1 = new Intent(ProductInfoActivity.this, MainActivity.class);
                Intent intent1 = new Intent(ProductInfoActivity.this, ShapCarActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent1.putExtra("tag", "againBuy");
                intent1.putExtra("type", "1");
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProductAddShopCarUtils.getInstance().setShopCarNum(mTvCarNum);
    }

    @Override
    public void initData() {
        super.initData();
        Map<String, String> map = new HashMap<>();
        map.put("itemCode", getIntent().getStringExtra("itemCode"));
        map.put("area", MyApplication.getmUserBean().getArea());
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        HttpUtil.getInstacne(this).get2(Urls.getOitmViewByCode, ProductListBean.class, map, new HttpUtil.OnCallBack<ProductListBean>() {
            @Override
            public void success(ProductListBean data) {
                mBean = data.getData().get(0);
                setView();
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtil.show("获取商品详情失败");
            }
        });
        ApiManager.getOiat(getIntent().getStringExtra("itemCode"), this, new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                mEventBean = JSON.parseArray(response, EventBean.class).get(0);
                mTvActivity.setVisibility(View.VISIBLE);
                String str[] = new String[]{"秒杀", "赠品", "预定"};
                mTvActivity.setText(str[mEventBean.getType() - 1]);
                mTvGoodsName.setText("            " + mBean.getItemname());
                mLinEvent.setVisibility(View.VISIBLE);
                mTvEvent1.setText(str[mEventBean.getType() - 1] + ":" + mEventBean.getAtname());
                mTvEventEndTime.setText("活动截止时间：" + new SimpleDateFormat("yyyy.MM.dd").format(Long.valueOf(mEventBean.getEnd() + "000")));
            }

            @Override
            public void onError(String msg) {
                mTvActivity.setVisibility(View.GONE);
            }
        });
    }

    private void setView() {
        mTvTitle.setText(mBean.getItemname());
        Glide.with(this).load(mBean.getPicturname()).fitCenter().placeholder(R.drawable.icon_img_load).error(R.drawable.icon_img_load_failed).into(mIvGoodsImg1);
        List<String> imgList = new ArrayList<>();
        if (mBean.getAttachment1() != null && !mBean.getAttachment1().isEmpty())
            imgList.add(mBean.getAttachment1());
        if (mBean.getAttachment2() != null && !mBean.getAttachment2().isEmpty())
            imgList.add(mBean.getAttachment2());
        if (mBean.getAttachment3() != null && !mBean.getAttachment3().isEmpty())
            imgList.add(mBean.getAttachment3());
        if (mBean.getAttachment4() != null && !mBean.getAttachment4().isEmpty())
            imgList.add(mBean.getAttachment4());
        if (mBean.getAttachment5() != null && !mBean.getAttachment5().isEmpty())
            imgList.add(mBean.getAttachment5());
        if (mBean.getAttachment6() != null && !mBean.getAttachment6().isEmpty())
            imgList.add(mBean.getAttachment6());
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mIvGoodsImg1.getLayoutParams());
        params.height = width;
        mIvGoodsImg1.setLayoutParams(params);
        for (int i = 0; i < imgList.size(); i++) {
            ImageView view = new ImageView(this);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(width, width);
            params1.topMargin = CompanyUtil.dip2px(15);
            mLinGoodsImg.addView(view, params1);
            Glide.with(this).load(imgList.get(i)).fitCenter().placeholder(R.drawable.icon_img_load).error(R.drawable.icon_img_load_failed).into(view);
        }
        mTvGoodsName.setText(mBean.getItemname());
        mTvPrice.setText(mBean.getPrice());
        mTvSaleNum.setText(mBean.getMonthSale());
        mTvGoodsCode.setText(mBean.getItemcode());
        mTvGoodsDetail.setText(mBean.getUserText() + "");
        mIvTitleBack.setOnClickListener(this);
        mIvTitleServer.setOnClickListener(this);
        mRbConnection.setChecked(mBean.getIsColler().equals("0"));
        if (!(mBean.getUUB() == null || mBean.getUUB().isEmpty()) && !(mBean.getUUX() == null || mBean.getUUX().isEmpty())) {
            mLinProductPackageSmall.setVisibility(View.VISIBLE);
            mLinProductPackageLarge.setVisibility(View.VISIBLE);
            mTvProductNumLarge.setText(mBean.getUUX());
            mTvProductNumSmall.setText(mBean.getUUB());
            mBean.setHavaPackage(true);
//            info.setLargePackage(true);
        } else {
            mLinProductPackageSmall.setVisibility(View.INVISIBLE);
            mLinProductPackageLarge.setVisibility(View.INVISIBLE);
        }
        mLinParentSize.setVisibility(View.GONE);
        mBean.setSelectNum(ProductAddShopCarUtils.getInstance().getGoodsNumInShopCar(mBean.getItemcode()));
        mEtProductSelectNum.setText(String.valueOf(mBean.getSelectNum()));
        if (!MyApplication.mHasComfim) {
            mLinEdit.setVisibility(View.GONE);
            mTvPrice.setText("****");
            mTvPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ValidationActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void iniActivityAnimation() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                onBackPressed();
                break;
            case R.id.iv_title_server:
                Intent intent = new Intent(this, AppServerCenterActivity.class);
                startActivity(intent);
                break;
        }
    }

    @OnClick({R.id.rb_connection, lin_product_package_large, lin_product_package_small, R.id.iv_product_num_add, R.id.iv_product_num_sub})
    void click(View v) {
        switch (v.getId()) {
            case R.id.rb_connection:
                ProductAddShopCarUtils.getInstance().connectionProduct(this, mRbConnection, mBean);
                break;
            case lin_product_package_large:
//                    info.setLargePackage(true);
//                    ProductAddShopCarUtils.getInstance().EditShopCar(true, info, mHolder.et_product_select_num, (Activity) mContext, mOnAddShopCarListener, mHolder.iv_product_num_add);
                if (mBean.isLargePackage()) {
                    mBean.setLargePackage(false);
                    ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                    initPackage();
                    return;
                }
                ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                mBean.setLargePackage(true);
                mBean.setSmallPackage(false);
                initPackage();
                break;
            case lin_product_package_small:
//                    info.setSmallPackage(true);
//                    ProductAddShopCarUtils.getInstance().EditShopCar(true, info, mHolder.et_product_select_num, (Activity) mContext, mOnAddShopCarListener, mHolder.iv_product_num_add);
                if (mBean.isSmallPackage()) {
                    mBean.setSmallPackage(false);
                    ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                    initPackage();
                    return;
                }
                ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                mBean.setLargePackage(false);
                mBean.setSmallPackage(true);
                initPackage();
                break;
            case R.id.iv_product_num_add:
                ProductAddShopCarUtils.getInstance().EditShopCar(true, mBean, mEtProductSelectNum, (Activity) mContext, this, mIvProductNumAdd);
                break;
            case R.id.iv_product_num_sub:
                ProductAddShopCarUtils.getInstance().EditShopCar(false, mBean, mEtProductSelectNum, (Activity) mContext, this, mIvProductNumSub);
                break;
        }
    }

    private void initPackage() {
        mLinProductPackageLarge.setSelected(mBean.isLargePackage());
        mLinProductPackageSmall.setSelected(mBean.isSmallPackage());
    }

    @Override
    public void onBackPressed() {
        if (mBean == null) {
            super.onBackPressed();
            return;
        }
        Intent intent = new Intent();
        Log.e("tag", "mBean.getIsColler():" + mBean.getIsColler());
        intent.putExtra("coller", mBean.getIsColler());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void addShopCarListener(ImageView productImgView) {
        hideSoftKeyboard();
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ProductAddShopCarUtils.getInstance().showAnimation(ProductInfoActivity.this, mIvGoodsImg1, mFlShopCar, mRlPartent);
                        ProductAddShopCarUtils.getInstance().setShopCarNum(mTvCarNum);
                        ProductAddShopCarUtils.getInstance().startAlarm(ProductInfoActivity.this);
                    }
                }, 100);
            }
        }, 100);
    }

    @OnClick(R.id.tv_activity)
    void clickActivity() {
        if (mEventBean == null) return;
        DialogUtil.productActivityDia(this,mEventBean);
    }
}
