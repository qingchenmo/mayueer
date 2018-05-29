package com.jlkf.ego.newpage.bean;

/**
 * Created by zcs on 2018/5/25.
 */

public class PersonActivityListBean {

    /**
     * name : 618特价
     * banner : http://c.hiphotos.baidu.com/image/pic/item/0df3d7ca7bcb0a46f8c4938f6763f6246a60afa5.jpg
     * attype : 1
     */

    private String name;
    private String banner;
    private int attype;
    private String at_id;

    public String getAt_id() {
        return at_id;
    }

    public void setAt_id(String at_id) {
        this.at_id = at_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getAttype() {
        return attype;
    }

    public void setAttype(int attype) {
        this.attype = attype;
    }
}
