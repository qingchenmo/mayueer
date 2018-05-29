package com.jlkf.ego.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.BitImgActivity;
import com.jlkf.ego.activity.ProductInfoActivity;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.utils.ProductAddShopCarUtils;
import com.jlkf.ego.widget.VerticalViewPager;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.jlkf.ego.R.id.iv_product_num_add;
import static com.jlkf.ego.R.id.iv_product_num_sub;
import static com.jlkf.ego.R.id.lin_product_package_large;
import static com.jlkf.ego.R.id.lin_product_package_small;
import static com.jlkf.ego.R.id.rb_connection;
import static com.jlkf.ego.R.id.tv_product_info;

/**
 * Created by zcs on 2017/7/12.
 * 本类快选ViewPager适配器
 */

public class ProductQuickAdapter extends PagerAdapter {
    private Context mContext;
    private List<ProductListBean.DataBean> mList;
    private List<View> mViews;
    private VerticalViewPager pager;
    private ProductAddShopCarUtils.OnAddShopCarListener mOnAddShopCarListener;
    private View.OnClickListener mListener;

    public ProductQuickAdapter(Context context, List<ProductListBean.DataBean> list,
                               VerticalViewPager pager, ProductAddShopCarUtils.OnAddShopCarListener onAddShopCarListener, View.OnClickListener listener) {
        mContext = context;
        mList = list;
        this.pager = pager;
        int size = mList.size();
        mViews = new ArrayList<>();
        mOnAddShopCarListener = onAddShopCarListener;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.product_quick_info_layout, null);
        final ViewHolder holder = new ViewHolder(v);
//        mViews.add(v);
        BindViewHolder(holder, position);
        container.addView(v);
        return v;
    }

    private void BindViewHolder(ViewHolder holder, int position) {
        ProductListBean.DataBean info = mList.get(position);
        if (!(info.getUUB() == null || info.getUUB().isEmpty()) && !(info.getUUX() == null || info.getUUX().isEmpty())) {
            holder.lin_product_package_large.setVisibility(View.VISIBLE);
            holder.lin_product_package_small.setVisibility(View.VISIBLE);
            info.setHavaPackage(true);
            holder.tv_product_num_small.setText(info.getUUB());
            holder.tv_product_num_large.setText(info.getUUX());
        } else {
            holder.lin_product_package_large.setVisibility(View.GONE);
            holder.lin_product_package_small.setVisibility(View.GONE);
        }
        info.setSelectNum(ProductAddShopCarUtils.getInstance().getGoodsNumInShopCar(info.getItemcode()));
        holder.mTvProductName.setText(info.getItemname());
        initPackage(holder, info);
        holder.et_product_select_num.setText(String.valueOf(info.getSelectNum()));
        holder.tv_product_price.setText(mContext.getString(R.string.money) + info.getPrice());
        holder.tv_product_stock.setText(":" + info.getOnhand());
        OnClickListener listener = new OnClickListener(position, holder);
        holder.lin_product_package_large.setOnClickListener(listener);
        holder.lin_product_package_small.setOnClickListener(listener);
        holder.iv_product_num_add.setOnClickListener(listener);
        holder.iv_product_num_sub.setOnClickListener(listener);
        holder.tv_product_info.setOnClickListener(listener);
        holder.et_product_select_num.setOnClickListener(listener);
        initProductImg(holder, info);
        try {
            holder.rb_connection.setChecked(info.getIsColler().equals("0"));
        } catch (Exception e) {
        }
        holder.tv_product_descri.setText(info.getUserText() + "");
        holder.rb_connection.setOnClickListener(listener);
    }

    /**
     * 初始化图片轮播图
     *
     * @param holder
     * @param info
     */
    private void initProductImg(ViewHolder holder, final ProductListBean.DataBean info) {
        final ArrayList<String> ImgList = new ArrayList<>();
        if (info.getAttachment1() != null && !info.getAttachment1().isEmpty())
            ImgList.add(info.getAttachment1());
        if (info.getAttachment2() != null && !info.getAttachment2().isEmpty())
            ImgList.add(info.getAttachment2());
        if (info.getAttachment3() != null && !info.getAttachment3().isEmpty())
            ImgList.add(info.getAttachment3());
        if (info.getAttachment4() != null && !info.getAttachment4().isEmpty())
            ImgList.add(info.getAttachment4());
        if (info.getAttachment5() != null && !info.getAttachment5().isEmpty())
            ImgList.add(info.getAttachment5());
        if (info.getAttachment6() != null && !info.getAttachment6().isEmpty())
            ImgList.add(info.getAttachment6());
//        if (ImgList.size() < 1)
//            ImgList.add(info.getPicturname());
        if (ImgList.size() > 0) {
            holder.iv_goods_img.setVisibility(View.GONE);
            holder.mBanner.setVisibility(View.VISIBLE);
            holder.mBanner.setImages(ImgList).setImageLoader(new GlideImageLoader()).start();


            holder.mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(mContext, BitImgActivity.class);
                    intent.putStringArrayListExtra("imgList", ImgList);
                    intent.putExtra("select", position);
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.iv_goods_img.setVisibility(View.VISIBLE);
            holder.mBanner.setVisibility(View.GONE);
            Glide.with(mContext).load(info.getPicturname()).error(R.drawable.icon_img_load_failed).placeholder(R.drawable.icon_img_load).centerCrop().into(holder.iv_goods_img);
        }

        List<View> list = new ArrayList<>();
        /*for (int i = 0; i < ImgList.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.vp_img_item, null);
            RadioButton button = new RadioButton(mContext);
            button.setClickable(false);
            button.setPadding(0, 0, CompanyUtil.dip2px(10), 0);
            button.setButtonDrawable(R.drawable.vp_indicator);
            holder.mRgIndicator.addView(button);
//            LinearLayout layout = new LinearLayout(mContext);
//            ImageView iv = new ImageView(mContext);
//            layout.addView(iv, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            list.add(view);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BitImgActivity.class);
                    intent.putStringArrayListExtra("imgList", ImgList);
                    intent.putExtra("select", finalI);
                    mContext.startActivity(intent);
                }
            });
        }
        if (list.size() > 0)
            ((RadioButton) holder.mRgIndicator.getChildAt(0)).setChecked(true);
        holder.mVpProductImg.setAdapter(new ProductQuickInfoImgAdapter(mContext, list, ImgList));
        holder.mVpProductImg.addOnPageChangeListener(new OnPageChangeListener(holder.mRgIndicator));
        if (list.size() > 0) {
            holder.iv_goods_img.setVisibility(View.GONE);
            holder.mVpProductImg.setVisibility(View.VISIBLE);
            if (list.size() == 1) {
                holder.mRgIndicator.setVisibility(View.GONE);
            }
        } else {
            holder.iv_goods_img.setVisibility(View.VISIBLE);
            holder.mVpProductImg.setVisibility(View.GONE);
            Glide.with(mContext).load(info.getPicturname()).error(R.drawable.icon_img_load_failed).placeholder(R.drawable.icon_img_load).centerCrop().into(holder.iv_goods_img);
        }*/
    }

    /**
     * 初始化大小包
     *
     * @param holder
     * @param info
     */
    private void initPackage(ViewHolder holder, ProductListBean.DataBean info) {
        if (info.isHavaPackage()) {
            holder.tv_product_num_small.setText(info.getUUB());
            holder.tv_product_num_large.setText(info.getUUX());
            /*if (info.isLargePackage()) {
                holder.iv_product_package_large.setImageResource(R.mipmap.icon_large_package_brown);
                holder.iv_product_package_small.setImageResource(R.mipmap.icon_small_package);
                holder.tv_product_num_large.setTextColor(mContext.getResources().getColor(R.color.bg_brown2));
                holder.tv_product_num_small.setTextColor(mContext.getResources().getColor(R.color.text_title));
                holder.lin_product_package_large.setSelected(true);
                holder.lin_product_package_small.setSelected(false);
            } else if (info.isSmallPackage()) {
                holder.iv_product_package_large.setImageResource(R.mipmap.icon_large_package);
                holder.iv_product_package_small.setImageResource(R.mipmap.icon_small_package_brown);
                holder.tv_product_num_large.setTextColor(mContext.getResources().getColor(R.color.text_title));
                holder.tv_product_num_small.setTextColor(mContext.getResources().getColor(R.color.bg_brown2));

                holder.lin_product_package_large.setSelected(false);
                holder.lin_product_package_small.setSelected(true);
            } else {
//                info.setLargePackage(true);
                holder.iv_product_package_large.setImageResource(R.mipmap.icon_large_package);
                holder.iv_product_package_small.setImageResource(R.mipmap.icon_small_package);
                holder.tv_product_num_large.setTextColor(mContext.getResources().getColor(R.color.text_title));
                holder.tv_product_num_small.setTextColor(mContext.getResources().getColor(R.color.text_title));

                holder.lin_product_package_large.setSelected(false);
                holder.lin_product_package_small.setSelected(false);
            }*/
            holder.lin_product_package_large.setSelected(info.isLargePackage());
            holder.lin_product_package_small.setSelected(info.isSmallPackage());
        }
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    class OnPageChangeListener implements ViewPager.OnPageChangeListener {
        private RadioGroup mRgIndicator;

        public OnPageChangeListener(RadioGroup rgIndicator) {
            mRgIndicator = rgIndicator;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ((RadioButton) mRgIndicator.getChildAt(position)).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class OnClickListener implements View.OnClickListener {
        private int position;
        private ProductListBean.DataBean info;
        private ViewHolder mHolder;

        public OnClickListener(int position, ViewHolder holder) {
            this.position = position;
            mHolder = holder;
            info = mList.get(position);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case iv_product_num_add:
                    ProductAddShopCarUtils.getInstance().EditShopCar(true, info, mHolder.et_product_select_num, (Activity) mContext, mOnAddShopCarListener, mHolder.iv_product_num_add);
                    break;
                case iv_product_num_sub:
                    ProductAddShopCarUtils.getInstance().EditShopCar(false, info, mHolder.et_product_select_num, (Activity) mContext, mOnAddShopCarListener, mHolder.iv_product_num_sub);
                    break;
                case lin_product_package_large:
//                    info.setLargePackage(true);
//                    ProductAddShopCarUtils.getInstance().EditShopCar(true, info, mHolder.et_product_select_num, (Activity) mContext, mOnAddShopCarListener, mHolder.iv_product_num_add);
                    if (info.isLargePackage()) {
                        info.setLargePackage(false);
                        initPackage(mHolder, info);
                        ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                        return;
                    }
                    ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                    info.setLargePackage(true);
                    info.setSmallPackage(false);
                    initPackage(mHolder, info);
                    break;
                case lin_product_package_small:
//                    info.setSmallPackage(true);
//                    ProductAddShopCarUtils.getInstance().EditShopCar(true, info, mHolder.et_product_select_num, (Activity) mContext, mOnAddShopCarListener, mHolder.iv_product_num_add);
                    if (info.isSmallPackage()) {
                        info.setSmallPackage(false);
                        initPackage(mHolder, info);
                        ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                        return;
                    }
                    ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                    info.setLargePackage(false);
                    info.setSmallPackage(true);
                    initPackage(mHolder, info);
                    break;
                case tv_product_info:
                    Intent intent = new Intent(mContext, ProductInfoActivity.class);
                    intent.putExtra("itemCode", info.getItemcode());
                    ((Activity) mContext).startActivityForResult(intent, 1);
                    break;
                case rb_connection:
                    ProductAddShopCarUtils.getInstance().connectionProduct((Activity) mContext, mHolder.rb_connection, info);
                    break;
                case R.id.et_product_select_num:
//                    DialogUtil.addShopCarDia(mContext, false, mList.get(position), mHolder.et_product_select_num, (Activity) mContext, mOnAddShopCarListener, mHolder.iv_product_num_sub);
                    break;
            }
        }
    }

    class ViewHolder {
        private View v;
        private TextView mTvProductName, tv_product_price, tv_product_stock, tv_product_num_large, tv_product_num_small, tv_product_info, tv_product_descri;
        private ImageView iv_product_num_sub, iv_product_num_add, iv_product_package_large, iv_product_package_small;
        //        private RadioGroup mRgIndicator;
//        private ViewPager mVpProductImg;
        private CheckBox rb_connection;
        private TextView et_product_select_num;
        private LinearLayout lin_product_package_large, lin_product_package_small;
        private FrameLayout fl_img;
        private ImageView iv_arrow, iv_goods_img;
        private Banner mBanner;

        public ViewHolder(View itemView) {
            v = itemView;
            findView();
        }

        private void findView() {
            mTvProductName = (TextView) v.findViewById(R.id.tv_product_name);
            tv_product_num_large = (TextView) v.findViewById(R.id.tv_product_num_large);
            tv_product_num_small = (TextView) v.findViewById(R.id.tv_product_num_small);
            tv_product_price = (TextView) v.findViewById(R.id.tv_product_price);
            tv_product_stock = (TextView) v.findViewById(R.id.tv_product_stock);
            iv_product_num_sub = (ImageView) v.findViewById(R.id.iv_product_num_sub);
            iv_product_num_add = (ImageView) v.findViewById(R.id.iv_product_num_add);
//            mRgIndicator = (RadioGroup) v.findViewById(R.id.rg_vp_indicator);
//            mVpProductImg = (ViewPager) v.findViewById(R.id.vp_product_info_img);
            mBanner = (Banner) v.findViewById(R.id.banner);
            tv_product_info = (TextView) v.findViewById(R.id.tv_product_info);
            rb_connection = (CheckBox) v.findViewById(R.id.rb_connection);
            iv_product_package_large = (ImageView) v.findViewById(R.id.iv_product_package_large);
            iv_product_package_small = (ImageView) v.findViewById(R.id.iv_product_package_small);
            lin_product_package_large = (LinearLayout) v.findViewById(R.id.lin_product_package_large);
            lin_product_package_small = (LinearLayout) v.findViewById(R.id.lin_product_package_small);
            et_product_select_num = (TextView) v.findViewById(R.id.et_product_select_num);
            fl_img = (FrameLayout) v.findViewById(R.id.fl_img);
            tv_product_descri = (TextView) v.findViewById(R.id.tv_product_descri);
            iv_arrow = (ImageView) v.findViewById(R.id.iv_arrow);
            iv_arrow.setOnClickListener(mListener);
            iv_goods_img = (ImageView) v.findViewById(R.id.iv_goods_img);
        }
    }

    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    public static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            //Glide 加载图片简单用法
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(path).centerCrop().error(R.drawable.icon_img_load_failed).placeholder(R.drawable.icon_img_load).into(imageView);
        }
    }
}
