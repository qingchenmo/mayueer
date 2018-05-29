package com.jlkf.ego.newpage.bean;

/**
 * Created by zcs on 2018/5/23.
 */

public class BannerBean {

    /**
     * id : 3
     * imgurl : http://attach.bbs.miui.com/forum/201710/09/173639gbhndy4nwmaiicbx.jpg
     * type : 2
     * url : 1
     */

    private int id;
    private String imgurl;
    private int type;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
