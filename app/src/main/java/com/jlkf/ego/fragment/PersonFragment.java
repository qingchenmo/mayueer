package com.jlkf.ego.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.AppServerCenterActivity;
import com.jlkf.ego.activity.MyConnectionActivity;
import com.jlkf.ego.activity.OrderListActivity;
import com.jlkf.ego.activity.SettingActivity;
import com.jlkf.ego.activity.SystemMessageActivity;
import com.jlkf.ego.activity.UserInfoActivity;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.OrderCountBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.newpage.activity.EventActyivity;
import com.jlkf.ego.newpage.activity.MembershipGradeActivity;
import com.jlkf.ego.newpage.activity.ValidationActivity;
import com.jlkf.ego.newpage.activity.YouHuiActivity;
import com.jlkf.ego.newpage.adapter.PersonActivityAdapter;
import com.jlkf.ego.newpage.bean.GradeInfoBean;
import com.jlkf.ego.newpage.bean.PersonActivityListBean;
import com.jlkf.ego.newpage.inter.OnItemClickListener;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.HttpUtils;
import com.jlkf.ego.utils.UIHelper;
import com.jlkf.ego.widget.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zcs
 *         个人资料Fragment
 */
public class PersonFragment extends BaseFragment {
    private CircleImageView civ_user_self_photo;
    private TextView tv_user_name;
    private TextView tv_wait_order_num, tv_wait_order_send, tv_has_order_send, tv_has_order_shouhuo, tv_has_order_cancel, rl_stor_address;

    @BindView(R.id.tv_redCircle_system)
    TextView tvRedCircleSystem;
    @BindView(R.id.recycler_view)
    RecyclerView mRecycleView;
    @BindView(R.id.lin_membership_grade)
    LinearLayout mTvMembershipGrade;
    @BindView(R.id.iv_grade_img)
    ImageView mIvGradeImg;
    @BindView(R.id.tv_grade_name)
    TextView mTvGradeName;
    @BindView(R.id.view_bg)
    View mViewbg;
    @BindView(R.id.view_top)
    View mViewTop;
    @BindView(R.id.tv_need_integer)
    TextView mTvNeedInteger;
    private List<PersonActivityListBean> activityList = new ArrayList<>();

    @Override
    public View getContentView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fmt_me, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        ApiManager.getRanks(MyApplication.getmUserBean().getUId() + "", getActivity(), new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                GradeInfoBean bean = JSON.parseObject(response, GradeInfoBean.class);
                Glide.with(getActivity()).load(bean.getImg()).into(mIvGradeImg);
                mTvGradeName.setText(bean.getRankname());
                mTvMembershipGrade.setVisibility(View.VISIBLE);
                mTvNeedInteger.setText(bean.getNextpoint() + "分升级");
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mViewTop.getLayoutParams();
                params.width = mViewbg.getMeasuredWidth() * bean.getPoint() / bean.getNextrankpoint();
                mViewTop.setLayoutParams(params);
            }

