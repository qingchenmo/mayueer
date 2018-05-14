package com.jlkf.ego.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.activity.ProductListActivity;
import com.jlkf.ego.adapter.BrandAdapter;
import com.jlkf.ego.adapter.GoodAdapter;
import com.jlkf.ego.adapter.GoodTypeAdapter;
import com.jlkf.ego.adapter.SmallBrandAdapter;
import com.jlkf.ego.bean.AllBrandBean;
import com.jlkf.ego.bean.BrandBean;
import com.jlkf.ego.bean.GoodBean;
import com.jlkf.ego.bean.GoodTypeBean;
import com.jlkf.ego.common.PingYinUtil;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.widget.ExListView;
import com.jlkf.ego.widget.MyGridView;
import com.jlkf.ego.widget.SideBar;
import com.jlkf.ego.widget.pinnedheaderlistview.PinnedHeaderListView;
import com.jlkf.ego.widget.pinnedheaderlistview.SectionedBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页Fragment
 */
public class IndexFragment extends BaseFragment implements View.OnClickListener{

	private RelativeLayout rl_one;
	private TextView tv_one;
	private View line_one;
	private RelativeLayout rl_two;
	private TextView tv_two;
	private View line_two;

	private List<View> mList = new ArrayList<View>();
	private ViewPager viewPagerOrder;

	//view1
	private WindowManager mWindowManager;
	private PinnedHeaderListView lv_brand_big;
	private TextView mDialogText;

	private View layout_head;
	private BrandAdapter mBigBrandAdapter;
	private List<BrandBean> mBigBrandList=new ArrayList<BrandBean>();
	private SmallBrandSelectAdapter mSmallBrand_SelectAdapter=null;

	private List<AllBrandBean> mList_AllBrandBean=new ArrayList<AllBrandBean>();

	//view2
	//----左边分类----//
	private ListView lv_good_type;
	private List<GoodTypeBean> mGoodTypeList = new ArrayList<GoodTypeBean>();
	private GoodTypeAdapter mGoodTypeAdapter=null;
	private GoodTypeBean selectGoodType=null;
	//----右边分类----//
	private GridView gv_good;
	private List<GoodBean> mGoodList = new ArrayList<GoodBean>();
	private GoodAdapter mGoodAdapter=null;


	private int selectBrand=0;// 1-品牌列表  2-商品分类

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1://品牌列表数据返回

					break;
				case 2://Toast处理
					showToast(String.valueOf(msg.obj), 0);
					break;
				case 3://商品分类数据返回

					break;

