package com.jlkf.ego.bean;

import java.util.List;

/**
 * 管理商品的组
 */

public class ShoupommodityCList {
    //商品类型
   private boolean isSelforagency;
    //孩子
    private List<ShoupommodityC> groups;
    public List<ShoupommodityC> getGroups() {
        return groups;
    }
    public void setGroups(List<ShoupommodityC> groups) {
        this.groups = groups;
    }
    public ShoupommodityCList(
            Boolean isSelforagency, List<ShoupommodityC> groups) {
        this.isSelforagency = isSelforagency;
        this.groups = groups;
    }
    public ShoupommodityCList(){
    }
   public   boolean getisSelforagency(){
        return isSelforagency;
    }
    public boolean isSelforagency() {
        return isSelforagency;
    }

    public void setSelforagency(boolean selforagency) {
        isSelforagency = selforagency;
    }
}
