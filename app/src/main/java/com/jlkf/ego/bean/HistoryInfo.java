package com.jlkf.ego.bean;

import java.io.Serializable;

/**
 * 历史商品名 实体类
 * @author  邓超桂
 * @E-mail  305436934@qq.com
 *@创建时间 2017年7月11日 下午6:10:14
 */

public class HistoryInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String time;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
