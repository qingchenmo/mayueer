package com.jlkf.ego.newpage.bean;

/**
 * Created by zcs on 2018/6/3.
 */

public class GradeInfoBean {

    /**
     * rankname : 青铜
     * img : 180.76.168.177/uploads/2018/05/16/16ab687a848569b27b395c1c42fcf730.jpg
     */

    private String rankname;
    private String img;
    /**
     * point : 1
     * nextpoint : 99
     * nextrankpoint : 100
     */

    private int point;
    private int nextpoint;
    private int nextrankpoint;

    public String getRankname() {
        return rankname;
    }

    public void setRankname(String rankname) {
        this.rankname = rankname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getNextpoint() {
        return nextpoint;
    }

    public void setNextpoint(int nextpoint) {
        this.nextpoint = nextpoint;
    }

    public int getNextrankpoint() {
        return nextrankpoint;
    }

    public void setNextrankpoint(int nextrankpoint) {
        this.nextrankpoint = nextrankpoint;
    }
}
