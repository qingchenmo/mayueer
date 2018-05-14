package com.jlkf.ego.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 *
 * 品牌列表
 */

public class BrandListBean {


    /**
     * ppId : 4
     * ppNo :
     * ppName :
     * ppMinlogo : http://52.28.215.17:8080//images//adal//201712110635952.png
     * ppMaxlogo : http://52.28.215.17:8080//images//adal//201712110624669.png
     * area : null
     * code : spain
     * name : KINGSTON
     * ppContext :
     * ushopcart : null
     */

    private int ppId;
    private String ppNo;
    private String ppName;
    private String ppMinlogo;
    private String ppMaxlogo;
    private Object area;
    private String code;
    private String name;
    private String ppContext;
    private Object ushopcart;

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    private String firstWord;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    private boolean select;

    public static List<BrandListBean> arrayBrandListBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<BrandListBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getPpId() {
        return ppId;
    }

    public void setPpId(int ppId) {
        this.ppId = ppId;
    }

    public String getPpNo() {
        return ppNo;
    }

    public void setPpNo(String ppNo) {
        this.ppNo = ppNo;
    }

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
    }

    public String getPpMinlogo() {
        return ppMinlogo;
    }

    public void setPpMinlogo(String ppMinlogo) {
        this.ppMinlogo = ppMinlogo;
    }

    public String getPpMaxlogo() {
        return ppMaxlogo;
    }

    public void setPpMaxlogo(String ppMaxlogo) {
        this.ppMaxlogo = ppMaxlogo;
    }

    public Object getArea() {
        return area;
    }

    public void setArea(Object area) {
        this.area = area;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPpContext() {
        return ppContext;
    }

    public void setPpContext(String ppContext) {
        this.ppContext = ppContext;
    }

    public Object getUshopcart() {
        return ushopcart;
    }

    public void setUshopcart(Object ushopcart) {
        this.ushopcart = ushopcart;
    }
}
