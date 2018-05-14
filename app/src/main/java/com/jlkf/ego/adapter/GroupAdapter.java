package com.jlkf.ego.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.bean.ShoupommodityC;
import com.jlkf.ego.bean.ShoupommodityCList;
import com.jlkf.ego.utils.ImageLoaderManager;

/**
 * 购物车商品适配器
 */

public class GroupAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ShoupommodityCList[] gs;
    private LayoutInflater mInflater;
    public GroupAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    //配置组大小
    @Override
    public int getGroupCount() {
        return null == gs ? 0 : gs.length;
    }

    //配置组位置为groupPosition的孩子的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return gs[groupPosition].getGroups() == null ? 0 : gs[groupPosition].getGroups().size();
    }

    //获取位置为groupPosition的组对象
    @Override
    public Object getGroup(int groupPosition) {

        return gs[groupPosition];
    }

    //获取位置为groupPosition的组中位置为childPosition的孩子对象
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return gs[groupPosition].getGroups().get(childPosition);
    }

    //获取组id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获取组中的孩子的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition * 100 + childPosition;  // 101 102  201 202
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //配置组的布局
    private  View view;
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (null == convertView){
            //是自營商品
                 if(gs[groupPosition].getisSelforagency()){
                     //返回自营的布局
                     view =mInflater.inflate(R.layout.autotrophy,null);
                 }else {
                     view =mInflater.inflate(R.layout.brand,null);
                 }
        } else {
          view=convertView;
        }
     return view;
    }
    //配置商品中孩子的布局
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout, null);
            holder= new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        ShoupommodityC item = gs[groupPosition].getGroups().get(childPosition);
        holder.checkBox.setSelected(item.isSelect());
        holder.textView2.setText(item.getShoupName());
        holder.textView3.setText(item.getShoupCode());
        //單價
        holder.textView.setText(item.getPirce()+"");
        //消極
//        holder.TextView6.setText(item.getCountpirce());
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public ShoupommodityCList[] getGroupInList() {
        return gs;
    }
    public void setGroupInList(ShoupommodityCList[] gs) {
        this.gs = gs;
        notifyDataSetChanged();
    }
    class ViewHolder {
        CheckBox checkBox;
        ImageView shouimage;
        TextView  textView2;
        TextView  textView3;
        TextView textView;
        TextView TextView6;
        ViewHolder(View v){
            v=mInflater.inflate(R.layout.layout,null);
            this.checkBox= (CheckBox) v.findViewById(R.id.selectShoup1);
            this.shouimage= (ImageView) v.findViewById(R.id.shouImage);
            this.textView2= (TextView) v.findViewById(R.id.textView2);
            this.textView3= (TextView) v.findViewById(R.id.textView3);
            this.textView= (TextView) v.findViewById(R.id.textView);
            this.TextView6= (TextView) v.findViewById(R.id.textView6);
        }
    }
}
