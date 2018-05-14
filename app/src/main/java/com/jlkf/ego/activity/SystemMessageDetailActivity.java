package com.jlkf.ego.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.bean.MessageDetailBean;
import com.jlkf.ego.bean.MyBaseBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.umanalytics.UMUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息详情
 *
 * @author 邓超桂
 * @date 创建时间：2017年7月12日 下午3:40:13
 */
public class SystemMessageDetailActivity extends BaseActivity implements View.OnClickListener {

    //title
    private TextView tv_title;
    private ImageView iv_back;

    private TextView tv_message_title;
    private TextView tv_message_time;
    private TextView tv_message_content;
    private LinearLayout ll_image;
    private ImageView iv_one;
    private ImageView iv_two;
    private ImageView iv_three;


    @Override
    public void iniActivityAnimation() {
        //滑动关闭activity
        addSwipeFinishLayout();
        //设置滑动开启/关闭
        setEnableGesture(true);
    }

    public String getDate(long s) {
        Date date = new Date(s);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void initData() {

        String id = getIntent().getStringExtra("id");

        Map<String, String> map = new HashMap<>();
        map.put("id", id);            // 消息id
        HttpUtil.getInstacne(mActivity).get(Urls.getMessageDetail, String.class, map, new HttpUtil.OnCallBack<String>() {

            @Override
            public void success(String data) {
                MessageDetailBean messageDetailBean = MessageDetailBean.objectFromData(data);

                tv_message_title.setText(messageDetailBean.getMTitle());
                tv_message_content.setText(messageDetailBean.getMContext());
                tv_message_time.setText(getDate(messageDetailBean.getMCreationtime()));
                setIsRead(messageDetailBean.getMId() + "");
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }

    public void setIsRead(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
//        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        HttpUtil.getInstacne(this).get2(Urls.isRead, MyBaseBean.class, map, new HttpUtil.OnCallBack<MyBaseBean>() {
            @Override
            public void success(MyBaseBean data) {

            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }


    @Override
    public void initView() {
        setContentView(R.layout.activity_message_detail);

        tv_title = getView(R.id.tv_title);
        iv_back = getView(R.id.iv_back);

        tv_title.setText(getResources().getString(R.string.message_detail_title));

        tv_message_title = getView(R.id.tv_message_title);
        tv_message_time = getView(R.id.tv_message_time);
        tv_message_content = getView(R.id.tv_message_content);
        ll_image = getView(R.id.ll_image);
        iv_one = getView(R.id.iv_one);
        iv_two = getView(R.id.iv_two);
        iv_three = getView(R.id.iv_three);


        iv_back.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        BaseActivity.finish(SystemMessageDetailActivity.this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMUtils.onResumeUMActivity(mContext, "SystemMessageDetail_page");
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.onPauseUMActivity(mContext, "SystemMessageDetail_page");
    }
}
