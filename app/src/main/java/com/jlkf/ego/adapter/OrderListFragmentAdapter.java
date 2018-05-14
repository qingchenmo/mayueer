package com.jlkf.ego.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zcs on 2017/7/17.
 * 订单列表适配器
 */

public class OrderListFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTabs;
    private FragmentManager mManager;

    public OrderListFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabs) {
        super(fm);
        this.mFragments = fragments;
        mTabs = tabs;
        mManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position % mTabs.size());
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        /*Fragment fragment = mFragments.get(position);

        if (!fragment.isAdded()) {
            mManager.beginTransaction().add(fragment, mTabs.get(position)).commit();
        } else {
            mManager.beginTransaction().show(fragment).commit();
        }*/
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        mManager.beginTransaction().hide(mFragments.get(position));
        super.destroyItem(container, position, object);
    }
}
