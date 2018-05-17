package com.jlkf.ego.newpage.bean;

import java.util.List;

/**
 * Created by zcs on 2018/5/17.
 *
 * @describe:
 */

public class FilterProductBean {
    private String title;
    private List<AttriBean> mList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AttriBean> getList() {
        return mList;
    }

    public void setList(List<AttriBean> list) {
        mList = list;
    }

    public static class AttriBean {
        private boolean isSelect;
        private String name;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
