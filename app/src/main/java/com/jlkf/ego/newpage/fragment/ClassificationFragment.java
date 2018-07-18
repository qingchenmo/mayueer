package com.jlkf.ego.newpage.fragment;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.ProductListActivity;
import com.jlkf.ego.fragment.BaseFragment;
import com.jlkf.ego.newpage.adapter.ClassificationLeftAdapter;
import com.jlkf.ego.newpage.adapter.ClassificationRightAdapter;
import com.jlkf.ego.newpage.bean.ClassificationBean;
import com.jlkf.ego.newpage.bean.GroupBean;
import com.jlkf.ego.newpage.inter.OnItemClickListener;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.HttpUtils;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 分类fragment
 */
public class ClassificationFragment extends BaseFragment {


    @BindView(R.id.left_recycle_view)
    RecyclerView mLeftRecycleView;
    @BindView(R.id.right_recycle_view)
    RecyclerView mRightRecycleView;
    Unbinder unbinder;
    public static List<GroupBean> mGroupList = new ArrayList<>();
    private String mBrandId;
    private String mIconId;
    private String mGroupId;
    private String twoGroupId;

    public void setmBrandId(String mBrandId) {
        this.mBrandId = mBrandId;
        if (getView() != null && getView().getVisibility() == View.VISIBLE) {
            initData();
        }
    }

    public void setIconId(String iconId) {
        this.mIconId = iconId;
        if (getView() != null && getView().getVisibility() == View.VISIBLE) {
            initData();
        }
    }


    public ClassificationFragment() {

    }


    @Override
    public View getContentView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_classification, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void initView() {
        mLeftRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRightRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {
        super.initData();
        if (getArguments() != null) {
            mGroupId = getArguments().getString("group_id");
            twoGroupId = getArguments().getString("itmsGrpCod");
        }
        ApiManager.getGroupList(mBrandId, mIconId, getActivity(), new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                List<GroupBean> list = JSON.parseArray(response, GroupBean.class);
                mGroupList.clear();
                mGroupList.addAll(list);
                if (mGroupList.size() > 0)
                    mGroupList.get(0).setSelect(true);
                mLeftRecycleView.setAdapter(new ClassificationLeftAdapter(getActivity(), mGroupList,
                        mGroupId, new OnItemClickListener<GroupBean>() {
                    @Override
                    public void itemClickListener(GroupBean s, int position) {
                        getRightData(s);
                    }
                }));
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void getRightData(final GroupBean bean) {
        OkGo.getInstance().cancelTag(getActivity());
        mRightRecycleView.setAdapter(new ClassificationRightAdapter(getActivity(), bean.getItmsGrpNam(), 0
                , new ArrayList<ClassificationBean>(), null));
        ApiManager.getSubtype(bean.getItemGroup_id(), mBrandId, mIconId, getActivity(), new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                final List<ClassificationBean> list = JSON.parseArray(response, ClassificationBean.class);
                int dex = 0;
                if (!TextUtils.isEmpty(twoGroupId)) {
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        if (list.get(i).getItemGroup_id().equals(twoGroupId)) {
                            dex = i + 1;
                            break;
                        }
                    }
                }
                mRightRecycleView.setAdapter(new ClassificationRightAdapter(getActivity(), bean.getItmsGrpNam(), dex
                        , list, new OnItemClickListener<ClassificationBean>() {
                    @Override
                    public void itemClickListener(ClassificationBean classificationBean, int position) {
                        Intent intent = new Intent(mContext, ProductListActivity.class);
                        intent.putExtra("code", mBrandId);
                        intent.putExtra("iconId", mIconId);
                        intent.putExtra("groupId", bean.getItemGroup_id());
                        intent.putExtra("itmsGrpCod", list.get(position).getItemGroup_id());
                        for (int i = 0; i < mGroupList.size(); i++) {
                            if (mGroupList.get(i).isSelect()) {
                                mGroupList.get(i).mNowSelect = position;
                                break;
                            }
                        }
                        mContext.startActivity(intent);
                    }
                }));
            }

            @Override
            public void onError(String msg) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
