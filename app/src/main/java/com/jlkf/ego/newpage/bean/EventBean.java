package com.jlkf.ego.newpage.bean;

/**
 * Created by zcs on 2018/6/4.
 */

public class EventBean {

    /**
     * atname : 618秒杀
     * type : 1
     * start : 1527001544
     * end : 1527649871
     * Price : 1.36
     * mrak : 活动介绍活动介绍活动介绍
     * content : 活动内容活动内容
     */

    private String atname;
    private int type;
    private String start;
    private String end;
    private String Price;
    private String mark;
    private String content;

    public String getAtname() {
        return atname;
    }

    public void setAtname(String atname) {
        this.atname = atname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
