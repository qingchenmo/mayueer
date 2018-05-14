package com.jlkf.ego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jlkf.ego.R;
import com.jlkf.ego.adapter.AdressAdapter;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.AdressBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.ShardeUtil;
import com.jlkf.ego.widget.BaseToolbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>
 * Created by 楊詩洋 on 2017/7/19 0019.
 * 收貨地址列表
 */

public class AdressActivity extends AppCompatActivity implements AdressAdapter.OnChangeUpdefelListener {

    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.rv_adress)
    RecyclerView rvAdress;
    @BindView(R.id.btn_add_adress)
    Button btnAddAdress;
    @BindView(R.id.iv_yindao)
    ImageView iv_yindao;

    public final static int STATE_NO_ADRESS = 0;
    public final static int STATE_ADRESS = 1;

    public final static int CHANGE_ADRESS = 5555;

    public int state = STATE_ADRESS;
    @BindView(R.id.rl_no_adress)
    RelativeLayout rlNoAdress;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_adress);
        ButterKnife.bind(this);

        toolbar.setTitle(getString(R.string.adress_title));
        toolbar.with(this);

        if (ShardeUtil.getInt("adress") ==1){
            iv_yindao.setVisibility(View.GONE);
        } else {
            iv_yindao.setVisibility(View.VISIBLE);
        }

        iv_yindao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShardeUtil.putInt("adress",1);
                iv_yindao.setVisibility(View.GONE);
            }
        });
        initView();
        initData();
    }

    private int tag = 0;

    private void initData() {
        tag = 0;
        load();
    }

    private void load() {
        Map<String, String> map = new HashMap<>();
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        HttpUtil.getInstacne(this).get(Urls.addrlist, String.class, map, new HttpUtil.OnCallBack<String>() {

            private AdressAdapter mAdapter;

            @Override
            public void success(String data) {

                List<AdressBean> adressBeen = new Gson().fromJson(data, new TypeToken<List<AdressBean>>() {
                }.getType());
                if (tag == 0){

                    if (adressBeen != null && adressBeen.size() > 0) {
                        state = STATE_ADRESS;
                        mAdapter = new AdressAdapter(AdressActivity.this, adressBeen, AdressActivity.this);
                        rvAdress.setAdapter(mAdapter);
                    } else {
                        state = STATE_NO_ADRESS;
                    }

                } else {
                    if (mAdapter == null){
                        mAdapter = new AdressAdapter(AdressActivity.this, adressBeen, AdressActivity.this);
                        rvAdress.setAdapter(mAdapter);
                    } else {
                        mAdapter.getDatas().clear();
                        mAdapter.getDatas().addAll(adressBeen);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                initView();
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }

    public void initView() {


        if (state == STATE_NO_ADRESS) {

            rvAdress.setVisibility(View.GONE);
            rlNoAdress.setVisibility(View.VISIBLE);
        } else {

            rvAdress.setLayoutManager(new LinearLayoutManager(MyApplication.getmContext()));

            rvAdress.setVisibility(View.VISIBLE);
            rlNoAdress.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn_add_adress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_adress:        //添加地址
                mIntent = new Intent(this,EditAdressActivity.class);
                mIntent.putExtra("type", 0);
                startActivityForResult(mIntent,CHANGE_ADRESS);
//                UIHelper.startOrer(this, "adress_edit");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHANGE_ADRESS && resultCode == RESULT_OK){
            tag = 1;
            load();
        }
    }

    @Override
    public void changeUpdefalListener() {
        initData();
    }
}
