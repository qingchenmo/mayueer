package com.jlkf.ego.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jlkf.ego.R;
import com.jlkf.ego.adapter.ConnectionAdatper;
import com.jlkf.ego.adapter.ProductAdapter;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.Connection;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcs
 *         我的收藏页面
 */
public class MyConnectionActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener
        , ProductAdapter.OnItemClickListener, View.OnClickListener, ProductAdapter.OnLongClickListener, ConnectionAdatper.OnLongListener {
    private SwipeToLoadLayout loadLayout;
    private RecyclerView mRecyclerView;
    Handler handler = new Handler();
    private ImageView iv_title_back, iv_title_shopcar;
    private ConnectionAdatper mAdapter;
    private TextView no_sc;
    public static final int CONNECTION_RESULT_CODE = 55555;

    @Override
    public void initView() {
        setContentView(R.layout.activity_my_connection);
        loadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        loadLayout.setOnLoadMoreListener(this);
        loadLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_shopcar = (ImageView) findViewById(R.id.iv_title_shopcar);
        no_sc = (TextView) findViewById(R.id.no_sc);
        iv_title_back.setOnClickListener(this);
        iv_title_shopcar.setOnClickListener(this);
        //为recyclerView设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<ProductListBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
//            ProductInfo info = new ProductInfo();
//            info.setImg(R.mipmap.ic_launcher);
//            info.setProductName("这里是模拟商品名称");
//            info.setHavaPackage(true);
//            info.setPrice("￥51.55");
//            info.setProductStock("5000");
//            info.setLargePackage(60);
//            info.setSmallPackage(30);
//            info.setSelectNum(200);
//            list.add(info);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    public void no_sc() {
        no_sc.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }


    private int tag = 0;

    @Override
    public void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        tag = 0;
        load();
    }

    private void load() {
        final Map<String, String> map = new HashMap<>();
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        map.put("pageNo", "1");
        map.put("pageSize", "10");

        HttpUtil.getInstacne(this).get(Urls.getMyColler, String.class, map, new HttpUtil.OnCallBack<String>() {

            @Override
            public void success(String data) {
                List<Connection> dataBeen = new Gson().fromJson(data, new TypeToken<List<Connection>>() {
                }.getType());

                if (dataBeen.size() > 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    if (tag == 0) {
//                mAdapter = new ProductAdapter(mActivity, dataBeen, ProductAdapter.LISTITEM, MyConnectionActivity.this, null);
                        mAdapter = new ConnectionAdatper(dataBeen, mActivity);
                        mAdapter.setOnLongListener(MyConnectionActivity.this);
                        mRecyclerView.setAdapter(mAdapter);

                    } else {
                        mAdapter.getConnections().clear();
                        mAdapter.getConnections().addAll(dataBeen);
                        mAdapter.notifyDataSetChanged();
                    }
                    no_sc.setVisibility(View.GONE);
                } else {
                    no_sc.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);

                }

            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (CONNECTION_RESULT_CODE == requestCode && Activity.RESULT_OK == resultCode) {
            tag = 1;
            load();
        }
    }

    @Override
    public void iniActivityAnimation() {

    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadLayout.setLoadingMore(false);
            }
        }, 2000);

    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void clickItemListener(ProductListBean.DataBean info) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.iv_title_shopcar:
//                Intent intent = new Intent(this, MainActivity.class);
                Intent intent = new Intent(this, ShapCarActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("tag", "againBuy");
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onLong(int position) {
        show(position);
    }


    public void show(final int position) {
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.delet_dialog, null);
        final Dialog dialog = new AlertDialog.Builder(mActivity)
                .setView(inflate)
                .create();
        dialog.getWindow().setBackgroundDrawable(new BitmapDrawable());

        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
            }
        });
        inflate.findViewById(R.id.tv_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                load(mAdapter.getConnections(), position, dialog);
            }
        });

        dialog.show();
    }

    private void load(final List<Connection> list, final int position, final Dialog dialog) {
        JSONObject object = new JSONObject();
        try {
            object.put("uId", MyApplication.getmUserBean().getUId());
            object.put("type", 2);
            object.put("itemCode", list.get(position).getItemcode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.getInstacne(mActivity).post(Urls.coller, String.class, object, new HttpUtil.OnCallBack<String>() {

            @Override
            public void success(String data) {
                list.remove(list.get(position));

                if (list.size() == 0) {
                    no_sc();
                }

                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onError(String msg, int code) {
                dialog.dismiss();
            }
        });
    }


}
