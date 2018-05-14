package com.jlkf.ego.fragment.wellcome;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;


/**
 * 欢迎页的第一个子页面 （注册）
 *
 * @author max
 */
public class Fragment1 extends Fragment {


    private View view;
    private LayoutInflater inflater;
    private Context mContext;
    private ImageView mViewById;
    private String mUrl;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        mContext = getActivity();
        if (view == null) {
            iniView();
        }
        return view;
    }


    private void iniView() {
        try {
            view = inflater.inflate(R.layout.fragment_wellcome_one, null);
            mViewById = (ImageView) view.findViewById(R.id.iv);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        mUrl = getArguments().getString("url");
        System.out.println(mUrl);
        Glide.with(mContext).load(mUrl).into(mViewById);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