            @Override
            public void onError(String msg) {
                mTvGradeName.setText("无会员");
                mTvMembershipGrade.setVisibility(View.VISIBLE);
            }
        });
        HttpUtil.getInstacne(mActivity).get(Urls.allRead, Integer.class, map, new HttpUtil.OnCallBack<Integer>() {
            @Override
            public void success(Integer data) {

                if (data == 0) {
                    tvRedCircleSystem.setVisibility(View.GONE);
                } else {
                    tvRedCircleSystem.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
        /*ApiManager.activitylist(getActivity(), new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                List<PersonActivityListBean> list = JSON.parseArray(response, PersonActivityListBean.class);
                activityList.addAll(list);
                mRecycleView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onError(String msg) {

            }
        });*/
    }


    @Override
    public void initView() {
        tv_wait_order_num = (TextView) rootView.findViewById(R.id.tv_wait_order_num);
        tv_wait_order_send = (TextView) rootView.findViewById(R.id.tv_wait_order_send);
        tv_has_order_send = (TextView) rootView.findViewById(R.id.tv_has_order_send);
        tv_has_order_shouhuo = (TextView) rootView.findViewById(R.id.tv_has_order_shouhuo);
        rl_stor_address = (TextView) rootView.findViewById(R.id.rl_stor_address);
        tv_has_order_cancel = (TextView) rootView.findViewById(R.id.tv_has_order_cancel);
        civ_user_self_photo = (CircleImageView) rootView.findViewById(R.id.civ_user_self_photo);
        tv_user_name = (TextView) rootView.findViewById(R.id.tv_user_name);

        SpannableStringBuilder style = new SpannableStringBuilder(rl_stor_address.getText().toString());
        style.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        rl_stor_address.setText(style);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setNestedScrollingEnabled(false);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(new PersonActivityAdapter(getActivity(), activityList, new OnItemClickListener<PersonActivityListBean>() {
            @Override
            public void itemClickListener(PersonActivityListBean personActivityListBean, int position) {
                Intent intent = new Intent(getActivity(), EventActyivity.class);
                intent.putExtra("id", personActivityListBean.getAt_id());
                intent.putExtra("type", personActivityListBean.getAttype());
                intent.putExtra("title", personActivityListBean.getName());
                startActivity(intent);
            }
        }));
    }


    @OnClick({R.id.lin_user_edit_info, R.id.fl_order_all, R.id.fl_order_wait_accept, R.id.fl_order_wait_send,
            R.id.fl_order_onway, R.id.fl_order_complete, R.id.fl_order_has_cancel, R.id.fl_system_msg, R.id.fl__my_collection,
            R.id.fl_server_hotline, R.id.fl_setting, R.id.rl_stor_address, R.id.lin_membership_grade, R.id.fl_youhui/*, R.id.civ_user_self_photo*/
    })
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lin_user_edit_info:
                if (MyApplication.mHasComfim) {
                    intent.setClass(mContext, UserInfoActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(mContext, ValidationActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fl_order_all:
                intent.setClass(mContext, OrderListActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_order_wait_accept:
                intent.setClass(mContext, OrderListActivity.class);
                intent.putExtra("select", 1);
                startActivity(intent);
                break;
            case R.id.fl_order_wait_send:
                intent.setClass(mContext, OrderListActivity.class);
                intent.putExtra("select", 2);
                startActivity(intent);
                break;
            case R.id.fl_order_onway:
                intent.setClass(mContext, OrderListActivity.class);
                intent.putExtra("select", 3);
                startActivity(intent);
                break;
            case R.id.fl_order_complete:
                intent.setClass(mContext, OrderListActivity.class);
                intent.putExtra("select", 4);
                startActivity(intent);
                break;
            case R.id.fl_order_has_cancel:
                intent.setClass(mContext, OrderListActivity.class);
                intent.putExtra("select", 5);
                startActivity(intent);
                break;
            case R.id.fl_system_msg:
                intent.setClass(mContext, SystemMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.fl__my_collection:
                intent.setClass(mContext, MyConnectionActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_server_hotline:
                intent.setClass(mContext, AppServerCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_setting:
                intent.setClass(mContext, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_stor_address:
                UIHelper.startOrer(getActivity(), "adress");
                break;
            case R.id.lin_membership_grade:
                intent.setClass(mContext, MembershipGradeActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_youhui:
                intent.setClass(mContext, YouHuiActivity.class);
                startActivity(intent);
                break;
            /*case R.id.civ_user_self_photo:
                intent.setClass(mContext, ValidationActivity.class);
                startActivity(intent);
                break;*/
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrderCount();

        Glide.with(mActivity).
                load(MyApplication.getmUserBean().getULogo())
                .error(R.mipmap.icon_user_img)
                .into(civ_user_self_photo);
        tv_user_name.setText(MyApplication.getmUserBean().getUName());
    }

    public void getOrderCount() {
        Map<String, String> map = new HashMap<>();
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        final TextView[] tvs = new TextView[]{tv_wait_order_num, tv_wait_order_send, tv_has_order_send, tv_has_order_shouhuo, tv_has_order_cancel};
        HttpUtil.getInstacne(getActivity()).get2(Urls.count, OrderCountBean.class, map, new HttpUtil.OnCallBack<OrderCountBean>() {
            @Override
            public void success(OrderCountBean data) {
                List<OrderCountBean.DataBean> list = data.getData();
                for (int i = 0; i < list.size(); i++) {
                    OrderCountBean.DataBean bean = list.get(i);
                    tvs[bean.getNum()].setVisibility(bean.getCount() == 0 ? View.GONE : View.VISIBLE);
                    tvs[bean.getNum()].setText(bean.getCount() + "");
                }
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }
}
