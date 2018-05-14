package com.jlkf.ego.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.bean.AppServerBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.umanalytics.UMUtils;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.utils.AppUtil;
import com.jlkf.ego.utils.ShardeUtil;
import com.jlkf.ego.widget.MyDialog;
import com.jlkf.ego.widget.permission.PermissionUtils;
import com.jlkf.ego.widget.rom.AndroidRomUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jlkf.ego.utils.NetworkUtil.mHandler;

/**
 * 服务咨询
 *
 * @author 邓超桂
 * @date 创建时间：2017年7月11日 下午3:40:13
 */
public class AppServerCenterActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_kefu_phone;
    private LinearLayout ll_company_phone;

    private Button btn_kefu_phone;
    private TextView tv_phone, tv_company_label,tv_company_time_label;
    private Button btn_company_phone;

    private String KEFU_PHONE = "10086";
    private String COMMPANY_PHONE = "1008611";

    //请求客服打电话的权限
    private final int CALLPHONE_PERMISSION_RESULT_CODE = 522;
    //请求公司打电话的权限
    private final int CALLPHONE_PERMISSION_COMPANY_RESULT_CODE = 523;
    private String mSmName;
    private String mSmPhone;

    @Override
    public void iniActivityAnimation() {
        //滑动关闭activity
        addSwipeFinishLayout();
        //设置滑动开启/关闭
        setEnableGesture(true);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_server_center);

        ll_kefu_phone = getView(R.id.ll_kefu_phone);
        ll_company_phone = getView(R.id.ll_company_phone);
        tv_phone = getView(R.id.tv_phone);
        tv_company_label = getView(R.id.tv_company_label);
        tv_company_time_label = getView(R.id.tv_company_time_label);

        btn_kefu_phone = getView(R.id.btn_kefu_phone);
        btn_company_phone = getView(R.id.btn_company_phone);
        btn_kefu_phone.setOnClickListener(this);
        btn_company_phone.setOnClickListener(this);
        ll_kefu_phone.setOnClickListener(this);
        ll_company_phone.setOnClickListener(this);

    }

    @Override
    public void initData() {


        Map<String, String> object = new HashMap<>();
        object.put("pageSize", "1");
        object.put("pageNumber", "1");
        object.put("type", ShardeUtil.getUser().getUType() + "");


        HttpUtil.getInstacne(mActivity).get(Urls.phone, String.class, object, new HttpUtil.OnCallBack<String>() {


            @Override
            public void success(String data) {

                List<AppServerBean> appServerBeen = AppServerBean.arrayAppServerBeanFromData(data);
                AppServerBean appServerBean = appServerBeen.get(0);
                COMMPANY_PHONE = appServerBean.getPhone();
                tv_phone.setText(COMMPANY_PHONE);
                tv_company_label.setText(getResources().getString(R.string.zx_time) + appServerBean.getOnlinetime());
                tv_company_time_label.setText(appServerBean.getOnlinecycle());
//                KEFU_PHONE = appServerBean.getSmPhone();
//                btn_kefu_phone.setText( getResources().getString(R.string.Exclusive_customer)+ "（" + appServerBean.getSmName() + "）");
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        BaseActivity.finish(AppServerCenterActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CALLPHONE_PERMISSION_RESULT_CODE://请求打电话权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户同意权限
                    AppLog.Logd("用户同意打电话权限");
                    callMobilePhone(KEFU_PHONE);
                } else {
                    //用户拒绝权限
                    AppLog.Logd("用户拒绝打电话权限");
                    //引导用户打开权限,打开系统设置
                    AppUtil.sendMessage(2, "APP需要开启打电话权限才能使用此功能", mHandler);
                    AndroidRomUtil.showDialogToSystemSetting(mContext);
                }
                break;
            case CALLPHONE_PERMISSION_COMPANY_RESULT_CODE://请求打电话权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户同意权限
                    AppLog.Logd("用户同意打电话权限");
                    callMobilePhone(KEFU_PHONE);
                } else {
                    //用户拒绝权限
                    AppLog.Logd("用户拒绝打电话权限");
                    //引导用户打开权限,打开系统设置
                    AppUtil.sendMessage(2, "APP需要开启打电话权限才能使用此功能", mHandler);
                    AndroidRomUtil.showDialogToSystemSetting(mContext);
                }
                break;
            default:
                break;
        }
    }

    private void callMobilePhone(final String mobile2) {
        dialog = MyDialog.showTelPhoneDialog(mContext,mContext.getString(R.string.ts),
                mContext.getString(R.string.bdgyhddh) , mContext.getString(R.string.bh),mContext.getString(R.string.no),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // 获取手机系统自带拨打电话功能
                        // 封装一个拨打电话的intent，并且将电话号码包装成一个Uri对象传入
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile2));
                        if (ActivityCompat.checkSelfPermission(AppServerCenterActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mContext.startActivity(intent);// 内部类
                        dialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                onBackPressed();
                break;

            case R.id.ll_kefu_phone://专属客服
            case R.id.btn_kefu_phone:
                //打电话，6.0以上手机需要申请权限
                if (PermissionUtils.checkPermission(AppServerCenterActivity.this, Manifest.permission.CALL_PHONE,
                        "APP需要开启打电话权限才能使用此功能", CALLPHONE_PERMISSION_RESULT_CODE)) {
                    callMobilePhone(KEFU_PHONE);
                }

                break;
            case R.id.ll_company_phone://公司电话
            case R.id.btn_company_phone:
                //打电话，6.0以上手机需要申请权限
                if (PermissionUtils.checkPermission(AppServerCenterActivity.this, Manifest.permission.CALL_PHONE,
                        "APP需要开启打电话权限才能使用此功能", CALLPHONE_PERMISSION_COMPANY_RESULT_CODE)) {
                    callMobilePhone(COMMPANY_PHONE);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMUtils.onResumeUMActivity(mContext, "AppServerCenter_page");
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.onPauseUMActivity(mContext, "AppServerCenter_page");
    }
}
