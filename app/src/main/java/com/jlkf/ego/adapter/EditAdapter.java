package com.jlkf.ego.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.MainActivity;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.AdressBean;
import com.jlkf.ego.bean.CityBean;
import com.jlkf.ego.bean.CountrBean;
import com.jlkf.ego.bean.PayType;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.utils.LanguageUtils;
import com.jlkf.ego.utils.ShardeUtil;
import com.jlkf.ego.utils.ToastUti;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class EditAdapter extends RecyclerView.Adapter {

    public final static int TYPE_HEAD = 0;
    private static final int TYPE_TOW = 1;
    private static final int TYPE_THREE = 3;
    private static final int TYPE_FUOR = 4;


    public static final int gender = 100;
    public static final int city = 111;
    public static final int countr = 112;
    public static final int dianpu_type = 102;
    public static final int fapiao = 103;
    public static final int yingyeshijian = 104;
    public static final int songhuoshijian = 105;
    public static final int zhifu = 106;
    public static final int dianmianji = 107;
    public static final int mubiaokehu = 108;
    public static final int wangji = 109;
    public static final int danji = 110;
    public static final int jylx = 115;
    public int type = gender;

    OptionsPickerView pvOptions;
    OptionsPickerView pvOptions1;
    OptionsPickerView.Builder builder, builder1;

    List<String> options1Items;


    List<String> fps;
    List<String> mbkh;
    List<String> dplx;
    List<String> jylxs;


    private Context context;
    private LinearLayoutManager layout;
    private HeadViewHolder mHeadViewHolder;
    private TowViewHolder mTowViewHolder;
    private ThreeViewHolder mThreeViewHolder;
    private FourViewHolder mFourViewHolder;
    private final AdressBean mAdressBean;
    private List<CountrBean> mCountrBeen;
    private List<String> mCityAndCountr;
    private List<CityBean> mCityBeen;
    private String mCountryname;
    private String mProvincename;
    private int mType;
    private List<PayType> mPayTypes;
    private List<String> mPays;
    private String mQuhao;

    public void setPhotos(ArrayList<String> photos, int requestCode) {
        String s = photos.get(requestCode);
        this.photos.set(requestCode, s);
        setBG();
        notifyDataSetChanged();
    }

    private ArrayList<String> photos;

    public EditAdapter(final Context context, ArrayList<String> photos, AdressBean bean, int type) {
        this.context = context;
        this.mType = type;

        if (photos != null) {

            this.photos = photos;
        } else {
            this.photos = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                this.photos.add("1");
            }
        }
        if (bean == null) {
            mAdressBean = new AdressBean();
        } else {
            mAdressBean = bean;
        }
        mPays = new ArrayList<String>();
        options1Items = new ArrayList();
        mCityAndCountr = new ArrayList<>();

        if (mType == 1) {
            isXing = true;
            isMing = true;
            isPhone = true;
            isSdmc = true;
            isGsmc = true;
            isShdz = true;
            isSh = true;
            isZffs = true;
            isFp = true;
            isFpdz = true;
            isPostak = true;
        }

        builder = new OptionsPickerView.Builder(context, new NormalOnOptionsSelectListener());
        builder1 = new OptionsPickerView.Builder(context, new CityAndCountrListener());

        fps = new ArrayList<>();
        fps.clear();
        fps.add("需要客户自付发票");


        mbkh = new ArrayList<>();
        mbkh.clear();
        mbkh.add(context.getResources().getString(R.string.bdk));
        mbkh.add(context.getResources().getString(R.string.bdz_ykf));
        mbkh.add(context.getResources().getString(R.string.ykz_bdf));
        mbkh.add(context.getResources().getString(R.string.ykz));


        dplx = new ArrayList<>();
        dplx.clear();
        dplx.add("手机店");
        dplx.add("百元店");
        dplx.add("混合店");
        dplx.add("数码卖场店");
        dplx.add("Locutorio");
        dplx.add("老外");
        dplx.add("仓库");
        dplx.add("其他");

        jylxs = new ArrayList<>();
        jylxs.clear();
        jylxs.add(context.getResources().getString(R.string.gth));
        jylxs.add(context.getResources().getString(R.string.ungth));
        if (mPayTypes == null) {
            HttpUtil.getInstacne((Activity) context).get(Urls.payType, String.class, new HttpUtil.OnCallBack<String>() {
                @Override
                public void success(String data) {
                    mPayTypes = PayType.arrayPayTypeFromData(data);


                    mQuhao = ShardeUtil.getString("quhao");
                    HttpUtil.getInstacne((Activity) context).post(Urls.countr, String.class, new HttpUtil.OnCallBack<String>() {

                        @Override
                        public void success(String data) {
                            mCountrBeen = CountrBean.arrayCountrBeanFromData(data);
                            for (int i = 0; i < mCountrBeen.size(); i++) {
                                if (mCountrBeen.get(i).getCountrycode().equals(mQuhao)) {
                                    mCountryname = mCountrBeen.get(i).getCountryname();
                                    GJCode = mCountrBeen.get(i).getCountrycode();
                                    mAdressBean.setUaCountries(GJCode);
                                    if (mTowViewHolder != null) {
                                        mTowViewHolder.mEtGj.setText(mCountryname);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(String msg, int code) {

                        }
                    });
                }

                @Override
                public void onError(String msg, int code) {
                }
            });
        }


        if (mAdressBean != null && !TextUtils.isEmpty(mAdressBean.getUpLogo())) {

            if (!mAdressBean.getUpLogo().equals("1")) {


                if (!TextUtils.isEmpty(mAdressBean.getUpLogo())) {
                    Log.v("地址的图片", mAdressBean.getUpLogo());


                    String url = mAdressBean.getUpLogo().replace(" ", "");
                    List<String> list = Arrays.asList(url.split(","));
                    for (int i = 0; i < list.size(); i++) {
                        this.photos.set(i, list.get(i));
                    }
                }
            }
        }

        initPickView(context, builder);
        initPickView(context, builder1);


    }


    private void initPickView(Context context, OptionsPickerView.Builder builder) {

        builder.setSubmitText(context.getResources().getString(R.string.ok));//确定按钮文字
        builder.setCancelText(context.getResources().getString(R.string.no));//取消按钮文字
        builder.setSubCalSize(16);//确定和取消文字大小
        builder.setTitleSize(18);//标题文字大小
        builder.setTitleColor(Color.BLACK);//标题文字颜色
        builder.setSubmitColor(context.getResources().getColor(R.color.black));//确定按钮文字颜色
        builder.setCancelColor(context.getResources().getColor(R.color.black));//取消按钮文字颜色
        builder.setTitleBgColor(context.getResources().getColor(R.color.white));//标题背景颜色 Night mode
        builder.setBgColor(context.getResources().getColor(R.color.white));//滚轮背景颜色 Night mode
        builder.setContentTextSize(18);//滚轮文字大小
        builder.setLinkage(false);//设置是否联动，默认true
        builder.setCyclic(false, false, false);//循环与否
        builder.setSelectOptions(0);  //设置默认选中项
        builder.setOutSideCancelable(false);//点击外部dismiss default true
    }


    class NormalOnOptionsSelectListener implements OptionsPickerView.OnOptionsSelectListener {

        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            //返回的分别是三个级别的选中位置
            String s = options1Items.get(options1);

            switch (type) {
                case gender:         //     选择性别
                    mHeadViewHolder.mEtSex.setText(s);
                    mAdressBean.setUaSex(options1);
                    break;
                case dianpu_type:         //     店铺类型

                    mThreeViewHolder.mTvDianpuleixing.setText(s);
                    mAdressBean.setUpShoptype(options1);
                    break;
                case yingyeshijian:         //     选择性别
                    mThreeViewHolder.mTvYingyeshijian.setText(s);
                    mAdressBean.setUaBusinesstime(s);
                    break;
                case songhuoshijian:         //     选择性别
                    mThreeViewHolder.mTvSonghuoshijian.setText(s);
                    mAdressBean.setUaDeliverytime(s);
                    break;
                case zhifu:         //     支付方式
                    mThreeViewHolder.mTvZhifu.setText(s);
                    int uPayId = mPayTypes.get(options1).getUPayId();
                    mAdressBean.setUaPaytype(uPayId);
                    mThreeViewHolder.mTvZhifu.setTextColor(context.getResources().getColor(R.color.black));
                    isZffs = true;
                    setBG();
                    break;
                case dianmianji:         //     选择性别
                    mThreeViewHolder.mTvDianmianji.setText(s);
                    mAdressBean.setUpShoparea(s);
                    break;
                case mubiaokehu:         //     选择性别
                    mThreeViewHolder.mTvMubiaokehu.setText(s);
                    mAdressBean.setUpTargetuser(options1);
                    break;
                case wangji:         //     旺季
                    mThreeViewHolder.mTvWangji.setText(s);
                    mAdressBean.setUpSwanseason(s);
                    break;
                case danji:         //     淡季
                    mThreeViewHolder.mTvDanji.setText(s);
                    mAdressBean.setUpLightseason(s);
                    break;
                case fapiao:         //     发票
                    mThreeViewHolder.mEtFp.setText(s);
                    mAdressBean.setUaUinvoice(1);
                    isFp = true;
                    mThreeViewHolder.mEtFp.setTextColor(context.getResources().getColor(R.color.black));
                    setBG();
                    break;
                case jylx:         //     经营类型
                    mThreeViewHolder.mTvYjlx.setText(s);
                    mAdressBean.setUaLocationId(options1);

                    break;
            }
        }
    }


    /**
     * 城市的选择器
     */
    class CityAndCountrListener implements OptionsPickerView.OnOptionsSelectListener {

        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            switch (type) {
                case countr:
                    mCountryname = mCountrBeen.get(options1).getCountryname();
                    mTowViewHolder.mEtGj.setText(mCountryname);
                    GJCode = mCountrBeen.get(options1).getCountrycode();
                    mAdressBean.setUaCountries(GJCode);
                    break;
                case city:
                    mProvincename = mCityBeen.get(options1).getProvincename();
                    mTowViewHolder.mEtZhou.setText(mProvincename);
                    CITYCode = mCityBeen.get(options1).getProvincecode();
                    mAdressBean.setUaProvincial(CITYCode);
                    break;
            }
        }
    }

    private String GJCode;
    private String CITYCode;


    /**
     * 隐藏键盘
     */
    public void hideInput() {
        try {
            ((InputMethodManager) ((Activity) context)
                    .getSystemService(((Activity) context).INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (RuntimeException re) {
            AppLog.Logi("dcg", "re==" + re);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            return new HeadViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.holder_edit_adress_head, parent, false));
        } else if (viewType == TYPE_TOW) {
            return new TowViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.holder_edit_adress_two, parent, false));
        } else if (viewType == TYPE_THREE) {
            return new ThreeViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.holder_edit_adress_three, parent, false));
        } else if (viewType == TYPE_FUOR) {
            return new FourViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.holder_edit_adress_fuor, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_HEAD) {

            mHeadViewHolder = (HeadViewHolder) holder;
            // 设置数据
            if (mAdressBean != null && mType == 1) {

                Log.v("地址：", mAdressBean.toString());


                mHeadViewHolder.mEtXing.setText(mAdressBean.getUaSurname());
                mHeadViewHolder.mEtMing.setText(mAdressBean.getUaName());
                if (mAdressBean.getUaSex() == 1) {

                    mHeadViewHolder.mEtSex.setText(context.getResources().getString(R.string.nan));
                } else if (mAdressBean.getUaSex() == 2) {
                    mHeadViewHolder.mEtSex.setText(context.getResources().getString(R.string.nv));

                } else {
                    mHeadViewHolder.mEtSex.setText(context.getResources().getString(R.string.user_sex));

                }
                mHeadViewHolder.mEtPhone.setText(mAdressBean.getUaContactphone() + "");
                mHeadViewHolder.mEtZj.setText(mAdressBean.getUaLandline() + "");
            }

            mHeadViewHolder.mEtXing.addTextChangedListener(new OnBtnBGListener(0));
            mHeadViewHolder.mEtMing.addTextChangedListener(new OnBtnBGListener(1));
            mHeadViewHolder.mEtPhone.addTextChangedListener(new OnBtnBGListener(2));

        } else if (getItemViewType(position) == TYPE_TOW) {
            mTowViewHolder = (TowViewHolder) holder;

            mTowViewHolder.mEtSpmc.addTextChangedListener(new OnBtnBGListener(3));
            mTowViewHolder.mEtGsmc.addTextChangedListener(new OnBtnBGListener(4));
            mTowViewHolder.mEtShdz.addTextChangedListener(new OnBtnBGListener(5));

            if (mAdressBean != null && mType == 1) {
                mTowViewHolder.mEtSpmc.setText(mAdressBean.getUaShopname());
                mTowViewHolder.mEtGsmc.setText(mAdressBean.getUaCompanyname());
                mTowViewHolder.mEtGj.setText(mAdressBean.getUaCountriesName() + "");
                mTowViewHolder.mEtZhou.setText(mAdressBean.getUaProvincialName() + "");
                mTowViewHolder.mEtCity.setText(mAdressBean.getUaCityName() + "");
                mTowViewHolder.mEtShdz.setText(mAdressBean.getUaDelivery() + "");
                mTowViewHolder.mEtFpdz.setText(mAdressBean.getUaInvoice() + "");
                mTowViewHolder.mEtYb.setText(mAdressBean.getUaZipcode() + "");
            } else {
                mTowViewHolder.mEtGj.setText(mCountryname + "");
            }

        } else if (getItemViewType(position) == TYPE_THREE) {
            mThreeViewHolder = (ThreeViewHolder) holder;
            if (mAdressBean != null && mType == 1) {
                mThreeViewHolder.mEtFp.setText(mAdressBean.getUaUinvoice() + "");
                mThreeViewHolder.mEtSh.setText(mAdressBean.getUaEin() + "");
                if (!TextUtils.isEmpty(mAdressBean.getUaBusinesstime())) {
                    mThreeViewHolder.mTvYingyeshijian.setText(mAdressBean.getUaBusinesstime() + "");
                }
                if (!TextUtils.isEmpty(mAdressBean.getUaDeliverytime())) {

                    mThreeViewHolder.mTvSonghuoshijian.setText(mAdressBean.getUaDeliverytime() + "");
                }


                try {
                    for (int i = 0; i < mPayTypes.size(); i++) {
                        if (mPayTypes.get(i).getUPayId() == mAdressBean.getUaPaytype()) {
                            mThreeViewHolder.mTvZhifu.setText(mPayTypes.get(i).getUPayName());
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (!TextUtils.isEmpty(mAdressBean.getUpShoparea())) {
                    mThreeViewHolder.mTvDianmianji.setText(mAdressBean.getUpShoparea() + "");
                }
                mThreeViewHolder.mTvMubiaokehu.setText(mAdressBean.getUpTargetuser() + "");
                if (!TextUtils.isEmpty(mAdressBean.getUpSwanseason())) {
                    mThreeViewHolder.mTvWangji.setText(mAdressBean.getUpSwanseason() + "");
                }
                if (!TextUtils.isEmpty(mAdressBean.getUpLightseason())) {
                    mThreeViewHolder.mTvDanji.setText(mAdressBean.getUpLightseason() + "");
                }
                mThreeViewHolder.mEtZysp.setText(mAdressBean.getUpMainproducts() + "");
                mThreeViewHolder.mEtBz.setText(mAdressBean.getUpDescr() + "");
                mThreeViewHolder.mTvDianpuleixing.setText(jylxs.get(mAdressBean.getUpShoptype()));

                mThreeViewHolder.mTvYjlx.setText(dplx.get(mAdressBean.getUaLocationId()));


                if (mAdressBean != null) {
                    String s = fps.get(0);
                    mThreeViewHolder.mEtFp.setText(s);
                }

                if (mAdressBean != null) {
                    String s = mbkh.get(mAdressBean.getUpTargetuser());
                    mThreeViewHolder.mTvMubiaokehu.setText(s);
                }
            }
            mThreeViewHolder.mEtSh.addTextChangedListener(new OnBtnBGListener(6));
        } else if (getItemViewType(position) == TYPE_FUOR) {
            mFourViewHolder = (FourViewHolder) holder;


            if (photos != null) {
                if (photos.size() > 0 && photos.size() == 0) {
                    Glide.with(context).load(photos.get(0)).error(R.drawable.updata).into(mFourViewHolder.ivUpdata);
                } else if (photos.size() > 0 && photos.size() == 2) {
                    Glide.with(context).load(photos.get(0)).error(R.drawable.updata).into(mFourViewHolder.ivUpdata);
                    Glide.with(context).load(photos.get(1)).error(R.drawable.updata).into(mFourViewHolder.ivUpdata1);
                } else if (photos.size() > 0 && photos.size() == 3) {
                    Glide.with(context).load(photos.get(0)).error(R.drawable.updata).into(mFourViewHolder.ivUpdata);
                    Glide.with(context).load(photos.get(1)).error(R.drawable.updata).into(mFourViewHolder.ivUpdata1);
                    Glide.with(context).load(photos.get(2)).error(R.drawable.updata).into(mFourViewHolder.ivUpdata2);
                }
            }

            if (mAdressBean != null && mType == 1) {
                if (mAdressBean.getUpDefault() == 0) {
                    mFourViewHolder.rb.setChecked(false);
                } else {
                    mFourViewHolder.rb.setChecked(true);
                }
            }
            setBG();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position == 1) {
            return TYPE_TOW;
        } else if (position == 2) {
            return TYPE_THREE;
        } else if (position == 3) {
            return TYPE_FUOR;
        }
        return super.getItemViewType(position);
    }


    class HeadViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_xingbie)
        RelativeLayout rlXingbie;


        @BindView(R.id.tv_xing1)
        TextView mTvXing1;
        @BindView(R.id.tv_des1)
        TextView mTvDes1;
        @BindView(R.id.rl)
        RelativeLayout mRl;
        @BindView(R.id.et_xing)
        EditText mEtXing;
        @BindView(R.id.tv_xing2)
        TextView mTvXing2;
        @BindView(R.id.tv_des2)
        TextView mTvDes2;
        @BindView(R.id.rl2)
        RelativeLayout mRl2;
        @BindView(R.id.et_ming)
        EditText mEtMing;
        @BindView(R.id.tv_des3)
        TextView mTvDes3;
        @BindView(R.id.rl3)
        RelativeLayout mRl3;
        @BindView(R.id.et_sex)
        TextView mEtSex;
        @BindView(R.id.tv_des4)
        TextView mTvDes4;
        @BindView(R.id.rl4)
        RelativeLayout mRl4;
        @BindView(R.id.et_phone)
        EditText mEtPhone;
        @BindView(R.id.tv_des5)
        TextView mTvDes5;
        @BindView(R.id.rl5)
        RelativeLayout mRl5;
        @BindView(R.id.et_zj)
        EditText mEtZj;


        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rlXingbie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    options1Items.clear();
                    hideInput();
                    type = gender;
                    builder.setTitleText(context.getResources().getString(R.string.sex));//标题

                    options1Items.add(context.getResources().getString(R.string.user_sex));
                    options1Items.add(context.getResources().getString(R.string.nan));
                    options1Items.add(context.getResources().getString(R.string.nv));
                    createPicker(options1Items, 0);

                }
            });
        }
    }

    class TowViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_xing1)
        TextView mTvXing1;
        @BindView(R.id.tv_des1)
        TextView mTvDes1;
        @BindView(R.id.rl)
        RelativeLayout mRl;
        @BindView(R.id.et_spmc)
        EditText mEtSpmc;
        @BindView(R.id.tv_xing2)
        TextView mTvXing2;
        @BindView(R.id.tv_des2)
        TextView mTvDes2;
        @BindView(R.id.r2)
        RelativeLayout mR2;
        @BindView(R.id.et_gsmc)
        EditText mEtGsmc;
        @BindView(R.id.tv_des3)
        TextView mTvDes3;
        @BindView(R.id.rl3)
        RelativeLayout mRl3;
        @BindView(R.id.tv_xing4)
        TextView mTvXing4;
        @BindView(R.id.tv_des4)
        TextView mTvDes4;
        @BindView(R.id.rl4)
        RelativeLayout mRl4;
        @BindView(R.id.tv_des5)
        TextView mTvDes5;
        @BindView(R.id.rl5)
        RelativeLayout mRl5;
        @BindView(R.id.et_city)
        EditText mEtCity;
        @BindView(R.id.tv_xing8)
        TextView mTvXing8;
        @BindView(R.id.tv_des6)
        TextView mTvDes6;
        @BindView(R.id.rl6)
        RelativeLayout mRl6;
        @BindView(R.id.et_shdz)
        EditText mEtShdz;
        @BindView(R.id.tv_xing7)
        TextView mTvXing7;
        @BindView(R.id.tv_des7)
        TextView mTvDes7;
        @BindView(R.id.rl7)
        RelativeLayout mRl7;
        @BindView(R.id.et_fpdz)
        EditText mEtFpdz;
        @BindView(R.id.tv_des9)
        TextView mTvDes9;
        @BindView(R.id.rl9)
        RelativeLayout mRl9;
        @BindView(R.id.et_yb)
        EditText mEtYb;

        @BindView(R.id.et_gj)
        TextView mEtGj;
        @BindView(R.id.et_zhou)
        TextView mEtZhou;

        public TowViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mEtYb.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (s.length() > 0) {
                        isPostak = true;
                    } else {
                        isPostak = false;
                    }

                    setBG();
                }
            });

            mEtFpdz.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        isFpdz = true;
                    } else {
                        isFpdz = false;
                    }

                    setBG();
                }
            });

        }

        @OnClick({R.id.rl_guojia, R.id.rl_zhou})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.rl_guojia:
                    type = countr;
                    mCityAndCountr.clear();
                    for (CountrBean countrBean : mCountrBeen
                            ) {
                        mCityAndCountr.add(countrBean.getCountryname());
                    }
                    pvOptions1 = builder1.build();
                    pvOptions1.setPicker(mCityAndCountr);//添加数据源
                    pvOptions1.show();
                    hideInput();
                    break;
                case R.id.rl_zhou:
                    type = city;
                    Map<String, String> map = new HashMap<>();
                    map.put("countryCode", GJCode);
                    HttpUtil.getInstacne((Activity) context).get(Urls.province, String.class, map, new HttpUtil.OnCallBack<String>() {

                        @Override
                        public void success(String data) {
                            mCityBeen = CityBean.arrayCityBeanFromData(data);
                            mCityAndCountr.clear();
                            for (CityBean countrBean : mCityBeen
                                    ) {
                                mCityAndCountr.add(countrBean.getProvincename());
                            }
                            pvOptions1 = builder1.build();
                            pvOptions1.setPicker(mCityAndCountr);//添加数据源
                            pvOptions1.show();
                            hideInput();
                        }

                        @Override
                        public void onError(String msg, int code) {

                        }
                    });
                    break;
            }
        }
    }

    class ThreeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_xing4)
        TextView mTvXing4;
        @BindView(R.id.tv_des4)
        TextView mTvDes4;
        @BindView(R.id.rl4)
        RelativeLayout mRl4;
        @BindView(R.id.tv_dianpuleixing)
        TextView mTvDianpuleixing;
        @BindView(R.id.pv_1)
        RelativeLayout mPv1;
        @BindView(R.id.tv_xing2)
        TextView mTvXing2;
        @BindView(R.id.tv_des2)
        TextView mTvDes2;
        @BindView(R.id.rl2)
        RelativeLayout mRl2;
        @BindView(R.id.pv_2)
        RelativeLayout mPv2;
        @BindView(R.id.tv_xing11)
        TextView mTvXing11;
        @BindView(R.id.tv_des11)
        TextView mTvDes11;
        @BindView(R.id.rl11)
        RelativeLayout mRl11;
        @BindView(R.id.tv_des3)
        TextView mTvDes3;
        @BindView(R.id.rl3)
        RelativeLayout mRl3;
        @BindView(R.id.tv_yingyeshijian)
        TextView mTvYingyeshijian;
        @BindView(R.id.pv_3)
        RelativeLayout mPv3;
        @BindView(R.id.tv_des5)
        TextView mTvDes5;
        @BindView(R.id.rl5)
        RelativeLayout mRl5;
        @BindView(R.id.tv_songhuoshijian)
        TextView mTvSonghuoshijian;
        @BindView(R.id.pv_4)
        RelativeLayout mPv4;
        @BindView(R.id.tv_xing1)
        TextView mTvXing1;
        @BindView(R.id.tv_des1)
        TextView mTvDes1;
        @BindView(R.id.rl1)
        RelativeLayout mRl1;
        @BindView(R.id.tv_zhifu)
        TextView mTvZhifu;
        @BindView(R.id.pv_5)
        RelativeLayout mPv5;
        @BindView(R.id.tv_des6)
        TextView mTvDes6;
        @BindView(R.id.rl6)
        RelativeLayout mRl6;
        @BindView(R.id.tv_dianmianji)
        TextView mTvDianmianji;
        @BindView(R.id.pv_6)
        RelativeLayout mPv6;
        @BindView(R.id.tv_des7)
        TextView mTvDes7;
        @BindView(R.id.rl7)
        RelativeLayout mRl7;
        @BindView(R.id.tv_mubiaokehu)
        TextView mTvMubiaokehu;
        @BindView(R.id.pv_7)
        RelativeLayout mPv7;
        @BindView(R.id.tv_des8)
        TextView mTvDes8;
        @BindView(R.id.rl8)
        RelativeLayout mRl8;
        @BindView(R.id.tv_wangji)
        TextView mTvWangji;
        @BindView(R.id.pv_8)
        RelativeLayout mPv8;
        @BindView(R.id.tv_des9)
        TextView mTvDes9;
        @BindView(R.id.rl9)
        RelativeLayout mRl9;
        @BindView(R.id.tv_danji)
        TextView mTvDanji;
        @BindView(R.id.pv_9)
        RelativeLayout mPv9;
        @BindView(R.id.tv_des10)
        TextView mTvDes10;
        @BindView(R.id.rl10)
        RelativeLayout mRl10;
        @BindView(R.id.tv_des12)
        TextView mTvDes12;
        @BindView(R.id.rl12)
        RelativeLayout mRl12;

        @BindView(R.id.et_sh)
        EditText mEtSh;
        @BindView(R.id.et_zysp)
        EditText mEtZysp;
        @BindView(R.id.et_bz)
        EditText mEtBz;

        @BindView(R.id.et_fp)
        TextView mEtFp;
        @BindView(R.id.tv_dianpuleixing50)
        TextView mTvYjlx;


        public ThreeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick({R.id.pv_1, R.id.pv_3, R.id.pv_4, R.id.pv_5, R.id.pv_6, R.id.pv_7, R.id.pv_8, R.id.pv_9, R.id.pv_2, R.id.pv_50})
        public void onViewClicked(View view) {

            int tag = 0;
            options1Items.clear();
            switch (view.getId()) {
                case R.id.pv_1:                   //店铺类型

                    type = dianpu_type;
                    builder.setTitleText("经营类型");

                    options1Items.addAll(jylxs);


                    break;
                case R.id.pv_3:                    //营业时间
                    type = yingyeshijian;
                    builder.setTitleText(context.getResources().getString(R.string.jysj));

                    options1Items.add("09:00~14:00");
                    options1Items.add("14:00~17:00");
                    options1Items.add("17:00~21:00");
                    options1Items.add(context.getResources().getString(R.string.qtyj));
                    options1Items.add(context.getResources().getString(R.string.qtwj));
                    break;
                case R.id.pv_4:                    //送货时间
                    type = songhuoshijian;
                    builder.setTitleText(context.getResources().getString(R.string.shsj));

                    options1Items.add("09:00~14:00");
                    options1Items.add("09:00~14:00");
                    options1Items.add("09:00~14:00");
                    options1Items.add(context.getResources().getString(R.string.dhyy));
                    break;
                case R.id.pv_5:
                    type = zhifu;
                    builder.setTitleText(context.getResources().getString(R.string.zffs));
                    tag = 5;
                    mPays.clear();
                    for (int i = 0; i < mPayTypes.size(); i++) {
                        setPay(i);
                    }
                    options1Items.addAll(mPays);

                    pvOptions = builder.build();
                    pvOptions.setPicker(options1Items);//添加数据源
                    pvOptions.show();


//                    builder.setTitleText("支付方式");

//                    options1Items.add("现金支票");
//                    options1Items.add("预付款");
//                    options1Items.add("现金");
//                    options1Items.add("期票");
                    break;
                case R.id.pv_6:

                    builder.setTitleText(context.getResources().getString(R.string.dmj));

                    type = dianmianji;
                    options1Items.add("100平以下");
                    options1Items.add("100~300");
                    options1Items.add("300~700");
                    options1Items.add("700~1000");
                    options1Items.add("1000~3000");
                    options1Items.add("3000以上");
                    break;
                case R.id.pv_7:
                    type = mubiaokehu;
                    builder.setTitleText(context.getResources().getString(R.string.mbkh));
                    options1Items.addAll(mbkh);


                    break;
                case R.id.pv_8:
                    type = wangji;
                    builder.setTitleText(context.getResources().getString(R.string.wj));

                    options1Items.add("1~2" + context.getResources().getString(R.string.yuefen));
                    options1Items.add("3~4" + context.getResources().getString(R.string.yuefen));
                    options1Items.add("5~6" + context.getResources().getString(R.string.yuefen));
                    options1Items.add("7~8" + context.getResources().getString(R.string.yuefen));
                    options1Items.add("9~10" + context.getResources().getString(R.string.yuefen));
                    options1Items.add("11~12" + context.getResources().getString(R.string.yuefen));
                    break;
                case R.id.pv_9:
                    type = danji;
                    builder.setTitleText(context.getResources().getString(R.string.danji));

                    options1Items.add("1~2" + context.getResources().getString(R.string.yuefen));
                    options1Items.add("3~4" + context.getResources().getString(R.string.yuefen));
                    options1Items.add("5~6" + context.getResources().getString(R.string.yuefen));
                    options1Items.add("7~8" + context.getResources().getString(R.string.yuefen));
                    options1Items.add("9~10" + context.getResources().getString(R.string.yuefen));
                    options1Items.add("11~12" + context.getResources().getString(R.string.yuefen));
                    break;
                case R.id.pv_2: // 发票
                    type = fapiao;
                    builder.setTitleText(context.getResources().getString(R.string.fp));
                    options1Items.addAll(fps);

                    break;
                case R.id.pv_50:    //经营类型
                    type = jylx;
                    builder.setTitleText("店铺类型选择");
                    options1Items.addAll(dplx);
                    break;


            }
            if (tag != 5) {

                createPicker(options1Items, tag);
            }
            hideInput();
        }
    }

    private void setPay(int i) {
        String language = ShardeUtil.getString("language");

        if (!TextUtils.isEmpty(language)) {

            switch (language) {
                case LanguageUtils.CHINAESE:
                    mPays.add(mPayTypes.get(i).getUPayName());
                    break;
                case LanguageUtils.ENGLISH:
                    mPays.add(mPayTypes.get(i).getuPayName4());
                    break;
                case LanguageUtils.ESPAÑOL:
                    mPays.add(mPayTypes.get(i).getuPayName1());
                    break;
                case LanguageUtils.ITALIANO:
                    mPays.add(mPayTypes.get(i).getuPayName3());
                    break;
                case LanguageUtils.FRANÇAIS:
                    mPays.add(mPayTypes.get(i).getuPayName2());
                    break;
            }
        }

    }

    private void createPicker(List<String> options1Items, int tag) {
        pvOptions = builder.build();
        pvOptions.setSelectOptions(tag);
        pvOptions.setPicker(options1Items);//添加数据源
        pvOptions.show();
    }

    // 设置背景
    public void setBG() {
        if (isXing && isMing && isPhone && isSdmc && isGsmc && isShdz && isSh && isPostak && isFp && isFpdz && isZffs) {
            if (mFourViewHolder != null) {

                mFourViewHolder.btnSaveAdress.setBackgroundResource(R.drawable.adress_btn_selector);
                mFourViewHolder.btnSaveAdress.setEnabled(true);
            }
        } else {
            if (mFourViewHolder != null) {

                mFourViewHolder.btnSaveAdress.setBackgroundResource(R.drawable.btn_bg);
                mFourViewHolder.btnSaveAdress.setEnabled(false);
            }
        }

    }

    private boolean isXing = false, isMing = false,
            isPhone = false, isSdmc = false,
            isGsmc = false, isShdz = false, isSh = false, isPostak = false, isFpdz = false, isFp = false, isZffs = false;

    class OnBtnBGListener implements TextWatcher {
        public OnBtnBGListener(int tag) {
            this.tag = tag;
        }

        private int tag;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            switch (tag) {
                case 0:
                    if (s.length() > 0) {
                        isXing = true;
                    } else {
                        isXing = false;
                    }
                    break;
                case 1:
                    if (s.length() > 0) {
                        isMing = true;
                    } else {
                        isMing = false;
                    }
                    break;
                case 2:
                    if (s.length() > 0) {
                        isPhone = true;
                    } else {
                        isPhone = false;
                    }
                    break;
                case 3:
                    if (s.length() > 0) {
                        isSdmc = true;
                    } else {
                        isSdmc = false;
                    }
                    break;
                case 4:
                    if (s.length() > 0) {
                        isGsmc = true;
                    } else {
                        isGsmc = false;
                    }
                    break;
                case 5:
                    if (s.length() > 0) {
                        isShdz = true;
                    } else {
                        isShdz = false;
                    }
                    break;
                case 6:
                    if (s.length() > 0) {
                        isSh = true;
                    } else {
                        isSh = false;
                    }
                    break;
            }
            setBG();

        }

    }

    class FourViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.iv_updata)
        ImageView ivUpdata;
        @BindView(R.id.iv_updata1)
        ImageView ivUpdata1;
        @BindView(R.id.iv_updata2)
        ImageView ivUpdata2;
        @BindView(R.id.rb)
        CheckBox rb;
        @BindView(R.id.btn_save_adress)
        Button btnSaveAdress;

        @BindView(R.id.btn1)
        Button btn1;
        @BindView(R.id.btn2)
        Button btn2;
        @BindView(R.id.btn3)
        Button btn3;

        public FourViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            btnSaveAdress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String xing = mHeadViewHolder.mEtXing.getText().toString();
                    mAdressBean.setUaSurname(xing);

                    String ming = mHeadViewHolder.mEtMing.getText().toString();
                    mAdressBean.setUaName(ming);

                    String phone = mHeadViewHolder.mEtPhone.getText().toString();
                    mAdressBean.setUaContactphone(phone);

                    String zj = mHeadViewHolder.mEtZj.getText().toString();
                    mAdressBean.setUaLandline(zj);

                    String sdmc = mTowViewHolder.mEtSpmc.getText().toString();
                    mAdressBean.setUaShopname(sdmc);

                    String gsmc = mTowViewHolder.mEtGsmc.getText().toString();
                    mAdressBean.setUaCompanyname(gsmc);

                    String gj = mTowViewHolder.mEtGj.getText().toString();
                    // 设置国家 和 设置国家id
                    mAdressBean.setUaCountriesName(gj);


                    String zhou = mTowViewHolder.mEtZhou.getText().toString();
                    // 设置周省 和 设置周省 id
                    mAdressBean.setUaProvincialName(zhou);


                    String city = mTowViewHolder.mEtCity.getText().toString();
                    mAdressBean.setUaCityName(city);
                    mAdressBean.setUaCity("0");

                    String shdz = mTowViewHolder.mEtShdz.getText().toString();
                    mAdressBean.setUaDelivery(shdz);

                    String fpdz = mTowViewHolder.mEtFpdz.getText().toString();
                    mAdressBean.setUaInvoice(fpdz);

                    String yb = mTowViewHolder.mEtYb.getText().toString();
                    mAdressBean.setUaZipcode(yb);

                    String sh = mThreeViewHolder.mEtSh.getText().toString();
                    /**
                     * 测试税号
                     */
                    mAdressBean.setUaEin(sh);

