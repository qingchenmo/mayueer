package com.jlkf.ego.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jlkf.ego.R;
import com.jlkf.ego.adapter.ProductAdapter;
import com.jlkf.ego.adapter.ProductListClassifyOneAdapter;
import com.jlkf.ego.adapter.ProductListClassifyTwoAdaper;
import com.jlkf.ego.adapter.ProductShaiXuanAdapter;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.BrandListBean;
import com.jlkf.ego.bean.GoodsBean;
import com.jlkf.ego.bean.MyBaseBean;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.bean.ShopCarGoodsBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.newpage.adapter.FilterProductOneAdapter;
import com.jlkf.ego.newpage.bean.FilterProductBean;
import com.jlkf.ego.newpage.fragment.ClassificationFragment;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.HttpUtils;
import com.jlkf.ego.utils.ProductAddShopCarUtils;
import com.jlkf.ego.utils.ShardeUtil;
import com.jlkf.ego.utils.ToastUti;
import com.jlkf.ego.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zcs
 *         2017年7月11日09:17:43
 *         商品列表页
 */
public class ProductListActivity extends com.jlkf.ego.base.BaseActivity implements ProductListClassifyOneAdapter.OnItemClickListener,
        ProductListClassifyTwoAdaper.OnItemClickListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener
        , ProductAdapter.OnItemClickListener, ProductAddShopCarUtils.OnAddShopCarListener {
    private RecyclerView mRvProductListClassifyOne, mRvProductListClassifyTwo, mRvProduct;
    private DrawerLayout mDrawerLayout;
    private EditText mEtTittle;
    private CheckBox mCbProductSort, mCbProductBatch;
    private RadioGroup mRgProductList;
    private LinearLayout mLinProductBatch;
    private ProductAdapter mProductAdapter;
    private View mViewPopup;
    private PopupWindow popupWindow;
    private PopupWindow productAttriPopup;//产品属性pop
    private RadioButton mRbProductShaiXuan;
    private GridView mGvShaiXuan;
    private TextView mBtnPopReset, mBtnPopComPleted;
    private LinearLayout mLinPopPriceShaiXuan;
    private TextView mTvBrandClass;
    private ImageView iv_productList_back;
    private FrameLayout fl_bottom, fl_silder;
    private ImageView iv_product_num_sub, iv_product_num_add;
    private EditText et_product_select_num;
    private TextView mTvAddShop;
    private CardView cv_bottom;
    private LinearLayout lin_shaoxuan;
    private RelativeLayout rl_search_no_data;
    private ProductListBean.DataBean info;
    private int mPositon;
    private List<ProductListBean.DataBean> mList;
    private String mBrandId;//查询的品牌id
    private String searchKey;//查询的关键字
    private int searchType;//查询的类型
    private int mPage = 1;//分页查询的页码
    private String minPrice, maxPrice;//筛选查询中的价格区间
    private String brand;//筛选中的品牌编号
    private String secondGrp;//查询中的分类编号
    private int mSelectBrandPosition = -1, mPriceInterPosition = -1;
    private EditText et_shaixuan_min_price, et_shaixuan_max_price;
    private ProductShaiXuanAdapter mAdapter;
    //    private TwinklingRefreshLayout refresh_layout;
    private RelativeLayout rl_partent;
    private ImageButton ib_shopCar;
    private FrameLayout fl_shop_car;
    private TextView tv_car_num;
    private SmartRefreshLayout mRefreshLayout;
    private String oldBrand;
    private ImageView iv_side;
    private ImageButton mImageButton;
    private TextView mTvFilter;
    private String attribute;

    static {
//        ClassicsHeader.REFRESH_HEADER_PULLDOWN = RefreshUtils.REFRESH_HEADER_PULLDOWN;
//        ClassicsHeader.REFRESH_HEADER_REFRESHING = RefreshUtils.REFRESH_HEADER_REFRESHING;
//        ClassicsHeader.REFRESH_HEADER_LOADING = RefreshUtils.REFRESH_HEADER_LOADING;
//        ClassicsHeader.REFRESH_HEADER_RELEASE = RefreshUtils.REFRESH_HEADER_RELEASE;
//        ClassicsHeader.REFRESH_HEADER_FINISH = RefreshUtils.REFRESH_HEADER_FINISH;
//        ClassicsHeader.REFRESH_HEADER_FAILED = RefreshUtils.REFRESH_HEADER_FAILED;
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = RefreshUtils.REFRESH_HEADER_LASTTIME;

//        ClassicsFooter.REFRESH_FOOTER_PULLUP = RefreshUtils.REFRESH_FOOTER_PULLUP;
//        ClassicsFooter.REFRESH_FOOTER_RELEASE = RefreshUtils.REFRESH_FOOTER_RELEASE;
//        ClassicsFooter.REFRESH_FOOTER_REFRESHING = RefreshUtils.REFRESH_FOOTER_REFRESHING;
//        ClassicsFooter.REFRESH_FOOTER_LOADING = RefreshUtils.REFRESH_FOOTER_LOADING;
//        ClassicsFooter.REFRESH_FOOTER_FINISH = RefreshUtils.REFRESH_FOOTER_FINISH;
//        ClassicsFooter.REFRESH_FOOTER_FAILED = RefreshUtils.REFRESH_FOOTER_FAILED;
//        ClassicsFooter.REFRESH_FOOTER_ALLLOADED = RefreshUtils.REFRESH_FOOTER_ALLLOADED;
    }

    @Override
    public void initView() {


        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        mTvFilter = (TextView) findViewById(R.id.tv_filter);
        mRvProductListClassifyOne = (RecyclerView) findViewById(R.id.rv_productList_classifyOne);
        mRvProductListClassifyTwo = (RecyclerView) findViewById(R.id.rv_productList_classifyTwo);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mEtTittle = (EditText) findViewById(R.id.et_title);
        mEtTittle.clearFocus();
        mEtTittle.setSelected(false);
        mEtTittle.setOnClickListener(this);
        mRvProduct = (RecyclerView) findViewById(R.id.rv_product);
        mCbProductSort = (CheckBox) findViewById(R.id.cb_product_sort);
        mCbProductSort.setOnClickListener(this);
        mRgProductList = (RadioGroup) findViewById(R.id.rg_product_list);
//        mRgProductList.setOnCheckedChangeListener(this);
        mCbProductBatch = (CheckBox) findViewById(R.id.cb_product_batch);
        mLinProductBatch = (LinearLayout) findViewById(R.id.lin_product_batch);
        mLinProductBatch.setOnClickListener(this);
        mRbProductShaiXuan = (RadioButton) findViewById(R.id.rb_product_sort_shaixuan);
        mRbProductShaiXuan.setOnClickListener(this);
        iv_productList_back = (ImageView) findViewById(R.id.iv_productList_back);
        fl_bottom = (FrameLayout) findViewById(R.id.fl_bottom);
        iv_productList_back.setOnClickListener(this);
        fl_silder = (FrameLayout) findViewById(R.id.fl_silder);
        iv_product_num_sub = (ImageView) findViewById(R.id.iv_product_num_sub);
        iv_product_num_sub.setOnClickListener(this);
        iv_product_num_add = (ImageView) findViewById(R.id.iv_product_num_add);
        iv_product_num_add.setOnClickListener(this);
        et_product_select_num = (EditText) findViewById(R.id.et_product_select_num);
        mTvAddShop = (TextView) findViewById(R.id.tv_add_shop);
        mTvAddShop.setOnClickListener(this);
        cv_bottom = (CardView) findViewById(R.id.cv_bottom);
        iv_side = (ImageView) findViewById(R.id.iv_side);
        iv_side.setOnClickListener(this);
        rl_search_no_data = (RelativeLayout) findViewById(R.id.rl_search_no_data);
//        refresh_layout = (TwinklingRefreshLayout) findViewById(R.id.refresh_layout);
        rl_partent = (RelativeLayout) findViewById(R.id.rl_partent);
        ib_shopCar = (ImageButton) findViewById(R.id.ib_shopCar);
        fl_shop_car = (FrameLayout) findViewById(R.id.fl_shop_car);
        fl_shop_car.setOnClickListener(this);
        tv_car_num = (TextView) findViewById(R.id.tv_car_num);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout1);
        initgouwuche();
        if (getSupportFragmentManager().findFragmentByTag("silder") == null) {
            FragmentManager manager = getSupportFragmentManager();
//            manager.beginTransaction().replace(R.id.fl_silder, new ProductClassFragment(), "silder").commit();
            manager.beginTransaction().replace(R.id.fl_silder, new ClassificationFragment(), "silder").commit();
        }
        initRefreshAdapter();
        mImageButton = (ImageButton) findViewById(R.id.ib_first);
        if (TextUtils.isEmpty(ShardeUtil.getString("projectList"))) {
            ShardeUtil.putString("projectList", "true");
            mImageButton.setVisibility(View.VISIBLE);
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dex++;
                    if (dex == 1) {
                        mImageButton.setImageResource(R.drawable.icon_product_2);
                    } else if (dex == 2) {
                        mImageButton.setImageResource(R.drawable.icon_product_3);
                    } else {
                        mImageButton.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            mImageButton.setVisibility(View.GONE);
        }
    }

    int dex;

    private void initgouwuche() {

        final Map<String, String> map = new HashMap<>();
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        HttpUtil.getInstacne((Activity) mContext).get(Urls.list, String.class, map, new HttpUtil.OnCallBack<String>() {
            @Override
            public void success(String data) {
                List<GoodsBean> goodsBeen = GoodsBean.arrayGoodsBeanFromData(data);
                MyApplication.mAppApplication.mGoodsBeen.clear();
                for (int i = 0; i < goodsBeen.size(); i++) {
                    int size = goodsBeen.get(i).getShopcart().size();
                    for (int j = 0; j < size; j++) {
                        ShopCarGoodsBean bean = new ShopCarGoodsBean();
                        GoodsBean.ShopcartBean shopcartBean = goodsBeen.get(i).getShopcart().get(j);
                        bean.setGoodsCode(shopcartBean.getItemcode());
                        bean.setNum(shopcartBean.getQuantity());
                        MyApplication.mAppApplication.mGoodsBeen.add(bean);
                        ProductAddShopCarUtils.getInstance().setShopCarNum(tv_car_num);
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mProductAdapter != null)
            mProductAdapter.notifyDataSetChanged();
        ProductAddShopCarUtils.getInstance().setShopCarNum(tv_car_num);
    }

    private void initRefreshAdapter() {
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                refreshData();
//                mRefreshLayout.setLoadmoreFinished(false);
            }
        });
    }

    @Override
    protected int getlayoutid() {
        return R.layout.activity_product_list;
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        mList = new ArrayList<>();
        initProductList(ProductAdapter.GRIDITEM);
        mBrandId = getIntent().getStringExtra("code");
        oldBrand = mBrandId;
        searchKey = getIntent().getStringExtra("productName");
        secondGrp = getIntent().getStringExtra(/*"secondGrp"*/"itmsGrpCod");
        if (searchKey != null && !searchKey.isEmpty()) {
            mEtTittle.setText(searchKey);
            mBrandId = "";
            searchType = 4;
        }
//        ((RadioButton) mRgProductList.getChildAt(0)).setChecked(true);
        refreshData();
//        mRefreshLayout.autoRefresh();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        searchKey = intent.getStringExtra("productName");
        if (searchKey != null && !searchKey.isEmpty()) {
            mEtTittle.setText(searchKey);
            mBrandId = "";
            searchType = 1;
            mPage = 1;
        }
        secondGrp = intent.getStringExtra(/*"secondGrp"*/"itmsGrpCod");
        if (secondGrp != null && !secondGrp.isEmpty()) {
//            mBrandId = "";
            searchType = 1;
            mPage = 1;
            mDrawerLayout.closeDrawer(Gravity.START);
        }
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
        }

//        if (((RadioButton) mRgProductList.getChildAt(0)).isChecked()) refreshData();
//        ((RadioButton) mRgProductList.getChildAt(0)).setChecked(true);
        rl_search_no_data.setVisibility(View.GONE);
        mRefreshLayout.autoRefresh();
        filterList.clear();
    }

    /**
     * 初始化商品列表
     */
    private void initProductList(int itemType) {
        if (itemType == ProductAdapter.LISTITEM) {
            mRvProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRvProduct.setAdapter(mProductAdapter = new ProductAdapter(this, mList, ProductAdapter.LISTITEM, this, this));
        } else if (itemType == ProductAdapter.GRIDITEM) {
            mRvProduct.setLayoutManager(new GridLayoutManager(this, 2));
            mRvProduct.setAdapter(mProductAdapter = new ProductAdapter(this, mList, ProductAdapter.GRIDITEM, this, this));
        }
        mProductAdapter.changeLayout(mCbProductBatch.isChecked());
    }

    @Override
    public void clickLisener(int position) {
    }

    @Override
    public void clickListener(int position) {
    }

    @Override
    public void clickItemListener(ProductListBean.DataBean info) {
        Intent intent = new Intent(this, ProductQuickSelectActivity.class);
        intent.putExtra("productName", info.getItemname());
        intent.putExtra("codeBars", info.getCodebars());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_product_sort:
                if (mCbProductSort.isChecked()) {
                    initProductList(ProductAdapter.LISTITEM);
                } else {
                    initProductList(ProductAdapter.GRIDITEM);
                }
                break;
            case R.id.lin_product_batch:
                if (mList.size() < 1) {
                    mCbProductBatch.setChecked(false);
                    return;
                }
                mCbProductBatch.setChecked(!mCbProductBatch.isChecked());
                mProductAdapter.showCheckBox(mCbProductBatch.isChecked());
                cv_bottom.setVisibility(mCbProductBatch.isChecked() ? View.VISIBLE : View.GONE);
                break;
            case R.id.rb_product_sort_shaixuan:
                showShaixuan();
                break;
            case R.id.view_popup:
                popupWindow.dismiss();
                break;
            case R.id.btn_pop_reset:
                //重置筛选条件
                et_shaixuan_max_price.setText("");
                et_shaixuan_min_price.setText("");
                if (mSelectBrandPosition != -1)
                    MyApplication.mAppApplication.getBrandListBeen().get(mSelectBrandPosition).setSelect(false);
                mAdapter.notifyDataSetChanged();
                if (mPriceInterPosition != -1)
                    mLinPopPriceShaiXuan.getChildAt(mPriceInterPosition).setSelected(false);
                mSelectBrandPosition = -1;
                mPriceInterPosition = -1;
                minPrice = "";
                maxPrice = "";
                mBrandId = oldBrand;
                brand = "";
                break;
            case R.id.btn_pop_completed:
                if ((et_shaixuan_min_price.getText().toString().trim().isEmpty() || et_shaixuan_max_price.getText().toString().trim().isEmpty()) && mPriceInterPosition != -1 &&
                        mLinPopPriceShaiXuan.getChildAt(mPriceInterPosition).isSelected()) {
                    if (mPriceInterPosition != -1 && mPriceInterPosition != 2) {
                        TextView tv = (TextView) mLinPopPriceShaiXuan.getChildAt(mPriceInterPosition);
                        minPrice = tv.getText().toString().split("-")[0];
                        maxPrice = tv.getText().toString().split("-")[1];
                    } else if (mPriceInterPosition == 2) {
                        minPrice = "10";
                    }
                } else {
                    minPrice = et_shaixuan_min_price.getText().toString().trim();
                    maxPrice = et_shaixuan_max_price.getText().toString().trim();
                    if (!TextUtils.isEmpty(minPrice) && !TextUtils.isEmpty(maxPrice) && Integer.parseInt(minPrice) > Integer.parseInt(maxPrice)) {
                        return;
                    }
                }
                if (mSelectBrandPosition != -1 && !MyApplication.mAppApplication.getBrandListBeen().get(mSelectBrandPosition).isSelect())
                    mSelectBrandPosition = -1;
                brand = mSelectBrandPosition != -1 ? MyApplication.mAppApplication.getBrandListBeen().get(mSelectBrandPosition).getCode() : null;
                searchType = 4;
                mPage = 1;
//                refreshData();
                mRefreshLayout.autoRefresh();
                popupWindow.dismiss();
                break;
            case R.id.iv_productList_back:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                }
                finish();
                break;
            case R.id.iv_product_num_sub:
                int num = 0;
                if (!et_product_select_num.getText().toString().trim().isEmpty())
                    num = Integer.parseInt(et_product_select_num.getText().toString().trim());
                if (num == 0) return;
                et_product_select_num.setText(num - 1 + "");
                break;
            case R.id.iv_product_num_add:
                int num1 = 0;
                if (!et_product_select_num.getText().toString().trim().isEmpty())
                    num1 = Integer.parseInt(et_product_select_num.getText().toString().trim());
                et_product_select_num.setText(num1 + 1 + "");
                break;
            case R.id.tv_add_shop:
                batchAddShop(et_product_select_num.getText().toString().trim(), mList);
                break;
            case R.id.et_title:
                Intent intent = new Intent(this, SearchProductActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_shop_car:
//                Intent intent1 = new Intent(this, MainActivity.class);
                Intent intent1 = new Intent(this, ShapCarActivity.class);
                intent1.putExtra("tag", "againBuy");
                intent1.putExtra("type", "1");
                startActivity(intent1);
                break;
            case R.id.tv_brand_class:
                if (mTvBrandClass.isSelected()) {
                    LinearLayout.LayoutParams linParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lin_shaoxuan.setLayoutParams(linParams);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.shaixuan_brand_gv_height));
                    mGvShaiXuan.setLayoutParams(params);
                    mAdapter.setCount(MyApplication.mAppApplication.getBrandListBeen().size() > 8 ? 8 : MyApplication.mAppApplication.getBrandListBeen().size());
                } else {
                    LinearLayout.LayoutParams linParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    lin_shaoxuan.setLayoutParams(linParams);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
                    mGvShaiXuan.setLayoutParams(params);
                    mAdapter.setCount(MyApplication.mAppApplication.getBrandListBeen().size() > 8 ? 8 : MyApplication.mAppApplication.getBrandListBeen().size());
                }
                mTvBrandClass.setSelected(!mTvBrandClass.isSelected());

                mAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_side:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
        }
    }

    /**
     * 筛选条件pop
     */
    private void showShaixuan() {
        if (MyApplication.mAppApplication.getBrandListBeen() == null) {
            getBrand();
            return;
        }

        if (popupWindow == null || popupWindow != null) {
            View v = LayoutInflater.from(this)
                    .inflate(R.layout.product_list_filter, null);
            popupWindow = new PopupWindow(v, RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT, false);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
//            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            mGvShaiXuan = (GridView) v.findViewById(R.id.gv_product_shaoxuan);
            mViewPopup = v.findViewById(R.id.view_popup);
            mBtnPopComPleted = (TextView) v.findViewById(R.id.btn_pop_completed);
            mBtnPopReset = (TextView) v.findViewById(R.id.btn_pop_reset);
            mBtnPopComPleted.setOnClickListener(this);
            mBtnPopReset.setOnClickListener(this);
            mLinPopPriceShaiXuan = (LinearLayout) v.findViewById(R.id.lin_pop_price_shaixuan);
            et_shaixuan_min_price = (EditText) v.findViewById(R.id.et_shaixuan_min_price);
            et_shaixuan_max_price = (EditText) v.findViewById(R.id.et_shaixuan_max_price);
            mTvBrandClass = (TextView) v.findViewById(R.id.tv_brand_class);
            mTvBrandClass.setOnClickListener(this);
            lin_shaoxuan = (LinearLayout) v.findViewById(R.id.lin_shaoxuan);
            et_shaixuan_max_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    for (int i = 0; i < mLinPopPriceShaiXuan.getChildCount(); i++) {
                        TextView button = (TextView) mLinPopPriceShaiXuan.getChildAt(i);
                        button.setSelected(false);
                    }
                    mPriceInterPosition = -1;
                }
            });
            et_shaixuan_min_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    for (int i = 0; i < mLinPopPriceShaiXuan.getChildCount(); i++) {
                        TextView button = (TextView) mLinPopPriceShaiXuan.getChildAt(i);
                        button.setSelected(false);
                    }
                    mPriceInterPosition = -1;
                }
            });
            et_shaixuan_max_price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mLinPopPriceShaiXuan.getChildCount(); i++) {
                        TextView button = (TextView) mLinPopPriceShaiXuan.getChildAt(i);
                        button.setSelected(false);
                    }
                    mPriceInterPosition = -1;
                }
            });
            et_shaixuan_min_price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mLinPopPriceShaiXuan.getChildCount(); i++) {
                        TextView button = (TextView) mLinPopPriceShaiXuan.getChildAt(i);
                        button.setSelected(false);
                    }
                    mPriceInterPosition = -1;
                }
            });
            for (int i = 0; i < mLinPopPriceShaiXuan.getChildCount(); i++) {
                final int position = i;
                final TextView button = (TextView) mLinPopPriceShaiXuan.getChildAt(i);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        button.setSelected(true);
                        button.requestFocus();
//                        hideSoftKeyboard();
                        View et;
                        if (et_shaixuan_min_price.hasFocus())
                            et = et_shaixuan_min_price;
                        else if (et_shaixuan_max_price.hasFocus())
                            et = et_shaixuan_max_price;
                        else
                            et = getWindow().getDecorView();
                        InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputmanger.hideSoftInputFromWindow(et.getWindowToken(), 0);
                        et_shaixuan_min_price.setText("");
                        et_shaixuan_max_price.setText("");
                        mPriceInterPosition = position;
                        for (int j = 0; j < mLinPopPriceShaiXuan.getChildCount(); j++) {
                            if (position != j) {
                                mLinPopPriceShaiXuan.getChildAt(j).setSelected(false);
                            }
                        }
                    }
                });
            }
            mViewPopup.setOnClickListener(this);
            if (mSelectBrandPosition != -1)
                MyApplication.mAppApplication.getBrandListBeen().get(mSelectBrandPosition).setSelect(false);
            mAdapter = new ProductShaiXuanAdapter(this, MyApplication.mAppApplication.getBrandListBeen());
            mAdapter.setCount(MyApplication.mAppApplication.getBrandListBeen().size() > 8 ? 8 : MyApplication.mAppApplication.getBrandListBeen().size());
            mGvShaiXuan.setAdapter(mAdapter);
            mGvShaiXuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mSelectBrandPosition = position;
                    for (int i = 0; i < MyApplication.mAppApplication.getBrandListBeen().size(); i++) {
                        MyApplication.mAppApplication.getBrandListBeen().get(i).setSelect(i == position);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    View et;
                    if (et_shaixuan_min_price != null && et_shaixuan_min_price.hasFocus())
                        et = et_shaixuan_min_price;
                    else if (et_shaixuan_max_price != null && et_shaixuan_max_price.hasFocus())
                        et = et_shaixuan_max_price;
                    else
                        et = getWindow().getDecorView();
                    InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(et.getWindowToken(), 0);
                    mRbProductShaiXuan.setChecked(false);
                }
            });
            popupWindow.showAsDropDown(mRgProductList, 0, 0);

        }
    }

    private List<BrandListBean> mBrandListBeen;

    public void getBrand() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("area", MyApplication.getmUserBean().getArea());
        HttpUtil.getInstacne(this).get(Urls.recommendBrand, Object.class, map1, new HttpUtil.OnCallBack<Object>() {

            @Override
            public void success(Object data) {
                mBrandListBeen = BrandListBean.arrayBrandListBeanFromData(data.toString());
                MyApplication.mAppApplication.setBrandListBeen(mBrandListBeen);
                showShaixuan();
            }

            @Override
            public void onError(String msg, int code) {
                ToastUti.show(msg);
            }
        });
    }


    /**
     * 批量添加
     *
     * @param trim
     * @param list
     */
    private void batchAddShop(final String trim, final List<ProductListBean.DataBean> list) {
        if (trim.isEmpty() || Integer.parseInt(trim) == 0) {
            ToastUtil.show(getResources().getString(R.string.xzsp));
            return;
        }
        final int size = list.size();
        JSONArray objects = new JSONArray();
        for (int i = 0; i < size; i++) {
            ProductListBean.DataBean bean = list.get(i);
            if (bean.isChecked()) {
                JSONObject object = new JSONObject();
                object.put("name", bean.getItemname());
                object.put("price", bean.getPrice());
                object.put("logo", bean.getPicturname());
                object.put("itemcode", bean.getItemcode());
                object.put("quantity", Integer.parseInt(bean.getOnhand()) > (Integer.parseInt(trim) + bean.getSelectNum()) ? trim : (Integer.parseInt(bean.getOnhand()) - bean.getSelectNum()) + "");
                object.put("codebars", bean.getCodebars());
                object.put("brandname", bean.getpName());
                object.put("brandId", bean.getUPp());
                object.put("uId", getUser().getUId() + "");
                objects.add(object);
            }
        }
        if (objects.size() < 1) {
            ToastUtil.show(getResources().getString(R.string.xzsp));
            return;
        }
        HttpUtil.getInstacne(this).post2(Urls.batchInsert, MyBaseBean.class, objects.toString(), new HttpUtil.OnCallBack<MyBaseBean>() {
            @Override
            public void success(MyBaseBean data) {
                for (int i = 0; i < size; i++) {
                    ProductListBean.DataBean bean = list.get(i);
                    if (bean.isChecked()) {
                        ShopCarGoodsBean carGoodsBean = new ShopCarGoodsBean();
                        carGoodsBean.setGoodsCode(bean.getItemcode());
                        int hand = Integer.parseInt(bean.getOnhand());
                        int trim1 = Integer.parseInt(trim);
                        int selectnum = (hand > (trim1 + bean.getSelectNum()) ? trim1 : hand - bean.getSelectNum()) + bean.getSelectNum();
                        Log.e("tag", selectnum + "\n" + hand + "\n" + trim1 + "\n" + bean.getSelectNum());

                        /*+
                                ((Integer.parseInt(bean.getOnhand()) > (Integer.parseInt(trim) + bean.getSelectNum()) ?
                                        Integer.parseInt(trim) : (Integer.parseInt(bean.getOnhand()) - bean.getSelectNum())))*/
                        carGoodsBean.setNum(/*bean.getSelectNum() + */selectnum);
                        ProductAddShopCarUtils.getInstance().statisShopNum(carGoodsBean);
                        bean.setSelectNum(carGoodsBean.getNum());
                    }
                }

                ProductAddShopCarUtils.getInstance().setShopCarNum(tv_car_num);
                mCbProductBatch.setChecked(!mCbProductBatch.isChecked());
                mProductAdapter.showCheckBox(mCbProductBatch.isChecked());
                cv_bottom.setVisibility(mCbProductBatch.isChecked() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onError(String msg, int code) {
            }
        });

    }

    private int tag;

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.rb_product_sort_shaixuan:

                break;
            case R.id.rb_product_sort_zonghe:
                searchType = 1;
                mPage = 1;
                refreshData();
//                mRefreshLayout.autoRefresh();
                break;
            case R.id.rb_product_sort_sales:
                searchType = 2;
                mPage = 1;
                refreshData();
//                mRefreshLayout.autoRefresh();
                tag = 1;
                break;
            case R.id.rb_product_sort_price:
                searchType = 3;
                mPage = 1;
                refreshData();
//                mRefreshLayout.autoRefresh();
                break;
        }
    }

    /**
     * 请求数据
     * 默认每页请求数量固定
     *
     * @param //            品牌编号
     * @param //查询类型（1.综合查询 2.销量查询 3.新品查询 4.筛选查询）
     * @param //            综合查询中价格高低（1.从低到高 2.从高到低）
     * @param //            名称模糊查询
     * @param //            筛选中最低价格
     * @param //            筛选中最高价格
     * @param //            筛选条件中品牌编号
     * @param //            分类编号
     * @param //            搜索页码
     */
    private void refreshData() {
        /*Map<String, String> map = new HashMap<>();
        map.put("area", MyApplication.getmUserBean().getArea());
        if (mBrandId != null && !mBrandId.isEmpty() && (brand == null || brand.isEmpty()))
            map.put("brandId", mBrandId);
        if (searchType != 0)
            map.put("type", searchType + "");
//        if (searchType == 1)
//            map.put("moneyType", "2");
        if (searchKey != null && !searchKey.isEmpty() && (brand == null || brand.isEmpty()))
            map.put("name", searchKey);
        if (minPrice != null && !minPrice.isEmpty() && searchType == 4)
            map.put("minPrice", minPrice);
        if (maxPrice != null && !maxPrice.isEmpty() && searchType == 4)
            map.put("maxPrice", maxPrice);
        if (brand != null && !brand.isEmpty() && searchType == 4)
            map.put("brand", brand);
        if (secondGrp != null && !secondGrp.isEmpty() *//*&& (brand == null || brand.isEmpty())*//*)
            map.put(*//*"secondGrp"*//*"itmsGrpCod", secondGrp);
        map.put("pageNo", mPage + "");
        map.put("pageSize", "20");
        map.put("uId", MyApplication.getmUserBean().getUId() + "");*/

        ApiManager.getOitmList(searchKey, secondGrp,
                (mBrandId != null && !mBrandId.isEmpty()
                        && (brand == null || brand.isEmpty())) ? mBrandId : brand, minPrice, maxPrice, mPage + "", attribute, this, new HttpUtils.OnCallBack() {
                    @Override
                    public void success(String response) {
                        List<ProductListBean.DataBean> list = JSON.parseArray(response, ProductListBean.DataBean.class);
                        if (mPage == 1)
                            mList.clear();
                        mPage++;
                        mList.addAll(list);
                        mRvProduct.getAdapter().notifyDataSetChanged();
                        rl_search_no_data.setVisibility(View.GONE);
                        mRefreshLayout.finishRefresh(true);
                        mRefreshLayout.finishLoadmore(true);
                    }

                    @Override
                    public void onError(String msg) {
                        if (mList.size() < 1)
                            rl_search_no_data.setVisibility(View.VISIBLE);
                        mRvProduct.getAdapter().notifyDataSetChanged();
                        mRefreshLayout.finishLoadmore(false);
                        mRefreshLayout.finishRefresh(false);
                    }
                });
       /* HttpUtil.getInstacne(this).get2(Urls.getOitmViewByBrand, ProductListBean.class, map, new HttpUtil.OnCallBack<ProductListBean>() {
            @Override
            public void success(ProductListBean data) {
                if (mPage == 1)
                    mList.clear();
                mPage++;
                if (mCbProductBatch.isChecked()) {
                    int size = data.getData().size();
                    for (int i = 0; i < size; i++) {
//                        data.getData().get(i).setChecked(true);
                    }
                }
                if ((mPage - 1 + "").equals(data.getTotalPage())) {
//                    refresh_layout.setEnableLoadmore(false);
                    mRefreshLayout.setEnableLoadmore(false);
                    mRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRefreshLayout.setEnableLoadmore(true);
                            mRefreshLayout.setLoadmoreFinished(true);
                        }
                    }, 2000);
                }
                mList.addAll(data.getData());
                mRvProduct.getAdapter().notifyDataSetChanged();
                rl_search_no_data.setVisibility(View.GONE);
//                refresh_layout.finishRefreshing();
//                refresh_layout.finishLoadmore();
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onError(String msg, int code) {
                rl_search_no_data.setVisibility(View.VISIBLE);
            }
        });*/
    }

    /*private void refreshData() {
        Map<String, String> map = new HashMap<>();
        map.put("area", MyApplication.getmUserBean().getArea());
        if (mBrandId != null && !mBrandId.isEmpty() && (brand == null || brand.isEmpty()))
            map.put("brandId", mBrandId);
        if (searchType != 0)
            map.put("type", searchType + "");
        if (searchKey != null && !searchKey.isEmpty() && (brand == null || brand.isEmpty()))
            map.put("name", searchKey);
        if (minPrice != null && !minPrice.isEmpty() && searchType == 4)
            map.put("minPrice", minPrice);
        if (maxPrice != null && !maxPrice.isEmpty() && searchType == 4)
            map.put("maxPrice", maxPrice);
        if (brand != null && !brand.isEmpty() && searchType == 4)
            map.put("brand", brand);
        if (secondGrp != null && !secondGrp.isEmpty())
            map.put("itmsGrpCod", secondGrp);
        map.put("pageNo", mPage + "");
        map.put("pageSize", "20");
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        HttpUtil.getInstacne(this).get2(Urls.getOitmViewByBrand, ProductListBean.class, map, new HttpUtil.OnCallBack<ProductListBean>() {
            @Override
            public void success(ProductListBean data) {
                if (mPage == 1)
                    mList.clear();
                mPage++;
                if (mCbProductBatch.isChecked()) {
                    int size = data.getData().size();
                    for (int i = 0; i < size; i++) {
//                        data.getData().get(i).setChecked(true);
                    }
                }
                if ((mPage - 1 + "").equals(data.getTotalPage())) {
//                    refresh_layout.setEnableLoadmore(false);
                    mRefreshLayout.setEnableLoadmore(false);
                    mRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRefreshLayout.setEnableLoadmore(true);
                            mRefreshLayout.setLoadmoreFinished(true);
                        }
                    }, 2000);
                }
                mList.addAll(data.getData());
                mRvProduct.getAdapter().notifyDataSetChanged();
                rl_search_no_data.setVisibility(View.GONE);
//                refresh_layout.finishRefreshing();
//                refresh_layout.finishLoadmore();
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onError(String msg, int code) {
                rl_search_no_data.setVisibility(View.VISIBLE);
            }
        });
    }*/

    private boolean canLoad = true;

    @Override
    public void addShopCarListener(final ImageView productImgView) {
        hideSoftKeyboard();
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                ProductAddShopCarUtils.getInstance().showAnimation(ProductListActivity.this, productImgView, fl_shop_car, rl_partent);
                ProductAddShopCarUtils.getInstance().setShopCarNum(tv_car_num);
                ProductAddShopCarUtils.getInstance().startAlarm(ProductListActivity.this);
            }
        }, 100);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (popupWindow != null && popupWindow.isShowing()) {
                popupWindow.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.tv_filter)
    void clickFilter() {
        if (TextUtils.isEmpty(secondGrp)) return;
        showProductAttri();
    }

    private List<FilterProductBean> filterList = new ArrayList<>();

    private void showProductAttri() {
        mTvFilter.setSelected(true);
        if (productAttriPopup == null || productAttriPopup != null) {
            View v = LayoutInflater.from(this)
                    .inflate(R.layout.filter_product_layout, null);
            productAttriPopup = new PopupWindow(v, RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.MATCH_PARENT, true);
            productAttriPopup.setOutsideTouchable(true);
            productAttriPopup.setFocusable(true);
            final RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(productAttriPopup.getContentView().getContext()));
            TextView btn_pop_reset = v.findViewById(R.id.btn_pop_reset);
            TextView btn_pop_completed = v.findViewById(R.id.btn_pop_completed);
            if (filterList.size() < 1)
                ApiManager.getattribute(secondGrp, this, new HttpUtils.OnCallBack() {
                    @Override
                    public void success(String response) {
                        List<FilterProductBean> beans = JSON.parseArray(response, FilterProductBean.class);
                        filterList.clear();
                        filterList.addAll(beans);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUti.show(msg);
                    }
                });
            recyclerView.setAdapter(new FilterProductOneAdapter(this, filterList));
            btn_pop_reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < filterList.size(); i++) {
                        filterList.get(i).setSelectIndex(-1);
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            });
            btn_pop_completed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productAttriPopup.dismiss();
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < filterList.size(); i++) {
                        if (filterList.get(i).getSelectIndex() >= 0) {
                            if (i == filterList.size() - 1) {
                                builder.append(filterList.get(i).getValue().get(filterList.get(i).getSelectIndex()));
                            } else {
                                builder.append(filterList.get(i).getValue().get(filterList.get(i).getSelectIndex())).append(",");
                            }
                        }
                    }
                    attribute = builder.toString();
                    mPage = 1;
                    rl_search_no_data.setVisibility(View.GONE);
                    mRefreshLayout.autoRefresh();
                }
            });
        }
        if (productAttriPopup.isShowing()) {
            productAttriPopup.dismiss();
        } else {
            productAttriPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mTvFilter.setSelected(false);
                }
            });
            if (Build.VERSION.SDK_INT >= 24) {
                Rect rect = new Rect();
                mRgProductList.getGlobalVisibleRect(rect);
                int h = mRgProductList.getResources().getDisplayMetrics().heightPixels - rect.bottom;
                productAttriPopup.setHeight(h);
            }
            productAttriPopup.showAsDropDown(mRgProductList, 0, 0);
        }
    }

}