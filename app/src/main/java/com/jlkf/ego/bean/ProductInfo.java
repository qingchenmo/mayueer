package com.jlkf.ego.bean;

/**
 * Created by zcs on 2017/7/11.
 * 商品信息实体类
 */
public class ProductInfo {
    private String productName;
    private int img;
    private String price;
    private String productStock;
    private boolean isChecked;
    private boolean havaPackage;
    private boolean isLargePackage;
    private int selectNum;
    private boolean isEditType;

    private int largePackage, smallPackage;

    public boolean isEditType() {
        return isEditType;
    }

    public void setEditType(boolean editType) {
        isEditType = editType;
    }

    public int getLargePackage() {
        return largePackage;
    }

    public void setLargePackage(int largePackage) {
        this.largePackage = largePackage;
    }

    public int getSmallPackage() {
        return smallPackage;
    }

    public void setSmallPackage(int smallPackage) {
        this.smallPackage = smallPackage;
    }

    public int getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(int selectNum) {
        this.selectNum = selectNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductStock() {
        return productStock;
    }

    public void setProductStock(String productStock) {
        this.productStock = productStock;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isHavaPackage() {
        return havaPackage;
    }

    public void setHavaPackage(boolean havaPackage) {
        this.havaPackage = havaPackage;
    }

    public boolean isLargePackage() {
        return isLargePackage;
    }

    public void setLargePackage(boolean largePackage) {
        isLargePackage = largePackage;
    }
}
