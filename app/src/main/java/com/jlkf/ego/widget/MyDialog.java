package com.jlkf.ego.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jlkf.ego.R;

import static android.view.LayoutInflater.*;

/**
 *  自定义对话框
 * @author dcg
 *
 */

public class MyDialog {
    public static Dialog showTelPhoneDialog(Context context, String title, String Message,
                                            String confirmText, String cancleText,
                                            View.OnClickListener confirmListener, View.OnClickListener cancelListener){
        Dialog dialog = new Dialog(context, R.style.mydialogstyle);
        View content = from(context).inflate(R.layout.layout_telphone_dialog, null);

        TextView tvTitle = (TextView)content.findViewById(R.id.tv_title);
        tvTitle.setText(title);

        TextView tvMessage = (TextView)content.findViewById(R.id.tv_message);
        tvMessage.setText(Message);

        Button btn_confirm = (Button)content.findViewById(R.id.btn_ok);
        btn_confirm.setText(confirmText);
        btn_confirm.setOnClickListener(confirmListener);

        Button btn_Cancel = (Button)content.findViewById(R.id.btn_cancel);
        btn_Cancel.setText(cancleText);
        btn_Cancel.setOnClickListener(cancelListener);

        dialog.setContentView(content);
        dialog.show();
        return dialog;
    }
}
