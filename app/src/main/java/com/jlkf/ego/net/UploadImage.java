package com.jlkf.ego.net;

import android.app.Activity;

import com.jlkf.ego.bean.StringBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29 0029.
 * <p>
 * 上传头像
 */

public class UploadImage {

    public static void upload(Activity activity, ArrayList<String> files, HttpUtil.OnCallBack onCallBack) {
        List<File> files1 = new ArrayList<>();
        for (int i=0;i<files.size();i++){
            File file = new File(files.get(i));
            files1.add(file);
        }
        HttpUtil.getInstacne(activity).postFile(Urls.pcphotoImg, StringBean.class, files1, onCallBack);
    }


    public static void upload1(Activity activity, ArrayList<String> files, HttpUtil.OnCallBack onCallBack) {
        List<File> files1 = new ArrayList<>();
        for (int i=0;i<files.size();i++){
            File file = new File(files.get(i));
            files1.add(file);
        }
        HttpUtil.getInstacne(activity).postFile3(Urls.pcphotoImg, StringBean.class, files1, onCallBack);
    }
}
