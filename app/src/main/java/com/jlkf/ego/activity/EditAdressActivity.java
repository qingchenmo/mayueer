package com.jlkf.ego.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.jlkf.ego.R;
import com.jlkf.ego.adapter.EditAdapter;
import com.jlkf.ego.bean.AdressBean;
import com.jlkf.ego.bean.StringBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.UploadImage;
import com.jlkf.ego.utils.UIHelper;
import com.jlkf.ego.widget.BaseToolbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by Administrator on 2017/7/19 0019.
 * <p>
 * 编辑地址
 */

public class EditAdressActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.edit_rv)
    RecyclerView editRv;
    @BindView(R.id.rl_des)
    RelativeLayout mRlDes;
    private ArrayList<String> mPhotos;
    private ArrayList<String> allmPhotos;
    private EditAdapter mAdapter;
    private int mType;
    private AdressBean mBean;
    private Activity activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_edit_adress);
        ButterKnife.bind(this);
        activity = this;
        initView();

        allmPhotos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            allmPhotos.add("1");
        }
    }

    private void initView() {

        LinearLayoutManager layout = new LinearLayoutManager(this);
        editRv.setLayoutManager(layout);
        mType = getIntent().getIntExtra("type", 0);
        if (mType == 0) {
        } else if (mType == 1) {
            mBean = (AdressBean) getIntent().getSerializableExtra("bean");
        }
        mAdapter = new EditAdapter(this, allmPhotos, mBean, mType);
        editRv.setAdapter(mAdapter);

        toolbar.with(this);
        toolbar.setTitle(getResources().getString(R.string.dzbj));

        if (mType == 2) {
            toolbar.setRight(true);
            toolbar.setRightText("跳过");
            toolbar.setOnRightListener(new BaseToolbar.OnRightListener() {
                @Override
                public void onRight(View v) {
                    UIHelper.startOrer(EditAdressActivity.this, "home");
                    finish();
                }
            });
            mRlDes.setVisibility(View.VISIBLE);
        } else {
            mRlDes.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data != null) {
                mPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);


                UploadImage.upload1(activity, mPhotos, new HttpUtil.OnCallBack<StringBean>() {

                    @Override
                    public void success(StringBean data) {
                        String purl =data.getData().getPurl();
                        Log.v("头像", purl);

                        allmPhotos.set(requestCode, purl);
                        mAdapter.setPhotos(allmPhotos,requestCode);
                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                });


            }
        } else {

        }
    }
}
