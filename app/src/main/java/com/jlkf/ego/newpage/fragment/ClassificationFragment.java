package com.jlkf.ego.newpage.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jlkf.ego.R;
import com.jlkf.ego.fragment.BaseFragment;
import com.jlkf.ego.newpage.adapter.ClassificationLeftAdapter;
import com.jlkf.ego.newpage.adapter.ClassificationRightAdapter;
import com.jlkf.ego.newpage.bean.ClassificationBean;
import com.jlkf.ego.newpage.inter.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
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

    public ClassificationFragment() {

    }


    @Override
    public View getContentView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_classification, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void initView() {
        mLeftRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRightRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String[] strs = new String[]{"全部", "充电器", "电池", "耳机", "键鼠", "平板皮(套)/壳XXX",
                "其他", "手机电脑贴膜", "手机皮套/壳", "线材", "手机电脑贴膜", "手机皮套/壳", "线材", "手机电脑贴膜", "手机皮套/壳", "线材"};
        List<String> leftList = Arrays.asList(strs);
        mLeftRecycleView.setAdapter(new ClassificationLeftAdapter(getActivity(), leftList,
                new OnItemClickListener<String>() {
                    @Override
                    public void itemClickListener(String s, int position) {
                        List<ClassificationBean> rightList = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            ClassificationBean bean = new ClassificationBean();
                            bean.setImg(R.mipmap.pic2);
                            bean.setText("CD TYPE C 最新充电器套装(15)");
                            rightList.add(bean);
                        }
                        mRightRecycleView.setAdapter(new ClassificationRightAdapter(getActivity(), s
                                , rightList, new OnItemClickListener<ClassificationBean>() {
                            @Override
                            public void itemClickListener(ClassificationBean classificationBean, int position) {

                            }
                        }));
                    }
                }));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
