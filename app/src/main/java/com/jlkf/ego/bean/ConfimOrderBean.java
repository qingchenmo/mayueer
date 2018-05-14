package com.jlkf.ego.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor zcs
 * @time 2017/8/29
 * @describe
 */

public class ConfimOrderBean extends MyBaseBean implements Parcelable {

    /**
     * mData : [{"spain":{"spainList":[{"A":0.25,"B":0.78,"name":2,"spainTotal":0.14684999999999998},{"A":0.25,"B":0.023,"name":3,"spainTotal":0.6521475},{"A":0.25,"B":0.555,"name":4,"spainTotal":0.29703749999999995},{"A":0.25,"B":0.325,"name":1,"spainTotal":0.45056250000000003}]},"total":0.89,"listData":[{"sId":8,"name":"车载充电器-VIP-CC03-1A iphone4-白色","price":0.89,"logo":"http://52.57.221.27:8898/8433772130057-1.jpg","itemcode":"1010005","quantity":10,"codebars":null,"brandname":null,"brandId":"001","uId":4}],"num":1,"brandData":{"Area":"spain","pp_minlogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503479607407&di=4924eb9e07d336ee480874b41b7a7ae6&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1604%2F18%2Fc19%2F20473373_1460987030407_mthumb.jpg","pp_maxlogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503479607407&di=4924eb9e07d336ee480874b41b7a7ae6&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1604%2F18%2Fc19%2F20473373_1460987030407_mthumb.jpg","brand_id":"001","Name":"ASM"}},{"spain":{"spainList":[{"A":0.25,"B":0.78,"name":2,"spainTotal":0.8002499999999998},{"A":0.25,"B":0.023,"name":3,"spainTotal":3.5538374999999998},{"A":0.25,"B":0.555,"name":4,"spainTotal":1.6186874999999996},{"A":0.25,"B":0.325,"name":1,"spainTotal":2.4553125}]},"total":4.85,"listData":[{"sId":32,"name":"荔枝纹-7寸-黑色（OME）","price":4.85,"logo":"http://52.57.221.27:8898/110066.jpg","itemcode":"1030162","quantity":1,"codebars":"8433772110066","brandname":"Digital OME","brandId":"002","uId":4}],"num":1,"brandData":{"Area":"spain","pp_minlogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503479607406&di=d15de86b14245725bba90ca337991ae8&imgtype=0&src=http%3A%2F%2Fimg17.3lian.com%2Fd%2Ffile%2F201702%2F09%2Fb4af0f5ec83f1aa4d693ee7476360d45.jpg","pp_maxlogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503479607406&di=d15de86b14245725bba90ca337991ae8&imgtype=0&src=http%3A%2F%2Fimg17.3lian.com%2Fd%2Ffile%2F201702%2F09%2Fb4af0f5ec83f1aa4d693ee7476360d45.jpg","brand_id":"002","Name":"Digital OME"}},{"spain":{"spainList":[{"A":0.25,"B":0.78,"name":2,"spainTotal":0.9470999999999998},{"A":0.25,"B":0.023,"name":3,"spainTotal":4.205984999999999},{"A":0.25,"B":0.555,"name":4,"spainTotal":1.9157249999999997},{"A":0.25,"B":0.325,"name":1,"spainTotal":2.905875}]},"total":5.739999999999999,"listData":[{"sId":48,"name":"车载充电器-VIP-CC03-1A iphone4-白色","price":0.89,"logo":"http://52.57.221.27:8898/8433772130057-1.jpg","itemcode":"1010005","quantity":10,"codebars":null,"brandname":"HOME","brandId":"003","uId":4},{"sId":47,"name":"荔枝纹-7寸-红色（OME）","price":4.85,"logo":"http://52.57.221.27:8898/110073.jpg","itemcode":"1030163","quantity":1,"codebars":null,"brandname":"HOME","brandId":"003","uId":4}],"num":2,"brandData":{"Area":"spain","pp_minlogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503479607405&di=a3d107fa67da66ebbb075ae9fd2b4c15&imgtype=0&src=http%3A%2F%2Fimg1.3lian.com%2F2015%2Fa1%2F78%2Fd%2F104.jpg","pp_maxlogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503479607405&di=a3d107fa67da66ebbb075ae9fd2b4c15&imgtype=0&src=http%3A%2F%2Fimg1.3lian.com%2F2015%2Fa1%2F78%2Fd%2F104.jpg","brand_id":"003","Name":"HOME"}}]
     * totalPage : null
     * totalRecord : null
     * total : null
     */

