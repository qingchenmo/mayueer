package com.jlkf.ego.newpage.bean;

import com.jlkf.ego.bean.ProductListBean;

import java.util.List;

/**
 * Created by zcs on 2018/5/29.
 */

public class EventOneTypeBean {

    /**
     * name : 619特价
     * attype : 2
     * banners : [{"banner":"http://c.hiphotos.baidu.com/image/pic/item/0df3d7ca7bcb0a46f8c4938f6763f6246a60afa5.jpg","bvalue":"1"},{"banner":"http://c.hiphotos.baidu.com/image/pic/item/0df3d7ca7bcb0a46f8c4938f6763f6246a60afa5.jpg","bvalue":"1"}]
     * atpics : [{"atpic":"http://c.hiphotos.baidu.com/image/pic/item/0df3d7ca7bcb0a46f8c4938f6763f6246a60afa5.jpg","oitmlist":[{"oitm_id":"1","Area":"italy","ItemCode":"1010052","CodeBars":"8433772142302","ItemName":"家充-EKA-Q42 平板电脑DC2.5","ItmsGrpCod":"101","Spec":null,"MinLevel":"0.000000","OnHand":"0","U_COLORS":null,"U_XYMS":null,"U_U_B":"10","U_U_X":"100","U_PP":"3","PicturName":"http://52.29.220.117:8898/8433772142302-1.jpg","U_isSale":"N","CreateDate":"2017-03-08 00:00:00","UpdateDate":"2018-02-24 11:19:52","Price":"1.360000","pp_name":"1111"},{"oitm_id":"2","Area":"italy","ItemCode":"1010053","CodeBars":"8433772142357","ItemName":"家充-EKA-Q34 ipad mini iphone 5","ItmsGrpCod":"101","Spec":null,"MinLevel":"0.000000","OnHand":"0","U_COLORS":null,"U_XYMS":null,"U_U_B":"10","U_U_X":"100","U_PP":"3","PicturName":"http://52.29.220.117:8898/8433772142357-1.jpg","U_isSale":"N","CreateDate":"2017-03-08 00:00:00","UpdateDate":"2018-02-24 11:19:52","Price":"1.560000","pp_name":"1111"}]},{"atpic":"http://c.hiphotos.baidu.com/image/pic/item/0df3d7ca7bcb0a46f8c4938f6763f6246a60afa5.jpg","oitmlist":[{"oitm_id":"1","Area":"italy","ItemCode":"1010052","CodeBars":"8433772142302","ItemName":"家充-EKA-Q42 平板电脑DC2.5","ItmsGrpCod":"101","Spec":null,"MinLevel":"0.000000","OnHand":"0","U_COLORS":null,"U_XYMS":null,"U_U_B":"10","U_U_X":"100","U_PP":"3","PicturName":"http://52.29.220.117:8898/8433772142302-1.jpg","U_isSale":"N","CreateDate":"2017-03-08 00:00:00","UpdateDate":"2018-02-24 11:19:52","Price":"1.360000","pp_name":"1111"},{"oitm_id":"2","Area":"italy","ItemCode":"1010053","CodeBars":"8433772142357","ItemName":"家充-EKA-Q34 ipad mini iphone 5","ItmsGrpCod":"101","Spec":null,"MinLevel":"0.000000","OnHand":"0","U_COLORS":null,"U_XYMS":null,"U_U_B":"10","U_U_X":"100","U_PP":"3","PicturName":"http://52.29.220.117:8898/8433772142357-1.jpg","U_isSale":"N","CreateDate":"2017-03-08 00:00:00","UpdateDate":"2018-02-24 11:19:52","Price":"1.560000","pp_name":"1111"}]}]
     */

    private String name;
    private String attype;
    private List<BannersBean> banners;
    private List<AtpicsBean> atpics;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttype() {
        return attype;
    }

    public void setAttype(String attype) {
        this.attype = attype;
    }

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public List<AtpicsBean> getAtpics() {
        return atpics;
    }

