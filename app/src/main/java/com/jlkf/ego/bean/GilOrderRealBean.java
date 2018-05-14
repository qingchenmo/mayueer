package com.jlkf.ego.bean;

import java.util.List;

/**
 * @autor zcs
 * @time 2017/9/7
 * @describe
 */

public class GilOrderRealBean extends MyBaseBean {

    /**
     * mData : [{"invoiceId":null,"area":"spain","sapdocnum":6186,"appdocnumber":201708041938759,"cardcode":"ES0101460","cardname":"SKY HE 2(969350789)","docduedate":1501171200000,"uSumqty":300,"totalexpns":null,"doctotal":925,"address1":"RUA CONSELHEIRO EMIDIO NAVARRO 35\r1950-063 LISBOA\rPORTUGAL","address":null,"itemcode":1110009,"codebars":8433772442297,"uXyms":"MINI FANS BLUE MODEL: FS-02,USB","quantity":100,"picturname":"442297.jpg","linetotal":295},{"invoiceId":null,"area":"spain","sapdocnum":6186,"appdocnumber":201708041938759,"cardcode":"ES0101460","cardname":"SKY HE 2(969350789)","docduedate":1501171200000,"uSumqty":300,"totalexpns":null,"doctotal":925,"address1":"RUA CONSELHEIRO EMIDIO NAVARRO 35\r1950-063 LISBOA\rPORTUGAL","address":null,"itemcode":1110002,"codebars":8433772442228,"uXyms":"MINI FANS RED MODEL: FS-02,USB","quantity":100,"picturname":"442228.jpg","linetotal":295},{"invoiceId":null,"area":"spain","sapdocnum":6186,"appdocnumber":201708041938759,"cardcode":"ES0101460","cardname":"SKY HE 2(969350789)","docduedate":1501171200000,"uSumqty":300,"totalexpns":null,"doctotal":925,"address1":"RUA CONSELHEIRO EMIDIO NAVARRO 35\r1950-063 LISBOA\rPORTUGAL","address":null,"itemcode":1110001,"codebars":8433772442211,"uXyms":"MINI FANS BLUE MODEL: FS-01,5V,LED","quantity":50,"picturname":"442211.jpg","linetotal":249.5},{"invoiceId":null,"area":"spain","sapdocnum":6186,"appdocnumber":201708041938759,"cardcode":"ES0101460","cardname":"SKY HE 2(969350789)","docduedate":1501171200000,"uSumqty":300,"totalexpns":null,"doctotal":925,"address1":"RUA CONSELHEIRO EMIDIO NAVARRO 35\r1950-063 LISBOA\rPORTUGAL","address":null,"itemcode":1110008,"codebars":8433772442280,"uXyms":"MINI FANS RED MODEL: FS-01,5V,LED","quantity":50,"picturname":"4422801.jpg","linetotal":249.5}]
     * totalPage : null
     * totalRecord : null
     * total : null
     */
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * invoiceId : null
         * area : spain
         * sapdocnum : 6186
         * appdocnumber : 201708041938759
         * cardcode : ES0101460
         * cardname : SKY HE 2(969350789)
         * docduedate : 1501171200000
         * uSumqty : 300
         * totalexpns : null
         * doctotal : 925
         * address1 : RUA CONSELHEIRO EMIDIO NAVARRO 351950-063 LISBOAPORTUGAL
         * address : null
         * itemcode : 1110009
         * codebars : 8433772442297
         * uXyms : MINI FANS BLUE MODEL: FS-02,USB
         * quantity : 100.0
         * picturname : 442297.jpg
         * linetotal : 295.0
         */

        private String invoiceId;
        private String area;
        private int sapdocnum;
        private String appdocnumber;
        private String cardcode;
        private String cardname;
        private long docduedate;
        private int uSumqty;
        private String totalexpns;
        private int doctotal;
        private String address1;
        private String address;
        private String itemcode;
        private long codebars;
        private String uXyms;
        private int quantity;
        private String picturname;
        private String linetotal;

        public String getU_FKFS() {
            return u_FKFS;
        }

        public void setU_FKFS(String u_FKFS) {
            this.u_FKFS = u_FKFS;
        }

        private String u_FKFS;

        public String getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(String invoiceId) {
            this.invoiceId = invoiceId;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getSapdocnum() {
            return sapdocnum;
        }

        public void setSapdocnum(int sapdocnum) {
            this.sapdocnum = sapdocnum;
        }

        public String getAppdocnumber() {
            return appdocnumber;
        }

        public void setAppdocnumber(String appdocnumber) {
            this.appdocnumber = appdocnumber;
        }

        public String getCardcode() {
            return cardcode;
        }

        public void setCardcode(String cardcode) {
            this.cardcode = cardcode;
        }

        public String getCardname() {
            return cardname;
        }

        public void setCardname(String cardname) {
            this.cardname = cardname;
        }

        public long getDocduedate() {
            return docduedate;
        }

        public void setDocduedate(long docduedate) {
            this.docduedate = docduedate;
        }

        public int getUSumqty() {
            return uSumqty;
        }

        public void setUSumqty(int uSumqty) {
            this.uSumqty = uSumqty;
        }

        public String getTotalexpns() {
            return totalexpns;
        }

        public void setTotalexpns(String totalexpns) {
            this.totalexpns = totalexpns;
        }

        public int getDoctotal() {
            return doctotal;
        }

        public void setDoctotal(int doctotal) {
            this.doctotal = doctotal;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getItemcode() {
            return itemcode;
        }

        public void setItemcode(String itemcode) {
            this.itemcode = itemcode;
        }

        public long getCodebars() {
            return codebars;
        }

        public void setCodebars(long codebars) {
            this.codebars = codebars;
        }

        public String getUXyms() {
            return uXyms;
        }

        public void setUXyms(String uXyms) {
            this.uXyms = uXyms;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getPicturname() {
            return picturname;
        }

        public void setPicturname(String picturname) {
            this.picturname = picturname;
        }

        public String getLinetotal() {
            return linetotal;
        }

        public void setLinetotal(String linetotal) {
            this.linetotal = linetotal;
        }
    }
}