    private double total;
    private List<DataBean> data;

    public List<Double> getPirces() {
        return mPirces;
    }

    public void setPirces(List<Double> pirces) {
        mPirces = pirces;
    }

    private List<Double> mPirces;       // 金额


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * spain : {"spainList":[{"A":0.25,"B":0.78,"name":2,"spainTotal":0.14684999999999998},{"A":0.25,"B":0.023,"name":3,"spainTotal":0.6521475},{"A":0.25,"B":0.555,"name":4,"spainTotal":0.29703749999999995},{"A":0.25,"B":0.325,"name":1,"spainTotal":0.45056250000000003}]}
         * total : 0.89
         * listData : [{"sId":8,"name":"车载充电器-VIP-CC03-1A iphone4-白色","price":0.89,"logo":"http://52.57.221.27:8898/8433772130057-1.jpg","itemcode":"1010005","quantity":10,"codebars":null,"brandname":null,"brandId":"001","uId":4}]
         * num : 1
         * brandData : {"Area":"spain","pp_minlogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503479607407&di=4924eb9e07d336ee480874b41b7a7ae6&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1604%2F18%2Fc19%2F20473373_1460987030407_mthumb.jpg","pp_maxlogo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503479607407&di=4924eb9e07d336ee480874b41b7a7ae6&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1604%2F18%2Fc19%2F20473373_1460987030407_mthumb.jpg","brand_id":"001","Name":"ASM"}
         */

        private SpainBean spain;
        private double total;
        private int num;
        private BrandDataBean brandData;
        private String spainTotal;
        private String uFkfs;
        private double uZk1;
        private double uZk2;
        private double uZk3;
        private double uZk4;
        private String docdisc;
        private double totalexpns = 0.0;
        private String doctotal ;

        public String getFkfs() {
            return fkfs;
        }

        public void setFkfs(String fkfs) {
            this.fkfs = fkfs;
        }

        private String fkfs ;

        public String getuFkfs() {
            return uFkfs;
        }

        public void setuFkfs(String uFkfs) {
            this.uFkfs = uFkfs;
        }

        public double getuZk1() {
            return uZk1;
        }

        public void setuZk1(double uZk1) {
            this.uZk1 = uZk1;
        }

        public double getuZk2() {
            return uZk2;
        }

        public void setuZk2(double uZk2) {
            this.uZk2 = uZk2;
        }

        public double getuZk3() {
            return uZk3;
        }

        public void setuZk3(double uZk3) {
            this.uZk3 = uZk3;
        }

        public double getuZk4() {
            return uZk4;
        }

        public void setuZk4(double uZk4) {
            this.uZk4 = uZk4;
        }

        public String getDocdisc() {
            return docdisc;
        }

        public void setDocdisc(String docdisc) {
            this.docdisc = docdisc;
        }

        public double getTotalexpns() {
            return totalexpns;
        }

        public void setTotalexpns(double totalexpns) {
            this.totalexpns = totalexpns;
        }

        public String getDoctotal() {
            return doctotal;
        }

        public void setDoctotal(String doctotal) {
            this.doctotal = doctotal;
        }

        public String getSpainTotal() {
            return spainTotal;
        }

        public void setSpainTotal(String spainTotal) {
            this.spainTotal = spainTotal;
        }

        public double getFinallyTotal() {
            return finallyTotal;
        }

        public void setFinallyTotal(double finallyTotal) {
            this.finallyTotal = finallyTotal;
        }

        private double finallyTotal;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        private String msg = "还需0.0即可免费配送";

        public String getLy() {
            return ly;
        }

        public void setLy(String ly) {
            this.ly = ly;
        }

        private String ly;//留言

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        private int type;//付款方式

        public double getMytotal() {
            return mytotal;
        }

        public void setMytotal(double mytotal) {
            this.mytotal = mytotal;
        }

        private double mytotal;

        public AdressBean getAdressBean() {
            return mAdressBean;
        }

