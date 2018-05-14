package com.jlkf.ego.bean;

/**
 * 购物车商品Model
 */
public class ShoupommodityC {
    //商品名称
    private String ShoupName;
    //商品编码
    private String ShoupCode;
    //商品价格
    private double  pirce;
    //是自营还是代理
    private boolean isSelforagency;
    //商品图片
    private int ShoupImage;
     //商品数量
    private  int num;
    //商品总价
    private  int  Countpirce;
    //是否是選擇得
    private  boolean isSelect;
    public boolean isSelect() {
        return isSelect;
    }
    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getShoupName() {
        return ShoupName;
    }
    public void setShoupName(String shoupName) {
        ShoupName = shoupName;
    }
    public String getShoupCode() {
        return ShoupCode;
    }
    public void setShoupCode(String shoupCode) {
        ShoupCode = shoupCode;
    }
    public double getPirce() {
        return pirce;
    }
    public void setPirce(long pirce) {
        this.pirce = pirce;
    }
    public boolean isSelforagency() {
        return isSelforagency;
    }
    public void setSelforagency(boolean selforagency) {
        isSelforagency = selforagency;
    }
    public int getShoupImage() {
        return ShoupImage;
    }
    public void setShoupImage(int shoupImage) {
        ShoupImage = shoupImage;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public int getCountpirce() {

        return Countpirce;
    }
    public void setCountpirce(int countpirce) {

        Countpirce = countpirce;
    }
    public ShoupommodityC(String shoupName, String shoupCode, double pirce, boolean isSelforagency, int shoupImage, int num, int countpirce) {
        ShoupName = shoupName;
        ShoupCode = shoupCode;
        this.pirce = pirce;
        this.isSelforagency = isSelforagency;
        this.ShoupImage = shoupImage;
        this.num = num;
        Countpirce = countpirce;
    }
    public ShoupommodityC() {
    }
}
