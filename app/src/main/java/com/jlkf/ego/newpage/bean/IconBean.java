package com.jlkf.ego.newpage.bean;

import android.text.TextUtils;

/**
 * Created by Dell on 2018/6/22.
 */

public class IconBean {

    /**
     * id : 1
     * name : null
     * minlogo : http://52.57.221.27:8080//images//adal//201803040864483.png
     */

    private String id;
    private String name;
    private Object minlogo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        if (TextUtils.isEmpty(name)) return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getMinlogo() {
        return minlogo;
    }

    public void setMinlogo(Object minlogo) {
        this.minlogo = minlogo;
    }
}