//                    String fp = mThreeViewHolder.mEtFp.getText().toString();
//                    mAdressBean.setUaUinvoice(fp);

                    String zysp = mThreeViewHolder.mEtZysp.getText().toString();
                    mAdressBean.setUpMainproducts(zysp);

                    String bz = mThreeViewHolder.mEtBz.getText().toString();
                    mAdressBean.setUpDescr(bz);

                    if (mFourViewHolder.rb.isChecked()) {
                        mAdressBean.setUpDefault(1);
                    } else {
                        mAdressBean.setUpDefault(0);
                    }

                    mAdressBean.setuId(MyApplication.getmUserBean().getUId() + "");


                    save();


                }


            });

            ivUpdata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadImage(0);
                }
            });
            ivUpdata1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadImage(1);
                }
            });
            ivUpdata2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadImage(2);
                }
            });

            if (mType == 1) {
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.VISIBLE);
                btn3.setVisibility(View.VISIBLE);

                btn1.setOnClickListener(new OnDeletImageClick(0, ivUpdata));
                btn2.setOnClickListener(new OnDeletImageClick(1, ivUpdata1));
                btn3.setOnClickListener(new OnDeletImageClick(2, ivUpdata2));
            } else {
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.GONE);
            }

        }
    }

    public class OnDeletImageClick implements View.OnClickListener {
        public OnDeletImageClick(int tag, ImageView ivUpdata) {
            this.tag = tag;
            this.ivUpdata = ivUpdata;
        }

        private int tag;
        private ImageView ivUpdata;

        @Override
        public void onClick(View v) {
            if (photos != null) {
                Glide.with(context).load(R.drawable.updata).into(ivUpdata);
                photos.set(tag, "1");

                setBG();
            }
        }
    }

    private void uploadImage(int tag) {
        // 上传图片
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start((Activity) context, tag);
    }


    private void save() {

        JSONObject object = new JSONObject();
        try {
            object.put("uaSurname", mAdressBean.getUaSurname());                     //            "uaSurname": "钟",
            object.put("uaName", mAdressBean.getUaName());                           //            "uaName": "志豪",
            object.put("uaSex", mAdressBean.getUaSex());                             //            "uaSex": "1",
            object.put("uaContactphone", mAdressBean.getUaContactphone());           //            "uaContactphone": "13265235733",
            object.put("uaLandline", mAdressBean.getUaLandline());                   //            "uaLandline": "1",
            object.put("uaCompanyname", mAdressBean.getUaCompanyname());             //            "uaCompanyname": "1",
            object.put("uaShopname", mAdressBean.getUaShopname());                   //            "uaShopname": "1",
            object.put("uaCountriesName", mAdressBean.getUaCountriesName());         //            "uaCountriesName": 1,
            object.put("uaCountries", mAdressBean.getUaCountries());                 //            "uaCountries": 1,
            object.put("uaProvincialName", mAdressBean.getUaProvincialName());       //            "uaProvincialName": 1,
            object.put("uaProvincial", mAdressBean.getUaProvincial());               //            "uaProvincial": 1,
            object.put("uaCityName", mAdressBean.getUaCityName());                   //            "uaCityName": 1,
            object.put("uaCity", mAdressBean.getUaCity());                           //            "uaCity": 1,
            object.put("uaDelivery", mAdressBean.getUaDelivery());                   //            "uaDelivery": "11",
            object.put("uaUinvoice", mAdressBean.getUaUinvoice());                   //            "uaUinvoice": "1",
            object.put("uaInvoice", mAdressBean.getUaInvoice());                     //            "uaInvoice": "1",
            object.put("uaZipcode", mAdressBean.getUaZipcode());                     //            "uaZipcode": "11",
            object.put("uaEin", mAdressBean.getUaEin());                             //            "uaEin": "1",
            object.put("uaBusinesstime", mAdressBean.getUaBusinesstime());           //            "uaBusinesstime": "1",
            object.put("uaDeliverytime", mAdressBean.getUaDeliverytime());           //            "uaDeliverytime": "1",
            object.put("uaPaytype", mAdressBean.getUaPaytype());                     //            "uaPaytype": 1,
            object.put("upShoptype", mAdressBean.getUpShoptype());                   //            "upShoptype": 1,
            object.put("upShoparea", mAdressBean.getUpShoparea());                   //            "upShoparea": "11",
            object.put("upTargetuser", mAdressBean.getUpTargetuser());               //            "upTargetuser": 1,
            object.put("upSwanseason", mAdressBean.getUpSwanseason());               //            "upSwanseason": "1",
            object.put("upLightseason", mAdressBean.getUpLightseason());             //            "upLightseason": "1",
            object.put("upMainproducts", mAdressBean.getUpMainproducts());           //            "upMainproducts": "1",
            object.put("upDescr", mAdressBean.getUpDescr());                         //            "upDescr": "1",
            object.put("upDefault", mAdressBean.getUpDefault());                     //            "upDefault": "1",
            String s = "";
            if (photos != null && photos.size() == 3) {
                for (int i = 0; i < photos.size(); i++) {
                    if (i == 1) {

                        s = s + "," + photos.get(i) + ",";
                    } else {

                        s = s + photos.get(i);
                    }
                }
                object.put("upLogo", s);                                               //             "upLogo": 1
            } else {
                object.put("upLogo", 1);                                               //             "upLogo": 1
            }
            object.put("uaLocationId", mAdressBean.getUaLocationId());                                               //             "upLogo": 1
            object.put("uId", mAdressBean.getuId());                                 //             "uId": 1
            if (mType == 1) {
                object.put("uaId", mAdressBean.getUaId());                                 //             "uId": 1

            }
            //            "upDefault": 1,

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mType == 2 || mType == 0) {

            HttpUtil.getInstacne((Activity) context).post(Urls.insert, String.class, object, new AdressListener());
        } else {
            HttpUtil.getInstacne((Activity) context).post(Urls.adressUpdate, String.class, object, new AdressListener());

        }

    }


    class AdressListener implements HttpUtil.OnCallBack<String> {

        @Override
        public void success(String data) {
            if (mType == 0) {
                ((Activity) context).setResult(((Activity) context).RESULT_OK);
                ((Activity) context).finish();
            } else if (mType == 2) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                ((Activity) context).finish();
            } else {
                ((Activity) context).setResult(((Activity) context).RESULT_OK);
                ToastUti.show("修改成功");
                ((Activity) context).finish();

            }
        }

        @Override
        public void onError(String msg, int code) {
            ToastUti.show(msg);

        }
    }
}