        public void setAdressBean(AdressBean adressBean) {
            mAdressBean = adressBean;
        }

        private AdressBean mAdressBean;
        private List<ListDataBean> listData;

        public SpainBean getSpain() {
            return spain;
        }

        public void setSpain(SpainBean spain) {
            this.spain = spain;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public BrandDataBean getBrandData() {
            return brandData;
        }

        public void setBrandData(BrandDataBean brandData) {
            this.brandData = brandData;
        }

        public List<ListDataBean> getListData() {
            return listData;
        }

        public void setListData(List<ListDataBean> listData) {
            this.listData = listData;
        }

        public static class SpainBean implements Parcelable {
            private List<SpainListBean> spainList;

            public List<SpainListBean> getSpainList() {
                return spainList;
            }

            public void setSpainList(List<SpainListBean> spainList) {
                this.spainList = spainList;
            }

            public static class SpainListBean implements Parcelable {
                /**
                 * A : 0.25
                 * B : 0.78
                 * name : 2
                 * spainTotal : 0.14684999999999998
                 */

                private double A;
                private double B;
                private double C;
                private double D;

                public double getC() {
                    return C;
                }

                public void setC(double c) {
                    C = c;
                }

                public double getD() {
                    return D;
                }

                public void setD(double d) {
                    D = d;
                }

                private String name;
                private double spainTotal;

                public double getA() {
                    return A;
                }

                public void setA(double A) {
                    this.A = A;
                }

                public double getB() {
                    return B;
                }

                public void setB(double B) {
                    this.B = B;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public double getSpainTotal() {
                    return spainTotal;
                }

                public void setSpainTotal(double spainTotal) {
                    this.spainTotal = spainTotal;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeDouble(this.A);
                    dest.writeDouble(this.B);
                    dest.writeDouble(this.C);
                    dest.writeDouble(this.D);
                    dest.writeString(this.name);
                    dest.writeDouble(this.spainTotal);
                }

                public SpainListBean() {
                }

                protected SpainListBean(Parcel in) {
                    this.A = in.readDouble();
                    this.B = in.readDouble();
                    this.C = in.readDouble();
                    this.D = in.readDouble();
                    this.name = in.readString();
                    this.spainTotal = in.readDouble();
                }

                public static final Creator<SpainListBean> CREATOR = new Creator<SpainListBean>() {
                    @Override
                    public SpainListBean createFromParcel(Parcel source) {
                        return new SpainListBean(source);
                    }

                    @Override
                    public SpainListBean[] newArray(int size) {
                        return new SpainListBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeTypedList(this.spainList);
            }

            public SpainBean() {
            }

            protected SpainBean(Parcel in) {
                this.spainList = in.createTypedArrayList(SpainListBean.CREATOR);
            }

            public static final Creator<SpainBean> CREATOR = new Creator<SpainBean>() {
                @Override
                public SpainBean createFromParcel(Parcel source) {
                    return new SpainBean(source);
                }

                @Override
                public SpainBean[] newArray(int size) {
                    return new SpainBean[size];
                }
            };
        }

        public static class BrandDataBean implements Parcelable {
            /**
             * Area : spain
             * pp_minlogo : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503479607407&di=4924eb9e07d336ee480874b41b7a7ae6&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1604%2F18%2Fc19%2F20473373_1460987030407_mthumb.jpg
             * pp_maxlogo : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503479607407&di=4924eb9e07d336ee480874b41b7a7ae6&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1604%2F18%2Fc19%2F20473373_1460987030407_mthumb.jpg
             * brand_id : 001
             * Name : ASM
             */

            private String Area;
            private String pp_minlogo;
            private String pp_maxlogo;
            private String brand_id;
            private String Name;

            public String getArea() {
                return Area;
            }

            public void setArea(String Area) {
                this.Area = Area;
            }

            public String getPp_minlogo() {
                return pp_minlogo;
            }

            public void setPp_minlogo(String pp_minlogo) {
                this.pp_minlogo = pp_minlogo;
            }

            public String getPp_maxlogo() {
                return pp_maxlogo;
            }

            public void setPp_maxlogo(String pp_maxlogo) {
                this.pp_maxlogo = pp_maxlogo;
            }

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.Area);
                dest.writeString(this.pp_minlogo);
                dest.writeString(this.pp_maxlogo);
                dest.writeString(this.brand_id);
                dest.writeString(this.Name);
            }

            public BrandDataBean() {
            }

            protected BrandDataBean(Parcel in) {
                this.Area = in.readString();
                this.pp_minlogo = in.readString();
                this.pp_maxlogo = in.readString();
                this.brand_id = in.readString();
                this.Name = in.readString();
            }

            public static final Creator<BrandDataBean> CREATOR = new Creator<BrandDataBean>() {
                @Override
                public BrandDataBean createFromParcel(Parcel source) {
                    return new BrandDataBean(source);
                }

                @Override
                public BrandDataBean[] newArray(int size) {
                    return new BrandDataBean[size];
                }
            };
        }

        public static class ListDataBean implements Parcelable {
            /**
             * sId : 8
             * name : 车载充电器-VIP-CC03-1A iphone4-白色
             * price : 0.89
             * logo : http://52.57.221.27:8898/8433772130057-1.jpg
             * itemcode : 1010005
             * quantity : 10
             * codebars : null
             * brandname : null
             * brandId : 001
             * uId : 4
             */

            private int sId;
            private String name;
            private double price;
            private String logo;
            private String itemcode;
            private int quantity;
            private String codebars;
            private String brandname;
            private String brandId;
            private int uId;

            public int getSId() {
                return sId;
            }

            public void setSId(int sId) {
                this.sId = sId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getItemcode() {
                return itemcode;
            }

            public void setItemcode(String itemcode) {
                this.itemcode = itemcode;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getCodebars() {
                return codebars;
            }

            public void setCodebars(String codebars) {
                this.codebars = codebars;
            }

            public String getBrandname() {
                return brandname;
            }

            public void setBrandname(String brandname) {
                this.brandname = brandname;
            }

            public String getBrandId() {
                return brandId;
            }

            public void setBrandId(String brandId) {
                this.brandId = brandId;
            }

            public int getUId() {
                return uId;
            }

            public void setUId(int uId) {
                this.uId = uId;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.sId);
                dest.writeString(this.name);
                dest.writeDouble(this.price);
                dest.writeString(this.logo);
                dest.writeString(this.itemcode);
                dest.writeInt(this.quantity);
                dest.writeString(this.codebars);
                dest.writeString(this.brandname);
                dest.writeString(this.brandId);
                dest.writeInt(this.uId);
            }

            public ListDataBean() {
            }

            protected ListDataBean(Parcel in) {
                this.sId = in.readInt();
                this.name = in.readString();
                this.price = in.readDouble();
                this.logo = in.readString();
                this.itemcode = in.readString();
                this.quantity = in.readInt();
                this.codebars = in.readString();
                this.brandname = in.readString();
                this.brandId = in.readString();
                this.uId = in.readInt();
            }

            public static final Creator<ListDataBean> CREATOR = new Creator<ListDataBean>() {
                @Override
                public ListDataBean createFromParcel(Parcel source) {
                    return new ListDataBean(source);
                }

                @Override
                public ListDataBean[] newArray(int size) {
                    return new ListDataBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.spain, flags);
            dest.writeDouble(this.total);
            dest.writeInt(this.num);
            dest.writeParcelable(this.brandData, flags);
            dest.writeList(this.listData);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.spain = in.readParcelable(SpainBean.class.getClassLoader());
            this.total = in.readDouble();
            this.num = in.readInt();
            this.brandData = in.readParcelable(BrandDataBean.class.getClassLoader());
            this.listData = new ArrayList<ListDataBean>();
            in.readList(this.listData, ListDataBean.class.getClassLoader());
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.total);
        dest.writeList(this.data);
    }

    public ConfimOrderBean() {
    }

    protected ConfimOrderBean(Parcel in) {
        this.total = in.readDouble();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ConfimOrderBean> CREATOR = new Parcelable.Creator<ConfimOrderBean>() {
        @Override
        public ConfimOrderBean createFromParcel(Parcel source) {
            return new ConfimOrderBean(source);
        }

        @Override
        public ConfimOrderBean[] newArray(int size) {
            return new ConfimOrderBean[size];
        }
    };
}
