package com.jlkf.ego.utils;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.newpage.bean.VersionBean;
import com.jlkf.ego.newpage.utils.TextInputUtils;

import static com.jlkf.ego.R.id.tv;
import static com.jlkf.ego.R.id.tv_cancel;
import static com.jlkf.ego.R.id.tv_commit;

/**********************************************
 * @类名: DialogUtil
 * @描述: 对话框创建工具类
 * @作者： LXY
 * @创建日期： 2015-7-29 上午9:33:51
 * @版本： V1.0
 * @修订历史：
 ***********************************************/
public class DialogUtil {

    public static AlertDialog.Builder createMessageDialog(Context context,
                                                          String content) {
        // AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return new AlertDialog.Builder(context).setMessage(content);

    }

    /**
     * @param context
     * @param content
     * @param pListener： void
     * @方法名: showDialogWithButton
     * @方法描述: 显示带两个按钮的提示框
     * @异常： 无
     * @作者： LXY
     * @创建日期： 2015-7-29 上午9:34:15
     */
    public static void showDialogWithButton(Context context, String content,
                                            OnClickListener pListener) {
        new AlertDialog.Builder(context).setMessage(content)
                .setPositiveButton("确定", pListener)
                .setNegativeButton("取消", null).show();

    }

    public static void showDialogWithTwoButton(Context context, String content,
                                               OnClickListener pListener, OnClickListener listener) {
        new AlertDialog.Builder(context).setMessage(content)
                .setPositiveButton("确定", pListener)
                .setNegativeButton("取消", listener).show();

    }

