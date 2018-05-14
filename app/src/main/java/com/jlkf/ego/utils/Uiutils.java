package com.jlkf.ego.utils;

import com.jlkf.ego.application.MyApplication;

/**
 * Created by Administrator on 2017/11/15.
 */
public class Uiutils {

    public static String getstring(int id){
      return   MyApplication.getmContext().getString(id);
    }
}
