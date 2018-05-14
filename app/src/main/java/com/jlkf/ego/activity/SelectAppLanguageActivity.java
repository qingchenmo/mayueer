package com.jlkf.ego.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.adapter.LanguageTypeAdapter;
import com.jlkf.ego.umanalytics.UMUtils;
import com.jlkf.ego.utils.LanguageUtils;
import com.jlkf.ego.widget.ExListView;
import com.jlkf.ego.widget.spiner.ItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 切换语言
 *
 * @author 邓超桂
 * @date 创建时间：2017年7月11日 下午3:40:13
 */
public class SelectAppLanguageActivity extends BaseActivity implements View.OnClickListener {

    //title
    private TextView tv_title;
    private ImageView iv_back;
    private TextView tv_sure;

    private ExListView lv_select_language;
    private List<ItemInfo> countryList = new ArrayList<ItemInfo>();
    private ItemInfo selectItemInfo = null;
    private LanguageTypeAdapter mLanguageTypeAdapter = null;

    @Override
    public void iniActivityAnimation() {
        //滑动关闭activity
        addSwipeFinishLayout();
        //设置滑动开启/关闭
        setEnableGesture(true);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_select_language);

        tv_title = getView(R.id.tv_title);
        iv_back = getView(R.id.iv_back);
        tv_sure = getView(R.id.tv_sure);

        lv_select_language = getView(R.id.lv_select_language);
        mLanguageTypeAdapter = new LanguageTypeAdapter(mContext);

        mLanguageTypeAdapter.setItemClick(itemClick);
        lv_select_language.setAdapter(mLanguageTypeAdapter);

        tv_title.setText(getResources().getString(R.string.select_yuyen_title));

        iv_back.setOnClickListener(this);
        tv_sure.setOnClickListener(this);

        setupViews();//设置语言下拉
    }

    View.OnClickListener itemClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ItemInfo info = (ItemInfo) view.getTag();
            if (info != null) {
                selectItemInfo = info;
                mLanguageTypeAdapter.setLangSelectId(info.getObjId());
                mLanguageTypeAdapter.notifyDataSetChanged();
            }
        }
    };

    //设置语言下拉内容
    private void setupViews() {
        ItemInfo mItemInfo_china = new ItemInfo();
        mItemInfo_china.setName("中文");
        mItemInfo_china.setObjId("1");
        mItemInfo_china.setDrawable(R.drawable.china);
        countryList.add(mItemInfo_china);

        ItemInfo mItemInfo_eng = new ItemInfo();//英语
        mItemInfo_eng.setName("English");
        mItemInfo_eng.setObjId("2");
        mItemInfo_eng.setDrawable(R.drawable.eng);
        countryList.add(mItemInfo_eng);

        ItemInfo mItemInfo_esp = new ItemInfo();//西班牙
        mItemInfo_esp.setName("Español");
        mItemInfo_esp.setObjId("3");
        mItemInfo_esp.setDrawable(R.drawable.esp);
        countryList.add(mItemInfo_esp);

        ItemInfo mItemInfo_ita = new ItemInfo();//意大利
        mItemInfo_ita.setName("Italiano");
        mItemInfo_ita.setObjId("4");
        mItemInfo_ita.setDrawable(R.drawable.ita);
        countryList.add(mItemInfo_ita);

        ItemInfo mItemInfo_fra = new ItemInfo();//法国
        mItemInfo_fra.setName("Français");
        mItemInfo_fra.setObjId("5");
        mItemInfo_fra.setDrawable(R.drawable.fra);
        countryList.add(mItemInfo_fra);

        //取第一个默认作为选中
        selectItemInfo = countryList.get(0);
        String langage = LanguageUtils.getLanguage(this);
        switch (langage) {
            case "zh":
                selectItemInfo = countryList.get(0);
                break;
            case "en":
                selectItemInfo = countryList.get(1);
                break;
            case "es":
                selectItemInfo = countryList.get(2);
                break;
            case "it":
                selectItemInfo = countryList.get(3);
                break;
            case "fr":
                selectItemInfo = countryList.get(4);
                break;
        }
        mLanguageTypeAdapter.setLangSelectId(selectItemInfo.getObjId());
        mLanguageTypeAdapter.setData(countryList);
        mLanguageTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        BaseActivity.finish(SelectAppLanguageActivity.this);
    }

    /**
     * 确认选择语言
     */
    private void selectLanguage() {
        switch (selectItemInfo.getName()) {
            case "中文":
                LanguageUtils.switchLanguage(this, LanguageUtils.CHINAESE);
                break;
            case "English":
                LanguageUtils.switchLanguage(this, LanguageUtils.ENGLISH);
                break;
            case "Español":
                LanguageUtils.switchLanguage(this, LanguageUtils.ESPAÑOL);
                break;
            case "Italiano":
                LanguageUtils.switchLanguage(this, LanguageUtils.ITALIANO);
                break;
            case "Français":
                LanguageUtils.switchLanguage(this, LanguageUtils.FRANÇAIS);
                break;
        }
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.putExtra("tag", "langage");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                onBackPressed();
                break;
            case R.id.tv_sure://确定
                selectLanguage();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        UMUtils.onResumeUMActivity(mContext, "SelectAppLanguage_page");
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.onPauseUMActivity(mContext, "SelectAppLanguage_page");
    }
}