    /**
     * @param context
     * @param title
     * @param content： void
     * @方法名: showDialogWithSingleButton
     * @方法描述:显示带单个按钮的提示框
     * @异常： 无
     * @作者： LXY
     * @创建日期： 2015-7-29 上午9:34:38
     */
    public static void showDialogWithSingleButton(Context context,
                                                  String title, String content) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(content)
                .setPositiveButton("确定", null).show();
    }

    /**
     * @param context
     * @param title
     * @param content： void
     * @方法名: showDialogWithSingleButton
     * @方法描述:显示带单个按钮的提示框
     * @异常： 无
     * @作者： LXY
     * @创建日期： 2015-7-29 上午9:34:38
     */
    public static void showDialogWithSingleButton(Context context,
                                                  String title, String content, OnClickListener pListener) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(content)
                .setPositiveButton("确定", pListener).show();
    }

    /**
     * @param context
     * @param content
     * @param items
     * @param listener： void
     * @方法名: showDialogWithItem
     * @方法描述: 显示列表对话框
     * @异常： 无
     * @作者： LXY
     * @创建日期： 2015-7-29 上午9:35:09
     */
    public static void showDialogWithItem(Context context, String content,
                                          String[] items, OnClickListener listener) {
        new AlertDialog.Builder(context).setTitle(content)
                .setItems(items, listener).show();

    }

    /**
     * 得到自定义的progressDialog圆形旋转，使用网络请求
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog LoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.ll_dialog_loading);// 加载布局

        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.iv_loading);
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.loading);
        animator.setTarget(spaceshipImage);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();
        if (msg != null) {
            TextView message_tv = (TextView) v.findViewById(R.id.tv_dialog_message);
            message_tv.setText(msg);
        }

        Dialog loadingDialog;
        loadingDialog = new Dialog(context, R.style.dialog_choose_department_style);// 创建自定义样式dialog

        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(true);// 用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;
    }

    /**
     * 请求确认类型DiaLog
     *
     * @param context
     * @param title
     * @param rightButton
     * @param leftButton
     * @param rightListener
     * @param leftListener
     * @return
     */
    public static Dialog getTitleWithTwoButtonDiaLog(Context context, String title, String rightButton
            , String leftButton, View.OnClickListener rightListener, View.OnClickListener leftListener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.delet_dialog, null);
        TextView titleView = (TextView) v.findViewById(R.id.tv_title_dialog);
        titleView.setText(title);
        TextView tv_left = (TextView) v.findViewById(tv_cancel);
        tv_left.setText(leftButton);
        tv_left.setOnClickListener(leftListener);
        TextView tv_right = (TextView) v.findViewById(tv_commit);
        tv_right.setText(rightButton);
        tv_right.setOnClickListener(rightListener);
        Dialog dialog = new AlertDialog.Builder(context)
                .setView(v)
                .create();
        dialog.getWindow().setBackgroundDrawable(new BitmapDrawable());
        return dialog;
    }

    public static Dialog getTitleWithOneButton(Context context, String title, String rightButton, View.OnClickListener listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.delet_dialog, null);
        TextView titleView = (TextView) v.findViewById(R.id.tv_title_dialog);
        titleView.setText(title);
        TextView tv_right = (TextView) v.findViewById(tv_cancel);
        tv_right.setText(rightButton);

        TextView tv_left = (TextView) v.findViewById(tv_commit);
        tv_left.setVisibility(View.GONE);
        View line = v.findViewById(R.id.view_line);
        line.setVisibility(View.GONE);
        final Dialog dialog = new AlertDialog.Builder(context)
                .setView(v)
                .create();
        dialog.getWindow().setBackgroundDrawable(new BitmapDrawable());
        if (listener != null)
            tv_right.setOnClickListener(listener);
        else tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    /**
     * 正在下载安装包Dia
     *
     * @param context
     * @return
     */
    public static Dialog LoadingAppDia(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dia_pross_text, null);// 得到加载view
        Dialog loadingDialog = new AlertDialog.Builder(context).setView(v).create();
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.iv_loading);
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.loading);
        animator.setTarget(spaceshipImage);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(true);// 用“返回键”取消
        loadingDialog.getWindow().setBackgroundDrawable(new BitmapDrawable());
        return loadingDialog;
    }

    public static Dialog addShopCarDia(Context context, final boolean add, final ProductListBean.DataBean info, final TextView etSelectNum,
                                       final Activity activity, final ProductAddShopCarUtils.OnAddShopCarListener listener, final ImageView view) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.add_shop_car_dia, null);
        TextView tv_cancel = (TextView) v.findViewById(R.id.tv_cancel);
        TextView tv_commit = (TextView) v.findViewById(R.id.tv_commit);
        final EditText et = (EditText) v.findViewById(R.id.et_num);
        final Dialog dialog = new AlertDialog.Builder(context).setView(v).create();
        dialog.getWindow().setBackgroundDrawable(new BitmapDrawable());
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et.getText().toString().trim().isEmpty() ? "0" : et.getText().toString().trim();
                if (Integer.parseInt(str) > Integer.parseInt(info.getOnhand())) {
                    ToastUtil.show("库存不足");
                    return;
                } else {
                    ProductAddShopCarUtils.getInstance().EditShopCar(add, info, etSelectNum, activity, listener, view, true, et.getText().toString().trim().isEmpty() ? "0" : et.getText().toString().trim());
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog hasNewVersionDia(Context context, final VersionBean bean) {
        final Dialog dialog = new Dialog(context, R.style.DefaultDialog);
        dialog.setContentView(R.layout.dia_has_new_version_layout);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.show();
        dialogWindow.setAttributes(lp);//此句代码一定要放在show()后面，否则不起作用
//        dialog.setCanceledOnTouchOutside(false);
        TextView tv_cancel = (TextView) dialogWindow.findViewById(R.id.btn_left);
        TextView tv_commit = (TextView) dialogWindow.findViewById(R.id.btn_right);
        TextView et = (TextView) dialogWindow.findViewById(R.id.tv_content);
        ImageView iv = (ImageView) dialogWindow.findViewById(R.id.iv_close);
        TextView tv_title = (TextView) dialogWindow.findViewById(R.id.tv_title);
        tv_title.setText(bean.getTitle());
        TextView tv_content = (TextView) dialogWindow.findViewById(R.id.tv_content);
        tv_content.setText(Html.fromHtml(bean.getMark()));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(bean.getAndroid()));
                v.getContext().startActivity(intent);
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static Dialog productActivityDia(Context context) {
        final Dialog dialog = new Dialog(context, R.style.DefaultDialog);
        dialog.setContentView(R.layout.dia_product_activity_layout);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.show();
        dialogWindow.setAttributes(lp);//此句代码一定要放在show()后面，否则不起作用
//        dialog.setCanceledOnTouchOutside(false);
        TextView tv = (TextView) dialogWindow.findViewById(R.id.tv_content);
        TextView tv1 = (TextView) dialogWindow.findViewById(R.id.tv_content1);
        TextView tv2 = (TextView) dialogWindow.findViewById(R.id.tv_content2);
        TextView tv3 = (TextView) dialogWindow.findViewById(R.id.tv_content3);
        ImageView iv = (ImageView) dialogWindow.findViewById(R.id.iv_close);
        Drawable drawable = context.getResources().getDrawable(R.mipmap.point1);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        tv.setText(TextInputUtils.getItalicText("   活动内容介绍…为回馈与感谢客户支持，活动期间，" +
                        "全场产品均参与秒杀活动，最高优惠1000元，时间有限，快来秒杀抢购吧..",
                drawable, 0, 1));

        tv1.setText(TextInputUtils.getItalicText("   价格优惠内容...",
                drawable, 0, 1));
        tv2.setText(TextInputUtils.getItalicText("   活动时间：2018.08.20-2018.09.30",
                drawable, 0, 1));
        tv3.setText(TextInputUtils.getItalicText("   活动内容介绍…为回馈与感谢客户支持，活动期间，" +
                        "全场产品均参与秒杀活动，最高优惠1000元，时间有限，快来秒杀抢购吧..",
                drawable, 0, 1));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

}
