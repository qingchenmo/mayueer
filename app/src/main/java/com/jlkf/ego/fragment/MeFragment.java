package com.jlkf.ego.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.jlkf.ego.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/7.
 */
public class MeFragment extends BaseFragment {
    @Override
    public View getContentView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fmt_me, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void initView() {

    }
}
