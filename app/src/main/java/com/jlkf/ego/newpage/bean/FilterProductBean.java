package com.jlkf.ego.newpage.bean;

import java.util.List;

/**
 * Created by zcs on 2018/5/17.
 *
 * @describe:
 */

public class FilterProductBean {

    /**
     * name : 规格
     * value : ["2.5mm","3.5mm"]
     */

    private String name;
    private List<String> value;
    private int selectIndex = -1;

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
