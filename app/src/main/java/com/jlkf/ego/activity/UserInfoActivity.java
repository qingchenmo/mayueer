package com.jlkf.ego.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.bean.StringBean;
import com.jlkf.ego.bean.UserInfo;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.UploadImage;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.DialogUtil;
import com.jlkf.ego.utils.ShardeUtil;
import com.jlkf.ego.widget.BottomDialog;
import com.jlkf.ego.widget.CircleImageView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;


/**
 * @author zcs
 *         个人信息页面
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private CircleImageView civ_user_self_photo;
    private ImageView iv_title_back;
    private LinearLayout lin_user_img, lin_user_name, lin_user_signa, lin_user_phone_num, lin_user_call_num,
            lin_user_sex, lin_user_birthday, lin_user_weixin, lin_user_twitter;
    private TextView tv_user_name, tv_user_signa, tv_user_phone_num, tv_user_call_num, tv_user_sex,
            tv_user_birthday, tv_user_weixin, tv_user_twitter, tv_user_facebook;
    private TextView tv_dia_one, tv_dia_two, tv_cancel;
    public static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    public static final int REQUEST_CODE_EDITNAME = 2;
    public static final int REQUEST_CODE_EDITSIGNA = 3;
    public static final int REQUEST_CODE_EDITCALLNUM = 4;
    private Dialog mDialog;
    private ArrayList<String> mMPhotos;
    private Dialog mDialog1;
    private Dialog mDialog2;

    @Override
    public void initView() {
        setContentView(R.layout.activity_user_info);
        findView();
        getAndroiodScreenProperty();

    }

    public void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 屏幕宽度（像素）
        int height = dm.heightPixels; // 屏幕高度（像素）
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);//屏幕宽度(dp)
        int screenHeight = (int) (height / density);//屏幕高度(dp)
        Log.e("123", screenWidth + "======" + screenHeight);
    }

    /**
     * 创建DiaLog
     *
     * @param type 0为性别选择 1为相册选择
     */
    private void initDialog(int type) {
        View v = getLayoutInflater().inflate(R.layout.user_sex_select_layout, null);
        tv_dia_one = (TextView) v.findViewById(R.id.tv_dia_one);
        tv_dia_two = (TextView) v.findViewById(R.id.tv_dia_two);
        tv_cancel = (TextView) v.findViewById(R.id.tv_cancel);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        tv_dia_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_user_sex.setText(getResources().getString(R.string.nan));
                mDialog.dismiss();
                tag = 1;
                load("uSex", 1 + "");

            }
        });
        tv_dia_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_user_sex.setText(getResources().getString(R.string.nv));
                mDialog.dismiss();
                tag = 1;
                load("uSex", 2 + "");
            }
        });

        if (type == 0) {
            tv_dia_one.setText(getResources().getString(R.string.nan));
            tv_dia_two.setText(getResources().getString(R.string.nv));
        } else {
            tv_dia_one.setText(getResources().getString(R.string.zx));
            tv_dia_two.setText(getResources().getString(R.string.select_xc));
        }
        mDialog = new BottomDialog(mContext);
        mDialog.setContentView(v);
    }

    private void load(String key, String vulae) {
        JSONObject object = new JSONObject();
        try {
            object.put("uId", getUser().getUId());
            object.put(key, vulae);
            object.put("uName", getUser().getUName());
            object.put("uMobile", getUser().getUMobile());
            object.put("uPhone", getUser().getUPhone());

            HttpUtil.getInstacne(mActivity).post(Urls.updateUser, String.class, object, new HttpUtil.OnCallBack() {

                @Override
                public void success(Object data) {

                    if (tag == 3) {
                        getUser().setULogo(mMPhotos.get(0));
                        Glide.with(mContext).load(mMPhotos.get(0)).into(civ_user_self_photo);
                        ShardeUtil.putUser(getUser());
                    } else if (tag == 1 || tag == 2) {

                    } else if (tag == 4) {

                    }

                }

                @Override
                public void onError(String msg, int code) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("uId", getUser().getUId() + "");
        HttpUtil.getInstacne(mActivity).get(Urls.getUserInfo, UserInfo.class, map, new HttpUtil.OnCallBack<UserInfo>() {

            @Override
            public void success(UserInfo data) {

//                http://192.168.1.121:8182//images//adal//201709041730930.jpg


                // 设置头像
                mImageLoader.load(getUser().getULogo()).error(R.mipmap.icon_user_img).into(civ_user_self_photo);

                tv_user_name.setText(data.getUName());
                if (data.getUSex() != 1 || data.getUSex() != 2) {
                    tv_user_sex.setText(getResources().getString(R.string.user_sex));
                }

                if (data.getUTwitter() == null) {
                    tv_user_twitter.setText(getResources().getString(R.string.goto_bind));
                }

                if (data.getUFacebook() == null) {
                    tv_user_facebook.setText(getResources().getString(R.string.goto_bind));
                }

                if (TextUtils.isEmpty(data.getUWechat())) {
                    tv_user_weixin.setText(getResources().getString(R.string.goto_bind));
                } else {
                    tv_user_weixin.setText(getResources().getString(R.string.no_bind));
                }
                if (data.getUSex() == 1) {
                    tv_user_sex.setText(getResources().getString(R.string.nan));
                } else {
                    tv_user_sex.setText(getResources().getString(R.string.nv));
                }

                // 个性签名
                tv_user_signa.setText(data.getUSignature());

                tv_user_birthday.setText(data.getUBirthday());

                tv_user_phone_num.setText(data.getUMobile());
                tv_user_call_num.setText(data.getUPhone());
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }

    private void findView() {
        civ_user_self_photo = (CircleImageView) findViewById(R.id.civ_user_self_photo);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
        lin_user_img = (LinearLayout) findViewById(R.id.lin_user_img);
        lin_user_img.setOnClickListener(this);
        lin_user_name = (LinearLayout) findViewById(R.id.lin_user_name);
        lin_user_name.setOnClickListener(this);
        lin_user_signa = (LinearLayout) findViewById(R.id.lin_user_signa);
        lin_user_signa.setOnClickListener(this);
        lin_user_phone_num = (LinearLayout) findViewById(R.id.lin_user_phone_num);
        lin_user_phone_num.setOnClickListener(this);
        lin_user_call_num = (LinearLayout) findViewById(R.id.lin_user_call_num);
        lin_user_call_num.setOnClickListener(this);
        lin_user_sex = (LinearLayout) findViewById(R.id.lin_user_sex);
        lin_user_sex.setOnClickListener(this);
        lin_user_birthday = (LinearLayout) findViewById(R.id.lin_user_birthday);
        lin_user_birthday.setOnClickListener(this);
        lin_user_weixin = (LinearLayout) findViewById(R.id.lin_user_weixin);
        lin_user_weixin.setOnClickListener(this);
        lin_user_twitter = (LinearLayout) findViewById(R.id.lin_user_twitter);
        lin_user_twitter.setOnClickListener(this);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_signa = (TextView) findViewById(R.id.tv_user_signa);
        tv_user_phone_num = (TextView) findViewById(R.id.tv_user_phone_num);
        tv_user_call_num = (TextView) findViewById(R.id.tv_user_call_num);
        tv_user_sex = (TextView) findViewById(R.id.tv_user_sex);
        tv_user_birthday = (TextView) findViewById(R.id.tv_user_birthday);
        tv_user_weixin = (TextView) findViewById(R.id.tv_user_weixin);
        tv_user_twitter = (TextView) findViewById(R.id.tv_user_twitter);
        tv_user_facebook = (TextView) findViewById(R.id.tv_user_facebook);


    }

    private boolean aaa = false;

    @Override
    public void iniActivityAnimation() {

    }

    public static int BIND_PHONE_SUCCESS = 10;

    @Override
    public void onClick(View v) {
        final Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.lin_user_img:
//                Picker.from(this)
//                        .count(1)
//                        .enableCamera(true)
//                        .setEngine(new GlideEngine())
//                        .forResult(REQUEST_CODE_CHOOSE_PHOTO);
                // 上传图片
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(mActivity, PhotoPicker.REQUEST_CODE);

                break;
            case R.id.lin_user_name:
                intent.setClass(mContext, EditUserInfoActivity.class);
                intent.putExtra("maxNum", 30);
                startActivityForResult(intent, REQUEST_CODE_EDITNAME);
                break;
            case R.id.lin_user_signa:
                intent.setClass(mContext, EditUserInfoActivity.class);
                intent.putExtra("maxNum", 50);
                startActivityForResult(intent, REQUEST_CODE_EDITSIGNA);
                break;
            case R.id.lin_user_phone_num:

                if (!TextUtils.isEmpty(getUser().getUMobile())) {
                    mDialog1 = DialogUtil.getTitleWithTwoButtonDiaLog(UserInfoActivity.this,
                            getResources().getString(R.string.jcbd),
                            getResources().getString(R.string.ok), getResources().getString(R.string.no), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intent.setClass(mContext, ForgetPasswordActivity.class);
                                    intent.putExtra("text", 1);
                                    startActivityForResult(intent, BIND_PHONE_SUCCESS);
                                    mDialog1.dismiss();
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDialog1.dismiss();
                                }
                            });
                    mDialog1.show();
                }  else {
                    intent.setClass(mContext, ForgetPasswordActivity.class);
                    intent.putExtra("text", 1);
                    startActivityForResult(intent, BIND_PHONE_SUCCESS);
                }

                break;
            case R.id.lin_user_call_num:
                intent.setClass(mContext, EditUserInfoActivity.class);
                intent.putExtra("maxNum", 20);
                startActivityForResult(intent, REQUEST_CODE_EDITCALLNUM);
                break;
            case R.id.lin_user_sex:
                initDialog(0);
                mDialog.show();
                break;
            case R.id.lin_user_birthday:
                Calendar selectedDate = Calendar.getInstance();//系统当前时间
                selectedDate.add(Calendar.YEAR, -150);
                Calendar endDate = Calendar.getInstance();


                new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {

                        tv_user_birthday.setText(formatDates(date));
                        tag = 4;
                        load("uBirthday", formatDates(date));


                    }
                }).setType(new boolean[]{true, true, true, false, false, false})
                        .setDate(endDate)
                        .setRangDate(selectedDate, endDate)
                        .setCancelColor(Color.parseColor("#a5a5a5"))//取消按钮文字颜色
                        .setSubmitColor(Color.parseColor("#000000"))
                        .build()
                        .show();
                break;
            case R.id.lin_user_weixin:
                if (tv_user_weixin.getText().equals(getResources().getString(R.string.no_bind))) {

                    mDialog2 = DialogUtil.getTitleWithTwoButtonDiaLog(this,
                            getResources().getString(R.string.is_bind),
                            getResources().getString(R.string.ok), getResources().getString(R.string.no), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    UbindWX();
                                    mDialog2.dismiss();
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDialog2.dismiss();
                                }
                            });

                    mDialog2.show();
                } else {
                    bind(SHARE_MEDIA.WEIXIN);

                }
                break;
            case R.id.lin_user_twitter:
                bind(SHARE_MEDIA.TWITTER);
                break;
        }
    }


    // 注销微信
    private void UbindWX() {
        UMShareAPI.get(mContext).deleteOauth(mActivity, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Map<String, String> object = new HashMap<String, String>();
                object.put("uId", getUser().getUId() + "");
                object.put("type", "1");
                HttpUtil.getInstacne(mActivity).get(Urls.unBind, String.class, object, new HttpUtil.OnCallBack() {
                    @Override
                    public void success(Object data) {

                        initData();
                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                });

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    public void bind(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.v("第三方授权", "开始" + share_media.toString());

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.v("第三方授权", "结束" + map.toString());
                // 开始绑定
                if (SHARE_MEDIA.WEIXIN == share_media) {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("uId", getUser().getUId());
                        object.put("bindType", 1);
                        object.put("uWechat", map.get("openid").toString());
                        object.put("uWechatName", map.get("screen_name").toString());
                        Log.e("uWechatName",map.get("screen_name").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    HttpUtil.getInstacne(mActivity).post(Urls.userBind, String.class, object, new HttpUtil.OnCallBack() {
                        @Override
                        public void success(Object data) {
                            initData();
                        }

                        @Override
                        public void onError(String msg, int code) {

                        }
                    });
                }

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.v("第三方授权", "失败" + share_media.toString());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.v("第三方授权", "取消" + share_media.toString());
            }
        });

    }

    public String filterEmoji(String source) {
        String string = "";
        if (!containsEmoji(source)) {
            return source;// 如果不包含，直接返回
        }
        StringBuilder buf = null;
        int len = source.length();
        System.out.println("filter running len = " + len);
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (buf == null) {
                buf = new StringBuilder(source.length());
            }
            if (!isEmojiCharacter(codePoint)) {
                string = String.valueOf(codePoint);
            } else {
                try {
                    StringBuilder builder = new StringBuilder(2);
                    byte[] str = builder.append(String.valueOf(codePoint))
                            .append(String.valueOf(source.charAt(i+1)))
                            .toString().getBytes("UTF-8");
                    String strin = Arrays.toString(str);
                    String newString = strin.substring(1, strin.length() - 1);
                    string = "Γ"+newString+"Γ";
                    System.out.println("filters running newStr = " + string);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                i++;
            }
            buf.append(string+"⅞");
        }
        if (buf == null) {
            return "";
        } else {
            if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                System.out.println("filter running buf.toString() = " + buf.toString());
                String bufStr = buf.toString();
                String newBufStr= bufStr.substring(0, bufStr.length() - 1);
                return newBufStr;
            }
        }
    }

    // 判别是否包含Emoji表情
    private boolean containsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }


    private String formatDates(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(date);
    }

    private int tag = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                mMPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                UploadImage.upload1(mActivity, mMPhotos, new HttpUtil.OnCallBack<StringBean>() {
                    @Override
                    public void success(StringBean data) {
                        tag = 3;
                        Log.v("头像", data.getData().getPurl());
                        // 绑定头像前 先上传头像到服务器
                        load("uLogo",data.getData().getPurl());
                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                });
            }
        } else {

            initData();
        }


    }
}