				default:
					break;
			}
		}
	};

	@Override
	public View getContentView(LayoutInflater inflater) {
		return inflater.inflate(R.layout.fragment_index, null);
	}

	@Override
	public void initView() {
		rl_one= (RelativeLayout) rootView.findViewById(R.id.rl_one);
		tv_one= (TextView) rootView.findViewById(R.id.tv_one);
		line_one= (View) rootView.findViewById(R.id.line_one);
		rl_two= (RelativeLayout) rootView.findViewById(R.id.rl_two);
		tv_two= (TextView) rootView.findViewById(R.id.tv_two);
		line_two= (View) rootView.findViewById(R.id.line_two);

		rl_one.setOnClickListener(this);
		rl_two.setOnClickListener(this);

		viewPagerOrder =(ViewPager) rootView.findViewById(R.id.viewPager_index);
		viewPagerOrder.setOnPageChangeListener(viewPageListener);

		View view1 =getActivity().getLayoutInflater().inflate(R.layout.activity_brand_list, null);
		View view2 =getActivity().getLayoutInflater().inflate(R.layout.activity_goods_list, null);
		mList.add(view1);
		mList.add(view2);
		viewPagerOrder.setAdapter(new MyAdapter());
		//view1
		lv_brand_big= (PinnedHeaderListView) view1.findViewById(R.id.lv_brand_list);

		getBigBrandData();


		//view2
		lv_good_type= (ListView) view2.findViewById(R.id.lv_good_type);
		mGoodTypeAdapter=new GoodTypeAdapter(mContext);
		mGoodTypeAdapter.setItemClick(goodTypeClick);
		lv_good_type.setAdapter(mGoodTypeAdapter);
       //getGoodTypes();//获取商品分类

		gv_good= (GridView) view2.findViewById(R.id.gv_good);
		mGoodAdapter=new GoodAdapter(mContext);
		gv_good.setAdapter(mGoodAdapter);

		//默认选第一个
		mGoodTypeAdapter.setSelectPosition(0);
		selectGoodType=mGoodTypeList.get(0);
		getGoods();

		mGoodTypeAdapter.setData(mGoodTypeList);
		mGoodTypeAdapter.notifyDataSetChanged();
	}

	/**
	 * 选择商品分类
	 */
	View.OnClickListener goodTypeClick=new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int position=(int)view.getTag();
			GoodTypeBean info=mGoodTypeList.get(position);
			if(info!=null){
				selectGoodType=info;
				mGoodTypeAdapter.setSelectPosition(position);
				mGoodTypeAdapter.notifyDataSetChanged();

				//刷新右边商品分支
				getGoods();
			}
		}
	};

	/**
	 * 获取右边货品
	 */
	private void getGoods() {
		if(selectGoodType!=null){
			if(mGoodList!=null && mGoodList.size()>0){
				mGoodList.clear();
			}
			for (int i=0;i<10;i++){
				GoodBean info=new GoodBean();
				mGoodList.add(info);
			}
			mGoodAdapter.setData(mGoodList);
			mGoodAdapter.notifyDataSetChanged();
		}
	}


	/**
	 * 选择买域名
	 * @param selectBrandWay
	 */
	private void onselectBrandWay(int selectBrandWay) {
		switch (selectBrandWay) {
			case 1://品牌列表
				tv_one.setTextColor(getResources().getColor(R.color.app_yellow));
				line_one.setVisibility(View.VISIBLE);
				tv_two.setTextColor(getResources().getColor(R.color.black));
				line_two.setVisibility(View.INVISIBLE);

				AppLog.Loge("正在加载品牌列表数据~");
//				if(isNetworkConnected(mContext)){
//					String uid= AppUtil.getUid(mContext);
//					String token=AppUtil.getUserToken(mContext);
//					NetworkUtil.getBuyNowPayListAPI(uid, token, page_nowPay, pageSize, mHandler, 1, mContext);
//				}else{
//					AppUtil.sendMessage(2, AppUtil.networkError(mContext), mHandler);
//					lv_buy_nowPay.stopRefresh();
//				}
				break;
			case 2://商品分类
				tv_one.setTextColor(getResources().getColor(R.color.black));
				line_one.setVisibility(View.INVISIBLE);
				tv_two.setTextColor(getResources().getColor(R.color.app_yellow));
				line_two.setVisibility(View.VISIBLE);
				AppLog.Loge("正在加载商品分类数据~");
//				if(isNetworkConnected(mContext)){
//					String uid=AppUtil.getUid(mContext);
//					String token=AppUtil.getUserToken(mContext);
//					NetworkUtil.getBuyHistoryListAPI(uid, token, page_history,pageSize, mHandler, 4, mContext);
//				}else{
//					AppUtil.sendMessage(2, AppUtil.networkError(mContext), mHandler);
//					lv_buy_history.stopRefresh();
//				}
				break;
			default:
				break;
		}
	}
	ViewPager.OnPageChangeListener viewPageListener=new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			switch (position) {
				case 0://品牌列表
					selectBrand =1;
					onselectBrandWay(1);
					break;
				case 1://商品分类
					selectBrand =2;
					onselectBrandWay(2);
					break;
			}
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};


	public class MyAdapter extends PagerAdapter {
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = mList.get(position);
			container.addView(view);
			return view;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mList.get(position));
		}
		@Override
		public int getCount() {
			return mList.size();
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

	//获取商品分类
	private void getGoodTypes() {
		if(mGoodTypeList!=null && mGoodTypeList.size()>0){
			mGoodTypeList.clear();
		}
//		//全部
//		GoodTypeBean info_all=new GoodTypeBean();
//		info_all.setName("全部");
//		info_all.setDrawable(R.drawable.good_all_seletor);
//		mGoodTypeList.add(info_all);
//
//		//充电器
//		GoodTypeBean info_charger=new GoodTypeBean();
//		info_charger.setName("充电器");
//		info_charger.setDrawable(R.drawable.good_charger_seletor);
//		mGoodTypeList.add(info_charger);
//
//		//电池
//		GoodTypeBean info_battery=new GoodTypeBean();
//		info_battery.setName("电池");
//		info_battery.setDrawable(R.drawable.good_battery_seletor);
//		mGoodTypeList.add(info_battery);
//
//		//电子书皮套/壳
//		GoodTypeBean info_holster=new GoodTypeBean();
//		info_holster.setName("电子书皮套/壳");
//		info_holster.setDrawable(R.drawable.good_holster_seletor);
//		mGoodTypeList.add(info_holster);
//
//		//耳机
//		GoodTypeBean info_headset=new GoodTypeBean();
//		info_headset.setName("耳机");
//		info_headset.setDrawable(R.drawable.good_headset_seletor);
//		mGoodTypeList.add(info_headset);
//
//		//键鼠
//		GoodTypeBean info_keyboard=new GoodTypeBean();
//		info_keyboard.setName("键鼠");
//		info_keyboard.setDrawable(R.drawable.good_keyboard_seletor);
//		mGoodTypeList.add(info_keyboard);
//
//		//平板皮套/壳
//		GoodTypeBean info_shell=new GoodTypeBean();
//		info_shell.setName("平板皮套/壳");
//		info_shell.setDrawable(R.drawable.good_shell_seletor);
//		mGoodTypeList.add(info_shell);
//
//		//情趣用品
//		GoodTypeBean info_sex=new GoodTypeBean();
//		info_sex.setName("情趣用品");
//		info_sex.setDrawable(R.drawable.good_sex_seletor);
//		mGoodTypeList.add(info_sex);
//
//		//手袋
//		GoodTypeBean info_handbag=new GoodTypeBean();
//		info_handbag.setName("手袋");
//		info_handbag.setDrawable(R.drawable.good_handbag_seletor);
//		mGoodTypeList.add(info_handbag);
//
//		//手机电脑贴膜
//		GoodTypeBean info_sticker=new GoodTypeBean();
//		info_sticker.setName("手机电脑贴膜");
//		info_sticker.setDrawable(R.drawable.good_sticker_seletor);
//		mGoodTypeList.add(info_sticker);
//
//		//线材
//		GoodTypeBean info_wire=new GoodTypeBean();
//		info_wire.setName("线材");
//		info_wire.setDrawable(R.drawable.good_wire_seletor);
//		mGoodTypeList.add(info_wire);
//
//		//小家电
//		GoodTypeBean info_home =new GoodTypeBean();
//		info_home.setName("小家电");
//		info_home.setDrawable(R.drawable.good_home_seletor);
//		mGoodTypeList.add(info_home);
//
//		//移动电源
//		GoodTypeBean info_power =new GoodTypeBean();
//		info_power.setName("移动电源");
//		info_power.setDrawable(R.drawable.good_power_seletor);
//		mGoodTypeList.add(info_power);
//
//		//音响
//		GoodTypeBean info_stereo=new GoodTypeBean();
//		info_stereo.setName("音响");
//		info_stereo.setDrawable(R.drawable.good_stereo_seletor);
//		mGoodTypeList.add(info_stereo);
//
//		//运动保护用品
//		GoodTypeBean info_sport=new GoodTypeBean();
//		info_sport.setName("运动保护用品");
//		info_sport.setDrawable(R.drawable.good_sport_seletor);
//		mGoodTypeList.add(info_sport);
//
//		//支架
//		GoodTypeBean info_stents=new GoodTypeBean();
//		info_stents.setName("支架");
//		info_stents.setDrawable(R.drawable.good_stents_seletor);
//		mGoodTypeList.add(info_stents);
//
//		//自拍器
//		GoodTypeBean info_selfie=new GoodTypeBean();
//		info_selfie.setName("自拍器");
//		info_selfie.setDrawable(R.drawable.good_selfie_seletor);
//		mGoodTypeList.add(info_selfie);
//
//		//其他
//		GoodTypeBean info_other=new GoodTypeBean();
//		info_other.setName("其他");
//		info_other.setDrawable(R.drawable.good_other_seletor);
//		mGoodTypeList.add(info_other);
	}

	/**
	 * 获取大品牌数据
	 */
	private void getBigBrandData() {
		if(mBigBrandList!=null && mBigBrandList.size()>0){
			mBigBrandList.clear();
		}
		for (int i = 0; i < 9; i++) {
			BrandBean info=new BrandBean();
			info.setId(i);
			info.setName("品牌"+i);
			mBigBrandList.add(info);
		}
	}
	/**
	 * 加载数据
	 */
	public void initData() {
		if(mList_AllBrandBean!=null && mList_AllBrandBean.size()>0){
			mList_AllBrandBean.clear();
		}

		//#默认为头部品牌
		AllBrandBean info_big=new AllBrandBean();
		List<BrandBean> listBrand_big=new ArrayList<BrandBean>();
		info_big.setFirstWord("#");
		info_big.setListBrand(listBrand_big);
		mList_AllBrandBean.add(info_big);

		//A
		AllBrandBean info_a=new AllBrandBean();
		List<BrandBean> listBrand_a=new ArrayList<BrandBean>();
		info_a.setFirstWord("A");
		for (int i = 0; i < 5; i++) {
			BrandBean info=new BrandBean();
			info.setName("品牌"+i);
			listBrand_a.add(info);
		}
		info_a.setListBrand(listBrand_a);
		mList_AllBrandBean.add(info_a);

		//B
		AllBrandBean info_b=new AllBrandBean();
		List<BrandBean> listBrand_b=new ArrayList<BrandBean>();
		info_b.setFirstWord("B");
		for (int i = 0; i < 5; i++) {
			BrandBean info=new BrandBean();
			info.setName("品牌"+i);
			listBrand_b.add(info);
		}
		info_b.setListBrand(listBrand_b);
		mList_AllBrandBean.add(info_b);

		//f
		AllBrandBean info_f=new AllBrandBean();
		List<BrandBean> listBrand_f=new ArrayList<BrandBean>();
		info_f.setFirstWord("F");
		for (int i = 0; i < 5; i++) {
			BrandBean info=new BrandBean();
			info.setName("品牌"+i);
			listBrand_f.add(info);
		}
		info_f.setListBrand(listBrand_f);
		mList_AllBrandBean.add(info_f);

		//N
		AllBrandBean info_n=new AllBrandBean();
		List<BrandBean> listBrand_n=new ArrayList<BrandBean>();
		info_n.setFirstWord("N");
		for (int i = 0; i <100; i++) {
			BrandBean info=new BrandBean();
			info.setName("品牌"+i);
			listBrand_n.add(info);
		}
		info_n.setListBrand(listBrand_n);
		mList_AllBrandBean.add(info_n);

		//y
		AllBrandBean info_y=new AllBrandBean();
		List<BrandBean> listBrand_y=new ArrayList<BrandBean>();
		info_y.setFirstWord("Y");
		for (int i = 0; i <100; i++) {
			BrandBean info=new BrandBean();
			info.setName("品牌"+i);
			listBrand_y.add(info);
		}
		info_y.setListBrand(listBrand_y);
		mList_AllBrandBean.add(info_y);

		//z
		AllBrandBean info_z=new AllBrandBean();
		List<BrandBean> listBrand_z=new ArrayList<BrandBean>();
		info_z.setFirstWord("Z");
		for (int i = 0; i < 5; i++) {
			BrandBean info=new BrandBean();
			info.setName("品牌"+i);
			listBrand_z.add(info);
		}
		info_z.setListBrand(listBrand_z);
		mList_AllBrandBean.add(info_z);


		//更新数据源
		mSmallBrand_SelectAdapter.setmBrandAll_small(mList_AllBrandBean);
		mSmallBrand_SelectAdapter.notifyDataSetChanged();
	}
	//HeadListView
	public class SmallBrandSelectAdapter extends SectionedBaseAdapter implements SectionIndexer {
		private List<AllBrandBean> mBrandAll_small=new ArrayList<AllBrandBean>();

		//所有品牌栏目对应的适配器
		private SmallBrandAdapter mSmallBrandAdapter;
		private AdapterView.OnItemClickListener itemClick;

		public void setItemClick(AdapterView.OnItemClickListener itemClick) {
			this.itemClick = itemClick;
		}


		public List<AllBrandBean> getmBrandAll_small() {
			return mBrandAll_small;
		}


		public void setmBrandAll_small(List<AllBrandBean> mBrandAll_small) {
			this.mBrandAll_small = mBrandAll_small;
		}

		@Override
		public Object getItem(int section, int position) {
			return mBrandAll_small.get(section);
		}
		@Override
		public long getItemId(int section, int position) {
			return section;
		}
		@Override
		public int getSectionCount() {
			return mBrandAll_small.size();
		}
		@Override
		public int getCountForSection(int section) {
			return 1;
		}
		@Override
		public View getItemView(int section, int position, View convertView, ViewGroup parent) {
			LinearLayout layout = null;
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();

				LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				layout = (LinearLayout) inflator.inflate(R.layout.list_item, null);

				//small
				holder.ll_small_item= (LinearLayout) layout.findViewById(R.id.ll_small_item);
				holder.lv_smallBrand = (ExListView) layout.findViewById(R.id.lv_smallBrand);

				//big
				holder.ll_big_item= (LinearLayout) layout.findViewById(R.id.ll_big_item);
				holder.gv_selected= (MyGridView) layout.findViewById(R.id.gv_selected);

				convertView = layout;
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			AllBrandBean info=mBrandAll_small.get(section);
			if(info!=null){

				if(section!=0){//显示小品牌
					holder.ll_small_item.setVisibility(View.VISIBLE);
					holder.ll_big_item.setVisibility(View.GONE);
					//显示对应的所有品牌
					if(info.getListBrand()!=null && info.getListBrand().size()!=0){
						List<BrandBean> allBrandBeanList = new ArrayList<BrandBean>();
						allBrandBeanList= info.getListBrand();
						mSmallBrandAdapter=new SmallBrandAdapter(mContext);
						holder.lv_smallBrand.setAdapter(mSmallBrandAdapter);
						mSmallBrandAdapter.setData(allBrandBeanList);
						mSmallBrandAdapter.notifyDataSetChanged();
					}
				}else{//显示大品牌
					holder.ll_small_item.setVisibility(View.GONE);
					holder.ll_big_item.setVisibility(View.VISIBLE);
					mBigBrandAdapter=new BrandAdapter(mContext);
					holder.gv_selected.setAdapter(mBigBrandAdapter);
					mBigBrandAdapter.setData(mBigBrandList);
					mBigBrandAdapter.notifyDataSetChanged();
				}
			}
			/**
			 * @autor zcs
			 * 临时添加，用于界面跳转
			 */
			holder.lv_smallBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(getActivity(),ProductListActivity.class);
					startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder{
			//small
			LinearLayout ll_small_item;
			ExListView lv_smallBrand ;

			//big
			LinearLayout ll_big_item;
			MyGridView gv_selected;
		}

		@Override
		public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
			LinearLayout layout = null;
			if (convertView == null) {
				LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
			} else {
				layout = (LinearLayout) convertView;
			}
			AllBrandBean info=mBrandAll_small.get(section);
			if(info!=null){
				if(section!=0){
					((LinearLayout) layout.findViewById(R.id.ll_item)).setVisibility(View.VISIBLE);
					String firsrWord=info.getFirstWord();
					((TextView) layout.findViewById(R.id.tv_firstWord)).setText(info.getFirstWord());
					((TextView) layout.findViewById(R.id.tv_firstWord)).setVisibility(View.VISIBLE);
				}else{
					((LinearLayout) layout.findViewById(R.id.ll_item)).setVisibility(View.GONE);
					((TextView) layout.findViewById(R.id.tv_firstWord)).setVisibility(View.GONE);
				}
			}
			return layout;
		}

		@Override
		public int getPositionForSection(int section) {
			for (int i = 0; i < mBrandAll_small.size(); i++) {
				String firsrWord=mBrandAll_small.get(i).getFirstWord();
				if(firsrWord!=null && !firsrWord.equals("")){
					String l = PingYinUtil.converterToFirstSpell(firsrWord)
							.substring(0, 1);
					char firstChar = l.toUpperCase().charAt(0);
					AppLog.Loge("普通首字母 , firstChar:"+firstChar+"---(int)firstChar"+(int)firstChar+"--section:"+section+"--i:"+i);
					if (firstChar == section) {
						return 2*i+1;
					}
				}
			}
			return -1;
		}
		@Override
		public Object[] getSections() {
			return null;
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_one://品牌列表
				if(selectBrand!=1){
					selectBrand=1;
					viewPagerOrder.setCurrentItem(0);
				}
				break;
			case R.id.rl_two://商品列表
				if(selectBrand!=2){
					selectBrand=2;
					viewPagerOrder.setCurrentItem(1);
				}
				break;
			default:
				break;
		}
	}
}
