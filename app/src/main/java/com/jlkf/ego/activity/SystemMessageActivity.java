package com.jlkf.ego.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.adapter.SystemMessageAdapter;
import com.jlkf.ego.bean.MessageListBean;
import com.jlkf.ego.bean.SystemMessageBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.umanalytics.UMUtils;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.utils.AppUtil;
import com.jlkf.ego.utils.ToastUti;
import com.jlkf.ego.widget.XListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统消息(消息中心)
 *
 * @author 邓超桂
 * @date 创建时间：2017年7月12日 下午3:40:13
 */
public class SystemMessageActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, AdapterView.OnItemLongClickListener {

    //title
    private TextView tv_title;
    private ImageView iv_back;

    private XListView lv_systemMessage;
    private View view_nodata;

    private SystemMessageAdapter mSystemMessageAdapter;
    private List<SystemMessageBean> mListData = new ArrayList<SystemMessageBean>();

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://获取系统消息数据返回
                    AppLog.Loge("dcg", "获取系统消息数据返回:" + String.valueOf(msg.obj));
//                    getResult(String.valueOf(msg.obj));
                    break;
                case 2://网络异常
                    showToast(String.valueOf(msg.obj), 0);
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 删除购物车
     *
     * @param layoutPosition
     */
    private void showDialogs(final int layoutPosition) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.delet_dialog, null);
        final Dialog dialog = new AlertDialog.Builder(mContext)
                .setView(inflate)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_radius_white);

        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUti.show(getResources().getString(R.string.no));
                dialog.dismiss();
            }
        });
        inflate.findViewById(R.id.tv_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<MessageListBean> list = mSystemMessageAdapter.getList();

                Map<String, String> map = new HashMap<String, String>();
                map.put("id", list.get(layoutPosition - 1).getMId() + "");
                HttpUtil.getInstacne(mActivity).get(Urls.deleteMessage, String.class, map, new HttpUtil.OnCallBack() {
                    @Override
                    public void success(Object data) {
                        list.remove(list.get(layoutPosition - 1));
                        mSystemMessageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                });


                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void iniActivityAnimation() {
        //滑动关闭activity
        addSwipeFinishLayout();
        //设置滑动开启/关闭
        setEnableGesture(true);
    }


    @Override
    public void initData() {


        Map<String, String> map = new HashMap<>();
        map.put("uId", getUser().getUId() + "");
        map.put("pageNo", "");
        map.put("pageSize", "");
        HttpUtil.getInstacne(mActivity).get(Urls.getMessage, String.class, map, new HttpUtil.OnCallBack<String>() {

            @Override
            public void success(String data) {
                if (!data.contains("暂无消息")) {


                    List<MessageListBean> messageListBeen = MessageListBean.arrayMessageListBeanFromData(data);


                    mSystemMessageAdapter = new SystemMessageAdapter(mActivity);
                    mSystemMessageAdapter.setData(messageListBeen);
                    lv_systemMessage.setAdapter(mSystemMessageAdapter);
                } else {
                    ToastUti.show(getResources().getString(R.string.no_message));
                }
            }

            @Override
            public void onError(String msg, int code) {

            }
        });

        lv_systemMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private Intent mIntent;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                List<MessageListBean> list = mSystemMessageAdapter.getList();
                list.get(position - 1).setMsIsunread("1");
                mIntent = new Intent(mContext, SystemMessageDetailActivity.class);
                mIntent.putExtra("id", list.get(position - 1).getMId() + "");
                BaseActivity.startActivity(mIntent, SystemMessageActivity.this);
                mSystemMessageAdapter.notifyDataSetChanged();
            }
        });

        lv_systemMessage.setOnItemLongClickListener(this);

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_system_message);

        tv_title = getView(R.id.tv_title);
        iv_back = getView(R.id.iv_back);

        tv_title.setText(getResources().getString(R.string.system_message_title));

        lv_systemMessage = getView(R.id.lv_systemMessage);
        view_nodata = getView(R.id.view_nodata);


        lv_systemMessage.setRefreshTime("上次更新时间：" + AppUtil.onLoadTime());
        lv_systemMessage.setXListViewListener(this);
        lv_systemMessage.setPullRefreshEnable(true);
        lv_systemMessage.setPullLoadEnable(false);


        iv_back.setOnClickListener(this);
    }

//    /**
//     * 系统消息item事件
//     */
//    View.OnClickListener itemSystemMessageClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
////            showToast("点击系统消息",0);
//
//        }
//    };

    private void updataView() {
        if (mListData != null && mListData.size() > 0) {
            mListData.clear();
        }
        for (int i = 0; i < 5; i++) {
            SystemMessageBean info = new SystemMessageBean();
            mListData.add(info);
        }
//        mSystemMessageAdapter.setData(mListData);
        mSystemMessageAdapter.notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {

        lv_systemMessage.setRefreshTime("上次更新时间：" + AppUtil.onLoadTime());
        if (isNetworkConnected(mContext)) {
            lv_systemMessage.stopRefresh();
//            NetworkUtil.getUserDomainListAPI(uid, token, page, mHandler, 1, mContext);
        } else {
            AppUtil.sendMessage(2, AppUtil.networkError(mContext), mHandler);
            lv_systemMessage.stopRefresh();
        }
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        BaseActivity.finish(SystemMessageActivity.this);
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
        UMUtils.onResumeUMActivity(mContext, "SystemMessage_page");
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.onPauseUMActivity(mContext, "SystemMessage_page");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showDialogs(position);
        return true;
    }
}
