package com.jlkf.ego.fragment.wellcome;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.WellcomeActivity;
import com.jlkf.ego.utils.SharedPreferencesUtil;


/**
 * 欢迎页的第三个子页面 （行情）
 *
 * @author max
 */
public class Fragment3 extends Fragment {


    private View view;
    private LayoutInflater inflater;
    private Button btn_go;

    private SharedPreferencesUtil mSharedPreferencesUtil;
    private String mUrl;
    private ImageView mViewById;
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;

        mSharedPreferencesUtil = new SharedPreferencesUtil(getActivity(), "start");
        mContext = getActivity();
        if (view == null) {
            iniView();
        }
        return view;
    }

    private void iniView() {
        try {
            view = inflater.inflate(R.layout.fragment_wellcome_three, null);
            btn_go = (Button) view.findViewById(R.id.btn_go);
            WellcomeActivity mWellcomeActivity = (WellcomeActivity) getActivity();

            mViewById = (ImageView) view.findViewById(R.id.iv);


            btn_go.setOnClickListener(mWellcomeActivity.mainListener);
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
