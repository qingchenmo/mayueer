package com.jlkf.ego.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.bean.PayType;
import com.jlkf.ego.utils.LanguageUtils;
import com.jlkf.ego.utils.ShardeUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class SpinnerAdapter extends BaseAdapter {

    public SpinnerAdapter(Context context, List<PayType> payTypes) {
        mPayTypes = payTypes;
        mContext = context;
    }

    private List<PayType> mPayTypes;
    private Context mContext;

    @Override
    public int getCount() {
        if (mPayTypes.size() > 0 && mPayTypes != null)
            return mPayTypes.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mPayTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PayType payType = mPayTypes.get(position);
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.spinner, parent, false);


        String language = ShardeUtil.getString("language");

        if (!TextUtils.isEmpty(language)) {

            switch (language) {
                case LanguageUtils.CHINAESE:
                    ((TextView)inflate.findViewById(R.id.tv)).setText(payType.getUPayName());
                    break;
                case LanguageUtils.ENGLISH:
                    ((TextView)inflate.findViewById(R.id.tv)).setText(payType.getuPayName4());
                    break;
                case LanguageUtils.ESPAÑOL:
                    ((TextView)inflate.findViewById(R.id.tv)).setText(payType.getuPayName1());
                    break;
                case LanguageUtils.ITALIANO:
                    ((TextView)inflate.findViewById(R.id.tv)).setText(payType.getuPayName3());
                    break;
                case LanguageUtils.FRANÇAIS:
                    ((TextView)inflate.findViewById(R.id.tv)).setText(payType.getuPayName2());
                    break;
            }
        }


        return inflate;
    }
}