    public void setAtpics(List<AtpicsBean> atpics) {
        this.atpics = atpics;
    }

    public static class BannersBean {
        /**
         * banner : http://c.hiphotos.baidu.com/image/pic/item/0df3d7ca7bcb0a46f8c4938f6763f6246a60afa5.jpg
         * bvalue : 1
         */

        private String banner;
        private String bvalue;

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getBvalue() {
            return bvalue;
        }

        public void setBvalue(String bvalue) {
            this.bvalue = bvalue;
        }
    }

    public static class AtpicsBean {
        /**
         * atpic : http://c.hiphotos.baidu.com/image/pic/item/0df3d7ca7bcb0a46f8c4938f6763f6246a60afa5.jpg
         * oitmlist : [{"oitm_id":"1","Area":"italy","ItemCode":"1010052","CodeBars":"8433772142302","ItemName":"家充-EKA-Q42 平板电脑DC2.5","ItmsGrpCod":"101","Spec":null,"MinLevel":"0.000000","OnHand":"0","U_COLORS":null,"U_XYMS":null,"U_U_B":"10","U_U_X":"100","U_PP":"3","PicturName":"http://52.29.220.117:8898/8433772142302-1.jpg","U_isSale":"N","CreateDate":"2017-03-08 00:00:00","UpdateDate":"2018-02-24 11:19:52","Price":"1.360000","pp_name":"1111"},{"oitm_id":"2","Area":"italy","ItemCode":"1010053","CodeBars":"8433772142357","ItemName":"家充-EKA-Q34 ipad mini iphone 5","ItmsGrpCod":"101","Spec":null,"MinLevel":"0.000000","OnHand":"0","U_COLORS":null,"U_XYMS":null,"U_U_B":"10","U_U_X":"100","U_PP":"3","PicturName":"http://52.29.220.117:8898/8433772142357-1.jpg","U_isSale":"N","CreateDate":"2017-03-08 00:00:00","UpdateDate":"2018-02-24 11:19:52","Price":"1.560000","pp_name":"1111"}]
         */

        private String atpic;
        private List<ProductListBean.DataBean> oitmlist;
        private String atpicv;

        public String getAtpicv() {
            return atpicv;
        }

        public void setAtpicv(String atpicv) {
            this.atpicv = atpicv;
        }

        public String getAtpic() {
            return atpic;
        }

        public void setAtpic(String atpic) {
            this.atpic = atpic;
        }

        public List<ProductListBean.DataBean> getOitmlist() {
            return oitmlist;
        }

        public void setOitmlist(List<ProductListBean.DataBean> oitmlist) {
            this.oitmlist = oitmlist;
        }

        public static class OitmlistBean {
            /**
             * oitm_id : 1
             * Area : italy
             * ItemCode : 1010052
             * CodeBars : 8433772142302
             * ItemName : 家充-EKA-Q42 平板电脑DC2.5
             * ItmsGrpCod : 101
             * Spec : null
             * MinLevel : 0.000000
             * OnHand : 0
             * U_COLORS : null
             * U_XYMS : null
             * U_U_B : 10
             * U_U_X : 100
             * U_PP : 3
             * PicturName : http://52.29.220.117:8898/8433772142302-1.jpg
             * U_isSale : N
             * CreateDate : 2017-03-08 00:00:00
             * UpdateDate : 2018-02-24 11:19:52
             * Price : 1.360000
             * pp_name : 1111
             */

            private String oitm_id;
            private String Area;
            private String ItemCode;
            private String CodeBars;
            private String ItemName;
            private String ItmsGrpCod;
            private Object Spec;
            private String MinLevel;
            private String OnHand;
            private Object U_COLORS;
            private Object U_XYMS;
            private String U_U_B;
            private String U_U_X;
            private String U_PP;
            private String PicturName;
            private String U_isSale;
            private String CreateDate;
            private String UpdateDate;
            private String Price;
            private String pp_name;

