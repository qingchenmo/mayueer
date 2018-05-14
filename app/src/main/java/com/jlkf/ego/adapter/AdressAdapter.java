package com.jlkf.ego.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.activity.AdressActivity;
import com.jlkf.ego.activity.EditAdressActivity;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.AdressBean;
import com.jlkf.ego.bean.MyBaseBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.DialogUtil;
import com.jlkf.ego.utils.ToastUti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jlkf.ego.activity.AdressActivity.CHANGE_ADRESS;
import static com.jlkf.ego.activity.AdressActivity.STATE_NO_ADRESS;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class AdressAdapter extends RecyclerView.Adapter {


    public List<AdressBean> getDatas() {
        return mDatas;
    }

    public void setDatas(List<AdressBean> datas) {
        mDatas = datas;
    }

    private List<AdressBean> mDatas;
    private Context context;
    private OnChangeUpdefelListener mListener;

    public AdressAdapter(Context context, List<AdressBean> mDatas, OnChangeUpdefelListener listener) {
        this.mDatas = mDatas;
        this.context = context;
        mListener = listener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.holder_adress_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final AdressBean adressBean = mDatas.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.mTvTitleName.setText(adressBean.getUaShopname());
        viewHolder.tv_yb.setText(context.getResources().getString(R.string.yb)+":"+adressBean.getUaZipcode());
        viewHolder.mTvName.setText(adressBean.getUaSurname() + adressBean.getUaName());
        viewHolder.mTvPhone.setText(adressBean.getUaContactphone());
        viewHolder.mTvAdress.setText( adressBean.getUaProvincialName() + adressBean.getUaDelivery());

        if (adressBean.getUpDefault() != 1) {
            viewHolder.mRb.setChecked(false);
        } else {
            viewHolder.mRb.setChecked(true);
        }
        viewHolder.ll_adress_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = ((Activity) context).getIntent().getIntExtra("tag", -1);
                if (tag == -1) {
                    return;
                } else {

                    Intent intent = new Intent();
                    adressBean.setPosition(tag + "");
                    intent.putExtra("order", adressBean);
                    ((Activity) context).setResult(((Activity) context).RESULT_OK, intent);
                    ((Activity) context).finish();
                }
            }
        });
        viewHolder.mRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdressBean adressBean = mDatas.get(position);
                if (adressBean.getUpDefault() != 1) {
                    editMorenAddress(mDatas.get(position).getUaId(), position);
                }
            }
        });

        viewHolder.tv_subit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditAdressActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("bean", adressBean);
                ((Activity) context).startActivityForResult(intent, CHANGE_ADRESS);
            }
        });
    }

    private void editMorenAddress(String id, final int position) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        HttpUtil.getInstacne((Activity) context).get2(Urls.udefault, MyBaseBean.class, map, new HttpUtil.OnCallBack<MyBaseBean>() {
            @Override
            public void success(MyBaseBean data) {
                mListener.changeUpdefalListener();
            }

            @Override
            public void onError(String msg, int code) {
                ToastUti.show(msg);
            }
        });
    }


    Dialog titleWithTwoButtonDiaLog;

    private void showDialog(final int layoutPosition) {

        titleWithTwoButtonDiaLog = DialogUtil.getTitleWithTwoButtonDiaLog(context,
                context.getResources().getString(R.string.is_delet),
                context.getResources().getString(R.string.ok), context.getResources().getString(R.string.no), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AdressBean adressBean = mDatas.get(layoutPosition);

                Map<String, String> map = new HashMap<String, String>();
                map.put("uaId", adressBean.getUaId());
                HttpUtil.getInstacne((Activity) context).get(Urls.adress_delete, String.class, map, new HttpUtil.OnCallBack() {
                    @Override
                    public void success(Object data) {

                        mDatas.remove(adressBean);
                        notifyDataSetChanged();
                        if (mDatas.size() == 0) {
                            ((AdressActivity) context).state = STATE_NO_ADRESS;
                            ((AdressActivity) context).initView();
                        }

                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                });
                titleWithTwoButtonDiaLog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titleWithTwoButtonDiaLog.dismiss();
            }
        });

        titleWithTwoButtonDiaLog.show();
    }


    @Override
    public int getItemCount() {
        if (mDatas.size() > 0 && mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_subit)
        TextView tv_subit;
        @BindView(R.id.tv_delet)
        TextView tvDelet;

        @BindView(R.id.tv_title_name)
        TextView mTvTitleName;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_phone)
        TextView mTvPhone;
        @BindView(R.id.tv_adress)
        TextView mTvAdress;
        @BindView(R.id.tv_yb)
        TextView tv_yb;
        @BindView(R.id.rb)
        CheckBox mRb;

        @BindView(R.id.ll_adress_item)
        LinearLayout ll_adress_item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);



            /*mRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    AdressBean adressBean = mDatas.get(getLayoutPosition());
                    if (adressBean.getUpDefault() == 0) {
                        adressBean.setUpDefault(1);
                        for (int i = 0; i < getItemCount(); i++) {

                        }
                    }
                }
            });*/

            tvDelet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(getLayoutPosition());
                }
            });
        }
    }

    public interface OnChangeUpdefelListener {
        void changeUpdefalListener();
    }
}