            public String getOitm_id() {
                return oitm_id;
            }

            public void setOitm_id(String oitm_id) {
                this.oitm_id = oitm_id;
            }

            public String getArea() {
                return Area;
            }

            public void setArea(String Area) {
                this.Area = Area;
            }

            public String getItemCode() {
                return ItemCode;
            }

            public void setItemCode(String ItemCode) {
                this.ItemCode = ItemCode;
            }

            public String getCodeBars() {
                return CodeBars;
            }

            public void setCodeBars(String CodeBars) {
                this.CodeBars = CodeBars;
            }

            public String getItemName() {
                return ItemName;
            }

            public void setItemName(String ItemName) {
                this.ItemName = ItemName;
            }

            public String getItmsGrpCod() {
                return ItmsGrpCod;
            }

            public void setItmsGrpCod(String ItmsGrpCod) {
                this.ItmsGrpCod = ItmsGrpCod;
            }

            public Object getSpec() {
                return Spec;
            }

            public void setSpec(Object Spec) {
                this.Spec = Spec;
            }

            public String getMinLevel() {
                return MinLevel;
            }

            public void setMinLevel(String MinLevel) {
                this.MinLevel = MinLevel;
            }

            public String getOnHand() {
                return OnHand;
            }

            public void setOnHand(String OnHand) {
                this.OnHand = OnHand;
            }

            public Object getU_COLORS() {
                return U_COLORS;
            }

            public void setU_COLORS(Object U_COLORS) {
                this.U_COLORS = U_COLORS;
            }

            public Object getU_XYMS() {
                return U_XYMS;
            }

            public void setU_XYMS(Object U_XYMS) {
                this.U_XYMS = U_XYMS;
            }

            public String getU_U_B() {
                return U_U_B;
            }

            public void setU_U_B(String U_U_B) {
                this.U_U_B = U_U_B;
            }

            public String getU_U_X() {
                return U_U_X;
            }

            public void setU_U_X(String U_U_X) {
                this.U_U_X = U_U_X;
            }

            public String getU_PP() {
                return U_PP;
            }

            public void setU_PP(String U_PP) {
                this.U_PP = U_PP;
            }

            public String getPicturName() {
                return PicturName;
            }

            public void setPicturName(String PicturName) {
                this.PicturName = PicturName;
            }

            public String getU_isSale() {
                return U_isSale;
            }

            public void setU_isSale(String U_isSale) {
                this.U_isSale = U_isSale;
            }

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String CreateDate) {
                this.CreateDate = CreateDate;
            }

            public String getUpdateDate() {
                return UpdateDate;
            }

            public void setUpdateDate(String UpdateDate) {
                this.UpdateDate = UpdateDate;
            }

            public String getPrice() {
                return Price;
            }

            public void setPrice(String Price) {
                this.Price = Price;
            }

            public String getPp_name() {
                return pp_name;
            }

            public void setPp_name(String pp_name) {
                this.pp_name = pp_name;
            }
            private boolean isChecked;
            private int selectNum;


            private String optime;
            private String oitmView;


            private boolean isHavaPackage;
            private boolean isLargePackage;
            private boolean havaPackage;
            private boolean isSmallPackage;

            public boolean isSmallPackage() {
                return isSmallPackage;
            }

            public void setSmallPackage(boolean smallPackage) {
                isSmallPackage = smallPackage;
            }

            public int getSelectNum() {
                return selectNum;
            }

            public void setSelectNum(int selectNum) {
                this.selectNum = selectNum;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public boolean isLargePackage() {
                return isLargePackage;
            }

            public void setLargePackage(boolean largePackage) {
                isLargePackage = largePackage;
            }


            public boolean isHavaPackage() {
                return havaPackage;
            }

            public void setHavaPackage(boolean havaPackage) {
                this.havaPackage = havaPackage;
            }
        }

    }
}
